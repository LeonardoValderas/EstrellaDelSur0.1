package com.estrelladelsur.estrelladelsur.auxiliar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap.CompressFormat;

import com.estrelladelsur.estrelladelsur.login.Login;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;
import com.estrelladelsur.estrelladelsur.navegador.usuario.SplashUsuario;

public class AuxiliarGeneral {
    private Context context;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public AuxiliarGeneral(Context context) {
        this.context = context;
    }

    public String setFormatoMes(String mes) {
        String mesFormato = null;
        switch (mes) {
            case "ENERO":
                mesFormato = "01";
                break;
            case "FEBRERO":
                mesFormato = "02";
                break;
            case "MARZO":
                mesFormato = "03";
                break;
            case "ABRIL":
                mesFormato = "04";
                break;
            case "MAYO":
                mesFormato = "05";
                break;
            case "JUNIO":
                mesFormato = "06";
                break;
            case "JULIO":
                mesFormato = "07";
                break;
            case "AGOSTO":
                mesFormato = "08";
                break;
            case "SEPTIEMBRE":
                mesFormato = "09";
                break;
            case "OCTUBRE":
                mesFormato = "10";
                break;
            case "NOVIEMBRE":
                mesFormato = "11";
                break;
            case "DICIEMBRE":
                mesFormato = "12";
                break;
        }
        return mesFormato;
    }

//    public String setFormatoMes(int mes) {
//        String mesFormato = null;
//        switch (mes) {
//            case 01:
//                mesFormato = "ENERO";
//                break;
//            case "FEBRERO":
//                mesFormato = "FEBRERO";
//                break;
//            case "MARZO":
//                mesFormato = "MARZO";
//                break;
//            case "ABRIL":
//                mesFormato = "ABRIL";
//                break;
//            case "MAYO":
//                mesFormato = "MAYO";
//                break;
//            case "JUNIO":
//                mesFormato = "JUNIO";
//                break;
//            case "JULIO":r
//                mesFormato = "JULIO";
//                break;
//            case "AGOSTO":
//                mesFormato = "AGOSTO";
//                break;
//            case "SEPTIEMBRE":
//                mesFormato = "SEPTIEMBRE";
//                break;
//            case "OCTUBRE":
//                mesFormato = "OCTUBRE";
//                break;
//            case "NOVIEMBRE":
//                mesFormato = "NOVIEMBRE";
//                break;
//            case "DICIEMBRE":
//                mesFormato = "DICIEMBRE";
//                break;
//        }
//        return mesFormato;
//    }

    public void errorDataBase(Context context) {
        Toast.makeText(context, "Error en la base de datos interna, vuelva a intentar." +
                        "\nSi el error persiste comuniquese con soporte.",
                Toast.LENGTH_SHORT).show();
    }

    public void errorWebService(Context context, String mensaje) {
        Toast.makeText(context, mensaje,
                Toast.LENGTH_LONG).show();
    }

