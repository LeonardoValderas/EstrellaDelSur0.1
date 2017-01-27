package com.estrelladelsur.estrelladelsur.webservice;

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
    private JsonParsingIndividual jsonParsing;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_DATE = "date";
    private MyAsyncTaskListener mListener;
    private String URL = null, mensaje = null, entidad;
    //public static final String ENTRENAMIENTO_DIVISION_ADEFUL = "entrenamiento_division_adeful";
    //public static final String ENTRENAMIENTO_CANTIDAD_ADEFUL = "entrenamiento_cantidad_adeful";
    //public static final String ENTRENAMIENTO_ASISTENCIA_ADEFUL = "entrenamiento_asistencia_adeful";
    // public static final String SUBMODULO = "submodulo";
    //  public static final String TABLA_DIVISION_LIFUBA = "DIVISION_LIFUBA";
    //  public static final String TABLA_USUARIO = "USUARIO";
    //  public static final String TABLA_PERMISO = "PERMISO";
   // public static final String TABLA_CARGO_ADEFUL = "CARGO";
  //  public static final String TORNEO_LIFUBA = "torneo_lifuba";
//  public static final String TABLA_POSICION = "POSICION_ADEFUL";
    // public static final String TABLA_ASISTENCIA = "ENTRENAMIENTO_ASISTENCIA_ADEFUL";

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
            mListener.onPostExecuteConcluded(result, mensaje);
        }
    }
}
