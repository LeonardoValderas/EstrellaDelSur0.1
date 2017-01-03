package com.estrelladelsur.estrelladelsur.database.usuario.adeful;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarioAdeful {

    private SQLiteDBConnectionUsuarioAdeful sqLiteDBConnectionUsuario;
    private Context ourcontext;
    private SQLiteDatabase database;

    public ControladorUsuarioAdeful(Context c) {
        ourcontext = c;

    }

    public ControladorUsuarioAdeful abrirBaseDeDatos() throws SQLException {
        sqLiteDBConnectionUsuario = new SQLiteDBConnectionUsuarioAdeful(ourcontext,
                "BaseDeDatosUsuarioAdeful.db", null, 1);
        database = sqLiteDBConnectionUsuario.getWritableDatabase();
        return this;
    }

    public void cerrarBaseDeDatos() {
        sqLiteDBConnectionUsuario.close();
    }


    // INSERTAR TABLA
    public boolean insertTabla(Tabla tabla)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_TABLA", tabla.getID_TABLA());
            cv.put("TABLA", tabla.getTABLA());
            cv.put("FECHA", tabla.getFECHA());

            long valor = database.insert("TABLA", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    //LISTA USUARIO
    public String selectTabla(String tabla) {

        String sql = "SELECT FECHA FROM TABLA WHERE TABLA ='"+tabla+"'";
        String fecha = "";
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        fecha = cursor.getString(cursor
                                .getColumnIndex("FECHA"));
                    }
                }
            } catch (Exception e) {
                fecha = null;
            }
        } else {
            fecha = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return fecha;
    }
    public List<Tabla> selectListaTablaAdeful() {

        String sql = "SELECT * FROM TABLA";
        List<Tabla> arrayArticuloAdeful = new ArrayList<>();
        String tablas = null, fecha = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Tabla tabla = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_TABLA"));
                        tablas = cursor.getString(cursor
                                .getColumnIndex("TABLA"));
                        fecha = cursor.getString(cursor
                                .getColumnIndex("FECHA"));

                        tabla = new Tabla(id, tablas, fecha);
                        //ARRAY TABLA
                        arrayArticuloAdeful.add(tabla);
                    }
                }
            } catch (Exception e) {
                arrayArticuloAdeful = null;
            }
        } else {
            arrayArticuloAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        tablas = null;
        fecha = null;
        return arrayArticuloAdeful;
    }

    // ELIMINAR TODOS ARTICULO
    public boolean eliminarTablaAdeful() {

        boolean res = false;
        String sql = "DELETE FROM TABLA";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                database.execSQL(sql);
                res = true;

            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    public boolean actualizarTablaXTablaIndiv(String tabla, String fecha)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("FECHA", fecha);

            long valor = database.update("TABLA", cv, "TABLA ='" + tabla + "'", null);
            cerrarBaseDeDatos();
            if (valor > 0)
                return true;
            else
                return false;

        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }


    ////////LIGA USUARIO/////////
    //EQUIPO INSERTAR
    public boolean insertEquipoUsuarioAdeful(Equipo equipoUsuario)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_EQUIPO", equipoUsuario.getID_EQUIPO());
            cv.put("NOMBRE", equipoUsuario.getNOMBRE_EQUIPO());
            cv.put("ESCUDO", equipoUsuario.getESCUDO());

            long valor = database.insert("EQUIPO_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    //EQUIPO lISTA
    public ArrayList<Equipo> selectListaEquipoUsuarioAdeful() {

        String sql = "SELECT * FROM EQUIPO_USUARIO_ADEFUL";
        ArrayList<Equipo> arrayEquipoAdeful = new ArrayList<>();
        String equipo = null;
        byte[] escudo = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Equipo equipoAdeful = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_EQUIPO"));
                        equipo = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        escudo = cursor
                                .getBlob(cursor.getColumnIndex("ESCUDO"));

                        equipoAdeful = new Equipo(id, equipo, escudo);

                        arrayEquipoAdeful.add(equipoAdeful);
                    }
                }
            } catch (Exception e) {
                arrayEquipoAdeful = null;
            }
        } else {
            arrayEquipoAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        equipo = null;
        escudo = null;

        return arrayEquipoAdeful;
    }

    //EQUIPO ELIMINAR
    public boolean eliminarEquipoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM EQUIPO_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // INSERTAR TORNEO
    public boolean insertTorneoUsuarioAdeful(Torneo torneo) throws SQLiteException {
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("ID_TORNEO", torneo.getID_TORNEO());
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            long valor = database.insert("TORNEO_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
    }

    //LISTA TORNEO
    public String selectTorneoUsuarioAdeful() {

        String sql = "SELECT * FROM TORNEO_USUARIO_ADEFUL";
        String descripcion = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        cerrarBaseDeDatos();
                    }
                }
            } catch (Exception e) {
                descripcion = null;
            }
        } else {
            descripcion = null;
        }

        sql = null;
        cursor = null;
        database = null;
        return descripcion;
    }

    //ELIMINAR TORNEO
    public boolean eliminarTorneoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM TORNEO_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // INSERTAR DIVISION
    public boolean insertDivisionUsuarioAdeful(Division division) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIVISION", division.getID_DIVISION());
            cv.put("DESCRIPCION", division.getDESCRIPCION());

            long valor = database.insert("DIVISION_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    //LISTA CANCHA
    public ArrayList<Division> selectListaDivisionUsuarioAdeful() {

        String sql = "SELECT * FROM DIVISION_USUARIO_ADEFUL";
        ArrayList<Division> arrayDivision = new ArrayList<>();
        String nombre = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Division division = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));

                        division = new Division(id, nombre);

                        arrayDivision.add(division);
                    }
                }
            } catch (Exception e) {
                arrayDivision = null;
            }
        } else {
            arrayDivision = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;

        return arrayDivision;
    }
    // ELIMINAR CANCHA
    public boolean eliminarDivisionUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM DIVISION_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // INSERTAR CANCHA
    public boolean insertCanchaUsuarioAdeful(Cancha cancha) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_CANCHA", cancha.getID_CANCHA());
            cv.put("NOMBRE", cancha.getNOMBRE());
            cv.put("LONGITUD", cancha.getLONGITUD());
            cv.put("LATITUD", cancha.getLATITUD());
            cv.put("DIRECCION", cancha.getDIRECCION());

            long valor = database.insert("CANCHA_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    //LISTA CANCHA
    public ArrayList<Cancha> selectListaCanchaUsuarioAdeful() {

        String sql = "SELECT * FROM CANCHA_USUARIO_ADEFUL";
        ArrayList<Cancha> arrayCancha = new ArrayList<>();
        String nombre = null, longitud = null, latitud = null, direccion = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Cancha cancha = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_CANCHA"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        longitud = cursor.getString(cursor
                                .getColumnIndex("LONGITUD"));
                        latitud = cursor.getString(cursor
                                .getColumnIndex("LATITUD"));
                        direccion = cursor.getString(cursor
                                .getColumnIndex("DIRECCION"));

                        cancha = new Cancha(id, nombre, longitud, latitud,
                                direccion);

                        arrayCancha.add(cancha);
                    }
                }
            } catch (Exception e) {
                arrayCancha = null;
            }
        } else {
            arrayCancha = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;

        return arrayCancha;
    }

    // ELIMINAR CANCHA
    public boolean eliminarCanchaUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM CANCHA_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

   // LISTA FECHA
    public ArrayList<Fecha> selectListaFecha() {

        String sql = "SELECT * FROM FECHA";
        ArrayList<Fecha> arrayFecha = new ArrayList<>();
        String fechaa = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        Fecha fecha = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_FECHA"));
                        fechaa = cursor.getString(cursor
                                .getColumnIndex("FECHA"));
                        fecha = new Fecha(id, fechaa);
                        arrayFecha.add(fecha);
                    }
                }
            } catch (Exception e) {
                arrayFecha = null;
            }
        } else {
            arrayFecha = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        fechaa = null;
        return arrayFecha;
    }

    //LISTA ANIO
    public ArrayList<Anio> selectListaAnio() {

        String sql = "SELECT * FROM ANIO";
        ArrayList<Anio> arrayAnio = new ArrayList<>();
        String anioo = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Anio anio = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_ANIO"));
                        anioo = cursor.getString(cursor.getColumnIndex("ANIO"));
                        anio = new Anio(id, anioo);

                        arrayAnio.add(anio);
                        cerrarBaseDeDatos();
                    }
                }
            } catch (Exception e) {
                arrayAnio = null;
            }
        } else {
            arrayAnio = null;
        }

        sql = null;
        cursor = null;
        database = null;
        anioo = null;
        return arrayAnio;
    }

    // INSERTAR FIXTURE
    public boolean insertFixtureUsuarioAdeful(Fixture fixture) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_FIXTURE", fixture.getID_FIXTURE());
            cv.put("ID_EQUIPO_LOCAL", fixture.getID_EQUIPO_LOCAL());
            cv.put("ID_EQUIPO_VISITA", fixture.getID_EQUIPO_VISITA());
            cv.put("ID_DIVISION", fixture.getID_DIVISION());
            cv.put("ID_TORNEO", fixture.getID_TORNEO());
            cv.put("ID_CANCHA", fixture.getID_CANCHA());
            cv.put("RESULTADO_LOCAL", fixture.getRESULTADO_LOCAL());
            cv.put("RESULTADO_VISITA", fixture.getRESULTADO_VISITA());
            cv.put("DIA", fixture.getDIA());
            cv.put("HORA", fixture.getHORA());
            cv.put("FECHA", fixture.getFECHA());
            cv.put("ANIO", fixture.getANIO());

            long valor = database.insert("FIXTURE_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    // ELIMINAR CANCHA
    public boolean eliminarFixtureUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM FIXTURE_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }


    //LISTA FIXTURE RECYCLER
    public ArrayList<Fixture> selectListaFixtureUsuarioAdeful() {

        String sql = "SELECT F.ID_FIXTURE AS ID, LOCALE.NOMBRE AS LOCAL, LOCALE.ESCUDO AS ESCUDOLOCAL, F.RESULTADO_LOCAL AS RESULTADOLOCAL, "
                + "VISITAE.NOMBRE AS VISITA, VISITAE.ESCUDO AS ESCUDOVISITA, F.RESULTADO_VISITA AS RESULTADOVISITA, "
                + "C.NOMBRE AS CANCHA, DIA, HORA, FECHA, ANIO, D.DESCRIPCION AS DESCRIPCION "
                + "FROM FIXTURE_USUARIO_ADEFUL F INNER JOIN EQUIPO_USUARIO_ADEFUL LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
                + "INNER JOIN EQUIPO_USUARIO_ADEFUL VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
                + "INNER JOIN CANCHA_USUARIO_ADEFUL C ON C.ID_CANCHA = F.ID_CANCHA "
                + "INNER JOIN DIVISION_USUARIO_ADEFUL D ON D.ID_DIVISION = F.ID_DIVISION ";


        ArrayList<Fixture> arrayFixture = new ArrayList<>();
        String dia = null, hora = null, e_local = null, e_visita = null, cancha = null, r_local = null,
                r_visita = null, fecha = null, anio = null, descripcion = null;
        int id_fixture;
        byte[] escudolocal, escudovisita;

        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Fixture fixtureRecycler = null;

                        id_fixture = cursor.getInt(cursor.getColumnIndex("ID"));
                        e_local = cursor.getString(cursor
                                .getColumnIndex("LOCAL"));
                        escudolocal = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOLOCAL"));
                        r_local = cursor.getString(cursor
                                .getColumnIndex("RESULTADOLOCAL"));
                        e_visita = cursor.getString(cursor
                                .getColumnIndex("VISITA"));
                        escudovisita = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOVISITA"));
                        r_visita = cursor.getString(cursor
                                .getColumnIndex("RESULTADOVISITA"));
                        cancha = cursor.getString(cursor
                                .getColumnIndex("CANCHA"));
                        dia = cursor.getString(cursor.getColumnIndex("DIA"));
                        hora = cursor.getString(cursor.getColumnIndex("HORA"));
                        fecha = cursor.getString(cursor.getColumnIndex("FECHA"));
                        anio = cursor.getString(cursor.getColumnIndex("ANIO"));
                        descripcion = cursor.getString(cursor.getColumnIndex("DESCRIPCION"));


                        fixtureRecycler = new Fixture(id_fixture,
                                e_local, escudolocal, r_local,
                                e_visita, escudovisita, r_visita,
                                cancha, dia, hora, fecha, anio, descripcion);

                        arrayFixture.add(fixtureRecycler);
                    }
                }
            } catch (Exception e) {
                arrayFixture = null;
            }
        } else {
            arrayFixture = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        dia = null;
        hora = null;
        e_local = null;
        e_visita = null;
        r_local = null;
        r_visita = null;

        return arrayFixture;
    }

