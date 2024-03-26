package org.example.controladores;

import io.javalin.Javalin;
import org.example.util.BaseControler;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PaginaInicio extends BaseControler {
    public PaginaInicio(Javalin app) {
        super(app);
    }
    public void aplicarRutas(){
        app.before("/",ctx->{
           if(ctx.sessionAttribute("usuario")==null){
               ctx.redirect("/login");
           }
        });
        app.get("/",ctx->{
            ctx.render("/publico/templates/index.html");
        });
    }

}
