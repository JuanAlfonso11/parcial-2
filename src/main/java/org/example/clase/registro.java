package org.example.clase;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idregistro;
    @OneToOne
    private usuario user;
    @OneToOne
    private encuesta encuesta;

    public registro(encuesta survey, usuario user) {

    }


    public registro( encuesta encuesta, usuario user) {

        this.encuesta = encuesta;
       this.user = user;

    }

    public int getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(int idregistro) {
        this.idregistro = idregistro;
    }


    public encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public usuario getUser() {
        return user;
    }

    public void setUser(usuario user) {
        this.user = user;
    }
}
