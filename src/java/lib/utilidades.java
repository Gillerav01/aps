package lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class utilidades {

    public static String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String devolverComunidad(int n) {
        ArrayList<String> comunidad = new ArrayList(Arrays.asList("Andalucía", "Aragón", "Canarias", "Cantabria", "Castilla y León",
                "Castilla-La Mancha", "Cataluña", "Ceuta", "Comunidad Valenciana",
                "Comunidad de Madrid", "Extremadura", "Galicia", "Islas Baleares",
                "La Rioja", "Melilla", "Navarra", "País Vasco", "Principado de Asturias",
                "Región de Murcia"));
        return comunidad.get(n);
    }

    public static String devolverProvincia(int n) {
        ArrayList<String> provincia = new ArrayList(Arrays.asList("Alava", "Albacete", "Alicante", "Almería", "Avila", "Badajoz", "Baleares", "Barcelona", "Burgos", "Cáceres",
        "Cádiz", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
        "Guipúzcoa", "Huelva", "Huesca", "Jaén", "León", "Lérida", "La rioja", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
        "Orense", "Asturias", "Palencia", "Las Palmas", "Pontevedra", "Salamanca", "Tenerife", "Cantabria", "Segovia", "Sevilla", "Soria", "Tarragona",
        "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza", "Ceuta", "Melilla"));
        return provincia.get(n);
    }

}
