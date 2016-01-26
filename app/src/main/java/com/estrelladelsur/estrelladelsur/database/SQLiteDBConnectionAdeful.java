package com.estrelladelsur.estrelladelsur.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBConnectionAdeful extends SQLiteOpenHelper {

    ///////MODULO GENERAL ADEFUL/////////
    //TABLAS
    String TABLA_TABLA_ADEFUL = "CREATE TABLE IF NOT EXISTS TABLA_ADEFUL (ID_TABLA INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE VARCHAR(200));";
    //MODULOS
    String TABLA_MODULO_ADEFUL = "CREATE TABLE IF NOT EXISTS MODULO_ADEFUL (ID_MODULO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE VARCHAR(200),"
            + " ID_TABLA INTEGER,"
            + " FECHA_ACTUALIZACION VARCHAR(100));";

    ///////MODULO INSTITUCION ADEFUL/////////
    //ARTICULO
    String TABLA_ARTICULO_ADEFUL = "CREATE TABLE IF NOT EXISTS ARTICULO_ADEFUL (ID_ARTICULO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " TITULO VARCHAR(200),"
            + " ARTICULO VARCHAR(700),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";

    //CARGO COMISION
    String TABLA_CARGO_ADEFUL = "CREATE TABLE IF NOT EXISTS CARGO_ADEFUL (ID_CARGO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " CARGO VARCHAR(200),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //COMISION
    String TABLA_COMISION_ADEFUL = "CREATE TABLE IF NOT EXISTS COMISION_ADEFUL(ID_COMISION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE_COMISION VARCHAR(100),"
            + " FOTO_COMISION BLOB,"
            + " ID_CARGO INTEGER,"
            + " PERIODO_DESDE VARCHAR(100),"
            + " PERIODO_HASTA VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";

    //DIRECCION
    String TABLA_DIRECCION_ADEFUL = "CREATE TABLE IF NOT EXISTS DIRECCION_ADEFUL(ID_DIRECCION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE_DIRECCION VARCHAR(100),"
            + " FOTO_DIRECCION BLOB,"
            + " ID_CARGO INTEGER,"
            + " PERIODO_DESDE VARCHAR(100),"
            + " PERIODO_HASTA VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";


    //////MODULO LIGA ADEFUL/////////
    //EQUIPO
    String TABLA_EQUIPO_ADEFUL = "CREATE TABLE IF NOT EXISTS EQUIPO_ADEFUL (ID_EQUIPO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE VARCHAR(100),"
            + " ESCUDO BLOB,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //DIVISION
    String TABLA_DIVISION_ADEFUL = "CREATE TABLE IF NOT EXISTS DIVISION_ADEFUL (ID_DIVISION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " DESCRIPCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //TORNEO
    String TABLA_TORNEO_ADEFUL = "CREATE TABLE IF NOT EXISTS TORNEO_ADEFUL (ID_TORNEO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " DESCRIPCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //CANCHA
    String TABLA_CANCHA_ADEFUL = "CREATE TABLE IF NOT EXISTS CANCHA_ADEFUL (ID_CANCHA INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE VARCHAR(100),"
            + " LONGITUD VARCHAR(100),"
            + " LATITUD VARCHAR(100),"
            + " DIRECCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";


    ////// MODULO MI EQUIPO ADEDUL /////////
    //FIXTURE
    String TABLA_FIXTURE_ADEFUL = "CREATE TABLE IF NOT EXISTS FIXTURE_ADEFUL(ID_FIXTURE INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_EQUIPO_LOCAL INTEGER,"
            + " ID_EQUIPO_VISITA INTEGER,"
            + " ID_DIVISION INTEGER,"
            + " ID_TORNEO INTEGER,"
            + " ID_CANCHA INTEGER,"
            + " ID_FECHA INTEGER,"
            + " ID_ANIO INTEGER,"
            + " DIA VARCHAR(100),"
            + " HORA VARCHAR(100),"
            + " RESULTADO_LOCAL VARCHAR(50),"
            + " RESULTADO_VISITA VARCHAR(50),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //FECHA
    String TABLA_FECHA = "CREATE TABLE IF NOT EXISTS FECHA (ID_FECHA INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " FECHA VARCHAR(100));";
    //ANO
    String TABLA_ANIO = "CREATE TABLE IF NOT EXISTS ANIO (ID_ANIO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ANIO VARCHAR(100));";
    //MES
    String TABLA_MES = "CREATE TABLE IF NOT EXISTS MES (ID_MES INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " MES VARCHAR(100));";
    //no se usar actualmente 27/11
    String TABLA_RESULTADO_ADEFUL = "CREATE TABLE IF NOT EXISTS RESULTADO_ADEFUL(ID_RESULTADO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_FIXTURE INTEGER,"
            + " RESULTADO_LOCAL INTEGER,"
            + " RESULTADO_VISITA INTEGER,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //JUGADOR
    String TABLA_JUGADOR_ADEFUL = "CREATE TABLE IF NOT EXISTS JUGADOR_ADEFUL(ID_JUGADOR INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " NOMBRE_JUGADOR VARCHAR(100),"
            + " FOTO_JUGADOR BLOB,"
            + " ID_DIVISION INTEGER,"
            + " ID_POSICION INTEGER,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //POSICION
    String TABLA_POSICION_ADEFUL = "CREATE TABLE IF NOT EXISTS POSICION_ADEFUL(ID_POSICION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " DESCRIPCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //ENTRENAMIENTO
    String TABLA_ENTRENAMIENTO_ADEFUL = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_ADEFUL(ID_ENTRENAMIENTO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " DIA_ENTRENAMIENTO VARCHAR(100),"
            + " HORA_ENTRENAMIENTO VARCHAR(100),"
            + " ID_CANCHA INTEGER," + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //ENTRENAMIENTO X DIVISION
    String TABLA_ENTRENAMIENTO_DIVISION_ADEFUL = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_DIVISION_ADEFUL(ID_ENTRENAMIENTO_DIVISION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_ENTRENAMIENTO INTEGER,"
            + " ID_DIVISION INTEGER);";

    public SQLiteDBConnectionAdeful(Context context, String name,
                                    CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //MODUDLO GENERAL
        db.execSQL(TABLA_TABLA_ADEFUL);
        db.execSQL(TABLA_MODULO_ADEFUL);
        //MODULO INSTITUCION
        db.execSQL(TABLA_ARTICULO_ADEFUL);
        db.execSQL(TABLA_CARGO_ADEFUL);
        db.execSQL(TABLA_COMISION_ADEFUL);
        db.execSQL(TABLA_DIRECCION_ADEFUL);
        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_ADEFUL);
        db.execSQL(TABLA_DIVISION_ADEFUL);
        db.execSQL(TABLA_TORNEO_ADEFUL);
        db.execSQL(TABLA_CANCHA_ADEFUL);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_ADEFUL);
        db.execSQL(TABLA_FECHA);
        db.execSQL(TABLA_ANIO);
        db.execSQL(TABLA_MES);
        db.execSQL(TABLA_RESULTADO_ADEFUL);
        db.execSQL(TABLA_JUGADOR_ADEFUL);
        db.execSQL(TABLA_POSICION_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_ADEFUL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //MODULO GENERAL
        db.execSQL("DROP TABLE IF EXISTS TABLA_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS MODULO_ADEFUL");
        //MODULO INSTITUCION
        db.execSQL("DROP TABLE IF EXISTS ARTICULO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS CARGO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS COMISION_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS DIRECCION_ADEFUL");
        //MODULO LIGA
        db.execSQL("DROP TABLE IF EXISTS EQUIPO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS DIVISION_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS TORNEO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS CANCHA_ADEFUL");
        //MODULO MI EQUIPO
        db.execSQL("DROP TABLE IF EXISTS FECHA");
        db.execSQL("DROP TABLE IF EXISTS ANIO");
        db.execSQL("DROP TABLE IF EXISTS MES");
        db.execSQL("DROP TABLE IF EXISTS FIXTURE_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS RESULTADO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS JUGADOR_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS POSICION_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_DIVISION_ADEFUL");

        //MODULO GENERAL
        db.execSQL(TABLA_TABLA_ADEFUL);
        db.execSQL(TABLA_MODULO_ADEFUL);
        //MODULO INSTITUCION
        db.execSQL(TABLA_ARTICULO_ADEFUL);
        db.execSQL(TABLA_CARGO_ADEFUL);
        db.execSQL(TABLA_COMISION_ADEFUL);
        db.execSQL(TABLA_DIRECCION_ADEFUL);
        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_ADEFUL);
        db.execSQL(TABLA_DIVISION_ADEFUL);
        db.execSQL(TABLA_TORNEO_ADEFUL);
        db.execSQL(TABLA_CANCHA_ADEFUL);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_ADEFUL);
        db.execSQL(TABLA_FECHA);
        db.execSQL(TABLA_ANIO);
        db.execSQL(TABLA_MES);
        db.execSQL(TABLA_RESULTADO_ADEFUL);
        db.execSQL(TABLA_JUGADOR_ADEFUL);
        db.execSQL(TABLA_POSICION_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_ADEFUL);
    }
}
