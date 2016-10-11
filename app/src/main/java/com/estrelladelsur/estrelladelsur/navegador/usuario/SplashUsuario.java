package com.estrelladelsur.estrelladelsur.navegador.usuario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class SplashUsuario extends AppCompatActivity {

    private ControladorUsuarioAdeful controladorUsuario;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressBar progressSplash;
    private Request request = new Request();
    private Request requestUrl = new Request();
    private JsonParsing jsonParsing = new JsonParsing(SplashUsuario.this);
    private static final String TAG_SUCCESS = "success";
    private static final String SINDATOS = "SIN DATOS";
    private static final String TAG_MESSAGE = "message";
    private String mensaje = null;
    private String sinDatos = null;
    private Articulo articulo;
    private Comision comision;
    private Direccion direccion;
    private Equipo equipo;
    private Torneo torneo;
    private Cancha cancha;
    private Division division;
    private Fixture fixture;
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
    public static final String NOTIFICACION = "notificacion";
    public static final String NOTICIAS = "noticias";
    public static final String FOTOS = "fotos";
    public static final String PUBLICIDAD = "publicidad";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        controladorUsuario = new ControladorUsuarioAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(SplashUsuario.this);
//
//        titulos = auxiliarGeneral.tituloFont(NavigationUsuario.this);
//        adeful = auxiliarGeneral.ligaFont(NavigationUsuario.this);
        init();
//        inicializarDatosGenerales();
//        insertUsuarioAdm();

    }


    public void init() {
        progressSplash = (ProgressBar) findViewById(R.id.progressSplash);
        envioWebService();
//        fecha_articulo = controladorUsuario.selectFechaWebService();
//        if (fecha_articulo != null) {
//            auxiliarGeneral.errorDataBase(SplashUsuario.this);
//        }}


//        Articulo a = new Articulo(0,"HISTORIA DEL CLUB","Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.\n" +
//                "\n" +
//                "Aunque no posee actualmente fuentes para justificar sus hipótesis, el profesor de filología clásica Richard McClintock asegura que su uso se remonta a los impresores de comienzos del siglo XVI.1 Su uso en algunos editores de texto muy conocidos en la actualidad ha dado al texto lorem ipsum nueva popularidad.\n" +
//                "\n" +
//                "El texto en sí no tiene sentido, aunque no es completamente aleatorio, sino que deriva de un texto de Cicerón en lengua latina, a cuyas palabras se les han eliminado sílabas o letras. El significado del texto no tiene importancia, ya que solo es una demostración o prueba, pero se inspira en la obra de Cicerón De finibus bonorum et malorum (Sobre los límites del bien y del mal) que comienza con");
//
//        Comision c = new Comision(1, "Juan Perez",null,"Presidente","01/12/2016","22/12/2016");
//
//        if(controladorUsuario.insertArticuloUsuario(1,a) && controladorUsuario.insertComisionUsuario(c)){
//            Intent i =  new Intent(SplashUsuario.this,NavigationUsuario.class);
//            startActivity(i);
//        }else{
//            auxiliarGeneral.errorDataBase(SplashUsuario.this);
//        }


    }

    public void envioWebService() {
        request.setMethod("POST");
        // request.setParametrosDatos("fecha_articulo", fecha_articulo);
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
        requestUrl.setParametrosDatos("URL", auxiliarGeneral.getURL() + auxiliarGeneral.getURLSINCRONIZARUSUARIO());

//
//        if (tipo == 0) {
//            request.setQuery("SUBIR");
//            request.setParametrosDatos("usuario_creador", articulo.getUSUARIO_CREADOR());
//            request.setParametrosDatos("fecha_creacion", articulo.getFECHA_CREACION());
//        } else {
//            request.setQuery("EDITAR");
//            request.setParametrosDatos("id_articulo", String.valueOf(articulo.getID_ARTICULO()));
//            request.setParametrosDatos("usuario_actualizacion", articulo.getUSUARIO_ACTUALIZACION());
//        }
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

                                if (!jsonParsing.processingJson(jsonArray, ARTICULO, SplashUsuario.this))
                                    precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(COMISION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, COMISION, SplashUsuario.this))
                                    precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(DIRECCION);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {

                                if (!jsonParsing.processingJson(jsonArray, DIRECCION, SplashUsuario.this))
                                    precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(EQUIPO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, EQUIPO_ADEFUL, SplashUsuario.this))
                                    precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(TORNEO_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, TORNEO_ADEFUL, SplashUsuario.this))
                                    precessOK = false;
                            }
                        }
                        jsonArray = null;
                        jsonAux = null;
                        jsonArray = json.optJSONArray(CANCHA_ADEFUL);
                        if (jsonArray != null) {
                            if (jsonArray.length() > 0) {
                                if (!jsonParsing.processingJson(jsonArray, CANCHA_ADEFUL, SplashUsuario.this))
                                    precessOK = false;
                            }
                            jsonArray = null;
                            jsonAux = null;
                            jsonArray = json.optJSONArray(DIVISION_ADEFUL);
                            if (jsonArray != null) {
                                if (jsonArray.length() > 0) {
                                    if (!jsonParsing.processingJson(jsonArray, DIVISION_ADEFUL, SplashUsuario.this))
                                        precessOK = false;
                                }
                            }
                            jsonArray = null;
                            jsonAux = null;
                            jsonArray = json.optJSONArray(FIXTURE_ADEFUL);
                            if (jsonArray != null) {
                                if (jsonArray.length() > 0) {
                                    if (!jsonParsing.processingJson(jsonArray, FIXTURE_ADEFUL, SplashUsuario.this))
                                        precessOK = false;
                                }
                            }
                            jsonArray = null;
                            jsonAux = null;
                            jsonArray = json.optJSONArray(JUGADOR_ADEFUL);
                            if (jsonArray != null) {
                                if (jsonArray.length() > 0) {
                                    if (!jsonParsing.processingJson(jsonArray, JUGADOR_ADEFUL, SplashUsuario.this))
                                        precessOK = false;
                                }
                            }
                            jsonArray = null;
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
                                    if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_ADEFUL, SplashUsuario.this))
                                        precessOK = false;
                                }
                            }
                            jsonArray = null;
                            jsonAux = null;
                            jsonArray = json.optJSONArray(ENTRENAMIENTO_DIVISION_ADEFUL);
                            if (jsonArray != null) {
                                if (jsonArray.length() > 0) {
                                    if (!jsonParsing.processingJson(jsonArray, ENTRENAMIENTO_DIVISION_ADEFUL, SplashUsuario.this))
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

                Intent i = new Intent(SplashUsuario.this, NavigationUsuario.class);
                startActivity(i);
            } else {
                auxiliarGeneral.errorWebService(SplashUsuario.this, mensaje);
            }
        }
    }

//    public static Bitmap getBitmap(String url) {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                .permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Bitmap bm = null;
//        BitmapFactory.Options bmOptions;
//        bmOptions = new BitmapFactory.Options();
//        bmOptions.inSampleSize = 1;
//
//        if (bmOptions != null)
//            bm = loadBitmap(url, bmOptions);
//
//        return bm;
//    }

//    public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
//        Bitmap bitmap = null;
//        InputStream in = null;
//        try {
//            in = OpenHttpConnection(URL);
//            bitmap = BitmapFactory.decodeStream(in, null, options);
//            if (in != null)
//                in.close();
//        } catch (IOException e1) {
//        }
//        return bitmap;
//    }
//
//    private static InputStream OpenHttpConnection(String strURL)
//            throws IOException {
//        InputStream inputStream = null;
//        URL url = new URL(strURL);
//        URLConnection conn = url.openConnection();
//
//        try {
//            HttpURLConnection httpConn = (HttpURLConnection) conn;
//            httpConn.setRequestMethod("GET");
//            httpConn.connect();
//
//            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                inputStream = httpConn.getInputStream();
//            }
//        } catch (Exception ex) {
//        }
//        return inputStream;
//    }


}
