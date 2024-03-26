package org.example.controladores;

import io.javalin.Javalin;
import org.example.clase.usuario;
import org.example.servicios.ServiciosUsuarios;
import org.example.util.BaseControler;
import org.example.util.RolesApp;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ControladorUsuarios extends BaseControler {
    public ControladorUsuarios(Javalin app) {
        super(app);
    }
    public void aplicarRutas(){
        app.routes(()->{
            path("/Usuario/",()->{
                get("/",ctx->{
                    ctx.redirect("/Usuarios/listar");
                });
                get("/listar",ctx->{
                    List<usuario> lista= ServiciosUsuarios.getInstance().findAll();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Usuarios");
                    modelo.put("lista",lista);
                    ctx.render("publico/Usuario/listar.html",modelo);
                });

                get("/crear",ctx->{
                    Map<String,Object> modelo = new HashMap<>();
                    modelo.put("titulo","Crear Usuario");
                    modelo.put("accion","/Usuarios/crear");
                    ctx.render("publico/Usuario/crear.html", modelo);
                });
                post("/crear",ctx->{
                    usuario user = new usuario(ctx.formParam("username"),ctx.formParam("password"), false);
                    ServiciosUsuarios.getInstance().crear(user);
                    ctx.redirect("/Usuario/listar");
                });
                get("/eliminar/{id}",ctx->{
                    ServiciosUsuarios.getInstance().eliminar(ctx.pathParamAsClass("id",Integer.class).get());
                    ctx.redirect("/Usuario/listar");
                });
                get("/editar/{id}",ctx->{
                    usuario user = ServiciosUsuarios.getInstance().find(ctx.pathParamAsClass("id",Integer.class).get());
                    Map<String,Object> modelo = new HashMap<>();
                    modelo.put("titulo","Editar Usuario");
                    modelo.put("accion","/Usuario/editar");
                    modelo.put("usuario",user);
                    ctx.render("publico/Usuario/crear.html",modelo);
                });
                post("/editar",ctx->{
                    usuario user = new usuario(ctx.formParam("username"),ctx.formParam("password"), false);
                    ServiciosUsuarios.getInstance().editar(user);
                    ctx.redirect("/Usuario/listar");
                });
            });
        });
        app.post("/registrarse",ctx->{
           usuario user =new usuario(ctx.formParam("username"),ctx.formParam("password"), false);
              ServiciosUsuarios.getInstance().crear(user);

            ctx.redirect("/login");
        });
        app.get("/register",ctx->{
            ctx.redirect("register.html");
        });

    }
}
