package com.estrelladelsur.estrelladelsur.database.administrador.lifuba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

import java.util.ArrayList;
import java.util.List;

public class ControladorLifuba {

    private SQLiteDBConnectionLifuba sqLiteDBConnectionLifuba;
    private Context ourcontext;
    private SQLiteDatabase database;

    public ControladorLifuba(Context c) {
        ourcontext = c;

    }

    public ControladorLifuba abrirBaseDeDatos() throws SQLException {
        sqLiteDBConnectionLifuba = new SQLiteDBConnectionLifuba(ourcontext,
                "BaseDeDatosLifuba.db", null, 1);
        database = sqLiteDBConnectionLifuba.getWritableDatabase();
        return this;
    }

    public void cerrarBaseDeDatos() {
        sqLiteDBConnectionLifuba.close();
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

    // ACTUALIZAR USUARIO
    public boolean actualizarTabla(Tabla tabla)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TABLA", tabla.getTABLA());
            cv.put("FECHA", tabla.getFECHA());

            long valor = database.update("TABLA", cv, "ID_TABLA=" + tabla.getID_TABLA(), null);

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

    // ACTUALIZAR
    public boolean actualizarTablaXTabla(String tabla, String fecha)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("FECHA", fecha);

            long valor = database.update("TABLA", cv, "TABLA = '"+tabla+"'", null);

            if (valor > 0) {
                valor = database.update("TABLA", cv, "TABLA = 'FECHA_TABLA'", null);
                cerrarBaseDeDatos();
                if (valor > 0)
                    return true;
                else
                    return false;
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

        String sql = "SELECT FECHA FROM TABLA WHERE TABLA ='" + tabla + "'";
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

    public List<Tabla> selectListaTablaLifuba() {

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

    // ELIMINAR
    public boolean eliminarTablaLifuba() {

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

    ////////LIGA LIFUBA/////////
    //EQUIPO INSERTAR
    public boolean insertEquipoLifuba(int id, Equipo equipoLifuba)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_EQUIPO", id);
            cv.put("NOMBRE", equipoLifuba.getNOMBRE_EQUIPO());
            cv.put("NOMBRE_ESCUDO", equipoLifuba.getNOMBRE_ESCUDO());
            cv.put("ESCUDO", equipoLifuba.getESCUDO());
            cv.put("URL_ESCUDO", equipoLifuba.getURL_ESCUDO());
            cv.put("USUARIO_CREADOR", equipoLifuba.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", equipoLifuba.getFECHA_CREACION());

            long valor = database.insert("EQUIPO_LIFUBA", null, cv);
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

    public boolean insertEquipoLifuba(Equipo equipoLifuba)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_EQUIPO", equipoLifuba.getID_EQUIPO());
            cv.put("NOMBRE", equipoLifuba.getNOMBRE_EQUIPO());
            cv.put("NOMBRE_ESCUDO", equipoLifuba.getNOMBRE_ESCUDO());
            cv.put("ESCUDO", equipoLifuba.getESCUDO());
            cv.put("URL_ESCUDO", equipoLifuba.getURL_ESCUDO());
            cv.put("USUARIO_CREADOR", equipoLifuba.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", equipoLifuba.getFECHA_CREACION());

            long valor = database.insert("EQUIPO_LIFUBA", null, cv);
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
    public ArrayList<Equipo> selectListaEquipoLifuba() {

        String sql = "SELECT * FROM EQUIPO_LIFUBA ORDER BY ID_EQUIPO DESC";
        ArrayList<Equipo> arrayEquipoLifuba = new ArrayList<>();
        String equipo = null, nombre_escudo = null, url_escudo = null, usuario = null, fechaCreacion = null,
                fechaActualizacion = null, usuario_act = null;
        byte[] escudo = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Equipo equipoLifuba = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_EQUIPO"));
                        equipo = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        escudo = cursor
                                .getBlob(cursor.getColumnIndex("ESCUDO"));
                        nombre_escudo = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_ESCUDO"));
                        url_escudo = cursor.getString(cursor
                                .getColumnIndex("URL_ESCUDO"));
                        usuario = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));

                        equipoLifuba = new Equipo(id, equipo, nombre_escudo, escudo, url_escudo, usuario,
                                fechaCreacion, usuario_act, fechaActualizacion);

                        arrayEquipoLifuba.add(equipoLifuba);

                    }
                }

            } catch (Exception e) {
                arrayEquipoLifuba = null;
            }
        } else {
            arrayEquipoLifuba = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        equipo = null;
        usuario = null;
        escudo = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;

        return arrayEquipoLifuba;
    }

    //EQUIPO ACTUALIZAR
    public boolean actualizarEquipoLifuba(Equipo equipo) throws SQLiteException {
        boolean ban = false;

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", equipo.getNOMBRE_EQUIPO());
            cv.put("NOMBRE_ESCUDO", equipo.getNOMBRE_ESCUDO());
            cv.put("URL_ESCUDO", equipo.getURL_ESCUDO());
            cv.put("ESCUDO", equipo.getESCUDO());
            cv.put("USUARIO_ACTUALIZACION", equipo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", equipo.getFECHA_ACTUALIZACION());

            long valor = database.update("EQUIPO_LIFUBA", cv, "ID_EQUIPO" + "="
                    + equipo.getID_EQUIPO(), null);
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

    //EQUIPO ELIMINAR
    public boolean eliminarEquipoLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM EQUIPO_LIFUBA WHERE ID_EQUIPO = " + id;
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

    public boolean eliminarEquipoLifuba() {

        boolean res = false;
        String sql = "DELETE FROM EQUIPO_LIFUBA";
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

    // DIVISION INSERTAR
    public boolean insertDivisionLifuba(int id, Division division) throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIVISION", id);
            cv.put("DESCRIPCION", division.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", division.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", division.getFECHA_CREACION());

            long valor = database.insert("DIVISION_LIFUBA", null, cv);
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

    public boolean insertDivisionLifuba(Division division) throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIVISION", division.getID_DIVISION());
            cv.put("DESCRIPCION", division.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", division.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", division.getFECHA_CREACION());

            long valor = database.insert("DIVISION_LIFUBA", null, cv);
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

    //DIVISION LISTA
    public ArrayList<Division> selectListaDivisionLifuba() {

        String sql = "SELECT * FROM DIVISION_LIFUBA";
        ArrayList<Division> arrayDivision = new ArrayList<>();
        String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Division division = null;
                        id = cursor
                                .getInt(cursor.getColumnIndex("ID_DIVISION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        usuario = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        division = new Division(id, descripcion, usuario,
                                fechaCreacion, usuario_act, fechaActualizacion);

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
        descripcion = null;
        usuario = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;

        return arrayDivision;
    }

    // ACTUALIZAR DIVISION
    public boolean actualizarDivisionLifuba(Division division) throws SQLiteException {

        boolean res = false;
        String sql = "UPDATE DIVISION_LIFUBA SET DESCRIPCION='"
                + division.getDESCRIPCION() + "', USUARIO_ACTUALIZACION='"
                + division.getUSUARIO_ACTUALIZACION() + "', FECHA_ACTUALIZACION='"
                + division.getFECHA_ACTUALIZACION() +
                "' WHERE ID_DIVISION ='" + division.getID_DIVISION() + "'";
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

    //ELIMINAR DIVISION
    public boolean eliminarDivisionLifuba(int id) throws SQLiteException {

        boolean res = false;

        String sql = "DELETE FROM DIVISION_LIFUBA WHERE ID_DIVISION = " + id;
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

    public boolean eliminarDivisionLifuba() throws SQLiteException {

        boolean res = false;

        String sql = "DELETE FROM DIVISION_LIFUBA";
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
    public boolean insertTorneoLifuba(int id, Torneo torneo) throws SQLiteException {
        long valorActual = 0;
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("ID_TORNEO", id);
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            cv.put("ACTUAL", torneo.getACTUAL());
            cv.put("USUARIO_CREADOR", torneo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", torneo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", torneo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", torneo.getFECHA_ACTUALIZACION());
            long valor = database.insert("TORNEO_LIFUBA", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {

                if (torneo.getACTUAL()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", id);
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    cvA.put("ISACTUAL", true);
                    valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                            + 1, null);
                    cerrarBaseDeDatos();
                    if (valorActual > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
    }

    public boolean insertTorneoLifuba(Torneo torneo) throws SQLiteException {
        long valorActual = 0;
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("ID_TORNEO", torneo.getID_TORNEO());
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            cv.put("ACTUAL", torneo.getACTUAL());
            cv.put("USUARIO_CREADOR", torneo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", torneo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", torneo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", torneo.getFECHA_ACTUALIZACION());
            long valor = database.insert("TORNEO_LIFUBA", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {

                if (torneo.getACTUAL()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", torneo.getID_TORNEO());
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    cvA.put("ISACTUAL", true);
                    valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                            + 1, null);
                    cerrarBaseDeDatos();
                    if (valorActual > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        }
    }

    //LISTA TORNEO
    public ArrayList<Torneo> selectListaTorneoLifuba() throws SQLiteException {

        String sql = "SELECT * FROM TORNEO_LIFUBA";
        ArrayList<Torneo> arrayTorneo = new ArrayList<>();
        String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null, tabla = null;
        int id;
        boolean isActual = false;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Torneo torneo = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_TORNEO"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        usuario = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        isActual = cursor.getInt(cursor.getColumnIndex("ACTUAL")) > 0;
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));

                        torneo = new Torneo(id, descripcion, isActual, usuario,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        arrayTorneo.add(torneo);
                        cerrarBaseDeDatos();
                    }
                }
            } catch (Exception e) {
                arrayTorneo = null;
            }
        } else {
            arrayTorneo = null;
        }

        sql = null;
        cursor = null;
        database = null;
        descripcion = null;
        usuario = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;

        return arrayTorneo;
    }

    //ACTUALIZAR TORNEO
    public boolean actualizarTorneoLifuba(Torneo torneo) throws SQLiteException {
        long valorActual = 0;
        boolean updateOK = true;
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            cv.put("ACTUAL", torneo.getACTUAL());
            cv.put("USUARIO_ACTUALIZACION", torneo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", torneo.getFECHA_ACTUALIZACION());

            long valor = database.update("TORNEO_LIFUBA", cv, "ID_TORNEO = "
                    + torneo.getID_TORNEO(), null);
            cerrarBaseDeDatos();
            if (valor > 0) {

                if (!torneo.getACTUAL() && torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", 0);
                    cvA.put("ID_ANIO", 0);
                    cvA.put("ISACTUAL", false);
                    valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                            + 1, null);
                    cerrarBaseDeDatos();

                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                } else if (torneo.getACTUAL() && torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                            + 1, null);
                    cerrarBaseDeDatos();


                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                } else if (torneo.getACTUAL() && !torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", torneo.getID_TORNEO());
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    cvA.put("ISACTUAL", true);
                    valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                            + 1, null);
                    cerrarBaseDeDatos();


                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                } else {
                    updateOK = true;
                }
            } else {
                updateOK = false;
            }
        } catch (SQLiteException e) {
            updateOK = false;
        }
        return updateOK;
    }

    //ELIMINAR TORNEO
    public boolean eliminarTorneoLifuba(int id, boolean isActual) throws SQLiteException {

        boolean res = false;
        String sql = "DELETE FROM TORNEO_LIFUBA WHERE ID_TORNEO = " + id;
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
        if (res) {

            if (isActual) {
                ContentValues cvA = new ContentValues();
                cvA.put("ID_TORNEO", 0);
                cvA.put("ID_ANIO", 0);
                cvA.put("ISACTUAL", false);
                long valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                        + 1, null);
                cerrarBaseDeDatos();

                if (valorActual > 0) {
                    res = true;
                } else {
                    res = false;
                }
            }
        }
        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    public boolean eliminarTorneoLifuba() throws SQLiteException {

        boolean res = false;
        String sql = "DELETE FROM TORNEO_LIFUBA";
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
        if (res) {
            ContentValues cvA = new ContentValues();
            cvA.put("ID_TORNEO", 0);
            cvA.put("ID_ANIO", 0);
            cvA.put("ISACTUAL", false);
            long valorActual = database.update("TORNEO_ACTUAL_LIFUBA", cvA, "ID_TORNEO_ACTUAL = "
                    + 1, null);
            cerrarBaseDeDatos();

            if (valorActual > 0) {
                res = true;
            } else {
                res = false;
            }
        }

        cerrarBaseDeDatos();
        database = null;
        sql = null;
        return res;
    }

    //ES TORNEO ACTUAL

    public Torneo selectActualTorneoLifuba() throws SQLiteException {

        String sql = "SELECT * FROM TORNEO_ACTUAL_LIFUBA";
        Torneo torneo = null;
        boolean isActual = false;
        int id, id_torneo, id_anio;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        id = cursor.getInt(cursor.getColumnIndex("ID_TORNEO_ACTUAL"));
                        id_torneo = cursor.getInt(cursor.getColumnIndex("ID_TORNEO"));
                        id_anio = cursor.getInt(cursor.getColumnIndex("ID_ANIO"));
                        isActual = cursor.getInt(cursor.getColumnIndex("ISACTUAL")) > 0;

                        torneo = new Torneo(id, id_torneo, id_anio, isActual);
                    }

                }
            } catch (Exception e) {
                torneo = null;
            }
        } else {
            torneo = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;

        return torneo;
    }

    // INSERTAR CANCHA
    public boolean insertCanchaLifuba(int id, Cancha cancha) {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_CANCHA", id);
            cv.put("NOMBRE", cancha.getNOMBRE());
            cv.put("LONGITUD", cancha.getLONGITUD());
            cv.put("LATITUD", cancha.getLATITUD());
            cv.put("DIRECCION", cancha.getDIRECCION());
            cv.put("USUARIO_CREADOR", cancha.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", cancha.getFECHA_CREACION());

            long valor = database.insert("CANCHA_LIFUBA", null, cv);
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

    public boolean insertCanchaLifuba(Cancha cancha) {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_CANCHA", cancha.getID_CANCHA());
            cv.put("NOMBRE", cancha.getNOMBRE());
            cv.put("LONGITUD", cancha.getLONGITUD());
            cv.put("LATITUD", cancha.getLATITUD());
            cv.put("DIRECCION", cancha.getDIRECCION());
            cv.put("USUARIO_CREADOR", cancha.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", cancha.getFECHA_CREACION());

            long valor = database.insert("CANCHA_LIFUBA", null, cv);
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
    public ArrayList<Cancha> selectListaCanchaLifuba() {

        String sql = "SELECT * FROM CANCHA_LIFUBA";
        ArrayList<Cancha> arrayCancha = new ArrayList<>();
        String nombre = null, longitud = null, latitud = null, direccion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Cancha cancha = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_CANCHA"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        longitud = cursor.getString(cursor
                                .getColumnIndex("LONGITUD"));
                        latitud = cursor.getString(cursor
                                .getColumnIndex("LATITUD"));
                        direccion = cursor.getString(cursor
                                .getColumnIndex("DIRECCION"));
                        usuario = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));

                        cancha = new Cancha(id, nombre, longitud, latitud,
                                direccion, usuario, fechaCreacion,
                                usuario_act, fechaActualizacion);

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
        usuario = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;

        return arrayCancha;
    }

    //ACTUALIZAR CANCHA
    public boolean actualizarCanchaLifuba(Cancha cancha) {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {

            cv.put("NOMBRE", cancha.getNOMBRE());
            cv.put("LONGITUD", cancha.getLONGITUD());
            cv.put("LATITUD", cancha.getLATITUD());
            cv.put("DIRECCION", cancha.getDIRECCION());
            cv.put("USUARIO_ACTUALIZACION", cancha.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cancha.getFECHA_ACTUALIZACION());

            long valor = database.update("CANCHA_LIFUBA", cv, "ID_CANCHA = " + cancha.getID_CANCHA(), null);
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
    public boolean eliminarCanchaLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM CANCHA_LIFUBA WHERE ID_CANCHA = " + id;
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

    public boolean eliminarCanchaLifuba() {

        boolean res = false;
        String sql = "DELETE FROM CANCHA_LIFUBA";
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

    ///////MI EQUIPO////////

    // FECHA INSERTAR
    public boolean insertFecha(Fecha fecha) {
        boolean ban = false;

        String sql = "INSERT INTO FECHA ( FECHA) VALUES ('" + fecha.getFECHA()
                + "')";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                ban = true;
                cerrarBaseDeDatos();
            } catch (Exception e) {
                ban = false;
            }
        } else {
            ban = false;
        }
        sql = null;
        database = null;
        return ban;
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

    // INSERTA ANIO
    public boolean insertAnio(Anio anio) {
        boolean ban = false;

        String sql = "INSERT INTO ANIO (ANIO) VALUES ('" + anio.getANIO() + "')";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                ban = true;

            } catch (Exception e) {
                ban = false;
            }
        } else {
            ban = false;
        }
        cerrarBaseDeDatos();
        sql = null;
        database = null;
        return ban;
    }

    //LISTA ANIO
    public ArrayList<Anio> selectListaAnio() {

        String sql = "SELECT * FROM ANIO";
        ArrayList<Anio> arrayAnio = new ArrayList<Anio>();
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
                    }
                }
            } catch (Exception e) {
                arrayAnio = null;
            }
        } else {
            arrayAnio = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        anioo = null;
        return arrayAnio;
    }

    //INSERTAR MES
    public boolean insertMes(Mes mes) {
        boolean ban = false;

        String sql = "INSERT INTO MES ( MES) VALUES ('" + mes.getMES()
                + "')";
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                database.execSQL(sql);
                ban = true;
            } catch (Exception e) {
                ban = false;
            }
        } else {
            ban = false;
        }
        cerrarBaseDeDatos();
        sql = null;
        database = null;
        return ban;
    }

