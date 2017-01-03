package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.navegador.administrador.SplashAdm;

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

/**
 * Created by LEO on 17/4/2016.
 */
public class JsonParsingAdm {

    static JSONObject jObj = null;
    static JSONArray jArray = null;

    public JsonParsingAdm() {
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

    public boolean processingJson(JSONArray jsonArray, String entity, Context context) {

        JSONObject jsonAux = null;
        boolean precessOK = true;
        if (jsonArray != null) {
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
        } else {
            precessOK = false;
        }
        return precessOK;
    }


    public boolean deleteEntity(String entity, Context context) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean deleteOk = true;
        switch (entity) {
            //TABLA ADEFUL
            case SplashAdm.TABLA_ADEFUL: {
                if (!controladorAdeful.eliminarTablaAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.TABLA_LIFUBA: {
                if (!controladorLifuba.eliminarTablaLifuba())
                    deleteOk = false;
                break;
            }
            //TABLA GENERAR
            case SplashAdm.TABLA_GENERAL: {
                if (!controladorGeneral.eliminarTablaGeneral())
                    deleteOk = false;
                break;
            }
            //INSTITUCION
            case SplashAdm.ARTICULO: {
                if (!controladorGeneral.eliminarArticulo())
                    deleteOk = false;
                break;
            }
            case SplashAdm.COMISION: {
                if (!controladorGeneral.eliminarComision())
                    deleteOk = false;
                break;
            }
            case SplashAdm.DIRECCION: {
                if (!controladorGeneral.eliminarDireccion())
                    deleteOk = false;
                break;
            }
            case SplashAdm.CARGO: {
                if (!controladorGeneral.eliminarCargo())
                    deleteOk = false;
                break;
            }
            //LIGA
            case SplashAdm.EQUIPO_ADEFUL: {
                if (!controladorAdeful.eliminarEquipoAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.EQUIPO_LIFUBA: {
                if (!controladorLifuba.eliminarEquipoLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.TORNEO_ADEFUL: {
                if (!controladorAdeful.eliminarTorneoAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.TORNEO_LIFUBA: {
                if (!controladorLifuba.eliminarTorneoLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.CANCHA_ADEFUL: {
                if (!controladorAdeful.eliminarCanchaAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.CANCHA_LIFUBA: {
                if (!controladorLifuba.eliminarCanchaLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.DIVISION_ADEFUL: {
                if (!controladorAdeful.eliminarDivisionAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarDivisionLifuba())
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case SplashAdm.FIXTURE_ADEFUL: {
                if (!controladorAdeful.eliminarFixtureAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.FIXTURE_LIFUBA: {
                if (!controladorLifuba.eliminarFixtureLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.JUGADOR_ADEFUL: {
                if (!controladorAdeful.eliminarJugadorAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarJugadorLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.POSICION_ADEFUL: {
                if (!controladorAdeful.eliminarPosicionAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarPosicionLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.ENTRENAMIENTO_ADEFUL: {
                if (!controladorAdeful.eliminarEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.ENTRENAMIENTO_DIVISION_ADEFUL: {
                if (!controladorAdeful.eliminarDivisionEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                if (!controladorAdeful.eliminarAsistenciaEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.SANCION_ADEFUL: {
                if (!controladorAdeful.eliminarSancionAdeful())
                    deleteOk = false;
                break;
            }
            case SplashAdm.SANCION_LIFUBA: {
                if (!controladorLifuba.eliminarSancionLifuba())
                    deleteOk = false;
                break;
            }
            case SplashAdm.NOTIFICACION: {
                if (!controladorGeneral.eliminarNotificacion())
                    deleteOk = false;
                break;
            }
            case SplashAdm.NOTICIA: {
                if (!controladorGeneral.eliminarNoticia())
                    deleteOk = false;
                break;
            }
            case SplashAdm.FOTO: {
                if (!controladorGeneral.eliminarFoto())
                    deleteOk = false;
                break;
            }
            case SplashAdm.PUBLICIDAD: {
                if (!controladorGeneral.eliminarPublicidad())
                    deleteOk = false;
                break;
            }
            case SplashAdm.USUARIO: {
                if (!controladorGeneral.eliminarUsuario())
                    deleteOk = false;
                break;
            }
            case SplashAdm.PERMISO: {
                if (!controladorGeneral.eliminarPermiso())
                    deleteOk = false;
                break;
            }
            case SplashAdm.PERMISO_MODULO: {
                if (!controladorGeneral.eliminarPermisoModulo())
                    deleteOk = false;
                break;
            }
        }
        return deleteOk;
    }

    public boolean processingJson(String entity, Context context) {
        boolean precessOK = true;
        if (!deleteEntity(entity, context))
            precessOK = false;
        return precessOK;
    }

    public boolean populateEntity(String entity, JSONObject jsonAux, Context context) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean insertOk = true;
        switch (entity) {
            //TABLA
            case SplashAdm.TABLA_ADEFUL: {
                try {
                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));

                    if (!controladorAdeful.insertTabla(tabla))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.TABLA_LIFUBA: {
                try {
                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));

                    if (!controladorLifuba.insertTabla(tabla))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.TABLA_GENERAL: {
                try {
                    Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                            jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));

                    if (!controladorGeneral.insertTabla(tabla))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //INSTITUCION
            case SplashAdm.ARTICULO: {
                try {
                    Articulo articulo = new Articulo(jsonAux.getInt("ID_ARTICULO"),
                            jsonAux.getString("TITULO"), jsonAux.getString("ARTICULO"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertArticulo(articulo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.COMISION: {
                try {
                    String imageString = jsonAux.getString("FOTO_COMISION");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Comision comision = new Comision(jsonAux.getInt("ID_COMISION"),
                            jsonAux.getString("NOMBRE_COMISION"), byteArray, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getInt("ID_CARGO"), "",
                            jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"), jsonAux.getString("FOTO_COMISION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertComision(comision))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.DIRECCION: {
                try {
                    String imageString = jsonAux.getString("FOTO_DIRECCION");
                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Direccion direccion = new Direccion(jsonAux.getInt("ID_DIRECCION"),
                            jsonAux.getString("NOMBRE_DIRECCION"), byteArray, jsonAux.getString("NOMBRE_FOTO"),
                            jsonAux.getInt("ID_CARGO"), "", jsonAux.getString("PERIODO_DESDE"),
                            jsonAux.getString("PERIODO_HASTA"), jsonAux.getString("FOTO_DIRECCION"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertDireccion(direccion))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.CARGO: {
                try {

                    Cargo cargo = new Cargo(jsonAux.getInt("ID_CARGO"),
                            jsonAux.getString("CARGO"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertCargo(cargo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //LIGA
            case SplashAdm.EQUIPO_ADEFUL: {
                try {
                    String imageString = jsonAux.getString("ESCUDO_EQUIPO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                            jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("NOMBRE_ESCUDO"), byteArray,
                            jsonAux.getString("ESCUDO_EQUIPO"),
                            jsonAux.getString("USUARIO_CREADOR"), jsonAux.getString("FECHA_CREACION"),
                            jsonAux.getString("USUARIO_ACTUALIZACION"), jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertEquipoAdeful(equipo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.EQUIPO_LIFUBA: {
                try {
                    String imageString = jsonAux.getString("ESCUDO_EQUIPO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                            jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("NOMBRE_ESCUDO"), byteArray,
                            jsonAux.getString("ESCUDO_EQUIPO"),
                            jsonAux.getString("USUARIO_CREADOR"), jsonAux.getString("FECHA_CREACION"),
                            jsonAux.getString("USUARIO_ACTUALIZACION"), jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorLifuba.insertEquipoLifuba(equipo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.TORNEO_ADEFUL: {
                try {

                    Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getInt("ACTUAL") > 0, jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertTorneoAdeful(torneo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.TORNEO_LIFUBA: {
                try {

                    Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getInt("ACTUAL") > 0, jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorLifuba.insertTorneoLifuba(torneo))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.CANCHA_ADEFUL: {
                try {
                    Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertCanchaAdeful(cancha))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.CANCHA_LIFUBA: {
                try {
                    Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorLifuba.insertCanchaLifuba(cancha))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.DIVISION_ADEFUL: {
                try {
                    Division division = new Division(jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertDivisionAdeful(division))
                        insertOk = false;
                    if (!controladorLifuba.insertDivisionLifuba(division))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case SplashAdm.FIXTURE_ADEFUL: {
                try {

                    Fixture fixture = new Fixture(jsonAux.getInt("ID_FIXTURE"),
                            jsonAux.getInt("ID_EQUIPO_LOCAL"),
                            jsonAux.getInt("ID_EQUIPO_VISITA"),
                            jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getInt("ID_CANCHA"), jsonAux.getInt("ID_FECHA"),
                            jsonAux.getInt("ID_ANIO"), jsonAux.getString("DIA"),
                            jsonAux.getString("HORA"), jsonAux.getString("RESULTADO_LOCAL"),
                            jsonAux.getString("RESULTADO_VISITA"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertFixtureAdeful(fixture))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.FIXTURE_LIFUBA: {
                try {

                    Fixture fixture = new Fixture(jsonAux.getInt("ID_FIXTURE"),
                            jsonAux.getInt("ID_EQUIPO_LOCAL"),
                            jsonAux.getInt("ID_EQUIPO_VISITA"),
                            jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getInt("ID_CANCHA"), jsonAux.getInt("ID_FECHA"),
                            jsonAux.getInt("ID_ANIO"), jsonAux.getString("DIA"),
                            jsonAux.getString("HORA"), jsonAux.getString("RESULTADO_LOCAL"),
                            jsonAux.getString("RESULTADO_VISITA"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorLifuba.insertFixtureLifuba(fixture))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.JUGADOR_ADEFUL: {
                try {
                    String imageString = jsonAux.getString("FOTO_JUGADOR");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);

                    Jugador jugador = new Jugador(jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getString("NOMBRE_JUGADOR"), byteArray, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("FOTO_JUGADOR"),
                            jsonAux.getInt("ID_DIVISION"), jsonAux.getInt("ID_POSICION"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertJugadorAdeful(jugador))
                        insertOk = false;
                    if (!controladorLifuba.insertJugadorLifuba(jugador))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //POSICION
            case SplashAdm.POSICION_ADEFUL: {
                try {

                    Posicion posicion = new Posicion(jsonAux.getInt("ID_POSICION"),
                            jsonAux.getString("DESCRIPCION"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertPosicionAdeful(posicion))
                        insertOk = false;
                    if (!controladorLifuba.insertPosicionLifuba(posicion))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ENTRENAMIENTO
            case SplashAdm.ENTRENAMIENTO_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getString("DIA_ENTRENAMIENTO"),
                            jsonAux.getString("HORA_ENTRENAMIENTO"), jsonAux.getInt("ID_CANCHA"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertEntrenamientoAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case SplashAdm.ENTRENAMIENTO_DIVISION_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(true, jsonAux.getInt("ID_ENTRENAMIENTO_DIVISION"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_DIVISION"));

                    if (!controladorAdeful.insertEntrenamientoDivisionAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ASISTENCIA
            case SplashAdm.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                try {

                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO_ASISTENCIA"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_JUGADOR"), "");

                    if (!controladorAdeful.insertAsistenciaEntrenamientoAdeful(entrenamiento))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SANCION
            case SplashAdm.SANCION_ADEFUL: {
                try {

                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorAdeful.insertSancionAdeful(sancion))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.SANCION_LIFUBA: {
                try {

                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorLifuba.insertSancionLifuba(sancion))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SOCIAL
            case SplashAdm.NOTIFICACION: {
                try {

                    Notificacion notificacion = new Notificacion(jsonAux.getInt("ID_NOTIFICACION"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("NOTIFICACION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertNotificacion(notificacion))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.NOTICIA: {
                try {

                    Noticia noticia = new Noticia(jsonAux.getInt("ID_NOTICIA"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("DESCRIPCION"),
                            jsonAux.getString("LINK"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertNoticia(noticia))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.FOTO: {
                try {
                    String imageString = jsonAux.getString("FOTO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);
                    Foto foto = new Foto(jsonAux.getInt("ID_FOTO"),
                            jsonAux.getString("TITULO"),
                            byteArray, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("FOTO"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertFoto(foto))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.PUBLICIDAD: {
                try {
                    String imageString = jsonAux.getString("LOGO");

                    Bitmap b = getBitmap(imageString);
                    byte[] byteArray = null;
                    if (b != null)
                        byteArray = parseBitmapToByte(b);
                    Publicidad publicidad = new Publicidad(jsonAux.getInt("ID_PUBLICIDAD"),
                            jsonAux.getString("TITULO"),
                            byteArray, jsonAux.getString("OTROS"), jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("LOGO"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertPublicidad(publicidad))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.USUARIO: {
                try {

                    Usuario usuario = new Usuario(jsonAux.getInt("ID_USUARIO"),
                            jsonAux.getString("USUARIO"),
                            jsonAux.getString("PASSWORD"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertUsuario(usuario))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.PERMISO: {
                try {

                    Permiso permiso = new Permiso(jsonAux.getInt("ID_PERMISO"),
                            jsonAux.getInt("ID_USUARIO"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));

                    if (!controladorGeneral.insertPermisos(permiso))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case SplashAdm.PERMISO_MODULO: {
                try {

                    Permiso permiso_modulo = new Permiso(jsonAux.getInt("ID_PERMISO_MODULO"),
                            jsonAux.getInt("ID_PERMISO"), jsonAux.getInt("ID_MODULO"), jsonAux.getInt("ID_SUBMODULO"));

                    if (!controladorGeneral.insertPermisoModulo(permiso_modulo))
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
