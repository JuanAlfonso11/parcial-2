package org.example.servicios;
import org.example.clase.encuesta;

public class ServiciosEncuesta extends GestionDb<encuesta>
{
    private static ServiciosEncuesta instance;
    private ServiciosEncuesta() {
        super(encuesta.class);
    }
    public static ServiciosEncuesta getInstance() {
        if (instance == null) {
            instance = new ServiciosEncuesta();
        }
        return instance;
    }


}
