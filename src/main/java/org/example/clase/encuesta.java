package org.example.clase;
import org.example.clase.usuario;
import jakarta.persistence.*;

@Entity
public class encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idencuaesta;
    private String nombre;
    private String Sector;
    private String NivelEducativo;
    @ManyToOne
    private usuario user;
    private double latitud;
    private double longitud;

    public encuesta() {
    }

    public encuesta(String nombre, String Sector, String NivelEducativo, usuario user, float latitud, float longitud) {

        this.nombre = nombre;
        this.Sector = Sector;
        this.NivelEducativo = NivelEducativo;
        this.user = user;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdencuaesta() {
        return idencuaesta;
    }

    public void setIdencuaesta(int idencuaesta) {
        this.idencuaesta = idencuaesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String Sector) {
        this.Sector = Sector;
    }

    public String getNivelEducativo() {
        return NivelEducativo;
    }

    public void setNivelEducativo(String NivelEducativo) {
        this.NivelEducativo = NivelEducativo;
    }

    public usuario getUser() {
        return user;
    }

    public void setUser(usuario user) {
        this.user = user;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


}
