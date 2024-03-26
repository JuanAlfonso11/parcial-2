package org.example.controladores;

import io.javalin.Javalin;
import org.example.clase.Criptografia;
import org.example.clase.encuesta;
import org.example.clase.usuario;
//import org.example.servicios.ServicioRegistro;
import org.example.servicios.ServiciosEncuesta;
import org.example.util.BaseControler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorRegistro extends BaseControler {
    public ControladorRegistro(Javalin app){super(app);}
    @Override
    public void aplicarRutas(){
        app.routes(()->{
            path("/RegistroEncuesta/",()->{
                get("/",ctx->{
                    ctx.redirect("RegistroEncuesta/listar");
                });
                get("/listar",ctx->{
                    usuario user = Criptografia.desencriptado(ctx.cookie("Logusuario"));

                });

            });
        });
    }


}
