package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        List<JSONObject> jsonsAux = new ArrayList<>();
        boolean precessOK = true;
        if (jsonArray != null) {
            try {
                if (deleteEntity(entity, context)) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonAux = jsonArray.getJSONObject(i);
                        jsonsAux.add(jsonAux);
                    }
                    if (!populateEntity(entity, jsonsAux, context)) {
                        precessOK = false;
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
            case Variable.TABLA_ADEFUL: {
                if (!controladorAdeful.eliminarTablaAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.TABLA_LIFUBA: {
                if (!controladorLifuba.eliminarTablaLifuba())
                    deleteOk = false;
                break;
            }
            //TABLA GENERAR
            case Variable.TABLA_GENERAL: {
                if (!controladorGeneral.eliminarTablaGeneral())
                    deleteOk = false;
                break;
            }
            //INSTITUCION
            case Variable.ARTICULO: {
                if (!controladorGeneral.eliminarArticulo())
                    deleteOk = false;
                break;
            }
            case Variable.COMISION: {
                if (!controladorGeneral.eliminarComision())
                    deleteOk = false;
                break;
            }
            case Variable.DIRECCION: {
                if (!controladorGeneral.eliminarDireccion())
                    deleteOk = false;
                break;
            }
            case Variable.CARGO: {
                if (!controladorGeneral.eliminarCargo())
                    deleteOk = false;
                break;
            }
            //LIGA
            case Variable.EQUIPO_ADEFUL: {
                if (!controladorAdeful.eliminarEquipoAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.EQUIPO_LIFUBA: {
                if (!controladorLifuba.eliminarEquipoLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.TORNEO_ADEFUL: {
                if (!controladorAdeful.eliminarTorneoAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.TORNEO_LIFUBA: {
                if (!controladorLifuba.eliminarTorneoLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.CANCHA_ADEFUL: {
                if (!controladorAdeful.eliminarCanchaAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.CANCHA_LIFUBA: {
                if (!controladorLifuba.eliminarCanchaLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.DIVISION_ADEFUL: {
                if (!controladorAdeful.eliminarDivisionAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarDivisionLifuba())
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case Variable.FIXTURE_ADEFUL: {
                if (!controladorAdeful.eliminarFixtureAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.FIXTURE_LIFUBA: {
                if (!controladorLifuba.eliminarFixtureLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.JUGADOR_ADEFUL: {
                if (!controladorAdeful.eliminarJugadorAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarJugadorLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.POSICION_ADEFUL: {
                if (!controladorAdeful.eliminarPosicionAdeful())
                    deleteOk = false;
                if (!controladorLifuba.eliminarPosicionLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_ADEFUL: {
                if (!controladorAdeful.eliminarEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_DIVISION_ADEFUL: {
                if (!controladorAdeful.eliminarDivisionEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                if (!controladorAdeful.eliminarAsistenciaEntrenamientoAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.SANCION_ADEFUL: {
                if (!controladorAdeful.eliminarSancionAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.SANCION_LIFUBA: {
                if (!controladorLifuba.eliminarSancionLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.NOTIFICACION: {
                if (!controladorGeneral.eliminarNotificacion())
                    deleteOk = false;
                break;
            }
            case Variable.NOTICIA: {
                if (!controladorGeneral.eliminarNoticia())
                    deleteOk = false;
                break;
            }
            case Variable.FOTO: {
                if (!controladorGeneral.eliminarFoto())
                    deleteOk = false;
                break;
            }
            case Variable.PUBLICIDAD: {
                if (!controladorGeneral.eliminarPublicidad())
                    deleteOk = false;
                break;
            }
            case Variable.USUARIO: {
                if (!controladorGeneral.eliminarUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.PERMISO: {
                if (!controladorGeneral.eliminarPermiso())
                    deleteOk = false;
                break;
            }
            case Variable.PERMISO_MODULO: {
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

    public boolean populateEntity(String entity, List<JSONObject> jsonsAux, Context context) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean insertOk = true;
        switch (entity) {
            //TABLA
            case Variable.TABLA_ADEFUL: {
                try {
                    List<Tabla> tablas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                                jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
                        tablas.add(tabla);
                    }

                    if (!controladorAdeful.insertTabla(tablas))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.TABLA_LIFUBA: {
                try {
                    List<Tabla> tablas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                                jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
                        tablas.add(tabla);
                    }
                    if (!controladorLifuba.insertTabla(tablas))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.TABLA_GENERAL: {
                try {
                    List<Tabla> tablas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Tabla tabla = new Tabla(jsonAux.getInt("ID_TABLA"),
                                jsonAux.getString("TABLA"), jsonAux.getString("FECHA"));
                        tablas.add(tabla);
                    }
                    if (!controladorGeneral.insertTabla(tablas))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //INSTITUCION
            case Variable.ARTICULO: {
                try {
                    List<Articulo> articulos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Articulo articulo = new Articulo(jsonAux.getInt("ID_ARTICULO"),
                                jsonAux.getString("TITULO"), jsonAux.getString("ARTICULO"), jsonAux.getString("USUARIO_CREADOR"),
                                jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                                jsonAux.getString("FECHA_ACTUALIZACION"));
                        articulos.add(articulo);
                    }
                    if (!controladorGeneral.insertArticulo(articulos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.COMISION: {
                try {
                    List<Comision> comisiones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Comision comision = new Comision(jsonAux.getInt("ID_COMISION"),
                                jsonAux.getString("NOMBRE_COMISION"), null, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getInt("ID_CARGO"), "",
                                jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"), jsonAux.getString("FOTO_COMISION"), jsonAux.getString("USUARIO_CREADOR"),
                                jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                                jsonAux.getString("FECHA_ACTUALIZACION"));
                        comisiones.add(comision);
                    }
                    if (!controladorGeneral.insertComision(comisiones))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.DIRECCION: {
                try {
                    List<Direccion> direcciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Direccion direccion = new Direccion(jsonAux.getInt("ID_DIRECCION"),
                                jsonAux.getString("NOMBRE_DIRECCION"), null, jsonAux.getString("NOMBRE_FOTO"),
                                jsonAux.getInt("ID_CARGO"), "", jsonAux.getString("PERIODO_DESDE"),
                                jsonAux.getString("PERIODO_HASTA"), jsonAux.getString("FOTO_DIRECCION"),
                                jsonAux.getString("USUARIO_CREADOR"),
                                jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                                jsonAux.getString("FECHA_ACTUALIZACION"));
                        direcciones.add(direccion);
                    }
                    if (!controladorGeneral.insertDireccion(direcciones))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.CARGO: {
                try {
                    List<Cargo> cargos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Cargo cargo = new Cargo(jsonAux.getInt("ID_CARGO"),
                                jsonAux.getString("CARGO"),
                                jsonAux.getString("USUARIO_CREADOR"),
                                jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                                jsonAux.getString("FECHA_ACTUALIZACION"));
                        cargos.add(cargo);
                    }
                    if (!controladorGeneral.insertCargo(cargos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //LIGA
            case Variable.EQUIPO_ADEFUL: {
                try {
                    List<Equipo> equipos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                                jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("NOMBRE_ESCUDO"), null,
                                jsonAux.getString("ESCUDO_EQUIPO"),
                                jsonAux.getString("USUARIO_CREADOR"), jsonAux.getString("FECHA_CREACION"),
                                jsonAux.getString("USUARIO_ACTUALIZACION"), jsonAux.getString("FECHA_ACTUALIZACION"));
                        equipos.add(equipo);
                    }
                    if (!controladorAdeful.insertEquipoAdeful(equipos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.EQUIPO_LIFUBA: {
                try {
                    List<Equipo> equipos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                            jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("NOMBRE_ESCUDO"), null,
                            jsonAux.getString("ESCUDO_EQUIPO"),
                            jsonAux.getString("USUARIO_CREADOR"), jsonAux.getString("FECHA_CREACION"),
                            jsonAux.getString("USUARIO_ACTUALIZACION"), jsonAux.getString("FECHA_ACTUALIZACION"));
                        equipos.add(equipo);
                    }
                    if (!controladorLifuba.insertEquipoLifuba(equipos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.TORNEO_ADEFUL: {
                try {
                    List<Torneo> torneos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getInt("ACTUAL") > 0, jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        torneos.add(torneo);
                    }
                    if (!controladorAdeful.insertTorneoAdeful(torneos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.TORNEO_LIFUBA: {
                try {
                    List<Torneo> torneos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getInt("ACTUAL") > 0, jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        torneos.add(torneo);
                    }
                    if (!controladorLifuba.insertTorneoLifuba(torneos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.CANCHA_ADEFUL: {
                try {
                    List<Cancha> canchas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        canchas.add(cancha);
                    }
                    if (!controladorAdeful.insertCanchaAdeful(canchas))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.CANCHA_LIFUBA: {
                try {
                    List<Cancha> canchas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                            jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        canchas.add(cancha);
                    }
                    if (!controladorLifuba.insertCanchaLifuba(canchas))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.DIVISION_ADEFUL: {
                try {
                    List<Division> divisiones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Division division = new Division(jsonAux.getInt("ID_DIVISION"),
                            jsonAux.getString("DESCRIPCION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        divisiones.add(division);
                    }
                    if (!controladorAdeful.insertDivisionAdeful(divisiones))
                        insertOk = false;
                    if (!controladorLifuba.insertDivisionLifuba(divisiones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case Variable.FIXTURE_ADEFUL: {
                try {
                    List<Fixture> fixtures = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
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
                        fixtures.add(fixture);
                    }
                    if (!controladorAdeful.insertFixtureAdeful(fixtures))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.FIXTURE_LIFUBA: {
                try {
                    List<Fixture> fixtures = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
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
                        fixtures.add(fixture);
                    }
                    if (!controladorLifuba.insertFixtureLifuba(fixtures))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.JUGADOR_ADEFUL: {
                try {
                    List<Jugador> jugadores = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Jugador jugador = new Jugador(jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getString("NOMBRE_JUGADOR"), null, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("FOTO_JUGADOR"),
                            jsonAux.getInt("ID_DIVISION"), jsonAux.getInt("ID_POSICION"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        jugadores.add(jugador);
                    }
                    if (!controladorAdeful.insertJugadorAdeful(jugadores))
                        insertOk = false;
                    if (!controladorLifuba.insertJugadorLifuba(jugadores))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //POSICION
            case Variable.POSICION_ADEFUL: {
                try {
                    List<Posicion> posiciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Posicion posicion = new Posicion(jsonAux.getInt("ID_POSICION"),
                            jsonAux.getString("DESCRIPCION"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        posiciones.add(posicion);
                    }
                    if (!controladorAdeful.insertPosicionAdeful(posiciones))
                        insertOk = false;
                    if (!controladorLifuba.insertPosicionLifuba(posiciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ENTRENAMIENTO
            case Variable.ENTRENAMIENTO_ADEFUL: {
                try {
                    List<Entrenamiento> entrenamientos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getString("DIA_ENTRENAMIENTO"),
                            jsonAux.getString("HORA_ENTRENAMIENTO"), jsonAux.getInt("ID_CANCHA"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorAdeful.insertEntrenamientoAdeful(entrenamientos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            case Variable.ENTRENAMIENTO_DIVISION_ADEFUL: {
                try {
                    List<Entrenamiento> entrenamientos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Entrenamiento entrenamiento = new Entrenamiento(true, jsonAux.getInt("ID_ENTRENAMIENTO_DIVISION"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_DIVISION"));
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorAdeful.insertEntrenamientoDivisionAdeful(entrenamientos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ASISTENCIA
            case Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                try {
                    List<Entrenamiento> entrenamientos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO_ASISTENCIA"),
                            jsonAux.getInt("ID_ENTRENAMIENTO"),
                            jsonAux.getInt("ID_JUGADOR"), "");
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorAdeful.insertAsistenciaEntrenamientoAdeful(entrenamientos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SANCION
            case Variable.SANCION_ADEFUL: {
                try {
                    List<Sancion> sanciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        sanciones.add(sancion);
                    }
                    if (!controladorAdeful.insertSancionAdeful(sanciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.SANCION_LIFUBA: {
                try {
                    List<Sancion> sanciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                            jsonAux.getInt("ID_JUGADOR"),
                            jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA")
                            , jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        sanciones.add(sancion);
                    }
                    if (!controladorLifuba.insertSancionLifuba(sanciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SOCIAL
            case Variable.NOTIFICACION: {
                try {
                    List<Notificacion> notificaciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Notificacion notificacion = new Notificacion(jsonAux.getInt("ID_NOTIFICACION"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("NOTIFICACION"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        notificaciones.add(notificacion);
                    }
                    if (!controladorGeneral.insertNotificacion(notificaciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.NOTICIA: {
                try {
                    List<Noticia> noticias = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Noticia noticia = new Noticia(jsonAux.getInt("ID_NOTICIA"),
                            jsonAux.getString("TITULO"),
                            jsonAux.getString("DESCRIPCION"),
                            jsonAux.getString("LINK"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        noticias.add(noticia);
                    }
                    if (!controladorGeneral.insertNoticia(noticias))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.FOTO: {
                try {
                    List<Foto> fotos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Foto foto = new Foto(jsonAux.getInt("ID_FOTO"),
                            jsonAux.getString("TITULO"),
                            null, jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("FOTO"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        fotos.add(foto);
                    }
                    if (!controladorGeneral.insertFoto(fotos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.PUBLICIDAD: {
                try {
                    List<Publicidad> publicidades = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                      Publicidad publicidad = new Publicidad(jsonAux.getInt("ID_PUBLICIDAD"),
                            jsonAux.getString("TITULO"),
                            null, jsonAux.getString("OTROS"), jsonAux.getString("NOMBRE_FOTO"), jsonAux.getString("LOGO"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        publicidades.add(publicidad);
                    }
                    if (!controladorGeneral.insertPublicidad(publicidades))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.USUARIO: {
                try {
                    List<Usuario> usuarios = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Usuario usuario = new Usuario(jsonAux.getInt("ID_USUARIO"),
                            jsonAux.getString("USUARIO"),
                            jsonAux.getString("PASSWORD"), jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        usuarios.add(usuario);
                    }
                    if (!controladorGeneral.insertUsuario(usuarios))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.PERMISO: {
                try {
                    List<Permiso> permisos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Permiso permiso = new Permiso(jsonAux.getInt("ID_PERMISO"),
                            jsonAux.getInt("ID_USUARIO"),
                            jsonAux.getString("USUARIO_CREADOR"),
                            jsonAux.getString("FECHA_CREACION"), jsonAux.getString("USUARIO_ACTUALIZACION"),
                            jsonAux.getString("FECHA_ACTUALIZACION"));
                        permisos.add(permiso);
                    }
                    if (!controladorGeneral.insertPermisos(permisos))
                        insertOk = false;

                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.PERMISO_MODULO: {
                try {
                    List<Permiso> permisos_modulo = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                    Permiso permiso_modulo = new Permiso(jsonAux.getInt("ID_PERMISO_MODULO"),
                            jsonAux.getInt("ID_PERMISO"), jsonAux.getInt("ID_MODULO"), jsonAux.getInt("ID_SUBMODULO"));
                        permisos_modulo.add(permiso_modulo);
                    }

                    if (!controladorGeneral.insertPermisoModulo(permisos_modulo))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
        }
        return insertOk;
    }
}
