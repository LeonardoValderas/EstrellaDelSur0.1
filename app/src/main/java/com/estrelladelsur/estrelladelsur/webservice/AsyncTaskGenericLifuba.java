package com.estrelladelsur.estrelladelsur.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEO on 11/9/2016.
 */
public class AsyncTaskGenericLifuba {

    private Context context;
    private Request request;
    private ProgressDialog dialog;
    private JsonParsing jsonParsing;
    private Object object;
    private String entity;
    private String fecha_delete = null;
    private boolean insert, isActual, delete, isPermiso;
    int id_delete;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID = "id";
    private String GUARDAR = " cargad";
    private String ACTUALIZAR = " actualizad";
    private String ELIMINAR = " eliminad";
    private String CORRECTO = " correctamente";
    private String URL = null, mensaje = null, genero;
    private MyAsyncTaskListener mListener;
    public static final String TABLA_CANCHA_LIFUBA = "CANCHA_LIFUBA";
    public static final String TABLA_EQUIPO_LIFUBA = "EQUIPO_LIFUBA";
    public static final String TABLA_FIXTURE_LIFUBA = "FIXTURE_LIFUBA";
    public static final String TABLA_SANCION_LIFUBA = "SANCION_LIFUBA";
    public static final String TABLA_TORNEO_LIFUBA = "TORNEO_LIFUBA";

