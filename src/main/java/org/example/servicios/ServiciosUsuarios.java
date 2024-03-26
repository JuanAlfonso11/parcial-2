package org.example.servicios;
import org.example.clase.usuario;
public class ServiciosUsuarios extends GestionDb<usuario> {
    private static ServiciosUsuarios instance;
    private ServiciosUsuarios() {
        super(usuario.class);
    }
    public static ServiciosUsuarios getInstance() {
        if (instance == null) {
            instance = new ServiciosUsuarios();
        }
        return instance;
    }

    public usuario verificarCredencialesUsuario(String username, String password) {
        return instance.verificarCredenciales(username, password);
    }

    public usuario verificarCredenciales(String username, String password) {
        var lista = getEntityManager().createQuery("SELECT u FROM usuario u WHERE u.username = :username AND u.password = :password", usuario.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }

}
