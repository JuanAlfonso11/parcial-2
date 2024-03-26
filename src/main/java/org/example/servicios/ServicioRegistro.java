package org.example.servicios;
import org.example.clase.registro;
public class ServicioRegistro  extends GestionDb<registro>{
    private static ServicioRegistro instance;
    private ServicioRegistro() {
        super( registro.class);
    }
    public static ServicioRegistro getInstance() {
        if (instance == null) {
            instance = new ServicioRegistro();
        }
        return instance;
    }
}