    //FOR INSERT-UPDATE-DELETE
    public AsyncTaskGenericLifuba(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean insert, boolean delete, int id_delete, String genero, boolean isActual) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.object = object;
        this.insert = insert;
        this.delete = delete;
        this.id_delete = id_delete;
        this.genero = genero;
        this.isActual = isActual;
        new TaskGeneric().execute(request);
    }
    public AsyncTaskGenericLifuba(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean insert, boolean delete, int id_delete, String genero, boolean isActual, String fecha_delete) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.object = object;
        this.insert = insert;
        this.delete = delete;
        this.id_delete = id_delete;
        this.genero = genero;
        this.isActual = isActual;
        this.fecha_delete = fecha_delete;
        new TaskGeneric().execute(request);
    }

    //FOR INSERT-UPDATE
    public AsyncTaskGenericLifuba(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean insert, String genero) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.object = object;
        this.insert = insert;
        this.genero = genero;
        this.delete = false;
        new TaskGeneric().execute(request);
    }

    //FOR DELETE
    public AsyncTaskGenericLifuba(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, boolean delete, int id_delete, String genero, String fecha_delete) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.delete = delete;
        this.id_delete = id_delete;
        this.genero = genero;
        this.fecha_delete = fecha_delete;
        new TaskGeneric().execute(request);
    }
    //FOR DELETE whit objet
    public AsyncTaskGenericLifuba(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean delete, String genero, boolean isPermiso) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.object = object;
        this.delete = delete;
        this.genero = genero;
        this.isPermiso = isPermiso;
        new TaskGeneric().execute(request);
    }


    public class TaskGeneric extends AsyncTask<Request, Boolean, Boolean> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Procesando...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            jsonParsing = new JsonParsing();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            try {
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (!delete) {
                            if (insert) {
                                int id = json.getInt(TAG_ID);
                                if (id > 0) {
                                    if (insertEntity(entity, id, object)) {
                                        mensaje = entity + GUARDAR + genero + CORRECTO;
                                        precessOK = true;
                                    } else {
                                        precessOK = false;
                                    }
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                if (updateEntity(entity, object)) {
                                    mensaje = entity + ACTUALIZAR + genero + CORRECTO;
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            }
                        } else if(isPermiso){

                            if (deleteEntity(entity, ((Permiso) object).getID_PERMISO(), object, fecha_delete)){
                                mensaje = entity + ELIMINAR + genero + CORRECTO;
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }

                        }else {
                            if (deleteEntity(entity, id_delete, null, fecha_delete)){
                                mensaje = entity + ELIMINAR + genero + CORRECTO;
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        }
                    } else {
                        precessOK = false;
                    }
                } else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con el administrador.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con el administrador.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            mListener.onPostExecuteConcluded(result, mensaje);
        }
    }


    public boolean insertEntity(String entity, int id, Object object) {
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        boolean insertOk = true;
        switch (entity) {
            //LIGA
            case "Equipo": {
                if (!controladorLifuba.insertEquipoLifuba(id, (Equipo) object))
                    insertOk = false;
                if (insertOk)
                    if (!updateTableXTable(TABLA_EQUIPO_LIFUBA, ((Equipo) object).getFECHA_CREACION(), context))
                        insertOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorLifuba.insertTorneoLifuba(id, (Torneo) object))
                    insertOk = false;
                if (insertOk)
                    if (!updateTableXTable(TABLA_TORNEO_LIFUBA, ((Torneo) object).getFECHA_CREACION(), context))
                        insertOk = false;
                break;
            }
            case "Cancha": {
                if (!controladorLifuba.insertCanchaLifuba(id, (Cancha) object))
                    insertOk = false;
                if (insertOk)
                    if (!updateTableXTable(TABLA_CANCHA_LIFUBA, ((Cancha) object).getFECHA_CREACION(), context))
                        insertOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorLifuba.insertFixtureLifuba(id, (Fixture) object))
                    insertOk = false;
                if (insertOk)
                    if (!updateTableXTable(TABLA_FIXTURE_LIFUBA, ((Fixture) object).getFECHA_CREACION(), context))
                        insertOk = false;
                break;
            }
            case "Sancion": {
                if (!controladorLifuba.insertSancionLifuba(id, (Sancion) object))
                    insertOk = false;
                if (insertOk)
                    if (!updateTableXTable(TABLA_SANCION_LIFUBA, ((Sancion) object).getFECHA_CREACION(), context))
                        insertOk = false;
                break;
            }
        }
        return insertOk;
    }

    public boolean updateEntity(String entity, Object object) {
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        boolean updateOk = true;
        switch (entity) {
            //LIGA
            case "Equipo": {
                if (!controladorLifuba.actualizarEquipoLifuba((Equipo) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_EQUIPO_LIFUBA, ((Equipo) object).getFECHA_CREACION(), context))
                        updateOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorLifuba.actualizarTorneoLifuba((Torneo) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_EQUIPO_LIFUBA, ((Torneo) object).getFECHA_CREACION(), context))
                        updateOk = false;
                break;
            }
            case "Cancha": {
                if (!controladorLifuba.actualizarCanchaLifuba((Cancha) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_CANCHA_LIFUBA, ((Cancha) object).getFECHA_CREACION(), context))
                        updateOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorLifuba.actualizarFixtureLifuba((Fixture) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_FIXTURE_LIFUBA, ((Fixture) object).getFECHA_CREACION(), context))
                        updateOk = false;
                break;
            }
            case "Resultado": {
                if (!controladorLifuba.actualizarResultadoLifuba((Resultado) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_FIXTURE_LIFUBA, ((Resultado) object).getFECHA_ACTUALIZACION(), context))
                        updateOk = false;
                break;
            }
            case "Sancion": {
                if (!controladorLifuba.actualizarSancionLifuba((Sancion) object))
                    updateOk = false;
                if (updateOk)
                    if (!updateTableXTable(TABLA_SANCION_LIFUBA, ((Sancion) object).getFECHA_CREACION(), context))
                        updateOk = false;
                break;
            }
        }
        return updateOk;
    }

    public boolean deleteEntity(String entity, int id,  Object object, String fecha_delete) {
        ControladorLifuba controladorLifuba = new ControladorLifuba(context);
        boolean deleteOk = true;
        switch (entity) {
            //LIGA
            case "Equipo": {
                if (!controladorLifuba.eliminarEquipoLifuba(id))
                    deleteOk = false;
                if (deleteOk)
                    if (!updateTableXTable(TABLA_EQUIPO_LIFUBA, fecha_delete
                            , context))
                        deleteOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorLifuba.eliminarTorneoLifuba(id, isActual))
                    deleteOk = false;
                if (deleteOk)
                    if (!updateTableXTable(TABLA_TORNEO_LIFUBA, fecha_delete
                            , context))
                        deleteOk = false;
                break;
            }
//            case "División": {
//                if (!controladorLifuba.eliminarDivisionLifuba(id))
//                    deleteOk = false;
//                if (deleteOk)
//                    if (updateTableXTable(TABLA_EQUIPO_LIFUBA, fecha_delete
//                            , context))
//                        deleteOk = false;
//                break;
//            }
            case "Cancha": {
                if (!controladorLifuba.eliminarCanchaLifuba(id))
                    deleteOk = false;
                if (deleteOk)
                    if (!updateTableXTable(TABLA_CANCHA_LIFUBA, fecha_delete
                            , context))
                        deleteOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorLifuba.eliminarFixtureLifuba(id))
                    deleteOk = false;
                if (deleteOk)
                    if (!updateTableXTable(TABLA_FIXTURE_LIFUBA, fecha_delete
                            , context))
                        deleteOk = false;
                break;
            }
            case "Sancion": {
                if (!controladorLifuba.eliminarSancionLifuba(id))
                    deleteOk = false;
                if (deleteOk)
                    if (!updateTableXTable(TABLA_SANCION_LIFUBA, fecha_delete
                            , context))
                        deleteOk = false;
                break;
            }
        }
        return deleteOk;
    }

    public boolean updateTableXTable(String tabla, String fecha, Context context) {
                ControladorLifuba controladorLifuba = new ControladorLifuba(context);
                if (!controladorLifuba.actualizarTablaXTabla(tabla, fecha))
                return false;
                else
                return true;
    }
}