//    // ///////////////////////////////////JUGADORES////////////////////////////////////////////
//    // INSERTAR JUGADOR
    public boolean insertJugadorUsuarioAdeful(Jugador jugador) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_JUGADOR", jugador.getID_JUGADOR());
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("DIVISION", jugador.getNOMBRE_DIVISION());
            cv.put("POSICION", jugador.getNOMBRE_POSICION());

            long valor = database.insert("JUGADOR_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    // ELIMINAR JUGADOR
    public boolean eliminarJugadorUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM JUGADOR_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // LISTA JUGADOR
    public ArrayList<Jugador> selectListaJugadorUsuarioAdeful(int division) {

        String sql = "SELECT ID_JUGADOR, NOMBRE_JUGADOR, FOTO_JUGADOR,"
                + " DIVISION, POSICION "
                + " FROM JUGADOR_USUARIO_ADEFUL WHERE ID_DIVISION=" + division;

        ArrayList<Jugador> arrayJugador = new ArrayList<Jugador>();
        String nombre = null, descripcion_division = null, descripcion_posicion = null;
        byte[] foto = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        Jugador jugador = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_JUGADOR"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_JUGADOR"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_JUGADOR"));
                        descripcion_division = cursor.getString(cursor
                                .getColumnIndex("DIVISION"));
                        descripcion_posicion = cursor.getString(cursor
                                .getColumnIndex("POSICION"));

                        jugador = new Jugador(id, nombre, foto,
                                0, descripcion_division,
                                descripcion_posicion);
                        arrayJugador.add(jugador);
                    }
                }
            } catch (Exception e) {
                arrayJugador = null;
            }
        } else {
            arrayJugador = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;

        return arrayJugador;
    }

    ////ENTRENAMIENTO//////
   //INSERTAR
    public boolean insertEntrenamientoUsuarioAdeful(Entrenamiento entrenamiento)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO", entrenamiento.getID_ENTRENAMIENTO());
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("CANCHA", entrenamiento.getCANCHA());

            long valor = database.insert("ENTRENAMIENTO_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
    }

    // INSERT TABLA INTERMEDIA
    public boolean insertEntrenamientoDivisionUsuarioAdeful(Entrenamiento entrenamiento_Division)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO_DIVISION",
                    entrenamiento_Division.getID_ENTRENAMIENTO_DIVISION());
            cv.put("ID_ENTRENAMIENTO",
                    entrenamiento_Division.getID_ENTRENAMIENTO());
            cv.put("ID_DIVISION", entrenamiento_Division.getID_DIVISION());

            long valor = database.insert("ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL", null,
                    cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }
    //SELECT ID TABLA INTERMEDIA
    public int selectCountEntrenamientoCantidad(int id_division) {

        String sql = "SELECT COUNT(*) AS CANTIDAD FROM ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL WHERE ID_DIVISION = "+id_division;
        int cant = 0;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        cant = cursor.getInt(cursor
                                .getColumnIndex("CANTIDAD"));
                    }
                }
            } catch (Exception e) {
                cant = 0;
            }
        } else {
            cant = 0;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;

        return cant;
    }
    //SELECT ID TABLA INTERMEDIA
    public int selectCountEntrenamientoJugador(int id_jugador) {

        String sql = "SELECT COUNT(*) AS CANTIDAD FROM ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL WHERE ID_JUGADOR = "+id_jugador;
        int cant = 0;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        cant = cursor.getInt(cursor
                                .getColumnIndex("CANTIDAD"));
                    }
                }
            } catch (Exception e) {
                cant = 0;
            }
        } else {
            cant = 0;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;

        return cant;
    }

    //LISTA ENTRENAMIENTO sin division
    public ArrayList<EntrenamientoRecycler> selectListaEntrenamientoUsuario(String fecha) {

        String sql = "SELECT ID_ENTRENAMIENTO, DIA_ENTRENAMIENTO, HORA_ENTRENAMIENTO, CANCHA"
                + " FROM ENTRENAMIENTO_USUARIO_ADEFUL"
                + " WHERE substr(DIA_ENTRENAMIENTO , 4, 7) >= '" + fecha + "' ORDER BY ID_ENTRENAMIENTO DESC";

        ArrayList<EntrenamientoRecycler> arrayEntrenamiento = new ArrayList<EntrenamientoRecycler>();
        int id_entrenamiento, id_cancha;
        String dia = null, hora = null, nombre = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        EntrenamientoRecycler entrenamientoRecycler = null;

                        id_entrenamiento = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO"));
                        dia = cursor.getString(cursor
                                .getColumnIndex("DIA_ENTRENAMIENTO"));
                        hora = cursor.getString(cursor
                                .getColumnIndex("HORA_ENTRENAMIENTO"));
                        nombre = cursor.getString(cursor.getColumnIndex("CANCHA"));

                        entrenamientoRecycler = new EntrenamientoRecycler(id_entrenamiento,
                                dia, hora, nombre);

                        arrayEntrenamiento.add(entrenamientoRecycler);
                    }
                }
            } catch (Exception e) {
                arrayEntrenamiento = null;
            }
        } else {
            arrayEntrenamiento = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        dia = null;
        hora = null;
        nombre = null;
        return arrayEntrenamiento;
    }

    //LISTA Division por Id
    public ArrayList<Entrenamiento> selectListaDivisionEntrenamientoAdefulId(int id_entrenamiento) {

        String sql = "SELECT ED.ID_ENTRENAMIENTO_DIVISION, ED.ID_DIVISION, D.DESCRIPCION"
                + " FROM ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL ED INNER JOIN DIVISION_USUARIO_ADEFUL D ON"
                + " ED.ID_DIVISION = D.ID_DIVISION"
                + " WHERE ID_ENTRENAMIENTO = " + id_entrenamiento + "";

        Cursor cursor = null;
        ArrayList<Entrenamiento> arrayDivision = new ArrayList<Entrenamiento>();
        int id, id_division;
        String descripcion = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Entrenamiento entrenamiento_division = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO_DIVISION"));
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));

                        entrenamiento_division = new Entrenamiento(id,
                                id_entrenamiento, id_division, descripcion, false);
                        arrayDivision.add(entrenamiento_division);
                    }
                }
            } catch (Exception e) {
                arrayDivision = null;
            }
        } else {
            arrayDivision = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return arrayDivision;
    }

    //ELIMINAR ENTRENAMIENTO
    public boolean eliminarEntrenamientoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

   //ELIMINAR DIVISION_ENTRENAMIENTO
    public boolean eliminarDivisionEntrenamientoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_DIVISION_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // INSERT TABLA INTERMEDIA
    public boolean insertEntrenamientoCantidadUsuarioAdeful(int id_division, int cantidad)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIVISION",
                    id_division);
            cv.put("CANTIDAD",
                    cantidad);

            long valor = database.insert("ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL", null,
                    cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    // INSERT ASISTENCIA
    public boolean insertAsistenciaEntrenamientoUsuarioAdeful(Entrenamiento entrenamientoAsistencia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        long valor = 0;
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO_ASISTENCIA", entrenamientoAsistencia.getID_ENTRENAMIENTO_ASISTENCIA());
            cv.put("ID_ENTRENAMIENTO", entrenamientoAsistencia.getID_ENTRENAMIENTO());
            cv.put("ID_JUGADOR", entrenamientoAsistencia.getID_JUGADOR());

            valor = database.insert("ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL", null,
                    cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;

        }

    }

    //ELIMINAR ASISTENCIA ENTRENAMIENTO
    public boolean eliminarCantidadEntrenamientoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_CANTIDAD_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }
