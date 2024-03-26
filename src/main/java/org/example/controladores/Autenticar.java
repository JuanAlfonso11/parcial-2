package org.example.controladores;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import io.javalin.Javalin;
import org.example.clase.Criptografia;
import org.example.clase.usuario;
import org.example.servicios.ServiciosUsuarios;
import org.example.util.BaseControler;

import java.util.HashMap;
import java.util.Map;

public class Autenticar extends BaseControler {
    public Autenticar(Javalin app) {
        super(app);
    }


    public void aplicarRutas(){
        app.post("/autenticar",ctx->{
            String nombreUsuario = ctx.formParam("username");
            String password = ctx.formParam("password");
            usuario user = ServiciosUsuarios.getInstance().verificarCredenciales(nombreUsuario,password);
            if(user == null) {
                ctx.redirect("/login");
            }
            else{
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("mostrarEtiqueta",true);
                modelo.put("mostrarRedirect",true);
                modelo.put("location","/index.html");
                modelo.put("usertoken", Criptografia.encriptado(user));
                System.out.println("Encriptado:"+Criptografia.encriptado(user));
                ctx.render("/publico/cargar.html",modelo);
            }
        });
        app.post("/verify",ctx->{
            String requestBody =ctx.body();
            JsonObject jsonDataToSend = JsonParser.parseString(requestBody).getAsJsonObject();
            String dataValue = jsonDataToSend.getAsJsonObject("dataToSend").get("data").getAsString();
            System.out.println("Received data: "+dataValue);
            usuario temp = Criptografia.desencriptado(dataValue);
            if(temp != null){
                ctx.result("true");
            }else{
                ctx.result("false");
            }
        });
    }
}
