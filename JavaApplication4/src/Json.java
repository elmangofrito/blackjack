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
    
    public String code_1(Object nombre, Object tiempo,Object espacios){
        paquete.addProperty("COD", "1");
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
}
