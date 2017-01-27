package com.estrelladelsur.estrelladelsur.navegador.administrador;

import android.content.Context;
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
import com.estrelladelsur.estrelladelsur.webservice.Variable;

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
                case Variable.TABLA_TABLA_ADEFUL:
                    request.setParametrosDatos(Variable.TABLA_ADEFUL, listTablaAdeful.get(i).getFECHA());
                    break;
                case Variable.TABLA_EQUIPO_ADEFUL:
                    request.setParametrosDatos(Variable.EQUIPO_ADEFUL,  listTablaAdeful.get(i).getFECHA());
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
                case Variable.TABLA_POSICION:
                    request.setParametrosDatos(Variable.POSICION_ADEFUL, listTablaAdeful.get(i).getFECHA());
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
            switch (listTablaLifuba.get(i).getTABLA()){
                case Variable.TABLA_TABLA_LIFUBA:
                    request.setParametrosDatos(Variable.TABLA_LIFUBA, listTablaLifuba.get(i).getFECHA());
                    break;
                case Variable.TABLA_EQUIPO_LIFUBA:
                    request.setParametrosDatos(Variable.EQUIPO_LIFUBA,  listTablaLifuba.get(i).getFECHA());
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
            switch (listTablaGeneral.get(i).getTABLA()){
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
                    request.setParametrosDatos(Variable.DIRECCION,  listTablaGeneral.get(i).getFECHA());
                    break;
                case Variable.TABLA_CARGO_ADEFUL:
                    request.setParametrosDatos(Variable.CARGO,  listTablaGeneral.get(i).getFECHA());
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
                case Variable.TABLA_PERMISO:
                    request.setParametrosDatos(Variable.PERMISO, listTablaGeneral.get(i).getFECHA());
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
        requestUrl.setParametrosDatos("URL", auxiliarGeneral.getURL() + auxiliarGeneral.getURLSINCRONIZARADM());
        if (auxiliarGeneral.isNetworkAvailable(SplashAdm.this))
            new TaskSincronizar().execute(request, requestUrl);
        else
            auxiliarGeneral.errorWebService(SplashAdm.this, "Sin conexión a Internet. Vuelva a intentarlo.");
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

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_ADEFUL, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_adeful");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TABLA_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_LIFUBA, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_lifuba");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.TABLA_GENERAL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.TABLA_GENERAL, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("tabla_general");
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ARTICULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.ARTICULO, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("articulo");
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.ARTICULO, SplashAdm.this)) {
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

                                if (!jsonParsing.processingJson(jsonArray, Variable.COMISION, SplashAdm.this)) {
                                    precessOK = false;
                                    mensaje = addMessage("comision");
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.COMISION, SplashAdm.this)) {
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

                                if (!jsonParsing.processingJson(jsonArray, Variable.DIRECCION, SplashAdm.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.DIRECCION, SplashAdm.this)) {
                                    mensaje = addMessage("direccion");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.CARGO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, Variable.CARGO, SplashAdm.this)) {
                                    mensaje = addMessage("cargo");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.CARGO, SplashAdm.this)) {
                                    mensaje = addMessage("cargo");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.EQUIPO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("equipo_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("equipo_adeful");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.EQUIPO_LIFUBA);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_LIFUBA, SplashAdm.this)) {
                                    mensaje = addMessage("equipo_lifuba");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(jsonArray, Variable.EQUIPO_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.TORNEO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("torneo_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.TORNEO_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.TORNEO_LIFUBA, SplashAdm.this)) {
                                    mensaje = addMessage("torneo_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.TORNEO_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.CANCHA_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("cancha_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.CANCHA_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.CANCHA_LIFUBA, SplashAdm.this)) {
                                    mensaje = addMessage("cancha_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.CANCHA_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.DIVISION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("division");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.DIVISION_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.FIXTURE_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("fixture_adeful");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.FIXTURE_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.FIXTURE_LIFUBA, SplashAdm.this)) {
                                    mensaje = addMessage("fixture_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.FIXTURE_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.JUGADOR_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.JUGADOR_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("jugador");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.POSICION_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.POSICION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("posicion");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.POSICION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("posicion");
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("entrenamiento");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_DIVISION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_DIVISION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("entrenamiento_division");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("asistencia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.ENTRENAMIENTO_ASISTENCIA_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.SANCION_ADEFUL, SplashAdm.this)) {
                                    mensaje = addMessage("sancion_adeful");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.SANCION_ADEFUL, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.SANCION_LIFUBA, SplashAdm.this)) {
                                    mensaje = addMessage("sancion_lifuba");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.SANCION_LIFUBA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.NOTIFICACION, SplashAdm.this)) {
                                    mensaje = addMessage("notificacion");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.NOTIFICACION, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.NOTICIA, SplashAdm.this)) {
                                    mensaje = addMessage("noticia");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.NOTICIA, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.FOTO, SplashAdm.this)) {
                                    mensaje = addMessage("foto ");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.FOTO, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.PUBLICIDAD, SplashAdm.this)) {
                                    mensaje = addMessage("publicidad");
                                    precessOK = false;
                                }
                            } else {
                                if (!jsonParsing.processingJson(Variable.PUBLICIDAD, SplashAdm.this)) {
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
                                if (!jsonParsing.processingJson(jsonArray, Variable.USUARIO, SplashAdm.this)) {
                                    mensaje = addMessage("usuario");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.PERMISO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.PERMISO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.PERMISO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso");
                                    precessOK = false;
                                }
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(Variable.PERMISO_MODULO);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, Variable.PERMISO_MODULO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso_modulo");
                                    precessOK = false;
                                }
                            }  else {
                                if (!jsonParsing.processingJson(Variable.PERMISO_MODULO, SplashAdm.this)) {
                                    mensaje = addMessage("permiso_modulo");
                                    precessOK = false;
                                }
                            }
                        }
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
