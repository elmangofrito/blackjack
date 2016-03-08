

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.JSONObject;

public class JSON {
    String cadena_JSON = null;
    String error = "No esta seleccionando una opcion valida.";
    static final int Nro_campos=25;
    JsonObject paquete = new JsonObject();
    JsonObject paquete_num = new JsonObject();
    ArrayList numeros = new ArrayList();
             
    
    public static final int IP_SERVER = 0 ;
	public static final int IP = 1 ;
	public static final int NAME = 2 ;
	public static final int CARTONES = 4 ;
	public static final int CLIENTE = 5 ;
	public static final int IDJUEGO = 6 ;
	public static final int IDCARTON = 7 ;
	public static final int NUMEROS = 8 ; //-- Array de números
	public static final int ACIERTOS = 9 ; //-- Array de numeros acertados
	public static final int TBINGO = 10 ;
	public static final int NROJUGADA = 11 ;
    // Opcion = 1 extrae el codigo.
    // Mensajes de conexion.
    // Mensaje 100 Solicitud de conexion.
    
    public int getCode(String recv){
	JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(recv); 
        
        return Objeto.getAsJsonObject().get("COD").getAsInt();		
    }
    
    public String code_100(Object IP, Object Cliente){
        paquete.addProperty("COD", 100);
        paquete.addProperty("IP", IP.toString());
        paquete.addProperty("CLIENTE", Cliente.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IP");
        paquete.remove("CLIENTE");
        
        return cadena_JSON;
    }
    
    public Object deco_100(String JSON, int opcion){// Opcion: 1-IP, 2-Cliente
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IP").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("CLIENTE").getAsString();
        else
           System.err.println(error); 
        
        return null;
    } 
    
    // Mensaje 101 Respuesta conexion.
    public String code_101(Object IDJuego){
        paquete.addProperty("COD", 101);
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDJUEGO");
                
        return cadena_JSON;
    }
    
    public Object deco_101(String JSON, int opcion){// Opcion: 1-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 102 Solicitud de carton.
    public String code_102(Object NroCartones){
        paquete.addProperty("COD", 102);
        paquete.addProperty("NROCARTONES", Integer.parseInt(NroCartones.toString()));
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("NROCARTONES");      
        
        return cadena_JSON;
    }
    
    public Object deco_102(String JSON, int opcion){// Opcion: 1-NroCartones
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("NROCARTONES").getAsInt();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 103 Envio de carton.
    public String code_103(Object IDCarton, Object Numeros){
        String cadena_aux;
        cadena_aux = code_matriz(Numeros);
        
        cadena_JSON="{\"COD\":103,\"IDCARTON\":\""+IDCarton+"\",\"NUMEROS\":"+cadena_aux
                +"}";
        
        return cadena_JSON;
    }
    
    public Object deco_103(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Numeros
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    

        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return deco_matriz(Objeto.getAsJsonObject().get("NUMEROS").getAsString());
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 105 Anuncio de Sala.
    public String code_105(Object IP, Object Sala){
        paquete.addProperty("COD", 105);
        paquete.addProperty("IP", IP.toString());
        paquete.addProperty("SALA", Sala.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IP");        
        paquete.remove("SALA");   
        
        return cadena_JSON;
    }
    
    public Object deco_105(String JSON, int opcion){// Opcion: 1-IP, 2-Sala
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IP").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("SALA").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 106 Anuncio de Sala.
    public String code_106(Object IDJuego, Object Motivo){
        paquete.addProperty("COD", 106);
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        paquete.addProperty("MOTIVO", Motivo.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDJUEGO");        
        paquete.remove("MOTIVO");        
        
        return cadena_JSON;
    }
    
    public Object deco_106(String JSON, int opcion){// Opcion: 1-IDJuego, 2-Motivo
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("MOTIVO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensajes de error.
    // Mensaje 200 Error de conexion.
    public String code_200(Object IP, Object Servidor, Object TipoError){
        paquete.addProperty("COD", 200);
        paquete.addProperty("IP", IP.toString());
        paquete.addProperty("SERVIDOR", Servidor.toString());
        paquete.addProperty("TIPOERROR", TipoError.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IP");        
        paquete.remove("SERVIDOR");
        paquete.remove("TIPOERROR");
        
        return cadena_JSON;
    }
    
    public Object deco_200(String JSON, int opcion){// Opcion: 1-IP, 2-Servidor, 3-TipoError
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IP").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("SERVIDOR").getAsString();
        if(opcion==3)
            return Objeto.getAsJsonObject().get("TIPOERROR").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }  
    
    // Mensaje 201 Carton invalido.
    public String code_201(Object IDCarton, Object Motivo){
        paquete.addProperty("COD", 201);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("MOTIVO", Motivo.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON");        
        paquete.remove("MOTIVO");
      
        return cadena_JSON;
    }
    
    public Object deco_201(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Motivo
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("MOTIVO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 202 Bingo invalido.
    public String code_202(Object IDCarton, Object Motivo){
        paquete.addProperty("COD", 202);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("MOTIVO", Motivo.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON");        
        paquete.remove("MOTIVO");       
        
        return cadena_JSON;
    }
    
    public Object deco_202(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Motivo
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("MOTIVO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensajes de informacion.
    // Mensaje 300 Inicio de juego.
    public String code_300(Object IDJuego){
        paquete.addProperty("COD", 300);
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDJUEGO");        
    
        return cadena_JSON;
    }
    
    public Object deco_300(String JSON, int opcion){// Opcion: 1-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    } 
    
    // Mensaje 301 Fin de juego.
    public String code_301(Object IDJuego){
        paquete.addProperty("COD", 301);
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDJUEGO");       
        
        return cadena_JSON;
    }
    
    public Object deco_301(String JSON, int opcion){// Opcion: 1-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 302 Bingo cantado - servidor.
    public String code_302(Object IDJuego){
        paquete.addProperty("COD", 302);
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDJUEGO");       
        
        return cadena_JSON;
    }
    
    public Object deco_302(String JSON, int opcion){// Opcion: 1-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 303 Bingo vertical.
    public String code_303(Object IDCarton, Object Numeros, Object Aciertos){
        String numeros_aux;
        String aciertos_aux;
        
        numeros_aux = code_matriz(Numeros);
        aciertos_aux = code_aciertos(Aciertos);
        paquete.addProperty("COD", 303);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("NUMEROS", numeros_aux);
        paquete.addProperty("ACIERTOS", aciertos_aux);
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON"); 
        paquete.remove("NUMEROS");
        paquete.remove("ACIERTOS"); 
        
        return cadena_JSON;
    }
    
    public Object deco_303(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Numeros, 3-Aciertos
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return deco_matriz(Objeto.getAsJsonObject().get("NUMEROS").getAsString());
        if(opcion==3)
            return deco_aciertos(Objeto.getAsJsonObject().get("ACIERTOS").getAsString());
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 304 Bingo horizontal.
    public String code_304(Object IDCarton, Object Numeros, Object Aciertos){
        String numeros_aux;
        String aciertos_aux;
        
        numeros_aux = code_matriz(Numeros);
        aciertos_aux = code_aciertos(Aciertos);
        paquete.addProperty("COD", 304);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("NUMEROS", numeros_aux);
        paquete.addProperty("ACIERTOS", aciertos_aux);
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON"); 
        paquete.remove("NUMEROS");
        paquete.remove("ACIERTOS"); 
        
        return cadena_JSON;
    }
    
    public Object deco_304(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Numeros, 3-Aciertos
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return deco_matriz(Objeto.getAsJsonObject().get("NUMEROS").getAsString());
        if(opcion==3)
            return deco_aciertos(Objeto.getAsJsonObject().get("ACIERTOS").getAsString());
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 305 Bingo diagonal.
    public String code_305(Object IDCarton, Object Numeros, Object Aciertos){
        String numeros_aux;
        String aciertos_aux;
        
        numeros_aux = code_matriz(Numeros);
        aciertos_aux = code_aciertos(Aciertos);
        paquete.addProperty("COD", 305);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("NUMEROS", numeros_aux);
        paquete.addProperty("ACIERTOS", aciertos_aux);
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON"); 
        paquete.remove("NUMEROS");
        paquete.remove("ACIERTOS"); 
        
        return cadena_JSON;
    }
    
    public Object deco_305(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Numeros, 3-Aciertos
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return deco_matriz(Objeto.getAsJsonObject().get("NUMEROS").getAsString());
        if(opcion==3)
            return deco_aciertos(Objeto.getAsJsonObject().get("ACIERTOS").getAsString());
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 306 Carton lleno.
    public String code_306(Object IDCarton, Object Numeros, Object Aciertos){
        String numeros_aux;
        String aciertos_aux;
        
        numeros_aux = code_matriz(Numeros);
        aciertos_aux = code_matriz(Aciertos);
        paquete.addProperty("COD", 306);
        paquete.addProperty("IDCARTON", IDCarton.toString());
        paquete.addProperty("NUMEROS", numeros_aux);
        paquete.addProperty("ACIERTOS", aciertos_aux);
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IDCARTON"); 
        paquete.remove("NUMEROS");
        paquete.remove("ACIERTOS"); 
        
        return cadena_JSON;
    }
    
    public Object deco_306(String JSON, int opcion){// Opcion: 1-IDCarton, 2-Numeros, 3-Aciertos
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IDCARTON").getAsString();
        if(opcion==2)
            return deco_matriz(Objeto.getAsJsonObject().get("NUMEROS").getAsString());
        if(opcion==3)
            return deco_matriz(Objeto.getAsJsonObject().get("ACIERTOS").getAsString());
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 307 Bingo aceptado - servidor.
    public String code_307(Object IP, Object Cliente, Object TipoBingo, Object IDJuego){
        paquete.addProperty("COD", 307);
        paquete.addProperty("IP", IP.toString());
        paquete.addProperty("CLIENTE", Cliente.toString());
        paquete.addProperty("TIPOBINGO", TipoBingo.toString());
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("IP"); 
        paquete.remove("CLIENTE");
        paquete.remove("TIPOBINGO");       
        paquete.remove("IDJUEGO"); 
        
        return cadena_JSON;
    }
    
    public Object deco_307(String JSON, int opcion){// Opcion: 1-IP, 2-Cliente, 3-TipoBingo, 4-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("IP").getAsString();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("CLIENTE").getAsString();
        if(opcion==3)
            return Objeto.getAsJsonObject().get("TIPOBINGO").getAsString();
        if(opcion==4)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 308 Cantar numero.
    public String code_308(Object NroJugada, Object Numero, Object IDJuego){
        paquete.addProperty("COD", 308);
        paquete.addProperty("NROJUGADA", Integer.parseInt(NroJugada.toString()));
        paquete.addProperty("NUMERO", Integer.parseInt(Numero.toString()));
        paquete.addProperty("IDJUEGO", IDJuego.toString());
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("NROJUGADA"); 
        paquete.remove("NUMERO");
        paquete.remove("IDJUEGO");          
        
        return cadena_JSON;
    }
    
    public Object deco_308(String JSON, int opcion){// Opcion: 1-NroJugada, 2-Numero, 3-IDJuego
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("NROJUGADA").getAsInt();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("NUMERO").getAsInt();
        if(opcion==3)
            return Objeto.getAsJsonObject().get("IDJUEGO").getAsString();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 309 Solicitar jugada.
    public String code_309(Object NroJugada){
        paquete.addProperty("COD", 309);
        paquete.addProperty("NROJUGADA", Integer.parseInt(NroJugada.toString()));
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("NROJUGADA"); 
        
        return cadena_JSON;
    }
    
    public Object deco_309(String JSON, int opcion){// Opcion: 1-NroJugada
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("NROJUGADA").getAsInt();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje 310 Repartir jugada.
    public String code_310(Object NroJugada, Object Numero){
        paquete.addProperty("COD", 310);
        paquete.addProperty("NROJUGADA", Integer.parseInt(NroJugada.toString()));
        paquete.addProperty("NUMERO", Integer.parseInt(Numero.toString()));
        cadena_JSON = paquete.toString();
        paquete.remove("COD");
        paquete.remove("NroJugada");
        paquete.remove("NUMERO");
                
        return cadena_JSON;
    }
    
    public Object deco_310(String JSON, int opcion){// Opcion: 1-NroJugada, 2-Numero
        JsonParser parser = new JsonParser();
        JsonElement Objeto = parser.parse(JSON);    
        
        if(opcion==0)
            return Objeto.getAsJsonObject().get("COD").getAsInt();
        if(opcion==1)
            return Objeto.getAsJsonObject().get("NROJUGADA").getAsInt();
        if(opcion==2)
            return Objeto.getAsJsonObject().get("NUMERO").getAsInt();
        else
           System.err.println(error); 
        
        return null;
    }
    
    // Mensaje paquete de numeros para encapsular.
    public String code_matriz(Object numeros){
        String cadena_json_aux;
        Gson paquete_aux = new Gson();
        ArrayList array = new ArrayList();
        int mat[][]=(int [][]) numeros;
        
        array.clear();
        for(int i=0;i<5;i++)
                array.add(mat[i]);
                               
        cadena_json_aux = paquete_aux.toJson(array);
        
        return cadena_json_aux;
    }
    
    public int[][] deco_matriz(String JSON){
        Gson paquete_aux = new Gson();
        ArrayList array = new ArrayList();
        Type tipoObjeto = new TypeToken<ArrayList>(){}.getType();
        int matriz[][] = new int[5][5], cont = 0;
        
        array.clear();
        array=paquete_aux.fromJson(JSON, tipoObjeto);
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++){
                matriz[i][j]=Integer.parseInt(array.get(cont).toString().replace(".0", ""));
                cont++;
            }
        return matriz;       
    }
    
    public String code_aciertos(Object numeros){
        String cadena_json_aux;
        Gson paquete_aux = new Gson();
        ArrayList array = new ArrayList();
        int mat[]=(int []) numeros;
        
        array.clear();
        for(int i=0;i<5;i++)
            array.add(mat[i]);                               
        cadena_json_aux = paquete_aux.toJson(array);
        
        return cadena_json_aux;
    }
    
    public int[] deco_aciertos(String JSON){
        Gson paquete_aux = new Gson();
        ArrayList array = new ArrayList();
        Type tipoObjeto = new TypeToken<ArrayList>(){}.getType();
        int vec[]= new int[5], cont = 0;
        
        array.clear();
        array=paquete_aux.fromJson(JSON, tipoObjeto);
        for(int i=0;i<5;i++)
            vec[i]=Integer.parseInt(array.get(i).toString().replace(".0", ""));
           
        return vec;       
    }
    
     public static Object decodificar(String recv, int opt){
    	JSONObject recibido = new JSONObject(recv);
    	
    	switch(opt){
			case IP:
				return recibido.getString("IP");
			case NAME:
				return recibido.getString("SALA");
			case CARTONES:
    			return recibido.getInt("NROCARTONES");
			case IP_SERVER:
				return recibido.getString("IP");
			case IDCARTON:
				return recibido.getString("IDCARTON");
			case IDJUEGO:
				return recibido.getString("IDJUEGO");
			case NROJUGADA:
				return recibido.getInt("NROJUGADA");
			case NUMEROS:
				return recibido.getJSONArray("NUMEROS");
			case TBINGO:
				return recibido.getString("TIPOBINGO");
			case ACIERTOS:
				return recibido.getInt("ACIERTOS");
			case CLIENTE:
				return recibido.getString("CLIENTE");
    	}
    	
    	return "ERROR: algún paquete llegó dañado.";
    }

    
    public static void main(String arg[]){
        JSON json = new JSON();
        String IDCarton="#$%TGHYJUKIOLP";
        String cadena1 = null;
        int i = 0;
        int [][]mat = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}}; 
        
        cadena1 = json.code_103(IDCarton, mat);
        System.out.print(cadena1);
        
        mat=(int[][]) json.deco_103(cadena1, 2);
        
        for(i=0;i<5;i++)
            for(int j=0;j<5;j++)
                System.out.print(mat[i][j]);
    }

}
