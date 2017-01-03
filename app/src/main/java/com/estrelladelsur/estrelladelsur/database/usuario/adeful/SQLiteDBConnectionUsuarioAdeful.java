package com.estrelladelsur.estrelladelsur.database.usuario.adeful;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBConnectionUsuarioAdeful extends SQLiteOpenHelper {

    ///////MODULO GENERAL ADEFUL/////////
    //TABLAS
    String TABLA_TABLA = "CREATE TABLE IF NOT EXISTS TABLA (ID_TABLA INTEGER PRIMARY KEY,"
            + " TABLA VARCHAR(100),"
            + " FECHA VARCHAR(100));";

        //////MODULO LIGA USUARIO/////////
    //EQUIPO ADEFUL
    String TABLA_EQUIPO_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS EQUIPO_USUARIO_ADEFUL (ID_EQUIPO INTEGER PRIMARY KEY,"
            + " NOMBRE VARCHAR(100),"
            + " ESCUDO BLOB);";
    //DIVISION
    String TABLA_DIVISION_USUARIO = "CREATE TABLE IF NOT EXISTS DIVISION_USUARIO_ADEFUL (ID_DIVISION INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100));";
    //TORNEO
    String TABLA_TORNEO_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS TORNEO_USUARIO_ADEFUL (ID_TORNEO INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100));";
    //CANCHA
    String TABLA_CANCHA_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS CANCHA_USUARIO_ADEFUL (ID_CANCHA INTEGER PRIMARY KEY,"
            + " NOMBRE VARCHAR(100),"
            + " LONGITUD VARCHAR(100),"
            + " LATITUD VARCHAR(100),"
            + " DIRECCION VARCHAR(100));";

    ////// MODULO MI EQUIPO USUARIO /////////
    //FIXTURE
    String TABLA_FIXTURE_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS FIXTURE_USUARIO_ADEFUL(ID_FIXTURE INTEGER PRIMARY KEY,"
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
            + " FOREIGN KEY(ID_EQUIPO_LOCAL) REFERENCES EQUIPO_ADEFUL(ID_EQUIPO),"
            + " FOREIGN KEY(ID_EQUIPO_VISITA) REFERENCES EQUIPO_ADEFUL(ID_EQUIPO),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_ADEFUL(ID_DIVISION),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_ADEFUL(ID_TORNEO),"
            + " FOREIGN KEY(ID_CANCHA) REFERENCES CANCHA_ADEFUL(ID_CANCHA));";
    //JUGADOR
    String TABLA_JUGADOR_USUARIO = "CREATE TABLE IF NOT EXISTS JUGADOR_USUARIO_ADEFUL(ID_JUGADOR INTEGER PRIMARY KEY,"
            + " NOMBRE_JUGADOR VARCHAR(100),"
            + " FOTO_JUGADOR BLOB,"
            + " ID_DIVISION INTEGER,"
            + " DIVISION VARCHAR(150),"
            + " POSICION VARCHAR(150));";
    //POSICION
    String TABLA_POSICION_USUARIO = "CREATE TABLE IF NOT EXISTS POSICION_USUARIO_ADEFUL(ID_POSICION INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100));";
    //ENTRENAMIENTO
    String TABLA_ENTRENAMIENTO_USUARIO = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_USUARIO_ADEFUL(ID_ENTRENAMIENTO INTEGER PRIMARY KEY,"
            + " DIA_ENTRENAMIENTO VARCHAR(100),"
            + " HORA_ENTRENAMIENTO VARCHAR(100),"
            + " CANCHA VARCHAR(100));";

    //ENTRENAMIENTO X DIVISION
    String TABLA_ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL(ID_ENTRENAMIENTO_DIVISION INTEGER PRIMARY KEY,"
            + " ID_ENTRENAMIENTO INTEGER,"
            + " ID_DIVISION INTEGER,"
            + " FOREIGN KEY(ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTO_USUARIO_ADEFUL(ID_ENTRENAMIENTO),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_USUARIO_ADEFUL(ID_DIVISION));";

    //ENTRENAMIENTO X DIVISION
    String TABLA_ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL(ID_DIVISION INTEGER,"
            + " CANTIDAD INTEGER,"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_USUARIO_ADEFUL(ID_DIVISION));";

    //ENTRENAMIENTO ASISTENCIA X DIVISION
    String TABLA_ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL(ID_ENTRENAMIENTO_ASISTENCIA INTEGER PRIMARY KEY,"
            + " ID_ENTRENAMIENTO INTEGER,"
            + " ID_JUGADOR INTEGER,"
            + " FOREIGN KEY(ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTO_USUARIO_ADEFUL(ID_ENTRENAMIENTO),"
            + " FOREIGN KEY(ID_JUGADOR) REFERENCES JUGADOR_USUARIO_ADEFUL(ID_JUGADOR));";
    //SANCIONES
    String TABLA_SANCION_USUARIO_ADEFUL= "CREATE TABLE IF NOT EXISTS SANCION_USUARIO_ADEFUL(ID_SANCION INTEGER PRIMARY KEY,"
            + " ID_JUGADOR INTEGER,"
            + " ID_TORNEO INTEGER,"
            + " AMARILLA INTEGER,"
            + " ROJA INTEGER,"
            + " FECHA_SUSPENSION INTEGER,"
            + " OBSERVACIONES VARCHAR(100),"
            + " FOREIGN KEY(ID_JUGADOR) REFERENCES JUGADOR_ADEFUL(ID_JUGADOR),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_ADEFUL(ID_TORNEO));";
    public SQLiteDBConnectionUsuarioAdeful(Context context, String name,
                                           CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


        //MODUDLO GENERAL
        db.execSQL(TABLA_TABLA);

        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_USUARIO_ADEFUL);
        db.execSQL(TABLA_DIVISION_USUARIO);
        db.execSQL(TABLA_TORNEO_USUARIO_ADEFUL);
        db.execSQL(TABLA_CANCHA_USUARIO_ADEFUL);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_USUARIO_ADEFUL);
        db.execSQL(TABLA_JUGADOR_USUARIO);
        db.execSQL(TABLA_POSICION_USUARIO);
        db.execSQL(TABLA_ENTRENAMIENTO_USUARIO);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL);
        db.execSQL(TABLA_SANCION_USUARIO_ADEFUL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //MODULO GENERAL
        db.execSQL("DROP TABLE IF EXISTS TABLA");

        //MODULO LIGA
        db.execSQL("DROP TABLE IF EXISTS EQUIPO_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS DIVISION_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS TORNEO_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS CANCHA_USUARIO_ADEFUL");
         //MODULO MI EQUIPO
        db.execSQL("DROP TABLE IF EXISTS FIXTURE_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS JUGADOR_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS POSICION_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL");
        db.execSQL("DROP TABLE IF EXISTS SANCION_USUARIO_ADEFUL");

        //MODUDLO GENERAL
        db.execSQL(TABLA_TABLA);

        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_USUARIO_ADEFUL);
        db.execSQL(TABLA_DIVISION_USUARIO);
        db.execSQL(TABLA_TORNEO_USUARIO_ADEFUL);
        db.execSQL(TABLA_CANCHA_USUARIO_ADEFUL);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_USUARIO_ADEFUL);
        db.execSQL(TABLA_JUGADOR_USUARIO);
        db.execSQL(TABLA_POSICION_USUARIO);
        db.execSQL(TABLA_ENTRENAMIENTO_USUARIO);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL);
        db.execSQL(TABLA_ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL);
        db.execSQL(TABLA_SANCION_USUARIO_ADEFUL);
    }
}