package org.example.controladores;
import io.javalin.Javalin;
import io.javalin.http.Cookie;
import org.example.clase.Criptografia;
import org.example.clase.usuario;
import org.example.servicios.ServiciosUsuarios;
import org.example.util.BaseControler;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Login extends BaseControler {
    public Login(Javalin app){
        super(app);
    }
public void aplicarRutas(){
        app.routes(()->{
            path("/",()->{
                before(ctx ->{

                });
                get("/login",ctx->{

                    ctx.redirect("login.html");
                });
                get("/index",ctx->{

                    ctx.redirect("index.html");
                });
            });
        });
        app.get("/logout",ctxContext->{
            ctxContext.removeCookie("logusuario");
            ctxContext.redirect("/");
        });
}
}
