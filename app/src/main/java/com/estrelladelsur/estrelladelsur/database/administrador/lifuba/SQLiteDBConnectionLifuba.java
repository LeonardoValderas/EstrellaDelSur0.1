package com.estrelladelsur.estrelladelsur.database.administrador.lifuba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteDBConnectionLifuba extends SQLiteOpenHelper {

    String TABLA_TABLA = "CREATE TABLE IF NOT EXISTS TABLA (ID_TABLA INTEGER PRIMARY KEY,"
            + " TABLA VARCHAR(100),"
            + " FECHA VARCHAR(100));";

   //////MODULO LIGA LIFUBA/////////
    //EQUIPO
    String TABLA_EQUIPO_LIFUBA = "CREATE TABLE IF NOT EXISTS EQUIPO_LIFUBA (ID_EQUIPO INTEGER PRIMARY KEY,"
            + " NOMBRE VARCHAR(100),"
            + " NOMBRE_ESCUDO VARCHAR(100),"
            + " ESCUDO BLOB,"
            + " URL_ESCUDO VARCHAR(150),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //DIVISION
    String TABLA_DIVISION_LIFUBA = "CREATE TABLE IF NOT EXISTS DIVISION_LIFUBA (ID_DIVISION INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //TORNEO
    String TABLA_TORNEO_LIFUBA = "CREATE TABLE IF NOT EXISTS TORNEO_LIFUBA (ID_TORNEO INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100),"
            + " ACTUAL BOOLEAN,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //TORNEO ACTUAL
    String TABLA_TORNEO_ACTUAL_LIFUBA = "CREATE TABLE IF NOT EXISTS TORNEO_ACTUAL_LIFUBA (ID_TORNEO_ACTUAL INTEGER DEFAULT 1,"
            + " ID_TORNEO INTEGER DEFAULT 0,"
            + " ID_ANIO INTEGER DEFAULT 0,"
            + " ISACTUAL BOOLEAN DEFAULT 0,"
            + " FOREIGN KEY(ID_ANIO) REFERENCES ANIO(ID_ANIO));";
    String INSERT_TORNEO_ACTUAL_LIFUBA = "INSERT INTO TORNEO_ACTUAL_LIFUBA (ID_TORNEO_ACTUAL,"
            + " ID_TORNEO,"
            + " ID_ANIO,"
            + " ISACTUAL) VALUES (1,0,0,0);";

    //CANCHA
    String TABLA_CANCHA_LIFUBA = "CREATE TABLE IF NOT EXISTS CANCHA_LIFUBA (ID_CANCHA INTEGER PRIMARY KEY,"
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
    String TABLA_FIXTURE_LIFUBA = "CREATE TABLE IF NOT EXISTS FIXTURE_LIFUBA(ID_FIXTURE INTEGER PRIMARY KEY,"
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
            + " FECHA_ACTUALIZACION VARCHAR(100),"
            + " FOREIGN KEY(ID_EQUIPO_LOCAL) REFERENCES EQUIPO_LIFUBA(ID_EQUIPO),"
            + " FOREIGN KEY(ID_EQUIPO_VISITA) REFERENCES EQUIPO_LIFUBA(ID_EQUIPO),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_LIFUBA(ID_DIVISION),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_LIFUBA(ID_TORNEO),"
            + " FOREIGN KEY(ID_CANCHA) REFERENCES CANCHA_LIFUBA(ID_CANCHA),"
            + " FOREIGN KEY(ID_FECHA) REFERENCES FECHA(ID_FECHA),"
            + " FOREIGN KEY(ID_ANIO) REFERENCES ANIO(ID_ANIO));";
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
    String TABLA_RESULTADO_LIFUBA = "CREATE TABLE IF NOT EXISTS RESULTADO_LIFUBA(ID_RESULTADO INTEGER PRIMARY KEY ,"
            + " ID_FIXTURE INTEGER,"
            + " RESULTADO_LOCAL INTEGER,"
            + " RESULTADO_VISITA INTEGER,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100),"
            + " FOREIGN KEY(ID_FIXTURE) REFERENCES FIXTURE_LIFUBA(ID_FIXTURE));";
    //JUGADOR
    String TABLA_JUGADOR_LIFUBA = "CREATE TABLE IF NOT EXISTS JUGADOR_LIFUBA(ID_JUGADOR INTEGER PRIMARY KEY ,"
            + " NOMBRE_JUGADOR VARCHAR(100),"
            + " FOTO_JUGADOR BLOB,"
            + " NOMBRE_FOTO VARCHAR(150),"
            + " URL_JUGADOR VARCHAR(150),"
            + " ID_DIVISION INTEGER,"
            + " ID_POSICION INTEGER,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_LIFUBA(ID_DIVISION),"
            + " FOREIGN KEY(ID_POSICION) REFERENCES POSICION_LIFUBA(ID_POSICION));";
    //POSICION
    String TABLA_POSICION_LIFUBA = "CREATE TABLE IF NOT EXISTS POSICION_LIFUBA(ID_POSICION INTEGER PRIMARY KEY,"
            + " DESCRIPCION VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100));";
    //ENTRENAMIENTO
    String TABLA_ENTRENAMIENTO_LIFUBA = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_LIFUBA(ID_ENTRENAMIENTO INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " DIA_ENTRENAMIENTO VARCHAR(100),"
            + " HORA_ENTRENAMIENTO VARCHAR(100),"
            + " ID_CANCHA INTEGER,"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100),"
            + " FOREIGN KEY(ID_CANCHA) REFERENCES CANCHA_LIFUBA(ID_CANCHA));";
    //ENTRENAMIENTO X DIVISION
    String TABLA_ENTRENAMIENTO_DIVISION_LIFUBA = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_DIVISION_LIFUBA(ID_ENTRENAMIENTO_DIVISION INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_ENTRENAMIENTO INTEGER,"
            + " ID_DIVISION INTEGER,"
            + " FOREIGN KEY(ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTO_LIFUBA(ID_ENTRENAMIENTO),"
            + " FOREIGN KEY(ID_DIVISION) REFERENCES DIVISION_LIFUBA(ID_DIVISION));";

    //ENTRENAMIENTO ASISTENCIA X DIVISION
    String TABLA_ENTRENAMIENTO_ASISTENCIA_LIFUBA = "CREATE TABLE IF NOT EXISTS ENTRENAMIENTO_ASISTENCIA_LIFUBA(ID_ENTRENAMIENTO_ASISTENCIA INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " ID_ENTRENAMIENTO INTEGER,"
            + " ID_JUGADOR INTEGER,"
            + " FOREIGN KEY(ID_ENTRENAMIENTO) REFERENCES ENTRENAMIENTO_LIFUBA(ID_ENTRENAMIENTO),"
            + " FOREIGN KEY(ID_JUGADOR) REFERENCES JUGADOR_LIFUBA(ID_JUGADOR));";
    //SANCIONES
    String TABLA_SANCION_LIFUBA = "CREATE TABLE IF NOT EXISTS SANCION_LIFUBA(ID_SANCION INTEGER PRIMARY KEY,"
            + " ID_JUGADOR INTEGER,"
            + " ID_TORNEO INTEGER,"
            + " AMARILLA INTEGER,"
            + " ROJA INTEGER,"
            + " FECHA_SUSPENSION INTEGER,"
            + " OBSERVACIONES VARCHAR(100),"
            + " USUARIO_CREADOR VARCHAR(100),"
            + " FECHA_CREACION VARCHAR(100),"
            + " USUARIO_ACTUALIZACION VARCHAR(100),"
            + " FECHA_ACTUALIZACION VARCHAR(100),"
            + " FOREIGN KEY(ID_JUGADOR) REFERENCES JUGADOR_LIFUBA(ID_JUGADOR),"
            + " FOREIGN KEY(ID_TORNEO) REFERENCES TORNEO_LIFUBA(ID_TORNEO));";

    public SQLiteDBConnectionLifuba(Context context, String name,
                                    CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_TABLA);
        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_LIFUBA);
        db.execSQL(TABLA_DIVISION_LIFUBA);
        db.execSQL(TABLA_TORNEO_LIFUBA);
        db.execSQL(TABLA_TORNEO_ACTUAL_LIFUBA);
        db.execSQL(INSERT_TORNEO_ACTUAL_LIFUBA);
        db.execSQL(TABLA_CANCHA_LIFUBA);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_LIFUBA);
        db.execSQL(TABLA_FECHA);
        db.execSQL(TABLA_ANIO);
        db.execSQL(TABLA_MES);
        db.execSQL(TABLA_RESULTADO_LIFUBA);
        db.execSQL(TABLA_JUGADOR_LIFUBA);
        db.execSQL(TABLA_POSICION_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_ASISTENCIA_LIFUBA);
        db.execSQL(TABLA_SANCION_LIFUBA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TABLA");
        //MODULO INSTITUCION
        db.execSQL("DROP TABLE IF EXISTS ARTICULO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS CARGO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS COMISION_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS DIRECCION_LIFUBA");
        //MODULO LIGA
        db.execSQL("DROP TABLE IF EXISTS EQUIPO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS DIVISION_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS TORNEO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS TORNEO_ACTUAL_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS CANCHA_LIFUBA");
        //MODULO MI EQUIPO
        db.execSQL("DROP TABLE IF EXISTS FECHA");
        db.execSQL("DROP TABLE IF EXISTS ANIO");
        db.execSQL("DROP TABLE IF EXISTS MES");
        db.execSQL("DROP TABLE IF EXISTS FIXTURE_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS RESULTADO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS JUGADOR_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS POSICION_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_DIVISION_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS ENTRENAMIENTO_ASISTENCIA_LIFUBA");
        db.execSQL("DROP TABLE IF EXISTS SANCION_LIFUBA");

        db.execSQL(TABLA_TABLA);
        //MODULO LIGA
        db.execSQL(TABLA_EQUIPO_LIFUBA);
        db.execSQL(TABLA_DIVISION_LIFUBA);
        db.execSQL(TABLA_TORNEO_LIFUBA);
        db.execSQL(TABLA_TORNEO_ACTUAL_LIFUBA);

        db.execSQL(TABLA_CANCHA_LIFUBA);
        //MODULO MI EQUIPO
        db.execSQL(TABLA_FIXTURE_LIFUBA);
        db.execSQL(TABLA_FECHA);
        db.execSQL(TABLA_ANIO);
        db.execSQL(TABLA_MES);
        db.execSQL(TABLA_RESULTADO_LIFUBA);
        db.execSQL(TABLA_JUGADOR_LIFUBA);
        db.execSQL(TABLA_POSICION_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_DIVISION_LIFUBA);
        db.execSQL(TABLA_ENTRENAMIENTO_ASISTENCIA_LIFUBA);
        db.execSQL(TABLA_SANCION_LIFUBA);
    }
}