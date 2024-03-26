package org.example.clase;
import org.example.servicios.ServiciosUsuarios;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Criptografia {

    private static StandardPBEStringEncryptor encryptor;
    private static final String clave = "79er87451f8w9e97r4ew5rw9e87w4wet8w9ea98q7";

    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(clave);
    }

    public static String encriptado(usuario us) {
        String encrips = us.getUsername() + ";" + us.getPassword();
        return encryptor.encrypt(encrips);
    }

    public static usuario desencriptado(String txt) {
        String textoDesencriptado = encryptor.decrypt(txt);
        if (textoDesencriptado == null) {
            return null;
        }
        String[] partes = textoDesencriptado.split(";", 2);
        if (partes.length == 2) {
            String username = partes[0];
            String password = partes[1];
            System.out.println("Username: "+username+" password: "+password);
            return ServiciosUsuarios.getInstance().verificarCredenciales(username, password);
        } else {
            return null;
        }
    }
}
