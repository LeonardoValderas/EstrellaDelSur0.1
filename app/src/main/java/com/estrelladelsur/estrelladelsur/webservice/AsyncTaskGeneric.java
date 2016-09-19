package com.estrelladelsur.estrelladelsur.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.miequipo.adeful.MyAsyncTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEO on 11/9/2016.
 */
public class AsyncTaskGeneric {

    private Context context;
    private Request request;
    private ProgressDialog dialog;
    private JsonParsing jsonParsing;
    private Object object;
    private String entity;
    private boolean insert, isActual, delete;
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

//FOR INSERT-UPDATE-DELETE
    public AsyncTaskGeneric(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean insert, boolean delete, int id_delete, String genero, boolean isActual) {
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
    //FOR INSERT-UPDATE
    public AsyncTaskGeneric(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean insert, String genero) {
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
    public AsyncTaskGeneric(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, boolean delete, int id_delete, String genero) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.request = request;
        this.entity = entity;
        this.delete = delete;
        this.id_delete = id_delete;
        this.genero = genero;
        new TaskGeneric().execute(request);
    }


    public class TaskGeneric extends AsyncTask<Request, Boolean, Boolean> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Procesando...");
            dialog.show();
            jsonParsing = new JsonParsing(context);
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
                        } else {

                            if (deleteEntity(entity, id_delete)) {
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
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        boolean insertOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorAdeful.insertArticuloAdeful(id, (Articulo) object))
                    insertOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorAdeful.insertComisionAdeful(id, (Comision) object))
                    insertOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorAdeful.insertDireccionAdeful(id, (Direccion) object))
                    insertOk = false;
                break;
            }
            case "Cargo": {
                if (!controladorAdeful.insertCargoAdeful(id, (Cargo) object))
                    insertOk = false;
                break;
            }
            //LIGA
            case "Equipo": {
                if (!controladorAdeful.insertEquipoAdeful(id, (Equipo) object))
                    insertOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorAdeful.insertTorneoAdeful(id, (Torneo) object))
                    insertOk = false;
                break;
            }
            case "División": {
                if (!controladorAdeful.insertDivisionAdeful(id, (Division) object))
                    insertOk = false;
                break;
            }
            case "Cancha": {
                if (!controladorAdeful.insertCanchaAdeful(id, (Cancha) object))
                    insertOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorAdeful.insertFixtureAdeful(id, (Fixture) object))
                    insertOk = false;
                break;
            }

        }
        return insertOk;
    }

    public boolean updateEntity(String entity, Object object) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        boolean updateOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorAdeful.actualizarArticuloAdeful((Articulo) object))
                    updateOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorAdeful.actualizarComisionAdeful((Comision) object))
                    updateOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorAdeful.actualizarDireccionAdeful((Direccion) object))
                    updateOk = false;
                break;
            }
            case "Cargo": {
                if (!controladorAdeful.actualizarCargoAdeful((Cargo) object))
                    updateOk = false;
                break;
            }
            //LIGA
            case "Equipo": {
                if (!controladorAdeful.actualizarEquipoAdeful((Equipo) object))
                    updateOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorAdeful.actualizarTorneoAdeful((Torneo) object))
                    updateOk = false;
                break;
            }
            case "División": {
                if (!controladorAdeful.actualizarDivisionAdeful((Division) object))
                    updateOk = false;
                break;
            }
            case "Cancha": {
                if (!controladorAdeful.actualizarCanchaAdeful((Cancha) object))
                    updateOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorAdeful.actualizarFixtureAdeful((Fixture) object))
                    updateOk = false;
                break;
            }
            case "Resultado": {
                if (!controladorAdeful.actualizarResultadoAdeful((Resultado) object))
                    updateOk = false;
                break;
            }
        }
        return updateOk;
    }

    public boolean deleteEntity(String entity, int id) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        boolean deleteOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorAdeful.eliminarArticuloAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorAdeful.eliminarComisionAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorAdeful.eliminarDireccionAdeful(id))
                    deleteOk = false;
                break;
            }
            //LIGA
            case "Equipo": {
                if (!controladorAdeful.eliminarEquipoAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Torneo": {
                if (!controladorAdeful.eliminarTorneoAdeful(id, isActual))
                    deleteOk = false;
                break;
            }
            case "División": {
                if (!controladorAdeful.eliminarDivisionAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Cancha": {
                if (!controladorAdeful.eliminarCanchaAdeful(id))
                    deleteOk = false;
                break;
            }
            //MI EQUIPO
            case "Fixture": {
                if (!controladorAdeful.eliminarFixtureAdeful(id))
                    deleteOk = false;
                break;
            }
        }
        return deleteOk;
    }
}