//
    //ELIMINAR ASISTENCIA ENTRENAMIENTO
    public boolean eliminarAsistenciaEntrenamientoUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_ASISTENCIA_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    // INSERTAR JUGADOR
    public boolean insertSancionUsuarioAdeful(Sancion sancion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_SANCION", sancion.getID_SANCION());
            cv.put("ID_JUGADOR", sancion.getID_JUGADOR());
            cv.put("ID_TORNEO", sancion.getID_TORNEO());
            cv.put("AMARILLA", sancion.getAMARILLA());
            cv.put("ROJA", sancion.getROJA());
            cv.put("FECHA_SUSPENSION", sancion.getFECHA_SUSPENSION());
            cv.put("OBSERVACIONES", sancion.getOBSERVACIONES());

            long valor = database.insert("SANCION_USUARIO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return false;
        }
    }

    //ELIMINAR SANCION JUGADOR
    public boolean eliminarSancionUsuarioAdeful() {

        boolean res = false;
        String sql = "DELETE FROM SANCION_USUARIO_ADEFUL";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                res = true;
            } catch (Exception e) {
                res = false;
            }
        } else {
            res = false;
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    //LISTA FIXTURE RECYCLER
    public ArrayList<Sancion> selectListaSancionUsuarioAdeful(int division) {

        String sql = "SELECT S.ID_SANCION AS ID,S.ID_JUGADOR, J.NOMBRE_JUGADOR,J.FOTO_JUGADOR, J.ID_DIVISION, D.DESCRIPCION, "
                + "S.AMARILLA, S.ROJA, S.FECHA_SUSPENSION, S.OBSERVACIONES "
                + "FROM SANCION_USUARIO_ADEFUL S INNER JOIN JUGADOR_USUARIO_ADEFUL J ON J.ID_JUGADOR = S.ID_JUGADOR "
                + "INNER JOIN DIVISION_USUARIO_ADEFUL D ON D.ID_DIVISION = J.ID_DIVISION "
                + "WHERE D.ID_DIVISION="
                + division+"";

        ArrayList<Sancion> arraySancion = new ArrayList<>();
        String nombre_jugador = null, descripcion_division = null, obsevaciones = null;
        int id_sancion, id_jugador, id_division, amarilla, roja, fechas;
        byte[] foto = null;
        abrirBaseDeDatos();
        Cursor cursor = null;

        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Sancion sancion = null;

                        id_sancion = cursor.getInt(cursor.getColumnIndex("ID"));
                        id_jugador = cursor.getInt(cursor
                                .getColumnIndex("ID_JUGADOR"));
                        nombre_jugador = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_JUGADOR"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_JUGADOR"));
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion_division = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        amarilla = cursor.getInt(cursor
                                .getColumnIndex("AMARILLA"));
                        roja = cursor.getInt(cursor
                                .getColumnIndex("ROJA"));
                        fechas = cursor.getInt(cursor
                                .getColumnIndex("FECHA_SUSPENSION"));

                        obsevaciones = cursor.getString(cursor
                                .getColumnIndex("OBSERVACIONES"));


                        sancion = new Sancion(id_sancion, id_jugador, nombre_jugador, foto, id_division, descripcion_division,
                                amarilla, roja, fechas, obsevaciones);

                        arraySancion.add(sancion);
                    }
                }
            } catch (Exception e) {
                arraySancion = null;
            }
        } else {
            arraySancion = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre_jugador = null;
        descripcion_division = null;
        obsevaciones = null;

        return arraySancion;
    }
}