package com.estrelladelsur.estrelladelsur.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEO on 11/9/2016.
 */
public class AsyncTaskGenericIndividual {

    private Context context;
    private Request request;
    private JsonParsingIndividual jsonParsing;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DATE = "date";
    public static final String ARTICULO = "articulo";
    public static final String COMISION = "comision";
    public static final String DIRECCION = "direccion";
    public static final String CARGO = "cargo";
    public static final String EQUIPO_ADEFUL = "equipo_adeful";
    public static final String EQUIPO_LIFUBA = "equipo_lifuba";
    public static final String DIVISION_ADEFUL = "division_adeful";
    public static final String TORNEO_ADEFUL = "torneo_adeful";
    public static final String TORNEO_LIFUBA = "torneo_lifuba";
    public static final String CANCHA_ADEFUL = "cancha_adeful";
    public static final String CANCHA_LIFUBA = "cancha_lifuba";
    public static final String FIXTURE_ADEFUL = "fixture_adeful";
    public static final String FIXTURE_LIFUBA = "fixture_lifuba";
    // public static final String RESULTADO_ADEFUL = "resultado_adeful";
    public static final String JUGADOR_ADEFUL = "jugador_adeful";
    // public static final String POSICION_ADEFUL = "posicion_adeful";
    public static final String ENTRENAMIENTO_ADEFUL = "entrenamiento_adeful";
    public static final String ENTRENAMIENTO_DIVISION_ADEFUL = "entrenamiento_division_adeful";
    public static final String ENTRENAMIENTO_CANTIDAD_ADEFUL = "entrenamiento_cantidad_adeful";
    public static final String ENTRENAMIENTO_ASISTENCIA_ADEFUL = "entrenamiento_asistencia_adeful";
    public static final String SANCION_ADEFUL = "sancion_adeful";
    public static final String SANCION_LIFUBA = "sancion_lifuba";
    public static final String NOTIFICACION = "notificacion";
    public static final String NOTICIA = "noticia";
    public static final String FOTO = "foto";
    public static final String PUBLICIDAD = "publicidad";
    public static final String USUARIO = "usuario";
    public static final String MODULO = "modulo";
    public static final String SUBMODULO = "submodulo";
    public static final String PERMISO = "permiso";
    public static final String ANIO = "anio";
    public static final String FECHA = "fecha";
    private String URL = null, mensaje = null, entidad;
    private MyAsyncTaskListener mListener;
    public static final String TABLA_ARTICULO = "ARTICULO";
    public static final String TABLA_CANCHA_ADEFUL = "CANCHA_ADEFUL";
    public static final String TABLA_CANCHA_LIFUBA = "CANCHA_LIFUBA";
    public static final String TABLA_CARGO_ADEFUL = "CARGO";
    public static final String TABLA_COMISION = "COMISION";
    public static final String TABLA_DIRECCION = "DIRECCION";
    public static final String TABLA_DIVISION_ADEFUL = "DIVISION_ADEFUL";
    //  public static final String TABLA_DIVISION_LIFUBA = "DIVISION_LIFUBA";
    public static final String TABLA_ENTRENAMIENTO = "ENTRENAMIENTO_ADEFUL";
    public static final String TABLA_EQUIPO_ADEFUL = "EQUIPO_ADEFUL";
    public static final String TABLA_EQUIPO_LIFUBA = "EQUIPO_LIFUBA";
    public static final String TABLA_FIXTURE_ADEFUL = "FIXTURE_ADEFUL";
    public static final String TABLA_FIXTURE_LIFUBA = "FIXTURE_LIFUBA";
    public static final String TABLA_JUGADOR = "JUGADOR_ADEFUL";
    public static final String TABLA_POSICION = "POSICION_ADEFUL";
    public static final String TABLA_SANCION_ADEFUL = "SANCION_ADEFUL";
    public static final String TABLA_SANCION_LIFUBA = "SANCION_LIFUBA";
    public static final String TABLA_TORNEO_ADEFUL = "TORNEO_ADEFUL";
    public static final String TABLA_TORNEO_LIFUBA = "TORNEO_LIFUBA";
    public static final String TABLA_ASISTENCIA = "ENTRENAMIENTO_ASISTENCIA_ADEFUL";
    public static final String TABLA_NOTIFICACION = "NOTIFICACION";
    public static final String TABLA_NOTICIA = "NOTICIA";
    public static final String TABLA_FOTO = "FOTO";
    public static final String TABLA_PUBLICIDAD = "PUBLICIDAD";
    public static final String TABLA_USUARIO = "USUARIO";
    public static final String TABLA_PERMISO = "PERMISO";


    public AsyncTaskGenericIndividual(Context context, MyAsyncTaskListener listener, String URL, Request request, String entidad) {
        this.context = context;
        mListener = listener;
        this.URL = URL;
        this.entidad = entidad;
        new TaskGeneric().execute(request);
    }


    public class TaskGeneric extends AsyncTask<Request, Boolean, Boolean> {

        @Override
        protected void onPreExecute() {
            //         dialog = new ProgressDialog(context);
            //       dialog.setMessage("Procesando...");
            //     dialog.setCanceledOnTouchOutside(false);
            //   dialog.show();
            jsonParsing = new JsonParsingIndividual();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            JSONArray jsonArrayResponse = null;
            JSONObject jsonArrayResponseAux = null;
            JSONArray jsonArray = null;
            try {
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    jsonArrayResponse = json.optJSONArray("RESPONSE");
                    jsonArrayResponseAux = jsonArrayResponse.getJSONObject(0);
                    success = jsonArrayResponseAux.getInt(TAG_SUCCESS);
                    mensaje = jsonArrayResponseAux.getString(TAG_MESSAGE);

                    if (success == 4) {
                        String fecha = jsonArrayResponseAux.getString(TAG_DATE);
                        jsonArray = json.optJSONArray(entidad);
                        if (jsonArray.length() > 0) {
                            if (!jsonParsing.processingJson(jsonArray, entidad, context, fecha)) {
                                precessOK = false;
                            }
                        } else {
                            if (!jsonParsing.processingJson(entidad, context)) {
                                precessOK = false;
                            }
                        }
                        jsonArray = null;
                    } else if (success == 0) {
                        precessOK = true;
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
//            dialog.dismiss();
            mListener.onPostExecuteConcluded(result, mensaje);
        }
    }
}
