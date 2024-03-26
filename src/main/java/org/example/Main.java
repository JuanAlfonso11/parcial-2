package org.example;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import org.example.controladores.*;
import org.example.servicios.BootStrapServices;

public class Main {
    private static String modoConexion = "";

    public static void main(String[] args) {

        if(modoConexion.isEmpty()) {
            BootStrapServices.getInstancia().init();
        }

        Javalin app = Javalin.create(config->{
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath="/";
                staticFileConfig.directory="/publico";
                staticFileConfig.location=Location.CLASSPATH;
            });
            config.plugins.register(new RouteOverviewPlugin("/rutas"));
            config.plugins.enableCors(corsContainer -> {
                corsContainer.add(corsPluginConfig -> {
                    corsPluginConfig.anyHost();
                });
            });
        }).start(getHerokuAssignedPort());
        new Login(app).aplicarRutas();
        new Autenticar(app).aplicarRutas();
        new PaginaInicio(app).aplicarRutas();
        new ControladorUsuarios(app).aplicarRutas();
       new ControladorEncuesta(app).aplicarRutas();
    }

    public static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }



    public static String getModoConexion() {
        return modoConexion;
    }
}