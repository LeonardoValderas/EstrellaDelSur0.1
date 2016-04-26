package com.estrelladelsur.estrelladelsur.webservice;

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

    public static String URL = "http://valdroide.com/";
    public static String URLMODULO = "estrella_del_sur/testing/ADEFUL/Institucion/Articulo/";
    public static String URLSINCRONIZAR = "estrella_del_sur/testing/ADEFUL/sincronizar/usuario/";
    static JSONObject jObj = null;
    static  JSONArray jArray = null;
    static String json = "";

    // ARTICULO ADEFUL
    public  JSONObject parsingArticulo(Request request) {

        String uri = null;
        if (request.getQuery().equals("SUBIR")) {
            uri = URL + URLMODULO + "insertArticulo.php";
        } else if (request.getQuery().equals("EDITAR")) {
            uri = URL + URLMODULO + "updateArticulo.php";
        } else if (request.getQuery().equals("ELIMINAR")) {
            uri = URL + URLMODULO + "deleteArticulo.php";
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
                // Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
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

        String uri = URL + URLSINCRONIZAR + "sincronizarUsuario.php";
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
              //  JSONArray jsonArray = jObj.optJSONArray("ARTICULO_ADEFUL");
               // jArray = new JSONArray(json);

//                for (int i = 0; i < jsonArray.length(); i++) {
//                    jObj = jsonArray.getJSONObject(i);
//                }
            } catch (JSONException e) {
                jObj = null;
            }
            return jObj;
        }

    }
}
