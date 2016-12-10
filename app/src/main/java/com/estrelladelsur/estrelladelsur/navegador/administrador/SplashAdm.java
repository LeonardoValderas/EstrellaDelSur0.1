package com.estrelladelsur.estrelladelsur.navegador.administrador;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsingAdm;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class SplashAdm extends AppCompatActivity {

    private ControladorAdeful controladorAdeful;
    private ControladorLifuba controladorLifuba;
    private ControladorGeneral controladorGeneral;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressBar progressSplash;
    private Request request;
    private Request requestUrl;
    private JsonParsingAdm jsonParsing = new JsonParsingAdm();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
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
    public static final String JUGADOR_ADEFUL = "jugador_adeful";
    public static final String POSICION_ADEFUL = "posicion_adeful";
    public static final String ENTRENAMIENTO_ADEFUL = "entrenamiento_adeful";
    public static final String ENTRENAMIENTO_DIVISION_ADEFUL = "entrenamiento_division_adeful";
    public static final String ENTRENAMIENTO_ASISTENCIA_ADEFUL = "entrenamiento_asistencia_adeful";
    public static final String SANCION_ADEFUL = "sancion_adeful";
    public static final String SANCION_LIFUBA = "sancion_lifuba";
    public static final String NOTIFICACION = "notificacion";
    public static final String NOTICIA = "noticia";
    public static final String FOTO = "foto";
    public static final String PUBLICIDAD = "publicidad";
    public static final String USUARIO = "usuario";
    public static final String PERMISO = "permiso";
    public static final String PERMISO_MODULO = "permiso_modulo";
    public static final String ANIO = "anio";
    public static final String FECHA = "fecha";
    public static final String TABLA_ADEFUL = "tabla_fecha_adeful";
    public static final String TABLA_LIFUBA = "tabla_fecha_lifuba";
    public static final String TABLA_GENERAL = "tabla_fecha_general";
    public static final String TABLA_ARTICULO = "ARTICULO";
    public static final String TABLA_CANCHA_ADEFUL = "CANCHA_ADEFUL";
    public static final String TABLA_CANCHA_LIFUBA = "CANCHA_LIFUBA";
    public static final String TABLA_CARGO_ADEFUL = "CARGO";
    public static final String TABLA_COMISION= "COMISION";
    public static final String TABLA_DIRECCION = "DIRECCION";
    public static final String TABLA_DIVISION_ADEFUL = "DIVISION_ADEFUL";
    public static final String TABLA_ENTRENAMIENTO = "ENTRENAMIENTO_ADEFUL";
    public static final String TABLA_ENTRENAMIENTO_DIVISION = "ENTRENAMIENTO_DIVISION_ADEFUL";
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
    public static final String TABLA_TABLA_ADEFUL = "FECHA_TABLA";
    public static final String TABLA_TABLA_LIFUBA = "FECHA_TABLA";
    public static final String TABLA_TABLA_GENERAL = "FECHA_TABLA";
    public static final String TABLA_NOTIFICACION = "NOTIFICACION";
    public static final String TABLA_NOTICIA = "NOTICIA";
    public static final String TABLA_FOTO = "FOTO";
    public static final String TABLA_PUBLICIDAD = "PUBLICIDAD";
    public static final String TABLA_USUARIO = "USUARIO";
    public static final String TABLA_PERMISO = "PERMISO";
    public static final String TABLA_ANIO = "ANIO";
    public static final String TABLA_FECHA = "FECHA";
    private String usuarioAdministrador = null;
    private int idUsuarioAdministrador;
    private boolean close = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        controladorAdeful = new ControladorAdeful(this);
        controladorLifuba = new ControladorLifuba(this);
        controladorGeneral = new ControladorGeneral(this);
        auxiliarGeneral = new AuxiliarGeneral(SplashAdm.this);

        close = SplashAdm.this.getIntent().getBooleanExtra("close",
                false);
        usuarioAdministrador = SplashAdm.this.getIntent().getStringExtra("usuario");
        idUsuarioAdministrador = SplashAdm.this.getIntent().getIntExtra("id_usuario", 0);
        if(close)
            closeApp();

        init();

    }

    public void closeApp(){
         finish();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        init();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void init() {
        progressSplash = (ProgressBar) findViewById(R.id.progressSplash);
        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");

        List<Tabla> listTablaAdeful = new ArrayList<>();
        List<Tabla> listTablaLifuba = new ArrayList<>();
        List<Tabla> listTablaGeneral = new ArrayList<>();

        listTablaAdeful = controladorAdeful.selectListaTablaAdeful();
        listTablaLifuba = controladorLifuba.selectListaTablaLifuba();
        listTablaGeneral = controladorGeneral.selectListaTablaGeneral();

       //ADEFUL
        for (int i = 0; i < listTablaAdeful.size(); i++) {
            switch (listTablaAdeful.get(i).getTABLA()){
                case TABLA_TABLA_ADEFUL:
                    request.setParametrosDatos(TABLA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case TABLA_EQUIPO_ADEFUL:
                    request.setParametrosDatos(EQUIPO_ADEFUL,  listTablaAdeful.get(i).getFECHA());
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
                case TABLA_POSICION:
                    request.setParametrosDatos(POSICION_ADEFUL, listTablaAdeful.get(i).getFECHA());
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
            switch (listTablaLifuba.get(i).getTABLA()){
                case TABLA_TABLA_LIFUBA:
                    request.setParametrosDatos(TABLA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case TABLA_EQUIPO_LIFUBA:
                    request.setParametrosDatos(EQUIPO_LIFUBA,  listTablaLifuba.get(i).getFECHA());
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
            switch (listTablaGeneral.get(i).getTABLA()){
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
                    request.setParametrosDatos(DIRECCION,  listTablaGeneral.get(i).getFECHA());
                    break;
                case TABLA_CARGO_ADEFUL:
                    request.setParametrosDatos(CARGO,  listTablaGeneral.get(i).getFECHA());
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
                case TABLA_PERMISO:
                    request.setParametrosDatos(PERMISO, listTablaGeneral.get(i).getFECHA());
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
        requestUrl.setParametrosDatos("URL", auxiliarGeneral.getURL() + auxiliarGeneral.getURLSINCRONIZARADM());

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

                                if (!jsonParsing.processingJson(jsonArray, TABLA_ADEFUL, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_adeful");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TABLA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, TABLA_LIFUBA, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_lifuba");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TABLA_GENERAL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, TABLA_GENERAL, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_general");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
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
                        jsonArray = json.optJSONArray(CARGO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, CARGO, SplashAdm.this)) {
                                    mensaje = addMessage("cargo");
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
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, FIXTURE_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, JUGADOR_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(POSICION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, POSICION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("posicion");
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
//                        if (isAsistencia) {
//                            if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_CANTIDAD_ADEFUL, SplashAdm.this)) {
//                                mensaje = addMessage("cantidad");
//                                precessOK = false;
//                            }
//                        }
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
                                if (!jsonParsing.processingJson(jsonArray, SANCION_LIFUBA, SplashAdm.this)) {
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
                        jsonArray = json.optJSONArray(PERMISO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, PERMISO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(PERMISO_MODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, PERMISO_MODULO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso_modulo");
                                    precessOK = false;
                                }
                            }
                        }
//                        jsonArray = null;
//                        jsonAux = null;
//                        jsonArray = json.optJSONArray(MODULO);
//                        if (jsonArray != null) {
//                            if (jsonArray.length() > 0) {
//                                if (!jsonParsing.processingJson(jsonArray, MODULO, SplashAdm.this)) {
//                                    mensaje = addMessage("modulo");
//                                    precessOK = false;
//                                }
//                            }
//                        }
//                        jsonArray = null;
//                        jsonAux = null;
//                        jsonArray = json.optJSONArray(SUBMODULO);
//                        if (jsonArray != null) {
//                            if (jsonArray.length() > 0) {
//                                if (!jsonParsing.processingJson(jsonArray, SUBMODULO, SplashAdm.this)) {
//                                    mensaje = addMessage("submodulo");
//                                    precessOK = false;
//                                }
//                            }
//                        }
                    } else if (success == 3) {
                        Intent i = new Intent(SplashAdm.this, Navigation.class);
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
                    Intent i = new Intent(SplashAdm.this, Navigation.class);
                    i.putExtra("id_usuario",idUsuarioAdministrador);
                    i.putExtra("usuario", usuarioAdministrador);
                    startActivity(i);
            } else {
                auxiliarGeneral.errorWebService(SplashAdm.this, mensaje);
                intentError();
            }
        }
    }

    public String addMessage(String entity) {
        return "Error(" + entity + "). Por favor comuniquese con soporte.";
    }

    public void intentError(){
        Intent i = new Intent(SplashAdm.this, NavigationUsuario.class);
        startActivity(i);
    }
}
