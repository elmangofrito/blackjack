/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author javier
 */
public class Json {

    JsonObject paquete = new JsonObject();
    String cadena_JSON;

    public int getCode(String recv) {
        JsonParser parser = new JsonParser();
        com.google.gson.JsonElement Objeto = parser.parse(recv);

        return Objeto.getAsJsonObject().get("COD").getAsInt();
    }

    public String code_1(Object nombre, Object tiempo, Object espacios) {
        paquete.addProperty("COD", 1);
        paquete.addProperty("NOMBRE", nombre.toString());
        paquete.addProperty("TIEMPO", tiempo.toString());
        paquete.addProperty("ESPACIOS", espacios.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("NOMBRE");
        paquete.remove("TIEMPO");
        paquete.remove("ESPACIOS");
        return cadena_JSON;

    }

    public Object deco_1(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("NOMBRE").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("TIEMPO").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("ESPACIOS").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_2(Object nombre) {
        paquete.addProperty("COD", "2");
        paquete.addProperty("NOMBRE", nombre.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_2(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("NOMBRE").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_3(Object acpt, Object dir, Object id) {
        paquete.addProperty("COD", "3");
        paquete.addProperty("ACEPTADO", acpt.toString());
        paquete.addProperty("DIRECCION", dir.toString());
        paquete.addProperty("ID", id.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_3(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("ACEPTADO").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("DIRECCION").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("ID").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_4(Object jugador1, Object idjugador1, Object jugador2, Object idjugador2, Object jugador3, Object idjugador3, Object jugador4, Object idjugador4) {
        paquete.addProperty("COD", "4");
        paquete.addProperty("JUGADOR1", jugador1.toString());
        paquete.addProperty("IDJUGADOR1", idjugador1.toString());
        paquete.addProperty("JUGADOR2", jugador2.toString());
        paquete.addProperty("IDJUGADOR2", idjugador2.toString());
        paquete.addProperty("JUGADOR3", jugador3.toString());
        paquete.addProperty("IDJUGADOR3", idjugador3.toString());
        paquete.addProperty("JUGADOR4", jugador4.toString());
        paquete.addProperty("IDJUGADOR4", idjugador4.toString());

        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_4(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("JUGADOR1").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("IDJUGADOR1").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("JUGADOR2").getAsString();
        }
        if (opcion == 4) {
            return Objeto.getAsJsonObject().get("IDJUGADOR2").getAsString();
        }
        if (opcion == 5) {
            return Objeto.getAsJsonObject().get("JUGADOR3").getAsString();
        }
        if (opcion == 6) {
            return Objeto.getAsJsonObject().get("IDJUGADOR3").getAsString();
        }
        if (opcion == 7) {
            return Objeto.getAsJsonObject().get("JUGADOR4").getAsString();
        }
        if (opcion == 8) {
            return Objeto.getAsJsonObject().get("IDJUGADOR4").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_5(Object jugador1, Object idjugador1, Object jugador2, Object idjugador2, Object jugador3, Object idjugador3, Object jugador4, Object idjugador4) {
        paquete.addProperty("COD", "4");
        paquete.addProperty("PUNTAJE1", jugador1.toString());
        paquete.addProperty("IDJUGADOR1", idjugador1.toString());
        paquete.addProperty("PUNTAJE2", jugador2.toString());
        paquete.addProperty("IDJUGADOR2", idjugador2.toString());
        paquete.addProperty("PUNTAJE3", jugador3.toString());
        paquete.addProperty("IDJUGADOR3", idjugador3.toString());
        paquete.addProperty("PUNTAJE4", jugador4.toString());
        paquete.addProperty("IDJUGADOR4", idjugador4.toString());

        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_5(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("PUNTAJE1").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("IDJUGADOR1").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("PUNTAJE2").getAsString();
        }
        if (opcion == 4) {
            return Objeto.getAsJsonObject().get("IDJUGADOR2").getAsString();
        }
        if (opcion == 5) {
            return Objeto.getAsJsonObject().get("PUNTAJE3").getAsString();
        }
        if (opcion == 6) {
            return Objeto.getAsJsonObject().get("IDJUGADOR3").getAsString();
        }
        if (opcion == 7) {
            return Objeto.getAsJsonObject().get("PUNTAJE4").getAsString();
        }
        if (opcion == 8) {
            return Objeto.getAsJsonObject().get("IDJUGADOR4").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

}
