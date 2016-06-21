package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LEO on 17/4/2016.
 */
public class JsonParsing {

    static JSONObject jObj = null;
    static JSONArray jArray = null;
    static String json = "";
    private Context context;
    private AuxiliarGeneral auxiliarGeneral;
    public static String URL;
    //    public static String URLARTICULO = "estrella_del_sur/testing/ADEFUL/Institucion/Articulo/";
//    public static String URLSINCRONIZAR = "estrella_del_sur/testing/ADEFUL/sincronizar/usuario/";
    public static String URLARTICULO;
    public static String URLCOMISION;
    public static String URLSINCRONIZAR;

    public JsonParsing(Context c) {
        context = c;
        auxiliarGeneral = new AuxiliarGeneral(context);
        URL = auxiliarGeneral.getURL();
        URLARTICULO = auxiliarGeneral.getURLARTICULOADEFUL();
        URLSINCRONIZAR = auxiliarGeneral.getURLSINCRONIZARUSUARIO();
        URLCOMISION = auxiliarGeneral.getURLCOMISIONADEFUL();
    }

    public JSONObject parsingJsonObject(Request request,String url_parsing) {
        jObj = null;
        jArray = null;
        String json = null;
        String uri = null;
        uri = url_parsing;
//        if (request.getQuery().equals("SUBIR")) {
//            uri = URL + URLARTICULO + "insertArticulo.php";
//        } else if (request.getQuery().equals("EDITAR")) {
//            uri = URL + URLARTICULO + "updateArticulo.php";
//        } else if (request.getQuery().equals("ELIMINAR")) {
//            uri = URL + URLARTICULO + "deleteArticulo.php";
//        }

        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request.getMethod());

            if (request.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter write = new OutputStreamWriter(
                        con.getOutputStream());
                write.write(request.getEncodedParams());
                write.flush();
            }
            con.setConnectTimeout(4000);
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                if(json!=null)
                jObj = new JSONObject(json);
            } catch (JSONException e) {

            }
            json = null;
            return jObj;
        }

    }




    // ARTICULO ADEFUL
    public JSONObject parsingArticulo(Request request) {
        jObj = null;
        jArray = null;
        String json = null;
        String uri = null;
        if (request.getQuery().equals("SUBIR")) {
            uri = URL + URLARTICULO + "insertArticulo.php";
        } else if (request.getQuery().equals("EDITAR")) {
            uri = URL + URLARTICULO + "updateArticulo.php";
        } else if (request.getQuery().equals("ELIMINAR")) {
            uri = URL + URLARTICULO + "deleteArticulo.php";
        }

        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request.getMethod());

            if (request.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter write = new OutputStreamWriter(
                        con.getOutputStream());
                write.write(request.getEncodedParams());
                write.flush();
            }
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream(), "UTF-8"), 8);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {

            }
            json = null;
            return jObj;
        }

    }

   // COMISION
    public JSONObject parsingComision(Request request) {

        jObj = null;
        jArray = null;
        String json = null;
        String uri = null;
        if (request.getQuery().equals("SUBIR")) {
            uri = URL + URLCOMISION + "insertComision.php";
        } else if (request.getQuery().equals("EDITAR")) {
            uri = URL + URLCOMISION + "updateComision.php";
        } else if (request.getQuery().equals("ELIMINAR")) {
            uri = URL + URLCOMISION + "deleteComision.php";
        }


        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request.getMethod());

            if (request.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter write = new OutputStreamWriter(
                        con.getOutputStream());
                write.write(request.getEncodedParams());
                write.flush();
            }
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                // Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            json = null;
            return jObj;
        }

    }

    // trae promo splash
    public static String traerPromo() {
        String uri = URL + URLSINCRONIZAR + "sincronizarUsuario.php";

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    // SINCRONIZAR USUARIO
    public JSONObject parsingUsuarioSincronizar(Request request) {
        jObj = null;
        jArray = null;
        String json = null;
        String uri = null;
        uri = URL + URLSINCRONIZAR + "sincronizarUsuario.php";
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(request.getMethod());

            if (request.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter write = new OutputStreamWriter(
                        con.getOutputStream());
                write.write(request.getEncodedParams());
                write.flush();
            }
            con.setConnectTimeout(4000);
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                jObj = new JSONObject(json);
                //  JSONArray jsonArray = jObj.optJSONArray("ARTICULO_ADEFUL");
                // jArray = new JSONArray(json);

//                for (int i = 0; i < jsonArray.length(); i++) {
//                    jObj = jsonArray.getJSONObject(i);
//                }
            } catch (JSONException e) {
                jObj = null;
            }
            json = null;
            return jObj;
        }

    }
}
