package com.estrelladelsur.estrelladelsur.auxiliar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;


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

    //SELECCIONAR UNA IMAGEN DE LA GALERIA
    public Bitmap SeleccionarImagen(Intent data, Context context, boolean circle) {
        Bitmap b = null;
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
                b = UtilityImage
                        .decodeFile(UtilityImage.Paste_Target_Location);
                // use new copied path and use anywhere
                String valid_photo = UtilityImage.Paste_Target_Location
                        .toString();
                if(circle) {
                    b = Bitmap.createScaledBitmap(b, 100, 100, true);
                    //  pasamos la imagen a circulo
                    b = getRoundedBitmap(b);
                }else{
                    b = Bitmap.createScaledBitmap(b, 298, 298, true);
                    b = getRoundedBitmapRect(b);
                }
                cursor.close();

            } else {
                Toast toast = Toast.makeText(context,
                        "No se selecciono una foto.", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {

        }
        return b;
    }
    //PASA A CIRCULAR UNA IMAGEN
    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.BLACK;
        final Paint paint = new Paint();
        final Rect rect = new Rect(2, 2, bitmap.getWidth()-2, bitmap.getHeight()-1);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    //PASA A CIRCULAR UNA IMAGEN
    public static Bitmap getRoundedBitmapRect(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(299, 299, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.BLACK;
        final Paint paint = new Paint();
        final Rect rect = new Rect(2, 2, 299, 299);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRect(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    //PASA BITMAP A BYTE
    public byte[] pasarBitmapByte(Bitmap b){
        //Pasar bitmap a byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

    public String getFechaOficial() {

        Date dateOficial = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss");
        return sdf.format(dateOficial);
    }

    public Typeface tituloFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");
    }
    public Typeface textFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
    }
    public Typeface ligaFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "androidnation.ttf");
    }
}
