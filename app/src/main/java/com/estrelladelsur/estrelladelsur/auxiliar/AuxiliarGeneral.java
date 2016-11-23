package com.estrelladelsur.estrelladelsur.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap.CompressFormat;

import com.estrelladelsur.estrelladelsur.login.Login;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;
import com.estrelladelsur.estrelladelsur.navegador.usuario.SplashUsuario;

public class AuxiliarGeneral {
    private Context context;

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

    public void errorDataBase(Context context) {
        Toast.makeText(context, "Error en la base de datos interna, vuelva a intentar." +
                        "\n Si el error persiste comuniquese con soporte.",
                Toast.LENGTH_SHORT).show();
    }

    public void errorWebService(Context context, String mensaje) {
        Toast.makeText(context, mensaje,
                Toast.LENGTH_SHORT).show();
    }

    //SELECCIONAR UNA IMAGEN DE LA GALERIA
    public Bitmap SeleccionarImagen(Intent data, Context context, boolean circle) {
        Bitmap bRect = null;
        try {
            UtilityImage.uri = data.getData();
            if (UtilityImage.uri != null) {
                Cursor cursor = context.getContentResolver().query(
                        UtilityImage.uri, null, null, null, null);

                cursor.moveToFirst();
                String document_id = cursor.getString(0);
                document_id = document_id.substring(document_id
                        .lastIndexOf(":") + 1);

                cursor = context
                        .getContentResolver()
                        .query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null, MediaStore.Images.Media._ID + " = ? ",
                                new String[]{document_id}, null);
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();

                //asigamnos el string directorio
                UtilityImage.Default_DIR = new File(path);
                //creamos el nuevo directorio
                UtilityImage.Create_MY_IMAGES_DIR();
                // Copiamos la imagen
                UtilityImage.copyFile(UtilityImage.Default_DIR,
                        UtilityImage.MY_IMG_DIR);
                //tomamos la imagen y la codificamos
                bRect = UtilityImage
                        .decodeFile(UtilityImage.Paste_Target_Location);
                // use new copied path and use anywhere
                String valid_photo = UtilityImage.Paste_Target_Location
                        .toString();
                bRect = Bitmap.createScaledBitmap(bRect, 150, 150, true);
                cursor.close();
            } else {
                Toast toast = Toast.makeText(context,
                        "No se selecciono una foto.", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {

        }
        return bRect;
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

    public Bitmap getResizedBitmap(String path, int newWidth, int newHeight) {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / newWidth, photoH / newHeight);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        return bitmap;
    }

    public String selectImageForData(Intent data, Context context) {

        Cursor cursor = null;
        String photoPath = null;
        try {
            Uri uri = data.getData();
            if (uri != null) {
                cursor = context.getContentResolver().query(
                        uri, null, null, null, null);

                cursor.moveToFirst();
                String document_id = cursor.getString(0);
                document_id = document_id.substring(document_id
                        .lastIndexOf(":") + 1);

                cursor = context
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null, MediaStore.Images.Media._ID + " = ? ",
                                new String[]{document_id}, null);
                cursor.moveToFirst();
                photoPath = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
        } catch (Exception e) {
            photoPath = null;
        }
        return photoPath;
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

    //URLS
    //Http
    public String getURL() {
        return "http://valdroide.com/";
    }

    //SINCRONIZACION
    public String getURLSINCRONIZARUSUARIO() {
        return "estrella_del_sur/testing/SINCRONIZAR/Usuario/sincronizarUsuario.php";
    }

    //ARTICULO
    public String getURLARTICULOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Institucion/Articulo/";
    }

    public String getURLARTICULOADEFULALL() {
        return getURL() + getURLARTICULOADEFUL();
    }

    //COMISION
    public String getURLCOMISIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Institucion/Comision/";
    }

    public String getURLCOMISIONADEFULALL() {
        return getURL() + getURLCOMISIONADEFUL();
    }

    public String getURLFOTOCOMISIONADEFUL() {
        return getURL() + "estrella_del_sur/testing/ADEFUL/Institucion/Comision/foto_comision/";
    }

    //DIRECCION
    public String getURLDIRECCIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Institucion/Direccion/";
    }

