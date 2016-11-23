package com.estrelladelsur.estrelladelsur.navegador.administrador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;
import com.estrelladelsur.estrelladelsur.social.usuario.NotificacionUsuario;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashAdm extends AppCompatActivity {

    private ControladorUsuarioAdeful controladorUsuario;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressBar progressSplash;
    private Request request = new Request();
    private Request requestUrl = new Request();
    private JsonParsing jsonParsing = new JsonParsing(SplashAdm.this);
    private static final String TAG_SUCCESS = "success";
//    private static final String SINDATOS = "SIN DATOS";
    private static final String TAG_MESSAGE = "message";
    private Context context;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;
    private String SENDER_ID = "686300125964";
    private String regid;
    private GoogleCloudMessaging gcm;
    private static final String TAG_ID = "id";
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    private static final String PROPERTY_USER = "id";
    private String mensaje = null;
//    private String sinDatos = null;
//    private Articulo articulo;
//    private Comision comision;
//    private Direccion direccion;
//    private Equipo equipo;
//    private Torneo torneo;
//    private Cancha cancha;
//    private Division division;
//    private Fixture fixture;
    public static final String ARTICULO = "articulo";
    public static final String COMISION = "comision";
    public static final String DIRECCION = "direccion";
    public static final String EQUIPO_ADEFUL = "equipo_adeful";
    public static final String DIVISION_ADEFUL = "division_adeful";
    public static final String TORNEO_ADEFUL = "torneo_adeful";
    public static final String CANCHA_ADEFUL = "cancha_adeful";
    public static final String FIXTURE_ADEFUL = "fixture_adeful";
    public static final String RESULTADO_ADEFUL = "resultado_adeful";
    public static final String JUGADOR_ADEFUL = "jugador_adeful";
    public static final String POSICION_ADEFUL = "posicion_adeful";
    public static final String ENTRENAMIENTO_ADEFUL = "entrenamiento_adeful";
    public static final String ENTRENAMIENTO_DIVISION_ADEFUL = "entrenamiento_division_adeful";
    public static final String ENTRENAMIENTO_CANTIDAD_ADEFUL = "entrenamiento_cantidad_adeful";
    public static final String ENTRENAMIENTO_ASISTENCIA_ADEFUL = "entrenamiento_asistencia_adeful";
    public static final String SANCION_ADEFUL = "sancion_adeful";
    public static final String NOTIFICACION = "notificacion";
    public static final String NOTICIA = "noticia";
    public static final String FOTO = "foto";
    public static final String PUBLICIDAD = "publicidad";
    public static final String USUARIO = "usuario";
    public static final String MODULO = "modulo";
    public static final String SUBMODULO = "submodulo";
    public static String TAG = "GCM";
    private int id = 0;
    private boolean isNotificacion = false;
    private boolean close = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        controladorUsuario = new ControladorUsuarioAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(SplashAdm.this);

        close = SplashAdm.this.getIntent().getBooleanExtra("close",
                false);
        if(close)
            closeApp();

        if(checkGCM())
        init();

    }

    public void closeApp(){
         finish();

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        System.exit(0);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void init() {
        isNotificacion = getIntent().getBooleanExtra("Notificacion", false);
        progressSplash = (ProgressBar) findViewById(R.id.progressSplash);
        envioWebService();
    }

    public void envioWebService() {
        request.setMethod("POST");

            //INSTITUCION
            request.setParametrosDatos(ARTICULO, "2016-04-23--09-29-0");
            request.setParametrosDatos(COMISION, "2016-04-23--09-29-0");
            request.setParametrosDatos(DIRECCION, "2016-04-23--09-29-0");
            //LIGA
            request.setParametrosDatos(EQUIPO_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(DIVISION_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(TORNEO_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(CANCHA_ADEFUL, "2016-04-23--09-29-0");
            //MI EQUIPO
            request.setParametrosDatos(FIXTURE_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(JUGADOR_ADEFUL, "2016-04-23--09-29-0");
            //   request.setParametrosDatos("POSICION_ADEFUL", "2016-04-23--09-29-0");
            request.setParametrosDatos(ENTRENAMIENTO_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(ENTRENAMIENTO_DIVISION_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(ENTRENAMIENTO_ASISTENCIA_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(SANCION_ADEFUL, "2016-04-23--09-29-0");
            request.setParametrosDatos(NOTIFICACION, "2016-04-23--09-29-0");
            request.setParametrosDatos(NOTICIA, "2016-04-23--09-29-0");
            request.setParametrosDatos(FOTO, "2016-04-23--09-29-0");
            request.setParametrosDatos(PUBLICIDAD, "2016-04-23--09-29-0");
            request.setParametrosDatos(USUARIO, "2016-04-23--09-29-0");
            request.setParametrosDatos(MODULO, "2016-04-23--09-29-0");
            request.setParametrosDatos(SUBMODULO, "2016-04-23--09-29-0");

        requestUrl.setParametrosDatos("URL", auxiliarGeneral.getURL() + auxiliarGeneral.getURLSINCRONIZARUSUARIO());

        new TaskSincronizar().execute(request, requestUrl);
    }


    public class TaskSincronizar extends AsyncTask<Request, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            progressSplash.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success = 5;
            JSONObject json = null;
            JSONObject jsonAux = null;
            JSONArray jsonArray = null;
            JSONArray jsonArrayResponse = null;
            JSONObject jsonArrayResponseAux = null;
            boolean precessOK = true;
            boolean isAsistencia = false;
            String URL = null;
            try {

                URL = params[1].getParametros().get("URL");
                json = jsonParsing.parsingJsonObject(params[0], URL);

                if (json != null) {

                    //0=ok, 3 sin datos cargados
                    jsonArrayResponse = json.optJSONArray("RESPONSE");
                    jsonArrayResponseAux = jsonArrayResponse.getJSONObject(0);
                    success = jsonArrayResponseAux.getInt(TAG_SUCCESS);
                    mensaje = jsonArrayResponseAux.getString(TAG_MESSAGE);

                    if (success == 0) {
                        jsonArray = json.optJSONArray(ARTICULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, ARTICULO, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("articulo");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(COMISION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, COMISION, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("comision");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(DIRECCION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, DIRECCION, SplashAdm.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(EQUIPO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("equipo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TORNEO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("torneo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(CANCHA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("cancha");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(DIVISION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, DIVISION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("division");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(FIXTURE_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, FIXTURE_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("fixture");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(JUGADOR_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, JUGADOR_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            }
                        }
                        //                           jsonArray = null;
//                            jsonAux = null;
//                            jsonArray = json.optJSONArray(POSICION_ADEFUL);
//                            if (jsonArray != null) {
//                                if (jsonArray.length() > 0) {
//                                    if (!jsonParsing.processingJson(jsonArray, POSICION_ADEFUL, SplashUsuario.this))
//                                        precessOK = false;
//                                }
//                            }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ENTRENAMIENTO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("entrenamiento");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ENTRENAMIENTO_DIVISION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_DIVISION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            }
                        }
                        if (isAsistencia) {
                            if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_CANTIDAD_ADEFUL, SplashAdm.this)) {
                                mensaje = addMessage("cantidad");
                                precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ENTRENAMIENTO_ASISTENCIA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("asistencia");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(SANCION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, SANCION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("sancion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(NOTIFICACION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, NOTIFICACION, SplashAdm.this)) {
                                    mensaje = addMessage("notificacion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(NOTICIA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, NOTICIA, SplashAdm.this)) {
                                    mensaje = addMessage("noticia");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(FOTO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, FOTO, SplashAdm.this)) {
                                    mensaje = addMessage("foto ");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(PUBLICIDAD);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, PUBLICIDAD, SplashAdm.this)) {
                                    mensaje = addMessage("publicidad");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(USUARIO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, USUARIO, SplashAdm.this)) {
                                    mensaje = addMessage("usuario");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(MODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, MODULO, SplashAdm.this)) {
                                    mensaje = addMessage("modulo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(SUBMODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, SUBMODULO, SplashAdm.this)) {
                                    mensaje = addMessage("submodulo");
                                    precessOK = false;
                                }
                            }
                        }
                    } else if (success == 3) {
                        Intent i = new Intent(SplashAdm.this, NavigationUsuario.class);
                        startActivity(i);
                    } else {
                        precessOK = false;
                    }
                } else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con soporte.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con soporte.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressSplash.setVisibility(View.GONE);

            if (result) {
                if(isNotificacion){
                    Intent i = new Intent(SplashAdm.this, NotificacionUsuario.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashAdm.this, NavigationUsuario.class);
                    startActivity(i);
                }
            } else {
                auxiliarGeneral.errorWebService(SplashAdm.this, mensaje);
            }
        }
    }

    public String addMessage(String entity) {

        return "Error(" + entity + "). Por favor comuniquese con soporte.";
    }


    public boolean checkGCM() {

        context = getApplicationContext();
        //Chequemos si está instalado Google Play Services
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(SplashAdm.this);
            regid = getRegistrationId(context);
            if (regid.equals("")) {
                TareaRegistroGCM tarea = new TareaRegistroGCM();
                tarea.execute("");
                return true;
            }else{
                return true;
            }
        } else {
            auxiliarGeneral.errorWebService(SplashAdm.this, "No se ha encontrado Google Play Services.");
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    //llevar a la clase auxiliar
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        SharedPreferences prefs = getSharedPreferences(
                SplashAdm.class.getSimpleName(),
                Context.MODE_PRIVATE);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        if (registrationId.length() == 0) {
            Log.d(TAG, "Registro GCM no encontrado.");
            return "";
        }

//        String registeredUser =
//                prefs.getString(PROPERTY_USER, "user");

        int registeredVersion =
                prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);

        long expirationTime =
                prefs.getLong(PROPERTY_EXPIRATION_TIME, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String expirationDate = sdf.format(new Date(expirationTime));

        Log.d(TAG, "Registro GCM encontrado (version=" + registeredVersion +
                ", expira=" + expirationDate + ")");

        int currentVersion = getAppVersion(context);

        if (registeredVersion != currentVersion) {
            Log.d(TAG, "Nueva versión de la aplicación.");
            return "";
        } else if (System.currentTimeMillis() > expirationTime) {
            Log.d(TAG, "Registro GCM expirado.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);

            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Error al obtener versión: " + e);
        }
    }

    private class TareaRegistroGCM extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String msg = "";

            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);

                //Nos registramos en nuestro servidor
                boolean registrado = registroServidor(regid, String.valueOf(System.currentTimeMillis() + EXPIRATION_TIME_MS));

                //Guardamos los datos del registro
                if (registrado) {
                    setRegistrationId(context, regid);
                }else{
                    msg = mensaje;
                }
            } catch (IOException ex) {
                msg = ex.getMessage();
            }

            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
        if(!s.isEmpty())
            auxiliarGeneral.errorWebService(SplashAdm.this, s);
        }
    }

    private boolean registroServidor(String regId, String fecha) {

        final String URL = auxiliarGeneral.getURLGCMALL();
        Request request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("id", regId);
        request.setParametrosDatos("fecha", fecha);

        int success;
        JSONObject json = null;
        boolean precessOK = true;
        try {
            json = jsonParsing.parsingJsonObject(request, URL);
            if (json != null) {
                success = json.getInt(TAG_SUCCESS);
                mensaje = json.getString(TAG_MESSAGE);
                if (success == 0) {
                    id = json.getInt(TAG_ID);
                    if (id > 0) {
                        return precessOK;
                    } else {
                        mensaje = "Error id GCM";
                        precessOK = false;
                    }
                }else{
                    mensaje = "Error GCM";
                    precessOK = false;
                }
            }else{
                mensaje = "Error null GCM";
                precessOK = false;
            }
        } catch (JSONException e) {
            precessOK = false;
            mensaje = "Error(5). Por favor comuniquese con el administrador.";
        }
        return precessOK;
    }

    private void setRegistrationId(Context context, String regId) {
        SharedPreferences prefs = getSharedPreferences(
                SplashAdm.class.getSimpleName(),
                Context.MODE_PRIVATE);

        int appVersion = getAppVersion(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PROPERTY_USER, id);
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.putLong(PROPERTY_EXPIRATION_TIME,
                System.currentTimeMillis() + EXPIRATION_TIME_MS);

        editor.commit();
    }
}
