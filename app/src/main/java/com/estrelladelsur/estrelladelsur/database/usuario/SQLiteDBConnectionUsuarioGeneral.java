package com.estrelladelsur.estrelladelsur.database.usuario;

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
//    //PERMISO
//    String TABLA_PERMISO = "CREATE TABLE IF NOT EXISTS PERMISO (ID_PERMISO INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + " ID_USUARIO INTEGER, "
//            + " USUARIO_CREADOR VARCHAR(100),"
//            + " FECHA_CREACION VARCHAR(100),"
//            + " USUARIO_ACTUALIZACION VARCHAR(100),"
//            + " FECHA_ACTUALIZACION VARCHAR(100),"
//            + " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO_ADEFUL(ID_USUARIO));";
//
//    //PERMISO X MODULO
//    String TABLA_PERMISO_MODULO = "CREATE TABLE IF NOT EXISTS PERMISO_MODULO (ID_PERMISO_MODULO INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + " ID_PERMISO INTEGER,"
//            + " ID_MODULO INTEGER,"
//            + " ID_SUBMODULO INTEGER,"
//            + " FOREIGN KEY(ID_MODULO) REFERENCES MODULO(ID_MODULO)"
//            + " FOREIGN KEY(ID_SUBMODULO) REFERENCES SUBMODULO(ID_SUBMODULO));";
     ///////MODULO INSTITUCION ADEFUL/////////
    //ARTICULO
//    String TABLA_ARTICULO = "CREATE TABLE IF NOT EXISTS ARTICULO (ID_ARTICULO INTEGER,"
//            + " TITULO VARCHAR(40),"
//            + " ARTICULO VARCHAR(700),"
//            + " USUARIO_CREADOR VARCHAR(100),"
//            + " FECHA_CREACION VARCHAR(100),"
//            + " USUARIO_ACTUALIZACION VARCHAR(100),"
//            + " FECHA_ACTUALIZACION VARCHAR(100));";
//    //CARGO COMISION
//    String TABLA_CARGO = "CREATE TABLE IF NOT EXISTS CARGO (ID_CARGO INTEGER,"
//            + " CARGO VARCHAR(200),"
//            + " USUARIO_CREADOR VARCHAR(100),"
//            + " FECHA_CREACION VARCHAR(100),"
//            + " USUARIO_ACTUALIZACION VARCHAR(100),"
//            + " FECHA_ACTUALIZACION VARCHAR(100));";
//    //COMISION
//    String TABLA_COMISION = "CREATE TABLE IF NOT EXISTS COMISION(ID_COMISION INTEGER PRIMARY KEY,"
//            + " NOMBRE_COMISION VARCHAR(100),"
//            + " FOTO_COMISION BLOB,"
//            + " NOMBRE_FOTO VARCHAR(150),"
//            + " ID_CARGO INTEGER,"
//            + " PERIODO_DESDE VARCHAR(100),"
//            + " PERIODO_HASTA VARCHAR(100),"
//            + " URL_COMISION VARCHAR(150),"
//            + " USUARIO_CREADOR VARCHAR(100),"
//            + " FECHA_CREACION VARCHAR(100),"
//            + " USUARIO_ACTUALIZACION VARCHAR(100),"
//            + " FECHA_ACTUALIZACION VARCHAR(100),"
//            + " FOREIGN KEY(ID_CARGO) REFERENCES CARGO(ID_CARGO));";
//    //DIRECCION
//    String TABLA_DIRECCION = "CREATE TABLE IF NOT EXISTS DIRECCION (ID_DIRECCION INTEGER PRIMARY KEY,"
//            + " NOMBRE_DIRECCION VARCHAR(100),"
//            + " FOTO_DIRECCION BLOB,"
//            + " NOMBRE_FOTO VARCHAR(150),"
//            + " ID_CARGO INTEGER,"
//            + " PERIODO_DESDE VARCHAR(100),"
//            + " PERIODO_HASTA VARCHAR(100),"
//            + " URL_DIRECCION VARCHAR(150),"
//            + " USUARIO_CREADOR VARCHAR(100),"
//            + " FECHA_CREACION VARCHAR(100),"
//            + " USUARIO_ACTUALIZACION VARCHAR(100),"
//            + " FECHA_ACTUALIZACION VARCHAR(100),"
//            + " FOREIGN KEY(ID_CARGO) REFERENCES CARGO (ID_CARGO));";
    //////MODULO LIGA ADEFUL/////////
    //FECHA
