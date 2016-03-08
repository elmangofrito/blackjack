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
import static sun.management.Agent.error;

/**
 *
 * @author javier
 */
public class Json {

    JsonObject paquete = new JsonObject();
    String cadena_JSON;

    public int getCode(String recv) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(recv);

        return Objeto.getAsJsonObject().get("COD").getAsInt();
    }

    public String code_1(Object nombre, Object tiempo, Object espacios) {
        paquete.addProperty("COD", "1");
        paquete.addProperty("NOMBRE", nombre.toString());
        paquete.addProperty("TIEMPO", tiempo.toString());
        paquete.addProperty("ESPACIOS", espacios.toString());
        cadena_JSON = paquete.toString();
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

    public String code_4(Object acpt, Object dir, Object id) {
        paquete.addProperty("COD", "4");
        paquete.addProperty("ACEPTADO", acpt.toString());
        paquete.addProperty("DIRECCION", dir.toString());
        paquete.addProperty("ID", id.toString());
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
    

}
