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
import com.estrelladelsur.estrelladelsur.webservice.Variable;
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
    private static final String TAG_DATE = "fecha";
    private Context context;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;
    private long date_gcm;
    private String SENDER_ID = "686300125964";
    private String regid;
    private GoogleCloudMessaging gcm;
    private static final String TAG_ID = "id";
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    private static final String PROPERTY_USER = "id";
    private String mensaje = null;
    public static String TAG = "GCM";
    private int id = 0;
    private boolean isNotificacion = false;
    private String fecha_anterior;
    private boolean gestion = true;
    private boolean isDate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        controladorUsuarioAdeful = new ControladorUsuarioAdeful(this);
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(this);
        controladorUsuarioGeneral = new ControladorUsuarioGeneral(this);
        auxiliarGeneral = new AuxiliarGeneral(SplashUsuario.this);

//        close = SplashUsuario.this.getIntent().getBooleanExtra("close",
//                false);
//        if (close)
//            closeApp();

        if (checkGCM())
            init();
    }

//    public void closeApp() {
//        finish();
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
                case Variable.TABLA_TABLA_ADEFUL:
                    request.setParametrosDatos(Variable.TABLA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_EQUIPO_ADEFUL:
                    request.setParametrosDatos(Variable.EQUIPO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_DIVISION_ADEFUL:
                    request.setParametrosDatos(Variable.DIVISION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_TORNEO_ADEFUL:
                    request.setParametrosDatos(Variable.TORNEO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_CANCHA_ADEFUL:
                    request.setParametrosDatos(Variable.CANCHA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_FIXTURE_ADEFUL:
                    request.setParametrosDatos(Variable.FIXTURE_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_JUGADOR:
                    request.setParametrosDatos(Variable.JUGADOR_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_ENTRENAMIENTO:
                    request.setParametrosDatos(Variable.ENTRENAMIENTO_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_ENTRENAMIENTO_DIVISION:
                    request.setParametrosDatos(Variable.ENTRENAMIENTO_DIVISION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_ASISTENCIA:
                    request.setParametrosDatos(Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_SANCION_ADEFUL:
                    request.setParametrosDatos(Variable.SANCION_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
            }
        }
        //LIFUBA
        for (int i = 0; i < listTablaLifuba.size(); i++) {
            switch (listTablaLifuba.get(i).getTABLA()) {
                case Variable.TABLA_TABLA_LIFUBA:
                    request.setParametrosDatos(Variable.TABLA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_EQUIPO_LIFUBA:
                    request.setParametrosDatos(Variable.EQUIPO_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_TORNEO_LIFUBA:
                    request.setParametrosDatos(Variable.TORNEO_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_CANCHA_LIFUBA:
                    request.setParametrosDatos(Variable.CANCHA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_FIXTURE_LIFUBA:
                    request.setParametrosDatos(Variable.FIXTURE_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_SANCION_LIFUBA:
                    request.setParametrosDatos(Variable.SANCION_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
            }
        }
        //GENERAL
        for (int i = 0; i < listTablaGeneral.size(); i++) {
            switch (listTablaGeneral.get(i).getTABLA()) {
                case Variable.TABLA_TABLA_GENERAL:
                    request.setParametrosDatos(Variable.TABLA_GENERAL, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_ARTICULO:
                    request.setParametrosDatos(Variable.ARTICULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_COMISION:
                    request.setParametrosDatos(Variable.COMISION, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_DIRECCION:
                    request.setParametrosDatos(Variable.DIRECCION, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_NOTIFICACION:
                    request.setParametrosDatos(Variable.NOTIFICACION, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_NOTICIA:
                    request.setParametrosDatos(Variable.NOTICIA, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_FOTO:
                    request.setParametrosDatos(Variable.FOTO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_PUBLICIDAD:
                    request.setParametrosDatos(Variable.PUBLICIDAD, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_USUARIO:
                    request.setParametrosDatos(Variable.USUARIO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_MODULO:
                    request.setParametrosDatos(Variable.MODULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_SUBMODULO:
                    request.setParametrosDatos(Variable.SUBMODULO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_ANIO:
                    request.setParametrosDatos(Variable.ANIO, listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_FECHA:
                    request.setParametrosDatos(Variable.FECHA, listTablaGeneral.get(i).getFECHA());
                    break;
            }
        }
        requestUrl = new Request();
        requestUrl.setParametrosDatos("URL", auxiliarGeneral.getURL() + auxiliarGeneral.getURLSINCRONIZARUSUARIO());
        if (auxiliarGeneral.isNetworkAvailable(SplashUsuario.this))
            new TaskSincronizar().execute(request, requestUrl);
        else
            auxiliarGeneral.errorWebService(SplashUsuario.this, SplashUsuario.this.getResources().getString(R.string.error_without_internet));
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
                        jsonArray = json.optJSONArray(Variable.TABLA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TABLA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TABLA_GENERAL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_GENERAL, SplashUsuario.this)) {
                                    mensaje = addMessage("tabla_general");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ARTICULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.ARTICULO, SplashUsuario.this)) {
                                    mensaje = addMessage("articulo");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ARTICULO, SplashUsuario.this)) {
                                    mensaje = addMessage("articulo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.COMISION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.COMISION, SplashUsuario.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("comision");
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.COMISION, SplashUsuario.this)) {
                                    mensaje = addMessage("comision");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.DIRECCION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.DIRECCION, SplashUsuario.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.DIRECCION, SplashUsuario.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.EQUIPO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.EQUIPO_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TORNEO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.TORNEO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.TORNEO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TORNEO_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.TORNEO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.TORNEO_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("torneo_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.CANCHA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.CANCHA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.CANCHA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.CANCHA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.CANCHA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.CANCHA_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("cancha_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.DIVISION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, Variable.DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("division");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("division");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.FIXTURE_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.FIXTURE_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.FIXTURE_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.FIXTURE_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.FIXTURE_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.FIXTURE_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("fixture_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.JUGADOR_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.JUGADOR_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.JUGADOR_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ENTRENAMIENTO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ENTRENAMIENTO_DIVISION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                isAsistencia = true;
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_DIVISION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            }
                        }
                        if (isAsistencia) {
                            if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_CANTIDAD_ADEFUL, SplashUsuario.this)) {
                                mensaje = addMessage("cantidad");
                                precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("asistencia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("asistencia");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.SANCION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.SANCION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.SANCION_ADEFUL, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.SANCION_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.SANCION_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.SANCION_LIFUBA, SplashUsuario.this)) {
                                    mensaje = addMessage("sancion_lifuba");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.NOTIFICACION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.NOTIFICACION, SplashUsuario.this)) {
                                    mensaje = addMessage("notificacion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.NOTIFICACION, SplashUsuario.this)) {
                                    mensaje = addMessage("notificacion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.NOTICIA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.NOTICIA, SplashUsuario.this)) {
                                    mensaje = addMessage("noticia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.NOTICIA, SplashUsuario.this)) {
                                    mensaje = addMessage("noticia");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.FOTO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.FOTO, SplashUsuario.this)) {
                                    mensaje = addMessage("foto");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.FOTO, SplashUsuario.this)) {
                                    mensaje = addMessage("foto");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.PUBLICIDAD);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.PUBLICIDAD, SplashUsuario.this)) {
                                    mensaje = addMessage("publicidad");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.PUBLICIDAD, SplashUsuario.this)) {
                                    mensaje = addMessage("publicidad");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.USUARIO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.USUARIO, SplashUsuario.this)) {
                                    mensaje = addMessage("usuario");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.MODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.MODULO, SplashUsuario.this)) {
                                    mensaje = addMessage("modulo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.SUBMODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.SUBMODULO, SplashUsuario.this)) {
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

                date_gcm = System.currentTimeMillis() + EXPIRATION_TIME_MS;
                //Nos registramos en nuestro servidor
                boolean registrado = registroServidor(regid, String.valueOf(date_gcm), gestion);

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

    private boolean registroServidor(String regId, String fecha, boolean gestion ) {

        final String URL = auxiliarGeneral.getURLGCMALL();
        Request request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("id", regId);
        request.setParametrosDatos("fecha", fecha);
        if(gestion)
            request.setParametrosDatos("gestion", "0");
        else
            request.setParametrosDatos("gestion", "1");

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
                        isDate = false;
                        return precessOK;
                    } else {
                        mensaje = "Error id GCM";
                        precessOK = false;
                    }
                } else if(success == 4){
                    fecha_anterior = json.getString(TAG_DATE);
                    isDate = true;
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

        if(isDate){
            long verify_date =  Long.parseLong(fecha_anterior);
            if (System.currentTimeMillis() > verify_date) {
                gestion = false;
                TareaRegistroGCM tarea = new TareaRegistroGCM();
                tarea.execute("");
            } else {
                date_gcm = verify_date;
            }
        }

        SharedPreferences prefs = getSharedPreferences(
                SplashUsuario.class.getSimpleName(),
                Context.MODE_PRIVATE);

        int appVersion = getAppVersion(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PROPERTY_USER, id);
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.putLong(PROPERTY_EXPIRATION_TIME, date_gcm);

        editor.commit();
    }
}
