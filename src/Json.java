/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author javier
 */
public class Json {

    JsonObject paquete = new JsonObject();
    String cadena_JSON = new String();
    JsonObject[] vec = new JsonObject[4];
    JSONArray x = new JSONArray();

    public int getCode(String recv) {
        JsonParser parser = new JsonParser();
        com.google.gson.JsonElement Objeto = parser.parse(recv);

        return Objeto.getAsJsonObject().get("codigo").getAsInt();
    }

    public String code_1(Object nombre, Object tiempo, Object espacios) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 1);
        paquete.addProperty("nombre", nombre.toString());
        paquete.addProperty("tiempo", tiempo.toString());
        paquete.addProperty("espacios", espacios.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("codigo");
        paquete.remove("nombre");
        paquete.remove("tiempo");
        paquete.remove("espacios");
        return cadena_JSON;

    }

    public Object deco_1(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("nombre").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("tiempo").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("espacios").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_2(Object nombre) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 2);
        paquete.addProperty("nombre", nombre.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_2(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("nombre").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_3(Object acpt, Object dir, Object id) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 3);
        paquete.addProperty("aceptado", acpt.toString());
        paquete.addProperty("direccion", dir.toString());
        paquete.addProperty("id", id.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_3(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("aceptado").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("direccion").getAsString();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("id").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_4(Object jugadores) {
        JSONObject paquete4 = new JSONObject();
        JSONObject auxjson = new JSONObject();
        paquete4.put("codigo", 4);
        x = new JSONArray();
        String aux = "";
        String[] separar = jugadores.toString().trim().split(" ");
        for (int i = 0; i < separar.length / 2; i++) {
            auxjson = new JSONObject();
            auxjson.put("nombre", separar[i * 2]);
            auxjson.put("id_asignado", separar[(i * 2) + 1]);
            x.put(auxjson);
        }
        paquete4.put("jugadores", x);

        cadena_JSON = paquete4.toString();
        return cadena_JSON;
    }

    public Object deco_4(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);
        JsonArray auxx = Objeto.getAsJsonObject().get("jugadores").getAsJsonArray();
        System.out.println(auxx.get(0).getAsJsonObject().get("nombre").getAsString());
        // una vez que llegue la matriz, puede marcar artículos como 

        if (opcion == 0) {
            return auxx.size();
        }
        switch (opcion) {
            case 1:
                return auxx.get(0).getAsJsonObject().get("nombre").getAsString() + " " + auxx.get(0).getAsJsonObject().get("id_asignado").getAsString();
            case 2:
                return auxx.get(1).getAsJsonObject().get("nombre").getAsString() + " " + auxx.get(1).getAsJsonObject().get("id_asignado").getAsString();
            case 3:
                return auxx.get(2).getAsJsonObject().get("nombre").getAsString() + " " + auxx.get(2).getAsJsonObject().get("id_asignado").getAsString();
            case 4:
                return auxx.get(3).getAsJsonObject().get("nombre").getAsString() + " " + auxx.get(3).getAsJsonObject().get("id_asignado").getAsString();
            default:
                return null;
        }

    }

    public String code_5(Object jugador1) {
        JSONObject paquete5 = new JSONObject();
        JSONObject auxjson = new JSONObject();
        paquete5.put("codigo", 5);
        x = new JSONArray();
        String aux = "";
        String[] separar = jugador1.toString().trim().split(" ");

        for (int i = 0; i < separar.length / 2; i++) {
            auxjson = new JSONObject();
            auxjson.put("puntaje", separar[i * 2]);
            auxjson.put("id", separar[(i * 2) + 1]);
            x.put(auxjson);
        }
        
        System.out.println(x.toString());
        paquete5.put("puntaje", x);

        cadena_JSON = paquete5.toString();
        return cadena_JSON;
    }

    public Object deco_5(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);
        JsonArray auxx = Objeto.getAsJsonObject().get("puntaje").getAsJsonArray();
        // una vez que llegue la matriz, puede marcar artículos como 

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsString();
        }
        switch (opcion) {
            case 1:
                return auxx.get(0).getAsJsonObject().get("puntaje").getAsString() + " " + auxx.get(0).getAsJsonObject().get("id").getAsString();
            case 2:
                return auxx.get(1).getAsJsonObject().get("puntaje").getAsString() + " " + auxx.get(1).getAsJsonObject().get("id").getAsString();
            case 3:
                return auxx.get(2).getAsJsonObject().get("puntaje").getAsString() + " " + auxx.get(2).getAsJsonObject().get("id").getAsString();
            case 4:
                return auxx.get(3).getAsJsonObject().get("puntaje").getAsString() + " " + auxx.get(3).getAsJsonObject().get("id").getAsString();
            default:
                return null;
        }

    }

    public String code_6(Object solbono) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 6);
        paquete.addProperty("bono", solbono.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_6(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("bono").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_7(Object id) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 7);
        paquete.addProperty("id", id.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_7(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("id").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_8(Object jugar) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 8);
        paquete.addProperty("jugar", jugar.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_8(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);
        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("jugar").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_9(Object ID, Object CARTA) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 9);
        paquete.addProperty("id", ID.toString());
        paquete.addProperty("carta", CARTA.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_9(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);
        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("id").getAsString();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("carta").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_10(Object RONDAS, Object CARTAS, Object jugador1, Object idjugador1, Object jugador2, Object idjugador2, Object jugador3, Object idjugador3, Object jugador4, Object idjugador4) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 10);
        paquete.addProperty("rondas", RONDAS.toString());
        paquete.addProperty("cartas_jugadas", CARTAS.toString());
        paquete.addProperty("puntaje1", jugador1.toString());
        paquete.addProperty("id1", idjugador1.toString());
        paquete.addProperty("puntaje2", jugador2.toString());
        paquete.addProperty("id2", idjugador2.toString());
        paquete.addProperty("puntaje3", jugador3.toString());
        paquete.addProperty("id3", idjugador3.toString());
        paquete.addProperty("puntaje4", jugador4.toString());
        paquete.addProperty("id4", idjugador4.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_10(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);
        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("rondas").getAsInt();
        }
        if (opcion == 2) {
            return Objeto.getAsJsonObject().get("cartas_jugadas").getAsInt();
        }
        if (opcion == 3) {
            return Objeto.getAsJsonObject().get("puntaje1").getAsInt();
        }
        if (opcion == 4) {
            return Objeto.getAsJsonObject().get("id1").getAsString();
        }
        if (opcion == 5) {
            return Objeto.getAsJsonObject().get("puntaje2").getAsInt();
        }
        if (opcion == 6) {
            return Objeto.getAsJsonObject().get("id2").getAsString();
        }
        if (opcion == 7) {
            return Objeto.getAsJsonObject().get("puntaje3").getAsInt();
        }
        if (opcion == 8) {
            return Objeto.getAsJsonObject().get("id3").getAsString();
        }
        if (opcion == 9) {
            return Objeto.getAsJsonObject().get("puntaje4").getAsInt();
        }
        if (opcion == 10) {
            return Objeto.getAsJsonObject().get("id4").getAsString();
        } else {
            System.err.println("error");
        }
        return null;
    }

    public String code_11(Object ID) {
        paquete = new JsonObject();
        paquete.addProperty("codigo", 11);
        paquete.addProperty("id", ID.toString());
        cadena_JSON = paquete.toString();
        return cadena_JSON;
    }

    public Object deco_11(String JSON, int opcion) {
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);

        if (opcion == 0) {
            return Objeto.getAsJsonObject().get("codigo").getAsInt();
        }
        if (opcion == 1) {
            return Objeto.getAsJsonObject().get("id").getAsInt();
        } else {
            System.err.println("error");
        }
        return null;
    }
}
