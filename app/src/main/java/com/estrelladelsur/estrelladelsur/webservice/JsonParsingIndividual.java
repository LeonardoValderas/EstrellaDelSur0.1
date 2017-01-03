package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.database.usuario.general.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEO on 17/4/2016.
 */
public class JsonParsingIndividual {

    static JSONObject jObj = null;
    static JSONArray jArray = null;

    public JsonParsingIndividual() {
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
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return null;
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

    public boolean processingJson(JSONArray jsonArray, String entity, Context context, String fecha) {

        JSONObject jsonAux = null;
        boolean precessOK = true;
        if (jsonArray != null) {
            try {
                if (deleteEntity(entity, context)) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonAux = jsonArray.getJSONObject(i);

                        if (!populateEntity(entity, jsonAux, context, fecha)) {
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
        } else if (entity.equals(SplashUsuario.ENTRENAMIENTO_CANTIDAD_ADEFUL)) {
            populateCantidadEntrenamiento(entity, context);
        } else {
            precessOK = false;
        }
        return precessOK;
    }

    public boolean processingJson(String entity, Context context) {
        boolean precessOK = true;
        if (!deleteEntity(entity, context))
            precessOK = false;
        return precessOK;
    }


    public boolean deleteEntity(String entity, Context context) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        ControladorUsuarioLifuba controladorUsuarioLifuba = new ControladorUsuarioLifuba(context);
        ControladorUsuarioGeneral controladorUsuarioGeneral = new ControladorUsuarioGeneral(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean deleteOk = true;
        switch (entity) {
            //TABLA ADEFUL
//            case SplashUsuario.TABLA_ADEFUL: {
//                if (!controladorUsuarioAdeful.eliminarTablaAdeful())
//                    deleteOk = false;
//                break;
//            }
//            case SplashUsuario.TABLA_LIFUBA: {
//                if (!controladorUsuarioLifuba.eliminarTablaLifuba())
//                    deleteOk = false;
//                break;
//            }
//            //TABLA GENERAR
//            case SplashUsuario.TABLA_GENERAL: {
//                if (!controladorUsuarioGeneral.eliminarTablaAdeful())
//                    deleteOk = false;
//                break;
//            }
            //INSTITUCION
            case SplashUsuario.ARTICULO: {
                if (!controladorUsuarioGeneral.eliminarArticuloUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.COMISION: {
                if (!controladorUsuarioGeneral.eliminarComisionUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.DIRECCION: {
                if (!controladorUsuarioGeneral.eliminarDireccionUsuario())
                    deleteOk = false;
                break;
            }
            //LIGA
            case SplashUsuario.EQUIPO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarEquipoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.EQUIPO_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarEquipoUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.TORNEO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarTorneoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.TORNEO_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarTorneoUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.CANCHA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarCanchaUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.CANCHA_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarCanchaUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.DIVISION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarDivisionUsuarioAdeful())
                    deleteOk = false;
                if (!controladorUsuarioLifuba.eliminarDivisionUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case SplashUsuario.FIXTURE_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarFixtureUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.FIXTURE_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarFixtureUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.JUGADOR_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarJugadorUsuarioAdeful())
                    deleteOk = false;
                if (!controladorUsuarioLifuba.eliminarJugadorUsuarioLifuba())
                    deleteOk = false;
                break;
            }
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
            case SplashUsuario.ENTRENAMIENTO_CANTIDAD_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarCantidadEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarAsistenciaEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.SANCION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarSancionUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.SANCION_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarSancionUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.NOTIFICACION: {
                if (!controladorUsuarioGeneral.eliminarNotificacionUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.NOTICIA: {
                if (!controladorUsuarioGeneral.eliminarNoticiaUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.FOTO: {
                if (!controladorUsuarioGeneral.eliminarFotoUsuario())
                    deleteOk = false;
                break;
            }
            case SplashUsuario.PUBLICIDAD: {
                if (!controladorUsuarioGeneral.eliminarPublicidadUsuario())
                    deleteOk = false;
                break;
            }
//            case SplashUsuario.USUARIO: {
//                if (!controladorGeneral.eliminarUsuario())
//                    deleteOk = false;
//                break;
//            }
//            case SplashUsuario.MODULO: {
//                if (!controladorUsuarioGeneral.eliminarModuloUsuario())
//                    deleteOk = false;
//                if (!controladorGeneral.eliminarModulo())
//                    deleteOk = false;
//                break;
//            }
//            case SplashUsuario.SUBMODULO: {
//                if (!controladorUsuarioGeneral.eliminarSubmoduloUsuario())
//                    deleteOk = false;
//                if (!controladorGeneral.eliminarSubModulo())
//                    deleteOk = false;
//                break;
//            }
        }
        return deleteOk;
    }

    public boolean populateEntity(String entity, JSONObject jsonAux, Context context, String fecha) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        ControladorUsuarioGeneral controladorUsuarioGeneral = new ControladorUsuarioGeneral(context);
        ControladorUsuarioLifuba controladorUsuarioLifuba = new ControladorUsuarioLifuba(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean insertOk = true;
        switch (entity) {
            //TABLA
//            case SplashUsuario.TABLA_ADEFUL: {
//                try {
//                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
//                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
//
//                    if (!controladorUsuarioAdeful.insertTabla(tabla))
//                        insertOk = false;
//
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
//            case SplashUsuario.TABLA_LIFUBA: {
//                try {
//                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
//                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
//
//                    if (!controladorUsuarioLifuba.insertTabla(tabla))
//                        insertOk = false;
//
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
//            case SplashUsuario.TABLA_GENERAL: {
//                try {
//                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
//                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
//
//                    if (!controladorUsuarioGeneral.insertTabla(tabla))
//                        insertOk = false;
//
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
            //INSTITUCION
            case SplashUsuario.ARTICULO: {
                try {
                    Articulo articulo = new Articulo(jsonAux.getInt("ID_ARTICULO"),
                            jsonAux.getString("TITULO"), jsonAux.getString("ARTICULO"));

                    if (!controladorUsuarioGeneral.insertArticuloUsuario(articulo))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_ARTICULO, fecha, context))
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

                    if (!controladorUsuarioGeneral.insertComisionUsuario(comision))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_COMISION, fecha, context))
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

                    if (!controladorUsuarioGeneral.insertDireccionUsuario(direccion))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_DIRECCION, fecha, context))
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

                    if (!controladorUsuarioAdeful.insertEquipoUsuarioAdeful(equipo))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_EQUIPO_ADEFUL, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.EQUIPO_LIFUBA: {
                try {
                    String imageString = jsonAux.getString("ESCUDO_EQUIPO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                            jsonAux.getString("NOMBRE_EQUIPO"), byteArray);

                    if (!controladorUsuarioLifuba.insertEquipoUsuarioLifuba(equipo))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableLifuba(AsyncTaskGenericIndividual.TABLA_EQUIPO_LIFUBA, fecha, context))
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

                    if (!controladorUsuarioAdeful.insertTorneoUsuarioAdeful(torneo))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_TORNEO_ADEFUL, fecha, context))
                            insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.TORNEO_LIFUBA: {
                try {

                    Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"));

                    if (!controladorUsuarioLifuba.insertTorneoUsuarioLifuba(torneo))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableLifuba(AsyncTaskGenericIndividual.TABLA_TORNEO_LIFUBA, fecha, context))
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
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_CANCHA_ADEFUL, fecha, context))
                            insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.CANCHA_LIFUBA: {
                try {
                    Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"));

                    if (!controladorUsuarioLifuba.insertCanchaUsuarioLifuba(cancha))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableLifuba(AsyncTaskGenericIndividual.TABLA_CANCHA_LIFUBA, fecha, context))
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
                    if (!controladorUsuarioLifuba.insertDivisionUsuarioLifuba(division))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_DIVISION_ADEFUL, fecha, context))
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
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_FIXTURE_ADEFUL, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.FIXTURE_LIFUBA: {
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

                    if (!controladorUsuarioLifuba.insertFixtureUsuarioLifuba(fixture))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableLifuba(AsyncTaskGenericIndividual.TABLA_FIXTURE_LIFUBA, fecha, context))
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
                    if (!controladorUsuarioLifuba.insertJugadorUsuarioLifuba(jugador))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_JUGADOR, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ENTRENAMIENTO
            case SplashUsuario.ENTRENAMIENTO_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getString("DIA_ENTRENAMIENTO"),
                            jsonAux.getString("HORA_ENTRENAMIENTO"), jsonAux.getString("NOMBRE"));

                    if (!controladorUsuarioAdeful.insertEntrenamientoUsuarioAdeful(entrenamiento))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_ENTRENAMIENTO, fecha, context))
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
//                    if(insertOk)
//                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_E, fecha, context))
//                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ENTRNEMIENTO
            case SplashUsuario.ENTRENAMIENTO_CANTIDAD_ADEFUL: {

                List<Division> divisions = new ArrayList<>();
                int cantidad = 0;
                divisions = controladorUsuarioAdeful.selectListaDivisionUsuarioAdeful();
                if (divisions != null) {
                    if (divisions.size() > 0) {
                        for (int i = 0; i < divisions.size(); i++) {
                            cantidad = controladorUsuarioAdeful.selectCountEntrenamientoCantidad(divisions.get(i).getID_DIVISION());
                            if (!controladorUsuarioAdeful.insertEntrenamientoCantidadUsuarioAdeful(divisions.get(i).getID_DIVISION(), cantidad)) {
                                insertOk = false;
                                break;
                            }
                        }
                    }
                }
                break;
            }
            //ASISTENCIA
            case SplashUsuario.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO_ASISTENCIA"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_JUGADOR"), "");

