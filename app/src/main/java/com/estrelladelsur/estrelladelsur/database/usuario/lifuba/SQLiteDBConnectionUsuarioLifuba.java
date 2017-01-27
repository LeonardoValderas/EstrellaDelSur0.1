package com.estrelladelsur.estrelladelsur.database.usuario.lifuba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBConnectionUsuarioLifuba extends SQLiteOpenHelper {

    ///////MODULO GENERAL ADEFUL/////////
    //TABLAS
    String TABLA_TABLA = "CREATE TABLE IF NOT EXISTS TABLA (ID_TABLA INTEGER PRIMARY KEY,"
            + " TABLA VARCHAR(100),"
            + " FECHA VARCHAR(100));";

    //EQUIPO LIFUBA
//    String TABLA_EQUIPO_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS EQUIPO_USUARIO_LIFUBA (ID_EQUIPO INTEGER,"
//            + " NOMBRE VARCHAR(100),"
//            + " ESCUDO BLOB);";
    String TABLA_EQUIPO_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS EQUIPO_USUARIO_LIFUBA (ID_EQUIPO INTEGER,"
            + " NOMBRE VARCHAR(100),"
            + " ESCUDO VARCHAR(400));";
    //TORNEO
    String TABLA_TORNEO_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS TORNEO_USUARIO_LIFUBA (ID_TORNEO INTEGER,"
            + " DESCRIPCION VARCHAR(100));";
    //CANCHA
    String TABLA_CANCHA_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS CANCHA_USUARIO_LIFUBA (ID_CANCHA INTEGER,"
            + " NOMBRE VARCHAR(100),"
            + " LONGITUD VARCHAR(100),"
            + " LATITUD VARCHAR(100),"
            + " DIRECCION VARCHAR(100));";

    ////// MODULO MI EQUIPO USUARIO /////////
    //FIXTURE
    String TABLA_FIXTURE_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS FIXTURE_USUARIO_LIFUBA(ID_FIXTURE INTEGER,"
            + " ID_EQUIPO_LOCAL INTEGER,"
            + " ID_EQUIPO_VISITA INTEGER,"
            + " ID_DIVISION INTEGER,"
            + " ID_TORNEO VARCHAR(100),"
            + " ID_CANCHA INTEGER,"
            + " FECHA VARCHAR(100),"
            + " ANIO VARCHAR(100),"
            + " DIA VARCHAR(100),"
            + " HORA VARCHAR(100),"
            + " RESULTADO_LOCAL VARCHAR(50),"
            + " RESULTADO_VISITA VARCHAR(50),"
            + " FOREIGN KEY(ID_EQUIPO_LOCAL) REFERENCES EQUIPO_LIFUBA(ID_EQUIPO),"
            + " FOREIGN KEY(ID_EQUIPO_VISITA) REFERENCES EQUIPO_LIFUBA(ID_EQUIPO),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_ADEFUL(ID_DIVISION),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_LIFUBA(ID_TORNEO),"
            + " FOREIGN KEY(ID_CANCHA) REFERENCES CANCHA_LIFUBA(ID_CANCHA));";
    //DIVISION
    String TABLA_DIVISION_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS DIVISION_USUARIO_LIFUBA(ID_DIVISION INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100));";
    //JUGADOR
//    String TABLA_JUGADOR_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS JUGADOR_USUARIO_LIFUBA(ID_JUGADOR INTEGER PRIMARY KEY,"
//            + " NOMBRE_JUGADOR VARCHAR(100),"
//            + " FOTO_JUGADOR BLOB,"
//            + " ID_DIVISION INTEGER,"
//            + " DIVISION VARCHAR(150),"
//            + " POSICION VARCHAR(150));";

    String TABLA_JUGADOR_USUARIO_LIFUBA = "CREATE TABLE IF NOT EXISTS JUGADOR_USUARIO_LIFUBA(ID_JUGADOR INTEGER PRIMARY KEY,"
            + " NOMBRE_JUGADOR VARCHAR(100),"
            + " FOTO_JUGADOR VARCHAR(400),"
            + " ID_DIVISION INTEGER,"
            + " DIVISION VARCHAR(150),"
            + " POSICION VARCHAR(150));";
    //SANCIONES
    String TABLA_SANCION_USUARIO_LIFUBA= "CREATE TABLE IF NOT EXISTS SANCION_USUARIO_LIFUBA(ID_SANCION INTEGER PRIMARY KEY,"
            + " ID_JUGADOR INTEGER,"
            + " ID_TORNEO INTEGER,"
            + " AMARILLA INTEGER,"
            + " ROJA INTEGER,"
            + " FECHA_SUSPENSION INTEGER,"
            + " OBSERVACIONES VARCHAR(100),"
            + " FOREIGN KEY(ID_JUGADOR) REFERENCES JUGADOR_ADEFUL(ID_JUGADOR),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_LIFUBA(ID_TORNEO));";

    public SQLiteDBConnectionUsuarioLifuba(Context context, String name,
                                           CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


        //MODUDLO GENERAL
        db.execSQL(TABLA_TABLA);
        db.execSQL(TABLA_EQUIPO_USUARIO_LIFUBA);
        db.execSQL(TABLA_TORNEO_USUARIO_LIFUBA);
        db.execSQL(TABLA_CANCHA_USUARIO_LIFUBA);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_USUARIO_LIFUBA);
        db.execSQL(TABLA_SANCION_USUARIO_LIFUBA);
        db.execSQL(TABLA_DIVISION_USUARIO_LIFUBA);
        db.execSQL(TABLA_JUGADOR_USUARIO_LIFUBA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //MODULO GENERAL
          db.execSQL("DROP TABLE IF EXISTS TABLA");
        //MODULO LIGA
        db.execSQL("DROP TABLE IF EXISTS EQUIPO_USUARIO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS TORNEO_USUARIO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS CANCHA_USUARIO_LIFUBA");
        //MODULO MI EQUIPO
        db.execSQL("DROP TABLE IF EXISTS FIXTURE_USUARIO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS SANCION_USUARIO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS DIVISION_USUARIO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS JUGADOR_USUARIO_LIFUBA");

        //MODUDLO GENERAL
        db.execSQL(TABLA_TABLA);
        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_USUARIO_LIFUBA);
        db.execSQL(TABLA_TORNEO_USUARIO_LIFUBA);
        db.execSQL(TABLA_CANCHA_USUARIO_LIFUBA);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_USUARIO_LIFUBA);
        db.execSQL(TABLA_SANCION_USUARIO_LIFUBA);
        db.execSQL(TABLA_DIVISION_USUARIO_LIFUBA);
        db.execSQL(TABLA_JUGADOR_USUARIO_LIFUBA);
    }
}