    //LISTA MES
    public ArrayList<Mes> selectListaMes() {

        String sql = "SELECT * FROM MES";
        ArrayList<Mes> arrayMes = new ArrayList<>();
        String mess = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Mes mes = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_MES"));
                        mess = cursor.getString(cursor.getColumnIndex("MES"));
                        mes = new Mes(id, mess);

                        arrayMes.add(mes);
                    }
                }
            } catch (Exception e) {
                arrayMes = null;
            }
        } else {
            arrayMes = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        mess = null;
        return arrayMes;
    }

    // INSERTAR FIXTURE
    public boolean insertFixtureLifuba(int id, Fixture fixture) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_FIXTURE", id);
            cv.put("ID_EQUIPO_LOCAL", fixture.getID_EQUIPO_LOCAL());
            cv.put("ID_EQUIPO_VISITA", fixture.getID_EQUIPO_VISITA());
            cv.put("ID_DIVISION", fixture.getID_DIVISION());
            cv.put("ID_TORNEO", fixture.getID_TORNEO());
            cv.put("ID_CANCHA", fixture.getID_CANCHA());
            cv.put("ID_FECHA", fixture.getID_FECHA());
            cv.put("ID_ANIO", fixture.getID_ANIO());
            cv.put("DIA", fixture.getDIA());
            cv.put("HORA", fixture.getHORA());
            cv.put("USUARIO_CREADOR", fixture.getUSUARIO_CREACION());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

            long valor = database.insert("FIXTURE_LIFUBA", null, cv);
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

    public boolean insertFixtureLifuba(Fixture fixture) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_FIXTURE", fixture.getID_FIXTURE());
            cv.put("ID_EQUIPO_LOCAL", fixture.getID_EQUIPO_LOCAL());
            cv.put("ID_EQUIPO_VISITA", fixture.getID_EQUIPO_VISITA());
            cv.put("ID_DIVISION", fixture.getID_DIVISION());
            cv.put("ID_TORNEO", fixture.getID_TORNEO());
            cv.put("ID_CANCHA", fixture.getID_CANCHA());
            cv.put("ID_FECHA", fixture.getID_FECHA());
            cv.put("ID_ANIO", fixture.getID_ANIO());
            cv.put("DIA", fixture.getDIA());
            cv.put("HORA", fixture.getHORA());
            cv.put("RESULTADO_LOCAL", fixture.getRESULTADO_LOCAL());
            cv.put("RESULTADO_VISITA", fixture.getRESULTADO_VISITA());
            cv.put("USUARIO_CREADOR", fixture.getUSUARIO_CREACION());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

            long valor = database.insert("FIXTURE_LIFUBA", null, cv);
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

    //ACTUALIZAR FIXTURE
    public boolean actualizarFixtureLifuba(Fixture fixture)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_EQUIPO_LOCAL", fixture.getID_EQUIPO_LOCAL());
            cv.put("ID_EQUIPO_VISITA", fixture.getID_EQUIPO_VISITA());
            cv.put("ID_DIVISION", fixture.getID_DIVISION());
            cv.put("ID_TORNEO", fixture.getID_TORNEO());
            cv.put("ID_CANCHA", fixture.getID_CANCHA());
            cv.put("ID_FECHA", fixture.getID_FECHA());
            cv.put("ID_ANIO", fixture.getID_ANIO());
            cv.put("DIA", fixture.getDIA());
            cv.put("HORA", fixture.getHORA());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

            long valor = database.update("FIXTURE_LIFUBA", cv, "ID_FIXTURE = "
                    + fixture.getID_FIXTURE(), null);
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


    //LISTA FIXTURE RECYCLER
    public ArrayList<Fixture> selectListaFixtureLifuba(int division,
                                                       int torneo, int fecha, int anio) {

        String sql = "SELECT F.ID_FIXTURE AS ID,F.ID_EQUIPO_LOCAL AS ID_LOCAL, LOCALE.NOMBRE AS LOCAL,LOCALE.ESCUDO AS ESCUDOLOCAL,F.RESULTADO_LOCAL AS RESULTADOLOCAL, "
                + "F.ID_EQUIPO_VISITA AS ID_VISITA, VISITAE.NOMBRE AS VISITA, VISITAE.ESCUDO AS ESCUDOVISITA, F.RESULTADO_VISITA AS RESULTADOVISITA, "
                + "C.ID_CANCHA AS ID_CANCHA, C.NOMBRE AS CANCHA, DIA, HORA, A.ANIO, FE.FECHA  "
                + "FROM FIXTURE_LIFUBA F INNER JOIN EQUIPO_LIFUBA LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
                + "INNER JOIN EQUIPO_LIFUBA VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
                + "INNER JOIN CANCHA_LIFUBA C ON C.ID_CANCHA = F.ID_CANCHA "
                + "INNER JOIN ANIO A ON A.ID_ANIO = F.ID_ANIO "
                + "INNER JOIN FECHA FE ON FE.ID_FECHA = F.ID_FECHA "
                + "WHERE ID_DIVISION="
                + division
                + " AND ID_TORNEO="
                + torneo
                + " AND F.ID_FECHA=" + fecha + " AND F.ID_ANIO=" + anio + "";

        ArrayList<Fixture> arrayFixture = new ArrayList<>();
        String dia = null, hora = null, e_local = null, e_visita = null, cancha = null, r_local = null, r_visita = null, fechaF = null, anioF = null;
        int id_fixture, id_equipo_local, id_equipo_visita, id_cancha;
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
                        id_equipo_local = cursor.getInt(cursor
                                .getColumnIndex("ID_LOCAL"));
                        e_local = cursor.getString(cursor
                                .getColumnIndex("LOCAL"));
                        escudolocal = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOLOCAL"));
                        r_local = cursor.getString(cursor
                                .getColumnIndex("RESULTADOLOCAL"));
                        id_equipo_visita = cursor.getInt(cursor
                                .getColumnIndex("ID_VISITA"));
                        e_visita = cursor.getString(cursor
                                .getColumnIndex("VISITA"));
                        escudovisita = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOVISITA"));
                        r_visita = cursor.getString(cursor
                                .getColumnIndex("RESULTADOVISITA"));
                        id_cancha = cursor.getInt(cursor
                                .getColumnIndex("ID_CANCHA"));
                        cancha = cursor.getString(cursor
                                .getColumnIndex("CANCHA"));
                        dia = cursor.getString(cursor.getColumnIndex("DIA"));
                        hora = cursor.getString(cursor.getColumnIndex("HORA"));
                        anioF = cursor.getString(cursor.getColumnIndex("ANIO"));
                        fechaF = cursor.getString(cursor.getColumnIndex("FECHA"));

                        fixtureRecycler = new Fixture(id_fixture,
                                id_equipo_local, e_local, escudolocal, r_local,
                                id_equipo_visita, e_visita, escudovisita, r_visita,
                                id_cancha, cancha, dia, hora, fechaF, anioF);

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
        anioF = null;
        fechaF = null;

        return arrayFixture;
    }

    // ELIMINAR CANCHA
    public boolean eliminarFixtureLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM FIXTURE_LIFUBA WHERE ID_FIXTURE = " + id;
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
    public boolean eliminarFixtureLifuba() {

        boolean res = false;
        String sql = "DELETE FROM FIXTURE_LIFUBA";
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

    ////RESULTADOS////
    //ACTUALIZAR RESULTADO/FIXTURE
    public boolean actualizarResultadoLifuba(Resultado resultado) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("RESULTADO_LOCAL", resultado.getRESULTADO_LOCAL());
            cv.put("RESULTADO_VISITA", resultado.getRESULTADO_VISITA());
            cv.put("USUARIO_ACTUALIZACION", resultado.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", resultado.getFECHA_ACTUALIZACION());

            long valor = database.update("FIXTURE_LIFUBA", cv, "ID_FIXTURE="
                    + resultado.getID_FIXTURE(), null);
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

    //LISTA RECYCLER
    public ArrayList<Resultado> selectListaResultadoLifuba(
            int division, int torneo, int fecha, int anio) {

        String sql = "SELECT F.ID_FIXTURE AS ID,F.ID_EQUIPO_LOCAL AS ID_LOCAL, LOCALE.NOMBRE AS LOCAL,LOCALE.ESCUDO AS ESCUDOLOCAL, "
                + "F.ID_EQUIPO_VISITA AS ID_VISITA, VISITAE.NOMBRE AS VISITA, VISITAE.ESCUDO AS ESCUDOVISITA, C.ID_CANCHA AS ID_CANCHA, "
                + "C.NOMBRE AS CANCHA, DIA, HORA, F.RESULTADO_LOCAL AS RESULTADO_LOCAL, F.RESULTADO_VISITA AS RESULTADO_VISITA, A.ANIO, FE.FECHA "
                + "FROM FIXTURE_LIFUBA F INNER JOIN EQUIPO_LIFUBA LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
                + "INNER JOIN EQUIPO_LIFUBA VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
                + "INNER JOIN CANCHA_LIFUBA C ON C.ID_CANCHA = F.ID_CANCHA "
                + "INNER JOIN ANIO A ON A.ID_ANIO = F.ID_ANIO "
                + "INNER JOIN FECHA FE ON FE.ID_FECHA = F.ID_FECHA "
                + "WHERE ID_DIVISION="
                + division
                + " AND ID_TORNEO="
                + torneo
                + " AND F.ID_FECHA=" + fecha + " AND A.ID_ANIO=" + anio + "";

        ArrayList<Resultado> arrayResultado = new ArrayList<>();
        String e_local = null, e_visita = null, resultado_local = null, resultado_visita = null, fechaR = null, anioR = null;
        int id_fixture, id_equipo_local, id_equipo_visita;
        byte[] escudolocal, escudovisita;

        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        Resultado resultadoRecycler = null;

                        id_fixture = cursor.getInt(cursor.getColumnIndex("ID"));
                        id_equipo_local = cursor.getInt(cursor
                                .getColumnIndex("ID_LOCAL"));
                        e_local = cursor.getString(cursor
                                .getColumnIndex("LOCAL"));
                        escudolocal = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOLOCAL"));
                        resultado_local = cursor.getString(cursor
                                .getColumnIndex("RESULTADO_LOCAL"));
                        id_equipo_visita = cursor.getInt(cursor
                                .getColumnIndex("ID_VISITA"));
                        e_visita = cursor.getString(cursor
                                .getColumnIndex("VISITA"));
                        escudovisita = cursor.getBlob(cursor
                                .getColumnIndex("ESCUDOVISITA"));
                        resultado_visita = cursor.getString(cursor
                                .getColumnIndex("RESULTADO_VISITA"));
                        anioR = cursor.getString(cursor
                                .getColumnIndex("ANIO"));
                        fechaR = cursor.getString(cursor
                                .getColumnIndex("FECHA"));

                        resultadoRecycler = new Resultado(id_fixture,
                                id_equipo_local, e_local, escudolocal,
                                resultado_local, id_equipo_visita, e_visita,
                                escudovisita, resultado_visita, fechaR, anioR);

                        arrayResultado.add(resultadoRecycler);
                    }
                }
            } catch (Exception e) {
                arrayResultado = null;
            }
        } else {
            arrayResultado = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        e_local = null;
        e_visita = null;

        return arrayResultado;
    }

    // ///////////////////////////////////JUGADORES////////////////////////////////////////////

    // INSERTAR JUGADOR
    public boolean insertJugadorLifuba(int id, Jugador jugador) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_JUGADOR", id);
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("ID_POSICION", jugador.getID_POSICION());
            cv.put("USUARIO_CREADOR", jugador.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", jugador.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", jugador.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", jugador.getFECHA_ACTUALIZACION());

            long valor = database.insert("JUGADOR_LIFUBA", null, cv);
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

    public boolean insertJugadorLifuba(Jugador jugador) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_JUGADOR", jugador.getID_JUGADOR());
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("ID_POSICION", jugador.getID_POSICION());
            cv.put("USUARIO_CREADOR", jugador.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", jugador.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", jugador.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", jugador.getFECHA_ACTUALIZACION());

            long valor = database.insert("JUGADOR_LIFUBA", null, cv);
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

    // LISTA JUGADOR
    public ArrayList<Jugador> selectListaJugadorLifuba(int division) {

        String sql = "SELECT J.ID_JUGADOR AS ID_JUGADOR, J.NOMBRE_JUGADOR AS NOMBRE_JUGADOR, J.FOTO_JUGADOR AS FOTO_JUGADOR,"
                + " J.ID_DIVISION AS ID_DIVISION, D.DESCRIPCION AS DESCRIPCION_DIVISION,"
                + " J.ID_POSICION AS ID_POSICION, P.DESCRIPCION AS DESCRIPCION_POSICION"
                + " FROM JUGADOR_LIFUBA J INNER JOIN  DIVISION_LIFUBA D ON J.ID_DIVISION = D.ID_DIVISION"
                + " INNER JOIN POSICION_LIFUBA P ON P.ID_POSICION = J.ID_POSICION"
                + " WHERE J.ID_DIVISION=" + division;

        ArrayList<Jugador> arrayJugador = new ArrayList<>();
        String nombre = null, descripcion_division = null, descripcion_posicion = null;
        byte[] foto = null;
        int id;
        int id_division;
        int id_posicion;
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
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion_division = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION_DIVISION"));
                        id_posicion = cursor.getInt(cursor
                                .getColumnIndex("ID_POSICION"));
                        descripcion_posicion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION_POSICION"));

                        jugador = new Jugador(id, nombre, foto,
                                id_division, descripcion_division, id_posicion,
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

    //ACTUALIZAR JUGADOR
    public boolean actualizarJugadorLifuba(Jugador jugador)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("ID_POSICION", jugador.getID_POSICION());
            cv.put("USUARIO_ACTUALIZACION", jugador.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", jugador.getFECHA_ACTUALIZACION());

            long valor = database.update("JUGADOR_LIFUBA", cv, "ID_JUGADOR="
                    + jugador.getID_JUGADOR(), null);
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

    //ELIMINAR JUGADOR
    public boolean eliminarJugadorLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM JUGADOR_LIFUBA WHERE ID_JUGADOR = " + id;
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
    public boolean eliminarJugadorLifuba() {

        boolean res = false;
        String sql = "DELETE FROM JUGADOR_LIFUBA";
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

    //POSICION
    //INSERTAR
    public boolean insertPosicionLifuba(int id, Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_POSICION", id);
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", posicion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", posicion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

            long valor = database.insert("POSICION_LIFUBA", null, cv);
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
    public boolean insertPosicionLifuba(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_POSICION", posicion.getID_POSICION());
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", posicion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", posicion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

            long valor = database.insert("POSICION_LIFUBA", null, cv);
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


    //ACTUALIZAR
    public boolean actualizarPosicionLifuba(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

            long valor = database.update("POSICION_LIFUBA", cv, "ID_POSICION="
                    + posicion.getID_POSICION(), null);
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

    //LISTA POSICION
    public ArrayList<Posicion> selectListaPosicionLifuba() {

        String sql = "SELECT * FROM POSICION_LIFUBA";
        ArrayList<Posicion> arrayPosicion = new ArrayList<>();
        String descripcion = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        Posicion posicion = null;
                        id = cursor
                                .getInt(cursor.getColumnIndex("ID_POSICION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        posicion = new Posicion(id, descripcion, null, null, null, null);

                        arrayPosicion.add(posicion);
                    }
                }
            } catch (Exception e) {
                arrayPosicion = null;
            }
        } else {
            arrayPosicion = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        descripcion = null;

        return arrayPosicion;
    }

    public boolean eliminarPosicionLifuba() {

        boolean res = false;
        String sql = "DELETE FROM POSICION_LIFUBA";
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

    ////ENTRENAMIENTO//////
//INSERTAR
    public boolean insertEntrenamientoLifuba(int id, Entrenamiento entrenamiento)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO", id);
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
            cv.put("USUARIO_CREADOR", entrenamiento.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", entrenamiento.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", entrenamiento.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", entrenamiento.getFECHA_ACTUALIZACION());

            long valor = database.insert("ENTRENAMIENTO_LIFUBA", null, cv);
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

    // INSERT TABLA INTERMEDIA
    public boolean insertEntrenamientoDivisionLifuba(int id, int division)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO", id);
            cv.put("ID_DIVISION", division);

            long valor = database.insert("ENTRENAMIENTO_DIVISION_LIFUBA", null,
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

    //ACTUALIZAR
    public boolean actualizarEntrenamientoLifuba(Entrenamiento entrenamiento)
            throws SQLiteException {
        //   int id_entrenamiento = 0;
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
            cv.put("USUARIO_ACTUALIZACION", entrenamiento.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", entrenamiento.getFECHA_ACTUALIZACION());

            long valor = database.update("ENTRENAMIENTO_LIFUBA", cv, "ID_ENTRENAMIENTO="
                    + entrenamiento.getID_ENTRENAMIENTO(), null);
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
    public int selectIdEntrenamiento_Division() {

        String sql = "SELECT ID_ENTRENAMIENTO_DIVISION FROM ENTRENAMIENTO_DIVISION_LIFUBA ORDER BY ID_ENTRENAMIENTO_DIVISION DESC limit 1";
        int id = 0;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Posicion posicion = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO_DIVISION"));
                    }
                }
            } catch (Exception e) {
                id = 0;
            }
        } else {
            id = 0;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;

        return id;
    }

    //LISTA ENTRENAMIENTO sin division
    public ArrayList<EntrenamientoRecycler> selectListaEntrenamientoLifuba(String fecha) {

        String sql = "SELECT EA.ID_ENTRENAMIENTO, EA.DIA_ENTRENAMIENTO, EA.HORA_ENTRENAMIENTO, EA.ID_CANCHA, CA.NOMBRE"
                + " FROM ENTRENAMIENTO_LIFUBA EA INNER JOIN CANCHA_Lifuba CA ON EA.ID_CANCHA = CA.ID_CANCHA"
                + " WHERE substr(EA.DIA_ENTRENAMIENTO , 4, 7) = '" + fecha + "' ORDER BY EA.ID_ENTRENAMIENTO DESC";

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
                        id_cancha = cursor.getInt(cursor
                                .getColumnIndex("ID_CANCHA"));
                        nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));

                        entrenamientoRecycler = new EntrenamientoRecycler(id_entrenamiento,
                                dia, hora, id_cancha, nombre);

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
    public ArrayList<Entrenamiento> selectListaDivisionEntrenamientoLifubaId(int id_entrenamiento) {

        String sql = "SELECT ED.ID_ENTRENAMIENTO_DIVISION, ED.ID_DIVISION, D.DESCRIPCION"
                + " FROM ENTRENAMIENTO_DIVISION_LIFUBA ED INNER JOIN DIVISION_LIFUBA D ON"
                + " ED.ID_DIVISION = D.ID_DIVISION"
                + " WHERE ID_ENTRENAMIENTO = " + id_entrenamiento + "";

        Cursor cursor = null;
        ArrayList<Entrenamiento> arrayDivision = new ArrayList<>();
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

    //lISTA ENTRENAMIENTO division
    public ArrayList<Entrenamiento> selectListaDivisionEntrenamientoLifuba() {

        String sql = "SELECT * FROM DIVISION_LIFUBA";
        ArrayList<Entrenamiento> arrayDivision = new ArrayList<>();
        int id, id_division;
        String descripcion = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Entrenamiento entrenamiento_division = null;
                        id = 0;
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));

                        entrenamiento_division = new Entrenamiento(id,
                                id, id_division, descripcion, false);

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
        descripcion = null;
        return arrayDivision;
    }

    //ELIMINAR ENTRENAMIENTO
    public boolean eliminarEntrenamientoLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_LIFUBA WHERE ID_ENTRENAMIENTO = " + id;
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
    public boolean eliminarDivisionEntrenamientoLifuba(int id, int division) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_DIVISION_LIFUBA WHERE ID_ENTRENAMIENTO = " + id + " AND ID_DIVISION =" + division;
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
    public boolean eliminarDivisionEntrenamientoLifuba(int id) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_DIVISION_LIFUBA WHERE ID_ENTRENAMIENTO = " + id;
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


    // INSERT ASISTENCIA
    public boolean insertAsistenciaEntrenamientoLifuba(int id, int id_jugador)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        long valor = 0;
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO", id);
            cv.put("ID_JUGADOR", id_jugador);

            valor = database.insert("ENTRENAMIENTO_ASISTENCIA_LIFUBA", null,
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
    public boolean eliminarAsistenciaEntrenamientoLifuba(int id_entrenamiento, int id_jugador) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_ASISTENCIA_LIFUBA WHERE ID_ENTRENAMIENTO =" + id_entrenamiento + " AND ID_JUGADOR=" + id_jugador;
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

    //lISTA JUGADORES CITADOS POR ENTRENAMIENTO
    public ArrayList<Entrenamiento> selectListaJugadoresEntrenamientoLifuba(int id_entrenamiento) {

        String sql = "SELECT * FROM ENTRENAMIENTO_ASISTENCIA_LIFUBA WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
        ArrayList<Entrenamiento> arrayAsistencia = new ArrayList<>();

        int id, id_jugador, id_entre;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Entrenamiento entrenamientoAsistencia = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO_ASISTENCIA"));
                        id_entre = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO"));
                        id_jugador = cursor.getInt(cursor
                                .getColumnIndex("ID_JUGADOR"));

                        entrenamientoAsistencia = new Entrenamiento(id, id_entre, id_jugador, "");

                        arrayAsistencia.add(entrenamientoAsistencia);
                    }
                }
            } catch (Exception e) {
                arrayAsistencia = null;
            }
        } else {
            arrayAsistencia = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return arrayAsistencia;
    }

    //lISTA JUGADORES CITADOS POR ID DIVISION
    public ArrayList<Entrenamiento> selectListaJugadoresEntrenamientoLifuba(ArrayList<Integer> id_division_array, int id_entrenamiento) {

        String sql = "SELECT * FROM ENTRENAMIENTO_ASISTENCIA_LIFUBA WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
        ArrayList<Entrenamiento> arrayAsistencia = new ArrayList<Entrenamiento>();
        int id, id_jugador, id_entre;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Entrenamiento entrenamientoAsistencia = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO_ASISTENCIA"));
                        id_entre = cursor.getInt(cursor
                                .getColumnIndex("ID_ENTRENAMIENTO"));
                        id_jugador = cursor.getInt(cursor
                                .getColumnIndex("ID_JUGADOR"));

                        entrenamientoAsistencia = new Entrenamiento(id,
                                id_entre, id_jugador, "");

                        arrayAsistencia.add(entrenamientoAsistencia);
                    }
                }
            } catch (Exception e) {
                arrayAsistencia = null;
            }
        } else {
            arrayAsistencia = null;
        }
        cerrarBaseDeDatos();

        String sqlId = "";
        for (int ids = 0; ids < id_division_array.size(); ids++) {
            if (id_division_array.size() != 1) {
                if (ids == 0) {
                    sqlId = "" + id_division_array.get(0);
                } else {
                    sqlId += " OR J.ID_DIVISION=" + id_division_array.get(ids);
                }
            } else {
                sqlId = "" + id_division_array.get(ids);
            }
        }

        String sqlJ = "SELECT J.ID_JUGADOR, J.NOMBRE_JUGADOR, J.ID_DIVISION, D.DESCRIPCION " +
                "FROM JUGADOR_LIFUBA J INNER JOIN DIVISION_LIFUBA D ON D.ID_DIVISION = J.ID_DIVISION " +
                "WHERE J.ID_DIVISION=" + sqlId;
        ArrayList<Entrenamiento> arrayAsistenciaJugador = new ArrayList<>();

        int id_jug, id_divi;
        String nombre = null, descripcion = null;
        Cursor cursorJ = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursorJ = database.rawQuery(sqlJ, null);
                if (cursorJ != null && cursorJ.getCount() > 0) {

                    while (cursorJ.moveToNext()) {

                        Entrenamiento entrenamientoAsistenciaJugador = null;
                        id_jug = cursorJ.getInt(cursorJ
                                .getColumnIndex("ID_JUGADOR"));
                        nombre = cursorJ.getString(cursorJ
                                .getColumnIndex("NOMBRE_JUGADOR"));
                        id_divi = cursorJ.getInt(cursorJ
                                .getColumnIndex("ID_DIVISION"));
                        descripcion = cursorJ.getString(cursorJ
                                .getColumnIndex("DESCRIPCION"));

                        entrenamientoAsistenciaJugador = new Entrenamiento(0,
                                0, id_divi, descripcion, id_jug, nombre, false);

                        arrayAsistenciaJugador.add(entrenamientoAsistenciaJugador);
                    }
                }
            } catch (Exception e) {
                arrayAsistenciaJugador = null;
            }
        } else {
            arrayAsistenciaJugador = null;
        }

        if (arrayAsistencia != null) {
            if (arrayAsistencia.size() > 0) {

                for (int i = 0; i < arrayAsistencia.size(); i++) {
                    for (int j = 0; j < arrayAsistenciaJugador.size(); j++) {

                        if (arrayAsistencia.get(i).getID_JUGADOR() == arrayAsistenciaJugador.get(j).getID_JUGADOR()) {
                            arrayAsistenciaJugador.get(j).setSelected(true);
                            break;
                        }
                    }
                }
            }
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arrayAsistenciaJugador;
    }

    //lISTA JUGADORES CITADOS
    public ArrayList<Integer> selectListaIdDivisionEntrenamientoLifuba(int id_entrenamiento) {

        String sql = "SELECT ID_DIVISION FROM ENTRENAMIENTO_DIVISION_LIFUBA WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
        ArrayList<Integer> id_division_array = new ArrayList<>();
        int id_division;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));

                        id_division_array.add(id_division);
                    }
                }
            } catch (Exception e) {
                id_division_array = null;
            }
        } else {
            id_division_array = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return id_division_array;
    }


    // INSERTAR JUGADOR
    public boolean insertSancionLifuba(int id, Sancion sancion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_SANCION", id);
            cv.put("ID_JUGADOR", sancion.getID_JUGADOR());
            cv.put("ID_TORNEO", sancion.getID_TORNEO());
            cv.put("AMARILLA", sancion.getAMARILLA());
            cv.put("ROJA", sancion.getROJA());
            cv.put("FECHA_SUSPENSION", sancion.getFECHA_SUSPENSION());
            cv.put("OBSERVACIONES", sancion.getOBSERVACIONES());
            cv.put("USUARIO_CREADOR", sancion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", sancion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", sancion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", sancion.getFECHA_ACTUALIZACION());

            long valor = database.insert("SANCION_LIFUBA", null, cv);
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

    public boolean insertSancionLifuba(Sancion sancion) throws SQLiteException {

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
            cv.put("USUARIO_CREADOR", sancion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", sancion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", sancion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", sancion.getFECHA_ACTUALIZACION());

            long valor = database.insert("SANCION_LIFUBA", null, cv);
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

    // ACTUALIZAR SANCION
    public boolean actualizarSancionLifuba(Sancion sancion) throws SQLiteException {
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("ID_JUGADOR", sancion.getID_JUGADOR());
            cv.put("ID_TORNEO", sancion.getID_TORNEO());
            // cv.put("ID_ANIO", sancion.getID_ANIO());
            cv.put("AMARILLA", sancion.getAMARILLA());
            cv.put("ROJA", sancion.getROJA());
            cv.put("FECHA_SUSPENSION", sancion.getFECHA_SUSPENSION());
            cv.put("OBSERVACIONES", sancion.getOBSERVACIONES());
            cv.put("USUARIO_ACTUALIZACION", sancion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", sancion.getFECHA_ACTUALIZACION());

            long valor = database.update("SANCION_LIFUBA", cv, "ID_SANCION=" + sancion.getID_SANCION(), null);
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
    public boolean eliminarSancionLifuba(int id_sancion) {

        boolean res = false;
        String sql = "DELETE FROM SANCION_LIFUBA WHERE ID_SANCION =" + id_sancion;
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
    public boolean eliminarSancionLifuba() {

        boolean res = false;
        String sql = "DELETE FROM SANCION_LIFUBA";
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
    public ArrayList<Sancion> selectListaSancionLifuba(int division,
                                                       int jugador, int torneo) {

        String sql = "SELECT S.ID_SANCION AS ID,S.ID_JUGADOR, J.NOMBRE_JUGADOR,J.FOTO_JUGADOR, J.ID_DIVISION, D.DESCRIPCION, "
                + "S.AMARILLA, S.ROJA, S.FECHA_SUSPENSION, S.OBSERVACIONES "
                + "FROM SANCION_LIFUBA S "
                + "INNER JOIN JUGADOR_LIFUBA J ON J.ID_JUGADOR = S.ID_JUGADOR "
                + "INNER JOIN DIVISION_LIFUBA D ON D.ID_DIVISION = J.ID_DIVISION "
                + "WHERE D.ID_DIVISION = "
                + division
                + " AND J.ID_JUGADOR = "
                + jugador
                + " AND S.ID_TORNEO = " + torneo + "";

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