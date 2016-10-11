package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.navegador.usuario.SplashUsuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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

    public JSONObject parsingJsonObject(Request request, String url_parsing) {
        jObj = null;
        jArray = null;
        String json = null;
        String uri = null;
        uri = url_parsing;
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
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                json = sb.toString();
            } else {

                return null;
            }
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
                if (json != null)
                    jObj = new JSONObject(json);
            } catch (JSONException e) {
            }
            json = null;
            return jObj;
        }
    }

    public boolean processingJson(JSONArray jsonArray, String entity, Context context) {

        JSONObject jsonAux = null;
        boolean precessOK = true;
        try {
            if (deleteEntity(entity, context)) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonAux = jsonArray.getJSONObject(i);

                    if (!populateEntity(entity, jsonAux, context)) {
                        precessOK = false;
                        break;
                    }
                }
            } else {
                precessOK = false;
            }
        } catch (JSONException e) {
            precessOK = false;

        }
        return true;
    }


    public boolean deleteEntity(String entity, Context context) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        boolean deleteOk = true;
        switch (entity) {
            //INSTITUCION
            case SplashUsuario.ARTICULO: {
                if (!controladorUsuarioAdeful.eliminarArticuloUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.COMISION: {
                if (!controladorUsuarioAdeful.eliminarComisionUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.DIRECCION: {
                if (!controladorUsuarioAdeful.eliminarDireccionUsuario())
                    deleteOk = false;
                break;
            }
            //LIGA
            case SplashUsuario.EQUIPO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarEquipoUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.TORNEO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarTorneoUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.CANCHA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarCanchaUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.DIVISION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarDivisionUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case SplashUsuario.FIXTURE_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarFixtureUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.JUGADOR_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarJugadorUsuarioAdeful())
                    deleteOk = false;
                break;
            }
//            case SplashUsuario.POSICION_ADEFUL: {
//                if (!controladorUsuarioAdeful.eliminarPosicionUsuarioAdeful())
//                    deleteOk = false;
//                break;
//            }
            case SplashUsuario.ENTRENAMIENTO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.ENTRENAMIENTO_DIVISION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarDivisionEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
        }
        return deleteOk;
    }

    public boolean populateEntity(String entity, JSONObject jsonAux, Context context) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        boolean insertOk = true;
        switch (entity) {
            //INSTITUCION
            case SplashUsuario.ARTICULO: {
                try {
                    Articulo articulo = new Articulo(jsonAux.getInt("ID_ARTICULO"),
                            jsonAux.getString("TITULO"), jsonAux.getString("ARTICULO"));

                    if (!controladorUsuarioAdeful.insertArticuloUsuario(articulo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.COMISION: {
                try {
                    String imageString = jsonAux.getString("FOTO_COMISION");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Comision comision = new Comision(jsonAux.getInt("ID_COMISION"),
                            jsonAux.getString("NOMBRE_COMISION"), byteArray, jsonAux.getString("CARGO"), jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"));

                    if (!controladorUsuarioAdeful.insertComisionUsuario(comision))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.DIRECCION: {
                try {
                    String imageString = jsonAux.getString("FOTO_DIRECCION");
                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Direccion direccion = new Direccion(jsonAux.getInt("ID_DIRECCION"),
                            jsonAux.getString("NOMBRE_DIRECCION"), byteArray, jsonAux.getString("CARGO"), jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"));

                    if (!controladorUsuarioAdeful.insertDireccionUsuario(direccion))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //LIGA
            case SplashUsuario.EQUIPO_ADEFUL: {
                try {
                    String imageString = jsonAux.getString("ESCUDO_EQUIPO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                            jsonAux.getString("NOMBRE_EQUIPO"), byteArray);

                    if (!controladorUsuarioAdeful.insertEquipoUsuario(equipo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.TORNEO_ADEFUL: {
                try {

                    Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"));

                    if (!controladorUsuarioAdeful.insertTorneoUsuario(torneo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.CANCHA_ADEFUL: {
                try {
                    Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"));

                    if (!controladorUsuarioAdeful.insertCanchaUsuarioAdeful(cancha))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.DIVISION_ADEFUL: {
                try {
                    Division division = new Division(jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getString("DESCRIPCION"));

                    if (!controladorUsuarioAdeful.insertDivisionUsuarioAdeful(division))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case SplashUsuario.FIXTURE_ADEFUL: {
                try {

                    Fixture fixture = new Fixture(jsonAux.getInt("ID_FIXTURE"),
                            jsonAux.getInt("ID_EQUIPO_LOCAL"),
                            jsonAux.getInt("ID_EQUIPO_VISITA"),
                            jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getInt("ID_CANCHA"), jsonAux.getString("FECHA"),
                            jsonAux.getString("ANIO"), jsonAux.getString("DIA"),
                            jsonAux.getString("HORA"), jsonAux.getString("RESULTADO_LOCAL"),
                            jsonAux.getString("RESULTADO_VISITA"));

                    if (!controladorUsuarioAdeful.insertFixtureUsuarioAdeful(fixture))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.JUGADOR_ADEFUL: {
                try {
                    String imageString = jsonAux.getString("FOTO_JUGADOR");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Jugador jugador = new Jugador(jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getString("NOMBRE_JUGADOR"), byteArray, jsonAux.getInt("ID_DIVISION"), jsonAux.getString("DIVISION"),
                            jsonAux.getString("POSICION"));

                    if (!controladorUsuarioAdeful.insertJugadorUsuarioAdeful(jugador))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
//            case SplashUsuario.POSICION_ADEFUL: {
//                try {
//                    Posicion posicion = new Posicion(jsonAux.getInt("ID_POSICION"),
//                            jsonAux.getString("DESCRIPCION"));
//
//                    if (!controladorUsuarioAdeful.insertPosicionUsuarioAdeful(posicion))
//                        insertOk = false;
//
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
            //MI EQUIPO
            case SplashUsuario.ENTRENAMIENTO_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getString("DIA_ENTRENAMIENTO"),
                            jsonAux.getString("HORA_ENTRENAMIENTO"), jsonAux.getString("NOMBRE"));

                    if (!controladorUsuarioAdeful.insertEntrenamientoUsuarioAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case SplashUsuario.ENTRENAMIENTO_DIVISION_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(true, jsonAux.getInt("ID_ENTRENAMIENTO_DIVISION"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_DIVISION"));

                    if (!controladorUsuarioAdeful.insertEntrenamientoDivisionUsuarioAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
        }
        return insertOk;
    }

    public static Bitmap getBitmap(String url) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bm = null;
        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        if (bmOptions != null)
            bm = loadBitmap(url, bmOptions);

        return bm;
    }

    public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (in != null)
                in.close();
        } catch (IOException e1) {
        }
        return bitmap;
    }

    private static InputStream OpenHttpConnection(String strURL)
            throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {
        }
        return inputStream;
    }

    public byte[] parseBitmapToByte(Bitmap b) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