                    if (!controladorUsuarioAdeful.insertAsistenciaEntrenamientoUsuarioAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SANCION
            case SplashUsuario.SANCION_ADEFUL: {
                try {

                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"));

                    if (!controladorUsuarioAdeful.insertSancionUsuarioAdeful(sancion))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableAdeful(AsyncTaskGenericIndividual.TABLA_SANCION_ADEFUL, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.SANCION_LIFUBA: {
                try {

                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"));

                    if (!controladorUsuarioLifuba.insertSancionUsuarioLifuba(sancion))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableLifuba(AsyncTaskGenericIndividual.TABLA_SANCION_LIFUBA, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SOCIAL
            case SplashUsuario.NOTIFICACION: {
                try {

                    Notificacion notificacion = new Notificacion(jsonAux.getInt("ID_NOTIFICACION"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("NOTIFICACION"));

                    if (!controladorUsuarioGeneral.insertNotificacionUsuario(notificacion))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_NOTIFICACION, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.NOTICIA: {
                try {

                    Noticia noticia = new Noticia(jsonAux.getInt("ID_NOTICIA"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("DESCRIPCION"),
                            jsonAux.getString("LINK"));

                    if (!controladorUsuarioGeneral.insertNoticiaUsuario(noticia))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_NOTICIA, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.FOTO: {
                try {
                    String imageString = jsonAux.getString("FOTO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);
                    Foto foto = new Foto(jsonAux.getInt("ID_FOTO"),
                            jsonAux.getString("TITULO"),
                            byteArray);

                    if (!controladorUsuarioGeneral.insertFotoUsuario(foto))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_FOTO, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashUsuario.PUBLICIDAD: {
                try {
                    String imageString = jsonAux.getString("LOGO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);
                    Publicidad publicidad = new Publicidad(jsonAux.getInt("ID_PUBLICIDAD"),
                            jsonAux.getString("TITULO"),
                            byteArray, jsonAux.getString("OTROS"));

                    if (!controladorUsuarioGeneral.insertPublicidadUsuario(publicidad))
                        insertOk = false;
                    if (insertOk)
                        if (!updateTableXTableGral(AsyncTaskGenericIndividual.TABLA_PUBLICIDAD, fecha, context))
                            insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
//            case SplashUsuario.USUARIO: {
//                try {
//
//                    Usuario usuario = new Usuario(jsonAux.getInt("ID_USUARIO"),
//                            jsonAux.getString("USUARIO"),
//                            jsonAux.getString("PASSWORD"));
//
//                    if (!controladorGeneral.insertUsuario(usuario))
//                        insertOk = false;
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
//            case SplashUsuario.MODULO: {
//                try {
//
//                    Modulo modulo = new Modulo(jsonAux.getInt("ID_MODULO"),
//                            jsonAux.getString("NOMBRE"));
//
//                    if (!controladorUsuarioGeneral.insertModuloUsuario(modulo))
//                        insertOk = false;
//                    if (!controladorGeneral.insertModulo(modulo))
//                        insertOk = false;
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
//            case SplashUsuario.SUBMODULO: {
//                try {
//
//                    SubModulo subModulo = new SubModulo(jsonAux.getInt("ID_SUBMODULO"),
//                            jsonAux.getString("NOMBRE"),
//                            jsonAux.getInt("ID_MODULO"), jsonAux.getInt("ISSELECTED") > 0);
//
//                    if (!controladorUsuarioGeneral.insertSubModuloUsuario(subModulo))
//                        insertOk = false;
//                    if (!controladorGeneral.insertSubModulo(subModulo))
//                        insertOk = false;
//                } catch (JSONException e) {
//                    insertOk = false;
//                }
//                break;
//            }
        }
        return insertOk;
    }

    public boolean populateCantidadEntrenamiento(String entity, Context context) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        boolean insertOk = true;
        if (deleteEntity(entity, context)) {
            List<Division> divisions = new ArrayList<>();
            int cantidad = 0;
            divisions = controladorUsuarioAdeful.selectListaDivisionUsuarioAdeful();
            if (divisions != null) {
                if (divisions.size() > 0) {
                    for (int i = 0; i < divisions.size(); i++) {
                        cantidad = controladorUsuarioAdeful.selectCountEntrenamientoCantidad(divisions.get(i).getID_DIVISION());
                        if (!controladorUsuarioAdeful.insertEntrenamientoCantidadUsuarioAdeful(divisions.get(i).getID_DIVISION(), cantidad)) {
                            insertOk = false;
                            break;
                        }
                    }
                }
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

    public boolean updateTableXTableLifuba(String tabla, String fecha, Context context) {
        ControladorUsuarioLifuba controladorLifuba = new ControladorUsuarioLifuba(context);
        if (!controladorLifuba.actualizarTablaXTablaIndiv(tabla, fecha))
            return false;
        else
            return true;
    }

    public boolean updateTableXTableAdeful(String tabla, String fecha, Context context) {
        ControladorUsuarioAdeful controladorAdeful = new ControladorUsuarioAdeful(context);
        if (!controladorAdeful.actualizarTablaXTablaIndiv(tabla, fecha))
            return false;
        else
            return true;
    }

    public boolean updateTableXTableGral(String tabla, String fecha, Context context) {
        ControladorUsuarioGeneral controladorGeneral = new ControladorUsuarioGeneral(context);
        if (!controladorGeneral.actualizarTablaXTablaIndiv(tabla, fecha))
            return false;
        else
            return true;
    }
}
