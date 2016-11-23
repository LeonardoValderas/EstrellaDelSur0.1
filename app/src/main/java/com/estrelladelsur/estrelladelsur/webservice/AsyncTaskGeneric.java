package com.estrelladelsur.estrelladelsur.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
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
public class AsyncTaskGeneric {

    private Context context;
    private Request request;
    private ProgressDialog dialog;
    private JsonParsing jsonParsing;
    private Object object;
    private String entity;
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
    //FOR DELETE whit objet
    public AsyncTaskGeneric(Context context, MyAsyncTaskListener listener, String URL, Request request, String entity, Object object, boolean delete, String genero, boolean isPermiso) {
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
                        } else if(isPermiso){

                            if (deleteEntity(entity, ((Permiso) object).getID_PERMISO(), object)) {
                                mensaje = entity + ELIMINAR + genero + CORRECTO;
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }

                        }else {
                            if (deleteEntity(entity, id_delete, null)) {
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
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean insertOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorGeneral.insertArticulo(id, (Articulo) object))
                    insertOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorGeneral.insertComision(id, (Comision) object))
                    insertOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorGeneral.insertDireccion(id, (Direccion) object))
                    insertOk = false;
                break;
            }
            case "Cargo": {
                if (!controladorGeneral.insertCargo(id, (Cargo) object))
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
            case "Jugador": {
                if (!controladorAdeful.insertJugadorAdeful(id, (Jugador) object))
                    insertOk = false;
                break;
            }
            case "Posicion": {
                if (!controladorAdeful.insertPosicionAdeful(id, (Posicion) object))
                    insertOk = false;
                break;
            }
            case "Entrenamiento": {
                if (controladorAdeful.insertEntrenamientoAdeful(id, (Entrenamiento) object)) {
                    for (int i : ((Entrenamiento) object).getDivisionArrayAdd()) {
                        if (!controladorAdeful.insertEntrenamientoDivisionAdeful(id, i)) {
                            insertOk = false;
                            break;
                        }
                    }
                } else {
                    insertOk = false;
                }
                break;
            }
            case "Asistencia": {

                if (((Entrenamiento) object).getJugadorArrayAdd() != null) {
                    for (int i : ((Entrenamiento) object).getJugadorArrayAdd()) {
                        if (!controladorAdeful.insertAsistenciaEntrenamientoAdeful(((Entrenamiento) object).getID_ENTRENAMIENTO(), i)) {
                            insertOk = false;
                            break;
                        }
                    }
                }
                if (insertOk) {
                    if (((Entrenamiento) object).getJugadorArrayDelete() != null) {
                        for (int i : ((Entrenamiento) object).getJugadorArrayDelete()) {
                            if (!controladorAdeful.eliminarAsistenciaEntrenamientoAdeful(((Entrenamiento) object).getID_ENTRENAMIENTO(), i)) {
                                insertOk = false;
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case "Sancion": {
                if (!controladorAdeful.insertSancionAdeful(id, (Sancion) object))
                    insertOk = false;
                break;
            }
            case "Notificacion": {
                if (!controladorGeneral.insertNotificacion(id, (Notificacion) object))
                    insertOk = false;
                break;
            }
            case "Noticia": {
                if (!controladorGeneral.insertNoticia(id, (Noticia) object))
                    insertOk = false;
                break;
            }
            case "Foto": {
                if (!controladorGeneral.insertFoto(id, (Foto) object))
                    insertOk = false;
                break;
            }
            case "Publicidad": {
                if (!controladorGeneral.insertPublicidad(id, (Publicidad) object))
                    insertOk = false;
                break;
            }
            case "Usuario": {
                if (!controladorGeneral.insertUsuario(id, (Usuario) object))
                    insertOk = false;
                break;
            }
            case "Permiso": {
                if (controladorGeneral.insertPermisos(id, (Permiso) object)) {
                    for (int i = 0; i < ((Permiso) object).getSubModulosTrue().size(); i++) {
                        if (controladorGeneral.insertPermisoModulo(id, ((Permiso) object).getSubModulosTrue().get(i).getID_MODULO(), ((Permiso) object).getSubModulosTrue().get(i).getID_SUBMODULO())) {
                            if (!controladorGeneral.actualizarSubModuloSelectedTrue(((Permiso) object).getSubModulosTrue().get(i).getID_SUBMODULO())) {
                                insertOk = false;
                                break;
                            }
                        } else {
                            insertOk = false;
                            break;
                        }
                    }
                } else {
                    insertOk = false;
                }
                break;
            }
        }
        return insertOk;
    }

    public boolean updateEntity(String entity, Object object) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean updateOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorGeneral.actualizarArticulo((Articulo) object))
                    updateOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorGeneral.actualizarComision((Comision) object))
                    updateOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorGeneral.actualizarDireccion((Direccion) object))
                    updateOk = false;
                break;
            }
            case "Cargo": {
                if (!controladorGeneral.actualizarCargo((Cargo) object))
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
            case "Jugador": {
                if (!controladorAdeful.actualizarJugadorAdeful((Jugador) object))
                    updateOk = false;
                break;
            }
            case "Posicion": {
                if (!controladorAdeful.actualizarPosicionAdeful((Posicion) object))
                    updateOk = false;
                break;
            }
            case "Entrenamiento": {
                if (controladorAdeful.actualizarEntrenamientoAdeful((Entrenamiento) object)) {
                    if (((Entrenamiento) object).getDivisionArrayAdd() != null) {
                        for (int i : ((Entrenamiento) object).getDivisionArrayAdd()) {
                            if (!controladorAdeful.insertEntrenamientoDivisionAdeful(((Entrenamiento) object).getID_ENTRENAMIENTO(), i)) {
                                updateOk = false;
                                break;
                            }
                        }
                    }
                    if (updateOk) {
                        if (((Entrenamiento) object).getDivisionArrayDelete() != null) {
                            for (int i : ((Entrenamiento) object).getDivisionArrayDelete()) {
                                if (!controladorAdeful.eliminarDivisionEntrenamientoAdeful(((Entrenamiento) object).getID_ENTRENAMIENTO(), i)) {
                                    updateOk = false;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    updateOk = false;
                }
                break;
            }
            case "Sancion": {
                if (!controladorAdeful.actualizarSancionAdeful((Sancion) object))
                    updateOk = false;
                break;
            }
            case "Notificacion": {
                if (!controladorGeneral.actualizarNotificacion((Notificacion) object))
                    updateOk = false;
                break;
            }
            case "Noticia": {
                if (!controladorGeneral.actualizarNoticia((Noticia) object))
                    updateOk = false;
                break;
            }
            case "Foto": {
                if (!controladorGeneral.actualizarFoto((Foto) object))
                    updateOk = false;
                break;
            }
            case "Publicidad": {
                if (!controladorGeneral.actualizarPublicidad((Publicidad) object))
                    updateOk = false;
                break;
            }
            case "Usuario": {
                if (!controladorGeneral.actualizarUsuario((Usuario) object))
                    updateOk = false;
                break;
            }
            case "Permiso": {
                if (controladorGeneral.actualizarPermisos((Permiso) object)) {
                    for (int i = 0; i < ((Permiso) object).getSubModulosTrue().size(); i++) {
                        if (controladorGeneral.insertPermisoModulo(((Permiso) object).getID_PERMISO(), ((Permiso) object).getSubModulosTrue().get(i).getID_MODULO(), ((Permiso) object).getSubModulosTrue().get(i).getID_SUBMODULO())) {
                            if (!controladorGeneral.actualizarSubModuloSelectedTrue(((Permiso) object).getSubModulosTrue().get(i).getID_SUBMODULO())) {
                                updateOk = false;
                                break;
                            }
                        } else {
                            updateOk = false;
                            break;
                        }
                    }
                    if (updateOk) {
                        for (int i = 0; i < ((Permiso) object).getSubmoduloArrayFalse().size(); i++) {
                            if (controladorGeneral.eliminarPermisoModulo(((Permiso) object).getSubmoduloArrayFalse().get(i).getID_SUBMODULO())) {
                                if (!controladorGeneral.actualizarSubModuloSelectedFalse(((Permiso) object).getSubmoduloArrayFalse().get(i).getID_SUBMODULO())) {
                                    updateOk = false;
                                    break;
                                }
                            } else {
                                updateOk = false;
                                break;
                            }
                        }
                    } else {
                        updateOk = false;
                    }
                }
                break;
            }
        }
        return updateOk;
    }

    public boolean deleteEntity(String entity, int id,  Object object) {
        ControladorAdeful controladorAdeful = new ControladorAdeful(context);
        ControladorGeneral controladorGeneral = new ControladorGeneral(context);
        boolean deleteOk = true;
        switch (entity) {
            //INSTITUCION
            case "Articulo": {
                if (!controladorGeneral.eliminarArticulo(id))
                    deleteOk = false;
                break;
            }
            case "Comisión": {
                if (!controladorGeneral.eliminarComision(id))
                    deleteOk = false;
                break;
            }
            case "Dirección": {
                if (!controladorGeneral.eliminarDireccion(id))
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
            case "Jugador": {
                if (!controladorAdeful.eliminarJugadorAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Entrenamiento": {
                if (controladorAdeful.eliminarEntrenamientoAdeful(id)) {
                    if (!controladorAdeful.eliminarDivisionEntrenamientoAdeful(id))
                        deleteOk = false;
                } else {
                    deleteOk = false;
                }
                break;
            }
            case "Sancion": {
                if (!controladorAdeful.eliminarSancionAdeful(id))
                    deleteOk = false;
                break;
            }
            case "Notificacion": {
                if (!controladorGeneral.eliminarNotificacion(id))
                    deleteOk = false;
                break;
            }
            case "Noticia": {
                if (!controladorGeneral.eliminarNoticia(id))
                    deleteOk = false;
                break;
            }
            case "Foto": {
                if (!controladorGeneral.eliminarFoto(id))
                    deleteOk = false;
                break;
            }
            case "Publicidad": {
                if (!controladorGeneral.eliminarPublicidad(id))
                    deleteOk = false;
                break;
            }
            case "Usuario": {
                if (!controladorGeneral.eliminarUsuario(id))
                    deleteOk = false;
                break;
            }
            case "Permiso": {
                if (controladorGeneral.eliminarPermiso(id)) {
                    if (controladorGeneral.eliminarPermisoModulo(id)) {
                        for (int i = 0; i < ((Permiso) object).getSubModulosdelete().size(); i++) {
                            if (!controladorGeneral.actualizarSubModuloSelectedFalse(((Permiso) object).getSubModulosdelete().get(i))) {
                                deleteOk = false;
                                break;
                            }

                        }
                    } else {
                        deleteOk = false;
                    }
                } else {
                    deleteOk = false;
                }
                break;
            }
        }
        return deleteOk;
    }
}
