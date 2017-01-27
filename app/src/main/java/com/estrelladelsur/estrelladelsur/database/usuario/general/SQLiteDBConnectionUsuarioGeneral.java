package com.estrelladelsur.estrelladelsur.database.usuario.general;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBConnectionUsuarioGeneral extends SQLiteOpenHelper {

    //TABLAS
    String TABLA_TABLA = "CREATE TABLE IF NOT EXISTS TABLA (ID_TABLA INTEGER PRIMARY KEY,"
            + " TABLA VARCHAR(100),"
            + " FECHA VARCHAR(100));";
    //MODULOS
    String TABLA_MODULO = "CREATE TABLE IF NOT EXISTS MODULO_USUARIO (ID_MODULO INTEGER PRIMARY KEY,"
            + " NOMBRE VARCHAR(200));";
    //SUBMODULOS
    String TABLA_SUBMODULO = "CREATE TABLE IF NOT EXISTS SUBMODULO_USUARIO (ID_SUBMODULO INTEGER PRIMARY KEY,"
            + " NOMBRE VARCHAR(200), "
            + " ID_MODULO INTEGER,"
            + " FOREIGN KEY(ID_MODULO) REFERENCES MODULO_USUARIO(ID_MODULO));";
//    //USUARIOS
    String TABLA_USUARIO = "CREATE TABLE IF NOT EXISTS USUARIO_USUARIO (ID_USUARIO INTEGER PRIMARY KEY,"
            + " USUARIO VARCHAR(200),"
            + " PASSWORD VARCHAR(200));";
    ///////MODULO INSTITUCION ADEFUL/////////
    //ARTICULO
    String TABLA_ARTICULO_USUARIO = "CREATE TABLE IF NOT EXISTS ARTICULO_USUARIO (ID_ARTICULO INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " ARTICULO VARCHAR(700),"
            + " FECHA_ARTICULO VARCHAR(100));";
    //COMISION
//    String TABLA_COMISION_USUARIO = "CREATE TABLE IF NOT EXISTS COMISION_USUARIO (ID_COMISION INTEGER PRIMARY KEY,"
//            + " NOMBRE_COMISION VARCHAR(100),"
//            + " FOTO_COMISION BLOB,"
//            + " CARGO VARCHAR(100),"
//            + " PERIODO_DESDE VARCHAR(100),"
//            + " PERIODO_HASTA VARCHAR(100));";
    String TABLA_COMISION_USUARIO = "CREATE TABLE IF NOT EXISTS COMISION_USUARIO (ID_COMISION INTEGER PRIMARY KEY,"
            + " NOMBRE_COMISION VARCHAR(100),"
            + " FOTO_COMISION VARCHAR(400),"
            + " CARGO VARCHAR(100),"
            + " PERIODO_DESDE VARCHAR(100),"
            + " PERIODO_HASTA VARCHAR(100));";
    //DIRECCION
//    String TABLA_DIRECCION_USUARIO = "CREATE TABLE IF NOT EXISTS DIRECCION_USUARIO(ID_DIRECCION INTEGER PRIMARY KEY,"
//            + " NOMBRE_DIRECCION VARCHAR(100),"
//            + " FOTO_DIRECCION BLOB,"
//            + " CARGO VARCHAR(100),"
//            + " PERIODO_DESDE VARCHAR(100),"
//            + " PERIODO_HASTA VARCHAR(100));";
    String TABLA_DIRECCION_USUARIO = "CREATE TABLE IF NOT EXISTS DIRECCION_USUARIO(ID_DIRECCION INTEGER PRIMARY KEY,"
            + " NOMBRE_DIRECCION VARCHAR(100),"
            + " FOTO_DIRECCION VARCHAR(400),"
            + " CARGO VARCHAR(100),"
            + " PERIODO_DESDE VARCHAR(100),"
            + " PERIODO_HASTA VARCHAR(100));";

    //NOTIFICACION
    String TABLA_NOTIFICACION = "CREATE TABLE IF NOT EXISTS NOTIFICACION_USUARIO (ID_NOTIFICACION INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " NOTIFICACION VARCHAR(700));";
    //NOTICIA
    String TABLA_NOTICIA = "CREATE TABLE IF NOT EXISTS NOTICIA_USUARIO (ID_NOTICIA INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " DESCRIPCION VARCHAR(700),"
            + " LINK VARCHAR(200));";

    //FOTO
//    String TABLA_FOTO = "CREATE TABLE IF NOT EXISTS FOTO_USUARIO (ID_FOTO INTEGER PRIMARY KEY,"
//            + " TITULO VARCHAR(200),"
//            + " FOTO BLOB);";
    String TABLA_FOTO = "CREATE TABLE IF NOT EXISTS FOTO_USUARIO (ID_FOTO INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " FOTO VARCHAR(400));";

    //PUBLICIDAD
//    String TABLA_PUBLICIDAD = "CREATE TABLE IF NOT EXISTS PUBLICIDAD_USUARIO (ID_PUBLICIDAD INTEGER PRIMARY KEY,"
//            + " TITULO VARCHAR(200),"
//            + " LOGO BLOB,"
//            + " OTROS VARCHAR(200));";
    String TABLA_PUBLICIDAD = "CREATE TABLE IF NOT EXISTS PUBLICIDAD_USUARIO (ID_PUBLICIDAD INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " LOGO VARCHAR(400),"
            + " OTROS VARCHAR(200));";


    public SQLiteDBConnectionUsuarioGeneral(Context context, String name,
                                            CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        //MODULO INSTITUCION
        db.execSQL(TABLA_ARTICULO_USUARIO);
        db.execSQL(TABLA_COMISION_USUARIO);
        db.execSQL(TABLA_DIRECCION_USUARIO);

        //MODUDLO GENERAL
        db.execSQL(TABLA_MODULO);
        db.execSQL(TABLA_SUBMODULO);
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_TABLA);

         //MODULO SOCIAL
        db.execSQL(TABLA_NOTIFICACION);
        db.execSQL(TABLA_NOTICIA);
        db.execSQL(TABLA_FOTO);
        db.execSQL(TABLA_PUBLICIDAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //MODULO INSTITUCION
        db.execSQL("DROP TABLE IF EXISTS ARTICULO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS COMISION_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS DIRECCION_USUARIO");
        //MODULO GENERAL
        db.execSQL("DROP TABLE IF EXISTS MODULO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS SUBMODULO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS USUARIO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS TABLA");
        //MODULO SOCIAL
        db.execSQL("DROP TABLE IF EXISTS NOTIFICACION_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS NOTICIA_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS FOTO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS PUBLICIDAD_USUARIO");

        //MODULO INSTITUCION
        db.execSQL(TABLA_ARTICULO_USUARIO);
        db.execSQL(TABLA_COMISION_USUARIO);
        db.execSQL(TABLA_DIRECCION_USUARIO);
        //MODULO GENERAL
        db.execSQL(TABLA_MODULO);
        db.execSQL(TABLA_SUBMODULO);
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_TABLA);

        //MODULO SOCIAL
        db.execSQL(TABLA_NOTIFICACION);
        db.execSQL(TABLA_NOTICIA);
        db.execSQL(TABLA_FOTO);
        db.execSQL(TABLA_PUBLICIDAD);
    }
}