    //PASA BITMAP A BYTE
    public byte[] pasarBitmapByte(Bitmap b) {
        //Pasar bitmap a byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            b.compress(CompressFormat.PNG, 0, baos);
        } catch (Exception e) {
        }
        return baos.toByteArray();
    }

    public Bitmap setByteToBitmap(byte[] imageFotoByte, int w, int h) {
        Bitmap theImage = BitmapFactory.decodeByteArray(
                imageFotoByte, 0,
                imageFotoByte.length);
        return Bitmap.createScaledBitmap(theImage, w, h, true);
    }

    //FECHAS
    public String getFechaOficial() {
        Date dateOficial = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(dateOficial);
    }

    public String getFechaImagen() {
        Date dateOficial = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(dateOficial);
    }

    //FORMATOS LETRA
    public Typeface tituloFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");
    }

    public Typeface textFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
    }

    public Typeface ligaFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "androidnation.ttf");
    }

    public String getUsuarioPreferences(Context context) {
        SharedPreferences getUser = context.getSharedPreferences("UsuarioLog", context.MODE_PRIVATE);
        return getUser.getString("usuario", null);
    }

    //////////////////////URLS/////////////////////////////
    //Http
    public String getURL() {
        return "http://valdroide.com/";
    }

    //SINCRONIZACION
    public String getURLSINCRONIZARUSUARIO() {
        return "estrella_del_sur/testing/SINCRONIZAR/Usuario/sincronizarUsuario.php";
    }

    public String getURLSINCRONIZARADM() {
        return "estrella_del_sur/testing/SINCRONIZAR/Administrador/sincronizarAdm.php";
    }

    public String getURLSINCRONIZARINDIVIDUAL() {
        return getURL() + "estrella_del_sur/testing/SINCRONIZAR/Usuario/sincronizarIndividual.php";
    }

    //ARTICULO
    public String getURLARTICULO() {
        return "estrella_del_sur/testing/GENERAL/Articulo/";
    }

    public String getURLARTICULOADEFULALL() {
        return getURL() + getURLARTICULO();
    }

    //COMISION
    public String getURLCOMISION() {
        return "estrella_del_sur/testing/GENERAL/Comision/";
    }

    public String getURLCOMISIONADEFULALL() {
        return getURL() + getURLCOMISION();
    }

    public String getURLFOTOCOMISION() {
        return getURL() + "estrella_del_sur/testing/GENERAL/Comision/foto_comision/";
    }

    //DIRECCION
    public String getURLDIRECCION() {
        return "estrella_del_sur/testing/GENERAL/Direccion/";
    }

    public String getURLDIRECCIONADEFULALL() {
        return getURL() + getURLDIRECCION();
    }

    public String getURLFOTODIRECCION() {
        return getURL() + "estrella_del_sur/testing/GENERAL/Direccion/foto_direccion/";
    }

    //CARGO
    public String getURLCARGO() {
        return "estrella_del_sur/testing/GENERAL/Cargo/";
    }

    public String getURLCARGOALL() {
        return getURL() + getURLCARGO();
    }

    //EQUIPO
    public String getURLESCUDOEQUIPO() {
        return "escudo_equipo/";
    }

    public String getURLEQUIPOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/equipo/";
    }

    public String getURLEQUIPOLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Liga/equipo/";
    }

    public String getURLEQUIPOADEFULALL() {
        return getURL() + getURLEQUIPOADEFUL();
    }

    public String getURLEQUIPOLIFUBAALL() {
        return getURL() + getURLEQUIPOLIFUBA();
    }

    //DIVISION
    public String getURLDIVISIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/division/";
    }

    public String getURLDIVISIONADEFULALL() {
        return getURL() + getURLDIVISIONADEFUL();
    }

    public String getURLDIVISIONLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Liga/division/";
    }

    public String getURLDIVISIONLIFUBAALL() {
        return getURL() + getURLDIVISIONLIFUBA();
    }

    //TORNEO
    public String getURLTORNEOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/torneo/";
    }

    public String getURLTORNEOLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Liga/torneo/";
    }

    public String getURLTORNEOADEFULALL() {
        return getURL() + getURLTORNEOADEFUL();
    }

    public String getURLTORNEOLIFUBAALL() {
        return getURL() + getURLTORNEOLIFUBA();
    }

    //MAPA
    public String getURLCANCHAADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/cancha/";
    }

    public String getURLCANCHALIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Liga/cancha/";
    }

    public String getURLCANCHAADEFULALL() {
        return getURL() + getURLCANCHAADEFUL();
    }

    public String getURLCANCHALIFUBAALL() {
        return getURL() + getURLCANCHALIFUBA();
    }

    //MI EQUIPO
    //FIXTURE
    public String getURLFIXTUREADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Fixture/";
    }

    public String getURLFIXTURELIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Mi_Equipo/Fixture/";
    }

    public String getURLFIXTUREADEFULAll() {
        return getURL() + getURLFIXTUREADEFUL();
    }

    public String getURLFIXTURELIFUBAAll() {
        return getURL() + getURLFIXTURELIFUBA();
    }

    //RESULTADO
    public String getURLRESULTADOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Resultado/";
    }

    public String getURLRESULTADOLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Mi_Equipo/Resultado/";
    }

    public String getURLRESULTADOADEFULAll() {
        return getURL() + getURLRESULTADOADEFUL();
    }

    public String getURLRESULTADOLIFUBAAll() {
        return getURL() + getURLRESULTADOLIFUBA();
    }

    //JUGADOR
    public String getURLJUGADORADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Jugador/";
    }

    public String getURLJUGADORLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Mi_Equipo/Jugador/";
    }

    public String getURLJUGADORADEFULAll() {
        return getURL() + getURLJUGADORADEFUL();
    }

    public String getURLFOTOJUGADORADEFUL() {
        return getURL() + "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Jugador/foto_jugador/";
    }

    //POSICION
    public String getURLPOSICION() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Posicion/";
    }

    public String getURLPOSICIONALL() {
        return getURL() + getURLPOSICION();
    }

    //ENTRENAMIENTO
    public String getURLENTRENAMIENTOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Entrenamiento/";
    }

    public String getURLENTRENAMIENTOADEFULALL() {
        return getURL() + getURLENTRENAMIENTOADEFUL();
    }

    public String getURLENTRENAMIENTOASISTENCIAADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/EntrenamientoAsistencia/";
    }

    public String getURLENTRENAMIENTOASISTENCIAADEFULALL() {
        return getURL() + getURLENTRENAMIENTOASISTENCIAADEFUL();
    }

    //SANCION
    public String getURLSANCIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Sancion/";
    }

    public String getURLSANCIONLIFUBA() {
        return "estrella_del_sur/testing/LIFUBA/Mi_Equipo/Sancion/";
    }

    public String getURLSANCIONADEFULALL() {
        return getURL() + getURLSANCIONADEFUL();
    }

    public String getURLSANCIONLIFUBAALL() {
        return getURL() + getURLSANCIONLIFUBA();
    }

    ///////////SOCIAL/////////////
    //NOTIFICACION
    public String getURLNOTIFICACION() {
        return "estrella_del_sur/testing/GENERAL/Notificacion/";
    }

    public String getURLNOTIFICACIONALL() {
        return getURL() + getURLNOTIFICACION();
    }

    //NOTICIA
    public String getURLNOTICIA() {
        return "estrella_del_sur/testing/GENERAL/Noticia/";
    }

    public String getURLNOTICIAALL() {
        return getURL() + getURLNOTICIA();
    }

    //FOTO
    public String getURLFOTO() {
        return "estrella_del_sur/testing/GENERAL/Foto/";
    }

    public String getURLFOTOFOLDER() {
        return getURL() + "estrella_del_sur/testing/GENERAL/Foto/foto/";
    }

    public String getURLFOTOALL() {
        return getURL() + getURLFOTO();
    }

    //PUBLICIDAD
    public String getURLPUBLICIDAD() {
        return "estrella_del_sur/testing/GENERAL/Publicidad/";
    }

    public String getURLPUBLICIDADFOLDER() {
        return getURL() + "estrella_del_sur/testing/GENERAL/Publicidad/publicidad/";
    }

    public String getURLPUBLICIDADALL() {
        return getURL() + getURLPUBLICIDAD();
    }

    //USUARIO
    public String getURLUSUARIO() {
        return "estrella_del_sur/testing/GENERAL/Usuario/";
    }

    public String getURLUSUARIOALL() {
        return getURL() + getURLUSUARIO();
    }

    //PERMISO
    public String getURLPERMISO() {
        return "estrella_del_sur/testing/GENERAL/Permiso/";
    }

    public String getURLPERMISOALL() {
        return getURL() + getURLPERMISO();
    }

    /////////////////////////////GCM//////////////////
    public String getURLGCM() {
        return "estrella_del_sur/testing/GCM/insertPhoneID.php";
    }

    public String getURLGCMALL() {
        return getURL() + getURLGCM();
    }


    //INSERT - UPDATE - DELETE
    public String getInsertPHP(String Modulo) {
        return "insert" + Modulo + ".php";
    }

    public String getUpdatePHP(String Modulo) {
        return "update" + Modulo + ".php";
    }

    public String getDeletePHP(String Modulo) {
        return "delete" + Modulo + ".php";
    }

    private static Map<Character, Character> MAP_NORM;

    public String removeAccents(String value) {
        if (MAP_NORM == null || MAP_NORM.size() == 0) {
            MAP_NORM = new HashMap<Character, Character>();
            MAP_NORM.put('À', 'A');
            MAP_NORM.put('Á', 'A');
            MAP_NORM.put('Â', 'A');
            MAP_NORM.put('Ã', 'A');
            MAP_NORM.put('Ä', 'A');
            MAP_NORM.put('È', 'E');
            MAP_NORM.put('É', 'E');
            MAP_NORM.put('Ê', 'E');
            MAP_NORM.put('Ë', 'E');
            MAP_NORM.put('Í', 'I');
            MAP_NORM.put('Ì', 'I');
            MAP_NORM.put('Î', 'I');
            MAP_NORM.put('Ï', 'I');
            MAP_NORM.put('Ù', 'U');
            MAP_NORM.put('Ú', 'U');
            MAP_NORM.put('Û', 'U');
            MAP_NORM.put('Ü', 'U');
            MAP_NORM.put('Ò', 'O');
            MAP_NORM.put('Ó', 'O');
            MAP_NORM.put('Ô', 'O');
            MAP_NORM.put('Õ', 'O');
            MAP_NORM.put('Ö', 'O');
            MAP_NORM.put('Ñ', 'N');
            MAP_NORM.put('Ç', 'C');
            MAP_NORM.put('ª', 'A');
            MAP_NORM.put('º', 'O');
            MAP_NORM.put('§', 'S');
            MAP_NORM.put('³', '3');
            MAP_NORM.put('²', '2');
            MAP_NORM.put('¹', '1');
            MAP_NORM.put('à', 'a');
            MAP_NORM.put('á', 'a');
            MAP_NORM.put('â', 'a');
            MAP_NORM.put('ã', 'a');
            MAP_NORM.put('ä', 'a');
            MAP_NORM.put('è', 'e');
            MAP_NORM.put('é', 'e');
            MAP_NORM.put('ê', 'e');
            MAP_NORM.put('ë', 'e');
            MAP_NORM.put('í', 'i');
            MAP_NORM.put('ì', 'i');
            MAP_NORM.put('î', 'i');
            MAP_NORM.put('ï', 'i');
            MAP_NORM.put('ù', 'u');
            MAP_NORM.put('ú', 'u');
            MAP_NORM.put('û', 'u');
            MAP_NORM.put('ü', 'u');
            MAP_NORM.put('ò', 'o');
            MAP_NORM.put('ó', 'o');
            MAP_NORM.put('ô', 'o');
            MAP_NORM.put('õ', 'o');
            MAP_NORM.put('ö', 'o');
            MAP_NORM.put('ñ', 'n');
            MAP_NORM.put('ç', 'c');
        }
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(value);
        for (int i = 0; i < value.length(); i++) {
            Character c = MAP_NORM.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, c.charValue());
            }
        }
        return sb.toString();
    }

    public String getMounthYearCurrent() {

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String monthStg = String.valueOf(month);
        if (monthStg.length() == 1)
            monthStg = "0" + monthStg;

        return String.valueOf(monthStg + "-" + year);
    }

    public int getMounthCurrent() {

        Calendar c = Calendar.getInstance();

        // int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String monthStg = String.valueOf(month);
        if (monthStg.length() == 1)
            monthStg = "0" + monthStg;

        return Integer.valueOf(monthStg);
    }

    public void goToUser(Context context) {
        Intent usuario = new Intent(context, SplashUsuario.class);
        context.startActivity(usuario);
    }

    public void goToAdm(Context context) {
        Intent login = new Intent(context, Login.class);
        context.startActivity(login);
    }

    public static void close(Context context) {
        Intent intent = new Intent(context, NavigationUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("close", true);

        context.startActivity(intent);
//        context.finish();


//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }

    public String dateFormate(String date) {
        String dateFormat = "";
        if (date != null && !date.isEmpty()) {
            date = date.substring(0, 8);
            for (int i = 0; i < date.length(); i++) {
                dateFormat += date.charAt(i);
                if (i == 3) {
                    dateFormat += "/";
                } else if (i == 5) {
                    dateFormat += "/";
                }
            }
        }
        return dateFormat;
    }

    public String getYearSpinner() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        return String.valueOf(year);
    }

    public static Bitmap RotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static boolean isNetworkAvailable(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void showDialogPermission(final Context context, final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Es necesario activar el almacenamiento externo.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission(activity);
            }
        });
        builder.show();
    }

    public boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    private void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    //RESIZE IMAGE WITHOUT QUALITY!!!!!!

    public String compressImage(Context context, String imageUri) {

        String filePath = getRealPathFromURI(context, imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(Context context, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public Bitmap asignateImage(String imageUri) {
        Bitmap bitmapImage = BitmapFactory.decodeFile(imageUri);
        //     int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
        return Bitmap.createScaledBitmap(bitmapImage, 300, 300, true);
    }


}