//    String TABLA_FECHA = "CREATE TABLE IF NOT EXISTS FECHA (ID_FECHA INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + " FECHA VARCHAR(100));";
//    //ANO
//    String TABLA_ANIO = "CREATE TABLE IF NOT EXISTS ANIO (ID_ANIO INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + " ANIO VARCHAR(100));";
//    //MES
//    String TABLA_MES = "CREATE TABLE IF NOT EXISTS MES (ID_MES INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + " MES VARCHAR(100));";

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
    String TABLA_FOTO = "CREATE TABLE IF NOT EXISTS FOTO_USUARIO (ID_FOTO INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " FOTO BLOB);";

    //PUBLICIDAD
    String TABLA_PUBLICIDAD = "CREATE TABLE IF NOT EXISTS PUBLICIDAD_USUARIO (ID_PUBLICIDAD INTEGER PRIMARY KEY,"
            + " TITULO VARCHAR(200),"
            + " LOGO BLOB,"
            + " OTROS VARCHAR(200));";


    public SQLiteDBConnectionUsuarioGeneral(Context context, String name,
                                            CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


        //MODUDLO GENERAL
        db.execSQL(TABLA_MODULO);
        db.execSQL(TABLA_SUBMODULO);
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_TABLA);
//        db.execSQL(TABLA_PERMISO);
//        db.execSQL(TABLA_PERMISO_MODULO);


        //MODULO INSTITUCION
//        db.execSQL(TABLA_ARTICULO);
//        db.execSQL(TABLA_CARGO);
//        db.execSQL(TABLA_COMISION);
//        db.execSQL(TABLA_DIRECCION);
        //MODULO MI EQUIPO
     //   db.execSQL(TABLA_FECHA);
      //  db.execSQL(TABLA_ANIO);
      //  db.execSQL(TABLA_MES);
         //MODULO SOCIAL
        db.execSQL(TABLA_NOTIFICACION);
        db.execSQL(TABLA_NOTICIA);
        db.execSQL(TABLA_FOTO);
        db.execSQL(TABLA_PUBLICIDAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //MODULO GENERAL
        db.execSQL("DROP TABLE IF EXISTS MODULO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS SUBMODULO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS USUARIO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS TABLA");
//        db.execSQL("DROP TABLE IF EXISTS PERMISO_MODULO");
        //MODULO INSTITUCION
//        db.execSQL("DROP TABLE IF EXISTS ARTICULO");
//        db.execSQL("DROP TABLE IF EXISTS CARGO");
//        db.execSQL("DROP TABLE IF EXISTS COMISION");
//        db.execSQL("DROP TABLE IF EXISTS DIRECCION");
        //MODULO MI EQUIPO
//        db.execSQL("DROP TABLE IF EXISTS FECHA");
//        db.execSQL("DROP TABLE IF EXISTS ANIO");
//        db.execSQL("DROP TABLE IF EXISTS MES");
        //MODULO SOCIAL
        db.execSQL("DROP TABLE IF EXISTS NOTIFICACION_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS NOTICIA_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS FOTO_USUARIO");
        db.execSQL("DROP TABLE IF EXISTS PUBLICIDAD_USUARIO");

        //MODULO GENERAL
        db.execSQL(TABLA_MODULO);
        db.execSQL(TABLA_SUBMODULO);
        db.execSQL(TABLA_USUARIO);
        db.execSQL(TABLA_TABLA);
//        db.execSQL(TABLA_PERMISO);
//        db.execSQL(TABLA_PERMISO_MODULO);
        //MODULO INSTITUCION
//        db.execSQL(TABLA_ARTICULO);
//        db.execSQL(TABLA_CARGO);
//        db.execSQL(TABLA_COMISION);
//        db.execSQL(TABLA_DIRECCION);
          //MODULO MI EQUIPO
//        db.execSQL(TABLA_FECHA);
//        db.execSQL(TABLA_ANIO);
//        db.execSQL(TABLA_MES);
        //MODULO SOCIAL
        db.execSQL(TABLA_NOTIFICACION);
        db.execSQL(TABLA_NOTICIA);
        db.execSQL(TABLA_FOTO);
        db.execSQL(TABLA_PUBLICIDAD);
    }
}
