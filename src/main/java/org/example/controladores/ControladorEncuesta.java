package org.example.controladores;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.Javalin;
import org.example.clase.Criptografia;
import org.example.clase.encuesta;
import org.example.clase.registro;
import org.example.clase.usuario;
import org.example.servicios.ServiciosEncuesta;
import org.example.servicios.ServiciosUsuarios;
import org.example.util.BaseControler;
import org.json.JSONArray;


import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorEncuesta extends BaseControler {
    public ControladorEncuesta(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/encuesta", () -> {
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Encuesta");
                    ctx.render("publico/encuesta.html", modelo);
                });

                post(ctx -> {
                    System.out.println("ENTRO A POST");
                    usuario usuario = Criptografia.desencriptado(ctx.cookie("Logusuario"));
                    encuesta survey = new encuesta(
                            ctx.formParam("nombre"),
                            ctx.formParam("sector"),
                            ctx.formParam("nivel_escolar"),
                            usuario,
                            Float.parseFloat(ctx.formParam("latitud")),
                            Float.parseFloat(ctx.formParam("longitud"))
                    );
                    ServiciosEncuesta.getInstance().crear(survey);
                    ctx.redirect("/encuesta");
                });
            });
        });
        app.ws("/encuesta", wsConfig -> {
            wsConfig.onConnect(ctx -> {
                System.out.println("Conectado: " + ctx.getSessionId());
            });

            wsConfig.onMessage(ctx -> {
                String mensaje = ctx.message();
                System.out.println("Mensaje: " + mensaje);

                JsonElement jelement = JsonParser.parseString(mensaje);
                if (jelement.isJsonArray()) {
                    JsonArray jsonArray = jelement.getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        JsonObject jobject = element.getAsJsonObject();
                        String nombre = jobject.get("nombre").getAsString();
                        String sector = jobject.get("sector").getAsString();
                        String nivel_escolar = jobject.get("nivel_escolar").getAsString();
                        String usuario = jobject.get("usuario").getAsString();
                        float latitud = jobject.get("latitud").getAsFloat();
                        float longitud = jobject.get("longitud").getAsFloat();

                        usuario user = ServiciosUsuarios.getInstance().find(usuario);
                        encuesta survey = new encuesta(nombre, sector, nivel_escolar, user, latitud, longitud);
                        //registro reg = new registro(survey,user);

                        try {
                            ServiciosEncuesta.getInstance().crear(survey);
                           // ServiciosEncuesta.getInstance().crear(reg);
                            System.out.println("Encuesta creada");
                        } catch (Exception e) {
                            System.out.println("Error al crear encuesta");
                        }


                    }
                }

            });

        });
    }
}