    public String getURLDIRECCIONADEFULALL() {
        return getURL() + getURLDIRECCIONADEFUL();
    }

    public String getURLFOTODIRECCIONADEFUL() {
        return getURL() + "estrella_del_sur/testing/ADEFUL/Institucion/Direccion/foto_direccion/";
    }

    //CARGO
    public String getURLCARGOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Institucion/Cargo/";
    }

    public String getURLCARGOADEFULALL() {
        return getURL() + getURLCARGOADEFUL();
    }

    //EQUIPO
    public String getURLESCUDOEQUIPOADEFUL() {
        return "escudo_equipo/";
    }

    public String getURLEQUIPOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/equipo/";
    }

    //DIVISION
    public String getURLDIVISIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/division/";
    }

    //TORNEO
    public String getURLTORNEOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/torneo/";
    }

    public String getURLTORNEOADEFULALL() {
        return getURL() + getURLTORNEOADEFUL();
    }

    //MAPA
    public String getURLCANCHAADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Liga/cancha/";
    }

    public String getURLCANCHAADEFULALL() {
        return getURL() + getURLCANCHAADEFUL();
    }

    //MI EQUIPO
    //FIXTURE
    public String getURLFIXTUREADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Fixture/";
    }

    public String getURLFIXTUREADEFULAll() {
        return getURL() + getURLFIXTUREADEFUL();
    }

    //RESULTADO
    public String getURLRESULTADOADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Resultado/";
    }

    public String getURLRESULTADOADEFULAll() {
        return getURL() + getURLRESULTADOADEFUL();
    }

    //JUGADOR
    public String getURLJUGADORADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Jugador/";
    }

    public String getURLJUGADORADEFULAll() {
        return getURL() + getURLJUGADORADEFUL();
    }

    public String getURLFOTOJUGADORADEFUL() {
        return getURL() + "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Jugador/foto_jugador/";
    }

    //POSICION
    public String getURLPOSICIONADEFUL() {
        return "estrella_del_sur/testing/ADEFUL/Mi_Equipo/Posicion/";
    }

    public String getURLPOSICIONADEFULALL() {
        return getURL() + getURLPOSICIONADEFUL();
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

    public String getURLSANCIONADEFULALL() {
        return getURL() + getURLSANCIONADEFUL();
    }

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

    public String getURLFOTOFOLDER() {return getURL() + "estrella_del_sur/testing/GENERAL/Foto/foto/";
    }

    public String getURLFOTOALL() { return getURL() + getURLFOTO(); }

    //PUBLICIDAD
    public String getURLPUBLICIDAD() {
        return "estrella_del_sur/testing/GENERAL/Publicidad/";
    }
    public String getURLPUBLICIDADFOLDER() { return getURL() + "estrella_del_sur/testing/GENERAL/Publicidad/publicidad/";
    }
    public String getURLPUBLICIDADALL() { return getURL() + getURLPUBLICIDAD(); }
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

    public String getMounthCurrent() {

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String monthStg = String.valueOf(month);
        if (monthStg.length() == 1)
            monthStg = "0" + monthStg;

        return String.valueOf(monthStg + "-" + year);
    }

    public void goToUser(Context context){
        Intent usuario = new Intent(context, SplashUsuario.class);
        context.startActivity(usuario);
    }
    public void goToAdm(Context context){
        Intent login = new Intent(context, Login.class);
        context.startActivity(login);
    }


    public static void close(Context context) {
        Intent intent = new Intent(context, SplashUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("close", true);

        context.startActivity(intent);

//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }

    public String dateFormate(String date){
        String dateFormat = "";
        if(date != null && !date.isEmpty()){
            date = date.substring(0,8);
            for (int i = 0; i < date.length(); i++) {
               dateFormat +=date.charAt(i);
                if(i == 3){
                    dateFormat +="/";
                } else if(i == 5){
                    dateFormat +="/";
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
}
