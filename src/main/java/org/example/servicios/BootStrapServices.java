package org.example.servicios;
import org.example.clase.usuario;
import org.h2.tools.Server;
import java.sql.SQLException;
public class BootStrapServices {
    private  static BootStrapServices instancia;

    private BootStrapServices(){

    }
    public static BootStrapServices getInstancia(){
        if(instancia==null){
            instancia = new BootStrapServices();
        }
        return instancia;
    }
    public void startDb(){
        try{
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon",
                    "-ifNotExists").start();
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            System.out.println("Status Web: "+status);
        }catch(SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }
    public void init(){
        startDb();
       if(ServiciosUsuarios.getInstance().find(0)==null) {
           ServiciosUsuarios.getInstance().crear(new usuario("admin", "admin", true));
       }
    }

}
