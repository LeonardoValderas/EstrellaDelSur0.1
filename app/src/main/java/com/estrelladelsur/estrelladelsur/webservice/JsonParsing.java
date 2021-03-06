package com.estrelladelsur.estrelladelsur.webservice;

import android.content.Context;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
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
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by LEO on 17/4/2016.
 */
public class JsonParsing {

    static JSONObject jObj = null;
    static JSONArray jArray = null;

    public JsonParsing() {
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
        } else if (entity.equals(Variable.ENTRENAMIENTO_CANTIDAD_ADEFUL)) {
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
            case Variable.TABLA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarTablaAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.TABLA_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarTablaLifuba())
                    deleteOk = false;
                break;
            }
            //TABLA GENERAR
            case Variable.TABLA_GENERAL: {
                if (!controladorUsuarioGeneral.eliminarTablaAdeful())
                    deleteOk = false;
                break;
            }
            //INSTITUCION
            case Variable.ARTICULO: {
                if (!controladorUsuarioGeneral.eliminarArticuloUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.COMISION: {
                if (!controladorUsuarioGeneral.eliminarComisionUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.DIRECCION: {
                if (!controladorUsuarioGeneral.eliminarDireccionUsuario())
                    deleteOk = false;
                break;
            }
            //LIGA
            case Variable.EQUIPO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarEquipoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.EQUIPO_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarEquipoUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.TORNEO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarTorneoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.TORNEO_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarTorneoUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.CANCHA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarCanchaUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.CANCHA_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarCanchaUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.DIVISION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarDivisionUsuarioAdeful())
                    deleteOk = false;
                if (!controladorUsuarioLifuba.eliminarDivisionUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case Variable.FIXTURE_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarFixtureUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.FIXTURE_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarFixtureUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.JUGADOR_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarJugadorUsuarioAdeful())
                    deleteOk = false;
                if (!controladorUsuarioLifuba.eliminarJugadorUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_DIVISION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarDivisionEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_CANTIDAD_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarCantidadEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarAsistenciaEntrenamientoUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.SANCION_ADEFUL: {
                if (!controladorUsuarioAdeful.eliminarSancionUsuarioAdeful())
                    deleteOk = false;
                break;
            }
            case Variable.SANCION_LIFUBA: {
                if (!controladorUsuarioLifuba.eliminarSancionUsuarioLifuba())
                    deleteOk = false;
                break;
            }
            case Variable.NOTIFICACION: {
                if (!controladorUsuarioGeneral.eliminarNotificacionUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.NOTICIA: {
                if (!controladorUsuarioGeneral.eliminarNoticiaUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.FOTO: {
                if (!controladorUsuarioGeneral.eliminarFotoUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.PUBLICIDAD: {
                if (!controladorUsuarioGeneral.eliminarPublicidadUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.USUARIO: {
                if (!controladorGeneral.eliminarUsuario())
                    deleteOk = false;
                break;
            }
            case Variable.MODULO: {
                if (!controladorUsuarioGeneral.eliminarModuloUsuario())
                    deleteOk = false;
                if (!controladorGeneral.eliminarModulo())
                    deleteOk = false;
                break;
            }
            case Variable.SUBMODULO: {
                if (!controladorUsuarioGeneral.eliminarSubmoduloUsuario())
                    deleteOk = false;
                if (!controladorGeneral.eliminarSubModulo())
                    deleteOk = false;
                break;
            }
        }
        return deleteOk;
    }

    public boolean populateEntity(String entity, List<JSONObject> jsonsAux, Context context) {
        ControladorUsuarioAdeful controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
        ControladorUsuarioGeneral controladorUsuarioGeneral = new ControladorUsuarioGeneral(context);
        ControladorUsuarioLifuba controladorUsuarioLifuba = new ControladorUsuarioLifuba(context);
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
                    if (!controladorUsuarioAdeful.insertTabla(tablas))
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
                    if (!controladorUsuarioLifuba.insertTabla(tablas))
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
                    if (!controladorUsuarioGeneral.insertTabla(tablas))
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
                                jsonAux.getString("TITULO"), jsonAux.getString("ARTICULO"));
                        articulos.add(articulo);
                    }
                    if (!controladorUsuarioGeneral.insertArticuloUsuario(articulos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //COMISION
            case Variable.COMISION: {
                try {
                    List<Comision> comisiones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Comision comision = new Comision(jsonAux.getInt("ID_COMISION"),
                                jsonAux.getString("NOMBRE_COMISION"), jsonAux.getString("FOTO_COMISION"), jsonAux.getString("CARGO"), jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"));
                        comisiones.add(comision);
                    }
                    if (!controladorUsuarioGeneral.insertComisionUsuario(comisiones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //DIRECCION
            case Variable.DIRECCION: {
                try {
                    List<Direccion> direcciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Direccion direccion = new Direccion(jsonAux.getInt("ID_DIRECCION"),
                                jsonAux.getString("NOMBRE_DIRECCION"), jsonAux.getString("FOTO_DIRECCION"), jsonAux.getString("CARGO"), jsonAux.getString("PERIODO_DESDE"), jsonAux.getString("PERIODO_HASTA"));
                        direcciones.add(direccion);
                    }
                    if (!controladorUsuarioGeneral.insertDireccionUsuario(direcciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //LIGA
            //EQUIPO
            case Variable.EQUIPO_ADEFUL: {
                try {
                    List<Equipo> equipos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                                jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("ESCUDO_EQUIPO"));
                        equipos.add(equipo);
                    }
                    if (!controladorUsuarioAdeful.insertEquipoUsuarioAdeful(equipos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //EQUIPO
            case Variable.EQUIPO_LIFUBA: {
                try {
                    List<Equipo> equipos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Equipo equipo = new Equipo(jsonAux.getInt("ID_EQUIPO"),
                                jsonAux.getString("NOMBRE_EQUIPO"), jsonAux.getString("ESCUDO_EQUIPO"));
                        equipos.add(equipo);
                    }
                    if (!controladorUsuarioLifuba.insertEquipoUsuarioLifuba(equipos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //TORNEO
            case Variable.TORNEO_ADEFUL: {
                try {
                    List<Torneo> torneos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                                jsonAux.getString("DESCRIPCION"));
                        torneos.add(torneo);
                    }
                    if (!controladorUsuarioAdeful.insertTorneoUsuarioAdeful(torneos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //TORNEO
            case Variable.TORNEO_LIFUBA: {
                try {
                    List<Torneo> torneos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Torneo torneo = new Torneo(jsonAux.getInt("ID_TORNEO"),
                                jsonAux.getString("DESCRIPCION"));
                        torneos.add(torneo);
                    }
                    if (!controladorUsuarioLifuba.insertTorneoUsuarioLifuba(torneos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //CANCHA
            case Variable.CANCHA_ADEFUL: {
                try {
                    List<Cancha> canchas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                                jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"));
                        canchas.add(cancha);
                    }
                    if (!controladorUsuarioAdeful.insertCanchaUsuarioAdeful(canchas))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //CANCHA
            case Variable.CANCHA_LIFUBA: {
                try {
                    List<Cancha> canchas = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Cancha cancha = new Cancha(jsonAux.getInt("ID_CANCHA"), jsonAux.getString("NOMBRE"), jsonAux.getString("LONGITUD"),
                                jsonAux.getString("LATITUD"), jsonAux.getString("DIRECCION"));
                        canchas.add(cancha);
                    }
                    if (!controladorUsuarioLifuba.insertCanchaUsuarioLifuba(canchas))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //DIVISION
            case Variable.DIVISION_ADEFUL: {
                try {
                    List<Division> divisiones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Division division = new Division(jsonAux.getInt("ID_DIVISION"),
                                jsonAux.getString("DESCRIPCION"));
                        divisiones.add(division);
                    }
                    if (!controladorUsuarioAdeful.insertDivisionUsuarioAdeful(divisiones))
                        insertOk = false;
                    if (!controladorUsuarioLifuba.insertDivisionUsuarioLifuba(divisiones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            //FIXTURE
            case Variable.FIXTURE_ADEFUL: {
                try {
                    List<Fixture> fixtures = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Fixture fixture = new Fixture(jsonAux.getInt("ID_FIXTURE"),
                                jsonAux.getInt("ID_EQUIPO_LOCAL"),
                                jsonAux.getInt("ID_EQUIPO_VISITA"),
                                jsonAux.getInt("ID_DIVISION"),
                                jsonAux.getInt("ID_TORNEO"),
                                jsonAux.getInt("ID_CANCHA"), jsonAux.getString("FECHA"),
                                jsonAux.getString("ANIO"), jsonAux.getString("DIA"),
                                jsonAux.getString("HORA"), jsonAux.getString("RESULTADO_LOCAL"),
                                jsonAux.getString("RESULTADO_VISITA"));
                        fixtures.add(fixture);
                    }
                    if (!controladorUsuarioAdeful.insertFixtureUsuarioAdeful(fixtures))
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
                                jsonAux.getInt("ID_CANCHA"), jsonAux.getString("FECHA"),
                                jsonAux.getString("ANIO"), jsonAux.getString("DIA"),
                                jsonAux.getString("HORA"), jsonAux.getString("RESULTADO_LOCAL"),
                                jsonAux.getString("RESULTADO_VISITA"));
                        fixtures.add(fixture);
                    }
                    if (!controladorUsuarioLifuba.insertFixtureUsuarioLifuba(fixtures))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //JUGADOR
            case Variable.JUGADOR_ADEFUL: {
                try {
                    List<Jugador> jugadores = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Jugador jugador = new Jugador(jsonAux.getInt("ID_JUGADOR"),
                                jsonAux.getString("NOMBRE_JUGADOR"), jsonAux.getString("FOTO_JUGADOR"), jsonAux.getInt("ID_DIVISION"), jsonAux.getString("DIVISION"),
                                jsonAux.getString("POSICION"));
                        jugadores.add(jugador);
                    }
                    if (!controladorUsuarioAdeful.insertJugadorUsuarioAdeful(jugadores))
                        insertOk = false;
                    if (!controladorUsuarioLifuba.insertJugadorUsuarioLifuba(jugadores))
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
                                jsonAux.getString("HORA_ENTRENAMIENTO"), jsonAux.getString("NOMBRE"));
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorUsuarioAdeful.insertEntrenamientoUsuarioAdeful(entrenamientos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //MI EQUIPO
            //ENTRENAMIENTO DIVISION
            case Variable.ENTRENAMIENTO_DIVISION_ADEFUL: {
                try {
                    List<Entrenamiento> entrenamientos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Entrenamiento entrenamiento = new Entrenamiento(true, jsonAux.getInt("ID_ENTRENAMIENTO_DIVISION"),
                                jsonAux.getInt("ID_ENTRENAMIENTO"),
                                jsonAux.getInt("ID_DIVISION"));
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorUsuarioAdeful.insertEntrenamientoDivisionUsuarioAdeful(entrenamientos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //ENTRNEMIENTO CANTIDAD
            case Variable.ENTRENAMIENTO_CANTIDAD_ADEFUL: {
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
            case Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL: {
                try {
                    List<Entrenamiento> entrenamientos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Entrenamiento entrenamiento = new Entrenamiento(jsonAux.getInt("ID_ENTRENAMIENTO_ASISTENCIA"),
                                jsonAux.getInt("ID_ENTRENAMIENTO"),
                                jsonAux.getInt("ID_JUGADOR"), "");
                        entrenamientos.add(entrenamiento);
                    }
                    if (!controladorUsuarioAdeful.insertAsistenciaEntrenamientoUsuarioAdeful(entrenamientos))
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
                                jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA"),
                                jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"));
                        sanciones.add(sancion);
                    }
                    if (!controladorUsuarioAdeful.insertSancionUsuarioAdeful(sanciones))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            //SANCION
            case Variable.SANCION_LIFUBA: {
                try {
                    List<Sancion> sanciones = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Sancion sancion = new Sancion(jsonAux.getInt("ID_SANCION"),
                                jsonAux.getInt("ID_JUGADOR"),
                                jsonAux.getInt("ID_TORNEO"), jsonAux.getInt("AMARILLA"), jsonAux.getInt("ROJA"),
                                jsonAux.getInt("FECHA_SUSPENSION"), jsonAux.getString("OBSERVACIONES"));
                        sanciones.add(sancion);
                    }
                    if (!controladorUsuarioLifuba.insertSancionUsuarioLifuba(sanciones))
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
                                jsonAux.getString("NOTIFICACION"));
                        notificaciones.add(notificacion);
                    }
                    if (!controladorUsuarioGeneral.insertNotificacionUsuario(notificaciones))
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
                                jsonAux.getString("LINK"));
                        noticias.add(noticia);
                    }
                    if (!controladorUsuarioGeneral.insertNoticiaUsuario(noticias))
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
                            jsonAux.getString("FOTO"));
                        fotos.add(foto);
                    }
                    if (!controladorUsuarioGeneral.insertFotoUsuario(fotos))
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
                                jsonAux.getString("LOGO"), jsonAux.getString("OTROS"));
                        publicidades.add(publicidad);
                    }
                    if (!controladorUsuarioGeneral.insertPublicidadUsuario(publicidades))
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
                                jsonAux.getString("PASSWORD"));
                        usuarios.add(usuario);
                    }
                    if (!controladorGeneral.insertUsuario(usuarios))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.MODULO: {
                try {
                    List<Modulo> modulos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        Modulo modulo = new Modulo(jsonAux.getInt("ID_MODULO"),
                                jsonAux.getString("NOMBRE"));
                        modulos.add(modulo);
                    }
                    if (!controladorUsuarioGeneral.insertModuloUsuario(modulos))
                        insertOk = false;
                    if (!controladorGeneral.insertModulo(modulos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
            case Variable.SUBMODULO: {
                try {
                    List<SubModulo> subModulos = new ArrayList<>();
                    for (JSONObject jsonAux : jsonsAux) {
                        SubModulo subModulo = new SubModulo(jsonAux.getInt("ID_SUBMODULO"),
                                jsonAux.getString("NOMBRE"),
                                jsonAux.getInt("ID_MODULO"), jsonAux.getInt("ISSELECTED") > 0);
                        subModulos.add(subModulo);
                    }
                    if (!controladorUsuarioGeneral.insertSubModuloUsuario(subModulos))
                        insertOk = false;
                    if (!controladorGeneral.insertSubModulo(subModulos))
                        insertOk = false;
                } catch (JSONException e) {
                    insertOk = false;
                }
                break;
            }
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
}
