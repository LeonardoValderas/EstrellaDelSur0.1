package com.estrelladelsur.estrelladelsur.navegador.usuario;

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
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.database.usuario.general.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SplashUsuario extends AppCompatActivity {

    private ControladorUsuarioAdeful controladorUsuarioAdeful;
    private ControladorUsuarioLifuba controladorUsuarioLifuba;
    private ControladorUsuarioGeneral controladorUsuarioGeneral;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressBar progressSplash;
    private Request request;
    private Request requestUrl;
    private JsonParsing jsonParsing = new JsonParsing();
    private static final String TAG_SUCCESS = "success";
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
    public static final String TABLA_ADEFUL = "tabla_fecha_adeful";
    public static final String TABLA_LIFUBA = "tabla_fecha_lifuba";
    public static final String TABLA_GENERAL = "tabla_fecha_general";
    public static final String TABLA_ARTICULO = "ARTICULO";
    public static final String TABLA_CANCHA_ADEFUL = "CANCHA_ADEFUL";
    public static final String TABLA_CANCHA_LIFUBA = "CANCHA_LIFUBA";
    public static final String TABLA_COMISION = "COMISION";
    public static final String TABLA_DIRECCION = "DIRECCION";
    public static final String TABLA_DIVISION_ADEFUL = "DIVISION_ADEFUL";
    public static final String TABLA_ENTRENAMIENTO = "ENTRENAMIENTO_ADEFUL";
    public static final String TABLA_ENTRENAMIENTO_DIVISION = "ENTRENAMIENTO_DIVISION_ADEFUL";
    public static final String TABLA_EQUIPO_ADEFUL = "EQUIPO_ADEFUL";
    public static final String TABLA_EQUIPO_LIFUBA = "EQUIPO_LIFUBA";
    public static final String TABLA_FIXTURE_ADEFUL = "FIXTURE_ADEFUL";
    public static final String TABLA_FIXTURE_LIFUBA = "FIXTURE_LIFUBA";
    public static final String TABLA_JUGADOR = "JUGADOR_ADEFUL";
    public static final String TABLA_SANCION_ADEFUL = "SANCION_ADEFUL";
    public static final String TABLA_SANCION_LIFUBA = "SANCION_LIFUBA";
    public static final String TABLA_TORNEO_ADEFUL = "TORNEO_ADEFUL";
    public static final String TABLA_TORNEO_LIFUBA = "TORNEO_LIFUBA";
    public static final String TABLA_ASISTENCIA = "ENTRENAMIENTO_ASISTENCIA_ADEFUL";
    public static final String TABLA_TABLA_ADEFUL = "FECHA_TABLA";
    public static final String TABLA_TABLA_LIFUBA = "FECHA_TABLA";
    public static final String TABLA_TABLA_GENERAL = "FECHA_TABLA";
    public static final String TABLA_NOTIFICACION = "NOTIFICACION";
    public static final String TABLA_NOTICIA = "NOTICIA";
    public static final String TABLA_FOTO = "FOTO";
    public static final String TABLA_PUBLICIDAD = "PUBLICIDAD";
    public static final String TABLA_USUARIO = "USUARIO";
    public static final String TABLA_MODULO = "MODULO";
    public static final String TABLA_SUBMODULO = "SUBMODULO";
  //  public static final String TABLA_PERMISO = "PERMISO";
    public static final String TABLA_ANIO = "ANIO";
    public static final String TABLA_FECHA = "FECHA";

    public static String TAG = "GCM";
    private int id = 0;
    private boolean isNotificacion = false;
    private boolean close = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        controladorUsuarioAdeful = new ControladorUsuarioAdeful(this);
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(this);
        controladorUsuarioGeneral = new ControladorUsuarioGeneral(this);
        auxiliarGeneral = new AuxiliarGeneral(SplashUsuario.this);

        close = SplashUsuario.this.getIntent().getBooleanExtra("close",
                false);
        if (close)
            closeApp();

        if (checkGCM())
            init();
    }

    public void closeApp() {
        finish();
    }

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
        request = new Request();
        request.setMethod("POST");

        List<Tabla> listTablaAdeful = new ArrayList<>();
        List<Tabla> listTablaLifuba = new ArrayList<>();
        List<Tabla> listTablaGeneral = new ArrayList<>();

        listTablaAdeful = controladorUsuarioAdeful.selectListaTablaAdeful();
        listTablaLifuba = controladorUsuarioLifuba.selectListaTablaLifuba();
        listTablaGeneral = controladorUsuarioGeneral.selectListaTablaGeneral();

        //ADEFUL
        for (int i = 0; i < listTablaAdeful.size(); i++) {
            switch (listTablaAdeful.get(i).getTABLA()) {
                case TABLA_TABLA_ADEFUL:
                    request.setParametrosDatos(TABLA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_EQUIPO_ADEFUL:
                    request.setParametrosDatos(EQUIPO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_DIVISION_ADEFUL:
                    request.setParametrosDatos(DIVISION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_TORNEO_ADEFUL:
                    request.setParametrosDatos(TORNEO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_CANCHA_ADEFUL:
                    request.setParametrosDatos(CANCHA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_FIXTURE_ADEFUL:
                    request.setParametrosDatos(FIXTURE_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_JUGADOR:
                    request.setParametrosDatos(JUGADOR_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_ENTRENAMIENTO:
                    request.setParametrosDatos(ENTRENAMIENTO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_ENTRENAMIENTO_DIVISION:
                    request.setParametrosDatos(ENTRENAMIENTO_DIVISION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_ASISTENCIA:
                    request.setParametrosDatos(ENTRENAMIENTO_ASISTENCIA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_SANCION_ADEFUL:
                    request.setParametrosDatos(SANCION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
            }
        }
        //LIFUBA
        for (int i = 0; i < listTablaLifuba.size(); i++) {
            switch (listTablaLifuba.get(i).getTABLA()) {
                case TABLA_TABLA_LIFUBA:
                    request.setParametrosDatos(TABLA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_EQUIPO_LIFUBA:
                    request.setParametrosDatos(EQUIPO_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_TORNEO_LIFUBA:
                    request.setParametrosDatos(TORNEO_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_CANCHA_LIFUBA:
                    request.setParametrosDatos(CANCHA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_FIXTURE_LIFUBA:
                    request.setParametrosDatos(FIXTURE_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_SANCION_LIFUBA:
                    request.setParametrosDatos(SANCION_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
            }
        }
        //GENERAL
        for (int i = 0; i < listTablaGeneral.size(); i++) {
            switch (listTablaGeneral.get(i).getTABLA()) {
                case TABLA_TABLA_GENERAL:
                    request.setParametrosDatos(TABLA_GENERAL, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_ARTICULO:
                    request.setParametrosDatos(ARTICULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_COMISION:
                    request.setParametrosDatos(COMISION, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_DIRECCION:
                    request.setParametrosDatos(DIRECCION, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_NOTIFICACION:
                    request.setParametrosDatos(NOTIFICACION, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_NOTICIA:
                    request.setParametrosDatos(NOTICIA, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_FOTO:
                    request.setParametrosDatos(FOTO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_PUBLICIDAD:
                    request.setParametrosDatos(PUBLICIDAD, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_USUARIO:
                    request.setParametrosDatos(USUARIO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_MODULO:
                    request.setParametrosDatos(MODULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_SUBMODULO:
                    request.setParametrosDatos(SUBMODULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_ANIO:
                    request.setParametrosDatos(ANIO, listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_FECHA:
                    request.setParametrosDatos(FECHA, listTablaGeneral.get(i).getFECHA());
                    break;
            }
        }
        requestUrl = new Request();
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
                        jsonArray = json.optJSONArray(TABLA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, TABLA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TABLA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, TABLA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TABLA_GENERAL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, TABLA_GENERAL, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_general");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ARTICULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, ARTICULO, SplashUsuario.this)) {
                                    mensaje = addMessage("articulo");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(ARTICULO, SplashUsuario.this)) {
                                    mensaje = addMessage("articulo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(COMISION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, COMISION, SplashUsuario.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("comision");
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, COMISION, SplashUsuario.this)) {
                                    mensaje = addMessage("comision");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(DIRECCION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, DIRECCION, SplashUsuario.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, DIRECCION, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(EQUIPO_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TORNEO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(TORNEO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TORNEO_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(TORNEO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(CANCHA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(CANCHA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(CANCHA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(CANCHA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_lifuba");
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
                                if (!jsonParsing.processingJson(jsonArray, DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("division");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(DIVISION_ADEFUL, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, FIXTURE_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(FIXTURE_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(FIXTURE_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, FIXTURE_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(FIXTURE_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(JUGADOR_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, JUGADOR_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(JUGADOR_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ENTRENAMIENTO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(ENTRENAMIENTO_ADEFUL, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(ENTRENAMIENTO_DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            }
                        }
                        if (isAsistencia) {
                            if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_CANTIDAD_ADEFUL, SplashUsuario.this)) {
                                mensaje = addMessage("cantidad");
                                precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(ENTRENAMIENTO_ASISTENCIA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("asistencia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, SANCION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(SANCION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(SANCION_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, SANCION_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(SANCION_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(NOTIFICACION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, NOTIFICACION, SplashUsuario.this)) {
                                    mensaje = addMessage("notificacion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(NOTIFICACION, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, NOTICIA, SplashUsuario.this)) {
                                    mensaje = addMessage("noticia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(NOTICIA, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, FOTO, SplashUsuario.this)) {
                                    mensaje = addMessage("foto");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(FOTO, SplashUsuario.this)) {
                                    mensaje = addMessage("foto");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(PUBLICIDAD);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, PUBLICIDAD, SplashUsuario.this)) {
                                    mensaje = addMessage("publicidad");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(PUBLICIDAD, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, USUARIO, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, MODULO, SplashUsuario.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, SUBMODULO, SplashUsuario.this)) {
                                    mensaje = addMessage("submodulo");
                                    precessOK = false;
                                }
                            }
                        }
                    } else if (success == 3) {
                        Intent i = new Intent(SplashUsuario.this, NavigationUsuario.class);
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
                if (isNotificacion) {
                    Intent i = new Intent(SplashUsuario.this, NotificacionUsuario.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashUsuario.this, NavigationUsuario.class);
                    startActivity(i);
                }
            } else {
                auxiliarGeneral.errorWebService(SplashUsuario.this, mensaje);
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
            gcm = GoogleCloudMessaging.getInstance(SplashUsuario.this);
            regid = getRegistrationId(context);
            if (regid.equals("")) {
                TareaRegistroGCM tarea = new TareaRegistroGCM();
                tarea.execute("");
                return true;
            } else {
                return true;
            }
        } else {
            auxiliarGeneral.errorWebService(SplashUsuario.this, "No se ha encontrado Google Play Services.");
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
                SplashUsuario.class.getSimpleName(),
                Context.MODE_PRIVATE);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        if (registrationId.length() == 0) {
            Log.d(TAG, "Registro GCM no encontrado.");
            return "";
        }
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
                } else {
                    msg = mensaje;
                }
            } catch (IOException ex) {
                msg = ex.getMessage();
            }

            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.isEmpty())
                auxiliarGeneral.errorWebService(SplashUsuario.this, s);
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
                } else {
                    mensaje = "Error GCM";
                    precessOK = false;
                }
            } else {
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
                SplashUsuario.class.getSimpleName(),
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
