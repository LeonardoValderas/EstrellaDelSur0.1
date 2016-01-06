package com.estrelladelsur.estrelladelsur.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
import com.estrelladelsur.abstractclases.Anio;
import com.estrelladelsur.abstractclases.Cancha;
import com.estrelladelsur.abstractclases.Division;
import com.estrelladelsur.abstractclases.Entrenamiento;
import com.estrelladelsur.abstractclases.Entrenamiento_Division;
import com.estrelladelsur.abstractclases.Equipo;
import com.estrelladelsur.abstractclases.Fecha;
import com.estrelladelsur.abstractclases.Fixture;
import com.estrelladelsur.abstractclases.FixtureRecycler;
import com.estrelladelsur.abstractclases.Jugador;
import com.estrelladelsur.abstractclases.JugadorRecycler;
import com.estrelladelsur.abstractclases.Mes;
import com.estrelladelsur.abstractclases.Posicion;
import com.estrelladelsur.abstractclases.Resultado;
import com.estrelladelsur.abstractclases.ResultadoRecycler;
import com.estrelladelsur.abstractclases.Torneo;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData.Result;
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.estrelladelsur.estrelladelsur.abstracta.Articulo;
import com.estrelladelsur.estrelladelsur.abstracta.Cargo;
import com.estrelladelsur.estrelladelsur.abstracta.Comision;
import com.estrelladelsur.estrelladelsur.abstracta.Direccion;

public class ControladorAdeful {

    private SQLiteDBConnectionAdeful sqLiteDBConnectionAdeful;
    private Context ourcontext;
    private SQLiteDatabase database;

    public ControladorAdeful(Context c) {
        ourcontext = c;

    }

    public ControladorAdeful abrirBaseDeDatos() throws SQLException {
        sqLiteDBConnectionAdeful = new SQLiteDBConnectionAdeful(ourcontext,
                "BaseDeDatosAdeful.db", null, 1);
        database = sqLiteDBConnectionAdeful.getWritableDatabase();
        return this;
    }

    public void cerrarBaseDeDatos() {
        sqLiteDBConnectionAdeful.close();
    }

    public String getFechaOficial() {

        Date dateOficial = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss");
        return sdf.format(dateOficial);
    }

    // INSERTAR ARTICULO
    public boolean insertArticuloAdeful(Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());
            cv.put("USUARIO_CREADOR", articulo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", articulo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", articulo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", articulo.getFECHA_ACTUALIZACION());

            long valor = database.insert("ARTICULO_ADEFUL", null, cv);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //ACTUALIZAR ARTICULO
    public boolean actualizarArticuloAdeful(Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());
            cv.put("USUARIO_ACTUALIZACION", articulo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", articulo.getFECHA_ACTUALIZACION());

            long valor = database.update("ARTICULO_ADEFUL", cv, "ID_ARTICULO=" + articulo.getID_ARTICULO(), null);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //LISTA ARTICULO
    public ArrayList<Articulo> selectListaArticuloAdeful() {

        String sql = "SELECT * FROM ARTICULO_ADEFUL";
        ArrayList<Articulo> arrayArticuloAdeful = new ArrayList<Articulo>();
        String titulo = null, articulo = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Articulo articuloAdeful = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_ARTICULO"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        articulo = cursor.getString(cursor
                                .getColumnIndex("ARTICULO"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        articuloAdeful = new Articulo(id, titulo, articulo, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY ARTICULO
                        arrayArticuloAdeful.add(articuloAdeful);

                    }
                }

            } catch (Exception e) {
                Log.e("selectListaArticuloAdeful", e.toString());
            }
        } else {

            Log.e("selectListaArticuloAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        articulo = null;
        return arrayArticuloAdeful;
    }

    //ELIMINAR ARTICULO
    public boolean eliminarArticuloAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM ARTICULO_ADEFUL WHERE ID_ARTICULO = " + id;

        if (database != null && database.isOpen()) {

            try {

                database.execSQL(sql);
                res = true;

            } catch (Exception e) {

                res = false;
                Log.e("eliminarArticuloAdeful", e.toString());
            }

        } else {

            res = false;
            Log.e("eliminarArticuloAdeful", "Error Conexion Base de Datos");
        }

        database = null;
        sql = null;
        return res;
    }

    /////////CARGO//////////////
    // INSERTAR CARGO
    public boolean insertCargoAdeful(Cargo cargo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("CARGO", cargo.getCARGO());
            cv.put("USUARIO_CREADOR", cargo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", cargo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", cargo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cargo.getFECHA_ACTUALIZACION());

            long valor = database.insert("CARGO_ADEFUL", null, cv);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    // ACTUALIZAR CARGO
    public boolean actualizarCargoAdeful(Cargo cargo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("CARGO", cargo.getCARGO());
            cv.put("USUARIO_ACTUALIZACION", cargo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cargo.getFECHA_ACTUALIZACION());

            long valor = database.update("CARGO_ADEFUL", cv, "ID_CARGO=" + cargo.getID_CARGO(), null);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //LISTA CARGOS
    public ArrayList<Cargo> selectListaCargoAdeful() {

        String sql = "SELECT * FROM CARGO_ADEFUL";
        ArrayList<Cargo> arrayCargoAdeful = new ArrayList<Cargo>();
        String cargoS = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Cargo cargo = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        cargoS = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        cargo = new Cargo(id, cargoS, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayCargoAdeful.add(cargo);
                    }
                }
            } catch (Exception e) {
                Log.e("selectListaCargoAdeful", e.toString());
            }
        } else {

            Log.e("selectListaCargoAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        cargoS = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        creador = null;
        usuario_act = null;

        return arrayCargoAdeful;
    }

    ////////COMISION/////////
    //INSERTAR
    public boolean insertComisionAdeful(Comision comision) throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_COMISION", comision.getNOMBRE_COMISION());
            cv.put("FOTO_COMISION", comision.getFOTO_COMISION());
            cv.put("ID_CARGO", comision.getID_CARGO());
            cv.put("PERIODO_DESDE", comision.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", comision.getPERIODO_HASTA());
            cv.put("USUARIO_CREADOR", comision.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", comision.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", comision.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", comision.getFECHA_ACTUALIZACION());

            long valor = database.insert("COMISION_ADEFUL", null, cv);
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //COMISION POR ID
    public ArrayList<Comision> selectComisionAdeful(int id_comision) {

        String sql = "SELECT * FROM COMISION_ADEFUL WHERE ID_COMISION=" + id_comision;
        ArrayList<Comision> arrayComisionAdeful = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        int id_cargo;
        byte[] foto = null;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Comision comision = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_COMISION"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_COMISION"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_COMISION"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        comision = new Comision(id, nombre, foto, id_cargo,null, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayComisionAdeful.add(comision);
                    }
                }
            } catch (Exception e) {
                Log.e("selectComisionAdeful", e.toString());
            }
        } else {

            Log.e("selectComisionAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        creador = null;
        usuario_act = null;

        return arrayComisionAdeful;
    }

    //LISTA COMISION
    public ArrayList<Comision> selectListaComisionAdeful() {

        String sql = "SELECT C.ID_COMISION, C.NOMBRE_COMISION, C.FOTO_COMISION, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM COMISION_ADEFUL C INNER JOIN CARGO_ADEFUL CA ON CA.ID_CARGO=C.ID_CARGO";
        ArrayList<Comision> arrayComisionAdeful = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null;
        int id;
        int id_cargo;
        byte[] foto = null;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Comision comision = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_COMISION"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_COMISION"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_COMISION"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        comision = new Comision(id, nombre, foto, id_cargo,cargo, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY COMISION
                        arrayComisionAdeful.add(comision);
                    }
                }
            } catch (Exception e) {
                Log.e("selectListaComisionAdeful", e.toString());
            }
        } else {

            Log.e("selectListaComisionAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        creador = null;
        usuario_act = null;

        return arrayComisionAdeful;
    }

    //ACTUALIZAR
    public boolean actualizarComisionAdeful(Comision comision) throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_COMISION", comision.getNOMBRE_COMISION());
            cv.put("FOTO_COMISION", comision.getFOTO_COMISION());
            cv.put("ID_CARGO", comision.getID_CARGO());
            cv.put("PERIODO_DESDE", comision.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", comision.getPERIODO_HASTA());
            cv.put("USUARIO_CREADOR", comision.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", comision.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", comision.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", comision.getFECHA_ACTUALIZACION());

            long valor = database.update("COMISION_ADEFUL", cv, "ID_COMISION=" + comision.getID_COMISION(), null);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //ELIMINAR COMISION
    public boolean eliminarComisionAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM COMISION_ADEFUL WHERE ID_COMISION = " + id;

        if (database != null && database.isOpen()) {

            try {

                database.execSQL(sql);
                res = true;

            } catch (Exception e) {

                res = false;
                Log.e("eliminarComisionAdeful", e.toString());
            }

        } else {

            res = false;
            Log.e("eliminarComisionAdeful", "Error Conexion Base de Datos");
        }

        database = null;
        sql = null;
        return res;
    }

    ////////DIRECCION/////////
    //INSERTAR
    public boolean insertDireccionAdeful(Direccion direccion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
            cv.put("ID_CARGO", direccion.getID_CARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());
            cv.put("USUARIO_CREADOR", direccion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", direccion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", direccion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", direccion.getFECHA_ACTUALIZACION());

            long valor = database.insert("DIRECCION_ADEFUL", null, cv);
            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //COMISION POR ID
    public ArrayList<Direccion> selectDireccionAdeful(int id_direccion) {

        String sql = "SELECT * FROM DIRECCION_ADEFUL WHERE ID_DIRECCION=" + id_direccion;
        ArrayList<Direccion> arrayDireccionAdeful = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        int id_cargo;
        byte[] foto = null;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Direccion direccion = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_DIRECCION"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_DIRECCION"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_DIRECCION"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        direccion = new Direccion(id, nombre, foto, id_cargo,null, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayDireccionAdeful.add(direccion);
                    }
                }
            } catch (Exception e) {
                Log.e("selectDireccionAdeful", e.toString());
            }
        } else {

            Log.e("selectDireccionAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        creador = null;
        usuario_act = null;

        return arrayDireccionAdeful;
    }

    //LISTA COMISION
    public ArrayList<Direccion> selectListaDireccionAdeful() {

        String sql = "SELECT C.ID_DIRECCION, C.NOMBRE_DIRECCION, C.FOTO_DIRECCION, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM DIRECCION_ADEFUL C INNER JOIN CARGO_ADEFUL CA ON CA.ID_CARGO=C.ID_CARGO";
        ArrayList<Direccion> arrayDireccionAdeful = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null;
        int id;
        int id_cargo;
        byte[] foto = null;
        Cursor cursor = null;

        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Direccion direccion = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_DIRECCION"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_DIRECCION"));
                        foto = cursor.getBlob(cursor
                                .getColumnIndex("FOTO_DIRECCION"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        direccion = new Direccion(id, nombre, foto, id_cargo,cargo, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY COMISION
                        arrayDireccionAdeful.add(direccion);
                    }
                }
            } catch (Exception e) {
                Log.e("selectListaDireccionAdeful", e.toString());
            }
        } else {

            Log.e("selectListaDireccionAdeful", "Error Conexion Base de Datos");
        }

        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        creador = null;
        usuario_act = null;

        return arrayDireccionAdeful;
    }

    //ACTUALIZAR
    public boolean actualizarDireccionAdeful(Direccion direccion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_COMISION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_COMISION", direccion.getFOTO_DIRECCION());
            cv.put("ID_CARGO", direccion.getID_CARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());
            cv.put("USUARIO_CREADOR", direccion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", direccion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", direccion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", direccion.getFECHA_ACTUALIZACION());

            long valor = database.update("DIRECCION_ADEFUL", cv, "ID_DIRECCION=" + direccion.getID_DIRECCION(), null);

            if (valor > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {

            return false;
        }
    }

    //ELIMINAR COMISION
    public boolean eliminarDireccionAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM DIRECCION_ADEFUL WHERE ID_DIRECCION = " + id;

        if (database != null && database.isOpen()) {

            try {

                database.execSQL(sql);
                res = true;

            } catch (Exception e) {

                res = false;
                Log.e("eliminarDireccionAdeful", e.toString());
            }

        } else {

            res = false;
            Log.e("eliminarDireccionAdeful", "Error Conexion Base de Datos");
        }

        database = null;
        sql = null;
        return res;
    }

    /*

	*//**
     * Se usa ContentVales para la utilizaci�n de byte[] blob
     *//*

	public boolean insertEquipoAdeful(Equipo equipoAdeful)
			throws SQLiteException {

		ContentValues cv = new ContentValues();
		try {
			cv.put("NOMBRE", equipoAdeful.getNOMBRE_EQUIPO());
			cv.put("ESCUDO", equipoAdeful.getESCUDO());
			cv.put("USUARIO_CREADOR", equipoAdeful.getNOMBRE_USUARIO());
			cv.put("FECHA_CREACION", equipoAdeful.getFECHA_CREACION());
			cv.put("FECHA_ACTUALIZACION", equipoAdeful.getFECHA_ACTUALIZACION());
			cv.put("ESTADO", equipoAdeful.getESTADO());
			cv.put("TABLA", equipoAdeful.getTABLA());

			long valor = database.insert("EQUIPO_ADEFUL", null, cv);

			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     *
     * Metodo que obtiene lista de equipos adeful.
     *//*
    public ArrayList<Equipo> selectListaEquipoAdeful() {

		String sql = "SELECT * FROM EQUIPO_ADEFUL";
		ArrayList<Equipo> arrayEquipoAdeful = new ArrayList<Equipo>();
		String equipo = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, estado = null, tabla = null;
		byte[] escudo = null;
		int id;
		Cursor cursor = null;
		// Integer isFueraFrecuencia;

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

						usuario = cursor.getString(cursor
								.getColumnIndex("USUARIO_CREADOR"));
						fechaCreacion = cursor.getString(cursor
								.getColumnIndex("FECHA_CREACION"));
						fechaActualizacion = cursor.getString(cursor
								.getColumnIndex("FECHA_ACTUALIZACION"));
						estado = cursor.getString(cursor
								.getColumnIndex("ESTADO"));
						tabla = cursor
								.getString(cursor.getColumnIndex("TABLA"));

						equipoAdeful = new Equipo(id, equipo, escudo, usuario,
								fechaCreacion, fechaActualizacion, estado,
								tabla);

						arrayEquipoAdeful.add(equipoAdeful);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaEquipoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaEquipoAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		equipo = null;
		usuario = null;
		escudo = null;
		fechaCreacion = null;
		fechaActualizacion = null;
		estado = null;
		tabla = null;
		return arrayEquipoAdeful;
	}

	public boolean actualizarEquipoAdeful(Equipo equipo) throws SQLiteException {
		boolean ban = false;

		ContentValues cv = new ContentValues();
		try {
			cv.put("NOMBRE", equipo.getNOMBRE_EQUIPO());
			cv.put("ESCUDO", equipo.getESCUDO());
			cv.put("USUARIO_CREADOR", equipo.getNOMBRE_USUARIO());
			cv.put("FECHA_ACTUALIZACION", equipo.getFECHA_ACTUALIZACION());
			cv.put("ESTADO", equipo.getESTADO());
			cv.put("TABLA", equipo.getTABLA());

			long valor = database.update("EQUIPO_ADEFUL", cv, "ID_EQUIPO" + "="
					+ equipo.getID_EQUIPO(), null);

			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	public boolean eliminarEquipoAdeful(int id) {

		boolean res = false;
		String sql = "DELETE FROM EQUIPO_ADEFUL WHERE ID_EQUIPO = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarEquipoAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarEquipoAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	*//**
     * Metodo que inserta los datos de una division en la base de datos.
     *
     *//*

	public boolean insertDivisionAdeful(Division division) {
		boolean ban = false;

		String sql = "INSERT INTO DIVISION_ADEFUL ( DESCRIPCION, USUARIO_CREADOR, FECHA_CREACION, FECHA_ACTUALIZACION, ESTADO, TABLA ) VALUES ('"
				+ division.getDESCRIPCION()
				+ "', '"
				+ division.getNOMBRE_USUARIO()
				+ "', '"
				+ division.getFECHA_CREACION()
				+ "', '"
				+ division.getFECHA_ACTUALIZACION()
				+ "', '"
				+ division.getESTADO() + "', '" + division.getTABLA() + "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertDivision", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertDivision", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de division adeful.
     *//*
	public ArrayList<Division> selectListaDivisionAdeful() {

		String sql = "SELECT * FROM DIVISION_ADEFUL";
		ArrayList<Division> arrayDivision = new ArrayList<Division>();
		String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, estado = null, tabla = null;
		int id;
		Cursor cursor = null;

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
						fechaActualizacion = cursor.getString(cursor
								.getColumnIndex("FECHA_ACTUALIZACION"));
						estado = cursor.getString(cursor
								.getColumnIndex("ESTADO"));
						tabla = cursor
								.getString(cursor.getColumnIndex("TABLA"));

						division = new Division(id, descripcion, usuario,
								fechaCreacion, fechaActualizacion, estado,
								tabla);

						arrayDivision.add(division);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaDivisionAdeful", e.toString());
			}
		} else {

			Log.e("selectListaDivisionAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		descripcion = null;
		usuario = null;
		fechaCreacion = null;
		fechaActualizacion = null;
		estado = null;
		tabla = null;

		return arrayDivision;
	}

	public boolean actualizarDivisionAdeful(Division division) {

		boolean res = false;
		String sql = "UPDATE DIVISION_ADEFUL SET DESCRIPCION='"
				+ division.getDESCRIPCION() + "', USUARIO_CREADOR='"
				+ division.getNOMBRE_USUARIO() + "', FECHA_ACTUALIZACION='"
				+ division.getFECHA_ACTUALIZACION() + "', ESTADO='"
				+ division.getESTADO() + "', TABLA = '" + division.getTABLA()
				+ "' WHERE ID_DIVISION ='" + division.getID_DIVISION() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarDivision", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarDivision", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean eliminarDivisionAdeful(int id) {

		boolean res = false;

		String sql = "DELETE FROM DIVISION_ADEFUL WHERE ID_DIVISION = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarDivision", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarDivision", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	*//**
     * Metodo que inserta los datos de un torneo en la base de datos.
     *
     *//*

	public boolean insertTorneoAdeful(Torneo torneo) {
		boolean ban = false;

		String sql = "INSERT INTO TORNEO_ADEFUL ( DESCRIPCION, USUARIO_CREADOR, FECHA_CREACION, FECHA_ACTUALIZACION, ESTADO, TABLA ) VALUES ('"
				+ torneo.getDESCRIPCION()
				+ "', '"
				+ torneo.getNOMBRE_USUARIO()
				+ "', '"
				+ torneo.getFECHA_CREACION()
				+ "', '"
				+ torneo.getFECHA_ACTUALIZACION()
				+ "', '"
				+ torneo.getESTADO()
				+ "', '" + torneo.getTABLA() + "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertTorneoAdeful", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertTorneoAdeful", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de torneo adeful.
     *//*
	public ArrayList<Torneo> selectListaTorneoAdeful() {

		String sql = "SELECT * FROM TORNEO_ADEFUL";
		ArrayList<Torneo> arrayTorneo = new ArrayList<Torneo>();
		String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, estado = null, tabla = null;
		int id;
		Cursor cursor = null;

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
						fechaCreacion = cursor.getString(cursor
								.getColumnIndex("FECHA_CREACION"));
						fechaActualizacion = cursor.getString(cursor
								.getColumnIndex("FECHA_ACTUALIZACION"));
						estado = cursor.getString(cursor
								.getColumnIndex("ESTADO"));
						tabla = cursor
								.getString(cursor.getColumnIndex("TABLA"));

						torneo = new Torneo(id, descripcion, usuario,
								fechaCreacion, fechaActualizacion, estado,
								tabla);

						arrayTorneo.add(torneo);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaTorneoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaTorneoAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		descripcion = null;
		usuario = null;
		fechaCreacion = null;
		fechaActualizacion = null;
		estado = null;
		tabla = null;

		return arrayTorneo;
	}

	public boolean actualizarTorneoAdeful(Torneo torneo) {

		boolean res = false;

		String sql = "UPDATE TORNEO_ADEFUL SET DESCRIPCION='"
				+ torneo.getDESCRIPCION() + "', USUARIO_CREADOR='"
				+ torneo.getNOMBRE_USUARIO() + "', FECHA_ACTUALIZACION='"
				+ torneo.getFECHA_ACTUALIZACION() + "', ESTADO='"
				+ torneo.getESTADO() + "', TABLA = '" + torneo.getTABLA()
				+ "' WHERE ID_TORNEO ='" + torneo.getID_TORNEO() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarTorneoAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarTorneoAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean eliminarTorneoAdeful(int id) {

		boolean res = false;

		String sql = "DELETE FROM TORNEO_ADEFUL WHERE ID_TORNEO = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarTorneoAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarTorneoAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	*//**
     * Metodo que inserta los datos de un torneo en la base de datos.
     *
     *//*

	public boolean insertCanchaAdeful(Cancha cancha) {
		boolean ban = false;

		String sql = "INSERT INTO CANCHA_ADEFUL ( NOMBRE, LONGITUD, LATITUD, DIRECCION, USUARIO_CREADOR, FECHA_CREACION, FECHA_ACTUALIZACION, ESTADO, TABLA ) VALUES ('"
				+ cancha.getNOMBRE()
				+ "', '"
				+ cancha.getLONGITUD()
				+ "', '"
				+ cancha.getLATITUD()
				+ "', '"
				+ cancha.getDIRECCION()
				+ "', '"
				+ cancha.getNOMBRE_USUARIO()
				+ "', '"
				+ cancha.getFECHA_CREACION()
				+ "', '"
				+ cancha.getFECHA_ACTUALIZACION()
				+ "', '"
				+ cancha.getESTADO()
				+ "', '" + cancha.getTABLA() + "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertCanchaAdeful", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertCanchaAdeful", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de torneo adeful.
     *//*
	public ArrayList<Cancha> selectListaCanchaAdeful() {

		String sql = "SELECT * FROM CANCHA_ADEFUL";
		ArrayList<Cancha> arrayCancha = new ArrayList<Cancha>();
		String nombre = null, longitud = null, latitud = null, direccion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, estado = null, tabla = null;
		int id;
		Cursor cursor = null;

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
						fechaActualizacion = cursor.getString(cursor
								.getColumnIndex("FECHA_ACTUALIZACION"));
						estado = cursor.getString(cursor
								.getColumnIndex("ESTADO"));
						tabla = cursor
								.getString(cursor.getColumnIndex("TABLA"));

						cancha = new Cancha(id, nombre, longitud, latitud,
								direccion, usuario, fechaCreacion,
								fechaActualizacion, estado, tabla);

						arrayCancha.add(cancha);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaCanchaAdeful", e.toString());
			}
		} else {

			Log.e("selectListaCanchaAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		nombre = null;
		usuario = null;
		fechaCreacion = null;
		fechaActualizacion = null;
		estado = null;
		tabla = null;

		return arrayCancha;
	}

	public boolean actualizarCanchaAdeful(Cancha cancha) {

		boolean res = false;
		String sql = "UPDATE CANCHA_ADEFUL SET NOMBRE='" + cancha.getNOMBRE()
				+ "', LONGITUD='" + cancha.getLONGITUD() + "', LATITUD='"
				+ cancha.getLATITUD() + "', DIRECCION='"
				+ cancha.getDIRECCION() + "', USUARIO_CREADOR='"
				+ cancha.getNOMBRE_USUARIO() + "', FECHA_ACTUALIZACION='"
				+ cancha.getFECHA_ACTUALIZACION() + "', ESTADO='"
				+ cancha.getESTADO() + "', TABLA = '" + cancha.getTABLA()
				+ "' WHERE ID_CANCHA ='" + cancha.getID_CANCHA() + "'";

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("actualizarCanchaAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("actualizarCanchaAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	public boolean eliminarCanchaAdeful(int id) {

		boolean res = false;
		String sql = "DELETE FROM CANCHA_ADEFUL WHERE ID_CANCHA = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarCanchaAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarCanchaAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	// ////////////////////////////////////////MI
	// EQUIPO//////////////////////////////////////

	*//**
     * Metodo que inserta los datos de las fechas en la base de datos.
     *
     *//*

	public boolean insertFecha(Fecha fecha) {
		boolean ban = false;

		String sql = "INSERT INTO FECHA ( FECHA) VALUES ('" + fecha.getFECHA()
				+ "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertFecha", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertFecha", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de fecha.
     *//*
	public ArrayList<Fecha> selectListaFecha() {

		String sql = "SELECT * FROM FECHA";
		ArrayList<Fecha> arrayFecha = new ArrayList<Fecha>();
		String fechaa = null;
		int id;
		Cursor cursor = null;

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
				Log.e("selectListaFecha", e.toString());
			}
		} else {

			Log.e("selectListaFecha", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		fechaa = null;
		return arrayFecha;
	}

	*//**
     * Metodo que inserta los datos de los anio en la base de datos.
     *
     *//*

	public boolean insertAnio(Anio anio) {
		boolean ban = false;

		String sql = "INSERT INTO ANIO ( ANIO) VALUES ('" + anio.getANIO()
				+ "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertAnio", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertAnio", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de fecha.
     *//*
	public ArrayList<Anio> selectListaAnio() {

		String sql = "SELECT * FROM ANIO";
		ArrayList<Anio> arrayAnio = new ArrayList<Anio>();
		String anioo = null;
		int id;
		Cursor cursor = null;

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
				Log.e("selectListaAnio", e.toString());
			}
		} else {

			Log.e("selectListaAnio", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		anioo = null;
		return arrayAnio;
	}

	*//**
     * Metodo que inserta los datos de los mes en la base de datos.
     *
     *//*

	public boolean insertMes(Mes mes) {
		boolean ban = false;

		String sql = "INSERT INTO MES ( MES) VALUES ('" + mes.getMES()
				+ "')";

		if (database != null && database.isOpen()) {
			try {

				database.execSQL(sql);
				ban = true;

			} catch (Exception e) {

				Log.e("insertMes", e.toString());
				ban = false;
			}
		} else {
			Log.e("insertMes", "Error Conexi�n Base de Datos");
			ban = false;
		}

		sql = null;
		database = null;
		return ban;
	}

	*//**
     *
     * Metodo que obtiene lista de fecha.
     *//*
	public ArrayList<Mes> selectListaMes() {

		String sql = "SELECT * FROM MES";
		ArrayList<Mes> arrayMes = new ArrayList<Mes>();
		String mess = null;
		int id;
		Cursor cursor = null;

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
				Log.e("selectListaMes", e.toString());
			}
		} else {

			Log.e("selectListaMes", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		mess = null;
		return arrayMes;
	}
	
	
	*//**
     * Insertar Fixture 22/11/2015
     *//*
	public boolean insertFixtureAdeful(Fixture fixture) throws SQLiteException {

		ContentValues cv = new ContentValues();
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
			cv.put("USUARIO_CREADOR", fixture.getNOMBRE_USUARIO());
			cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
			cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());
			cv.put("ESTADO", fixture.getESTADO());
			cv.put("TABLA", fixture.getTABLA());

			long valor = database.insert("FIXTURE_ADEFUL", null, cv);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     * Actualizar Fixture 22/11/2015
     *//*

	public boolean actualizarFixtureAdeful(Fixture fixture)
			throws SQLiteException {

		ContentValues cv = new ContentValues();
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
			cv.put("USUARIO_CREADOR", fixture.getNOMBRE_USUARIO());
			cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
			cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());
			cv.put("ESTADO", fixture.getESTADO());
			cv.put("TABLA", fixture.getTABLA());

			long valor = database.update("FIXTURE_ADEFUL", cv, "ID_FIXTURE="
					+ fixture.getID_FIXTURE(), null);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     * Lista Fixture 22/11/2015
     *//*

	public ArrayList<FixtureRecycler> selectListaFixtureAdeful(int division,
			int torneo, int fecha, int anio) {

		String sql = "SELECT F.ID_FIXTURE AS ID,F.ID_EQUIPO_LOCAL AS ID_LOCAL, LOCALE.NOMBRE AS LOCAL,LOCALE.ESCUDO AS ESCUDOLOCAL, F.ID_EQUIPO_VISITA AS ID_VISITA,VISITAE.NOMBRE AS VISITA,VISITAE.ESCUDO AS ESCUDOVISITA, C.ID_CANCHA AS ID_CANCHA,C.NOMBRE AS CANCHA, DIA, HORA "
				+ "FROM FIXTURE_ADEFUL F INNER JOIN EQUIPO_ADEFUL LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
				+ "INNER JOIN EQUIPO_ADEFUL VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
				+ "INNER JOIN CANCHA_ADEFUL C ON C.ID_CANCHA = F.ID_CANCHA "
				+ "WHERE ID_DIVISION="
				+ division
				+ " AND ID_TORNEO="
				+ torneo
				+ " AND ID_FECHA=" + fecha + " AND ID_ANIO=" + anio + "";

		ArrayList<FixtureRecycler> arrayFixture = new ArrayList<FixtureRecycler>();
		String dia = null, hora = null, e_local = null, e_visita = null, cancha = null;
		int id_fixture, id_equipo_local, id_equipo_visita, id_cancha;
		byte[] escudolocal, escudovisita;

		Cursor cursor = null;

		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						FixtureRecycler fixtureRecycler = null;
						id_fixture = cursor.getInt(cursor.getColumnIndex("ID"));
						id_equipo_local = cursor.getInt(cursor
								.getColumnIndex("ID_LOCAL"));
						e_local = cursor.getString(cursor
								.getColumnIndex("LOCAL"));

						escudolocal = cursor.getBlob(cursor
								.getColumnIndex("ESCUDOLOCAL"));
						id_equipo_visita = cursor.getInt(cursor
								.getColumnIndex("ID_VISITA"));
						e_visita = cursor.getString(cursor
								.getColumnIndex("VISITA"));
						escudovisita = cursor.getBlob(cursor
								.getColumnIndex("ESCUDOVISITA"));
						id_cancha = cursor.getInt(cursor
								.getColumnIndex("ID_CANCHA"));
						cancha = cursor.getString(cursor
								.getColumnIndex("CANCHA"));
						dia = cursor.getString(cursor.getColumnIndex("DIA"));
						hora = cursor.getString(cursor.getColumnIndex("HORA"));

						fixtureRecycler = new FixtureRecycler(id_fixture,
								id_equipo_local, e_local, escudolocal,
								id_equipo_visita, e_visita, escudovisita,
								id_cancha, cancha, dia, hora);

						arrayFixture.add(fixtureRecycler);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaFixtureAdeful", e.toString());
			}
		} else {

			Log.e("selectListaFixtureAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		dia = null;
		hora = null;
		e_local = null;
		e_visita = null;

		return arrayFixture;
	}

	public boolean eliminarFixtureAdeful(int id) {

		boolean res = false;
		String sql = "DELETE FROM FIXTURE_ADEFUL WHERE ID_FIXTURE = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarFixtureAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarFixtureAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	// ///////////////////////////////////RESULTADOS////////////////////////////////////////////

	*//**
     * Insertar Fixture 22/11/2015
     *//*
	// public boolean insertResultadoAdeful(Resultado resultado)
	// throws SQLiteException {
	//
	//
	// ContentValues cv = new ContentValues();
	// cv.put("ID_FIXTURE",resultado.getID_FIXTURE());
	// cv.put("RESULTADO_LOCAL", resultado.getRESULTADO_LOCAL());
	// cv.put("RESULTADO_VISITA", resultado.getRESULTADO_VISITA());
	//
	//
	//
	// database.insert("FIXTURE_ADEFUL", null, cv);
	// return true;
	// }
	*//**
     * Actualizar Fixture 22/11/2015
     *//*

	public boolean actualizarResultadoAdeful(int id_fixture,
			String resultado_local, String resultado_visita)
			throws SQLiteException {

		ContentValues cv = new ContentValues();

		try {
			cv.put("RESULTADO_LOCAL", resultado_local);
			cv.put("RESULTADO_VISITA", resultado_visita);

			long valor = database.update("FIXTURE_ADEFUL", cv, "ID_FIXTURE="
					+ id_fixture, null);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     * Lista de resultados
     *//*

	public ArrayList<ResultadoRecycler> selectListaResultadoAdeful(
			int division, int torneo, int fecha, int anio) {

		String sql = "SELECT F.ID_FIXTURE AS ID,F.ID_EQUIPO_LOCAL AS ID_LOCAL, LOCALE.NOMBRE AS LOCAL,LOCALE.ESCUDO AS ESCUDOLOCAL, "
				+ "F.ID_EQUIPO_VISITA AS ID_VISITA, VISITAE.NOMBRE AS VISITA, VISITAE.ESCUDO AS ESCUDOVISITA, C.ID_CANCHA AS ID_CANCHA, "
				+ "C.NOMBRE AS CANCHA, DIA, HORA, F.RESULTADO_LOCAL AS RESULTADO_LOCAL, F.RESULTADO_VISITA AS RESULTADO_VISITA "
				+ "FROM FIXTURE_ADEFUL F INNER JOIN EQUIPO_ADEFUL LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
				+ "INNER JOIN EQUIPO_ADEFUL VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
				+ "INNER JOIN CANCHA_ADEFUL C ON C.ID_CANCHA = F.ID_CANCHA "
				+ "WHERE ID_DIVISION="
				+ division
				+ " AND ID_TORNEO="
				+ torneo
				+ " AND ID_FECHA=" + fecha + " AND ID_ANIO=" + anio + "";

		ArrayList<ResultadoRecycler> arrayResultado = new ArrayList<ResultadoRecycler>();
		// String dia = null, hora = null, e_local = null, e_visita = null ,
		// cancha = null;
		String e_local = null, e_visita = null, resultado_local = null, resultado_visita = null;
		// int id_fixture,id_equipo_local,id_equipo_visita,id_cancha;
		int id_fixture, id_equipo_local, id_equipo_visita;
		byte[] escudolocal, escudovisita;

		Cursor cursor = null;

		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						ResultadoRecycler resultadoRecycler = null;
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

						resultadoRecycler = new ResultadoRecycler(id_fixture,
								id_equipo_local, e_local, escudolocal,
								resultado_local, id_equipo_visita, e_visita,
								escudovisita, resultado_visita);

						arrayResultado.add(resultadoRecycler);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaFixtureAdeful", e.toString());
			}
		} else {

			Log.e("selectListaFixtureAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		// dia = null;
		// hora = null;
		e_local = null;
		e_visita = null;

		return arrayResultado;
	}

	// ///////////////////////////////////JUGADORES////////////////////////////////////////////

	*//**
     * Insertar Jugador 22/11/2015
     *//*
	public boolean insertJugadorAdeful(Jugador jugador) throws SQLiteException {

		ContentValues cv = new ContentValues();
		try {
			cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
			cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
			cv.put("ID_DIVISION", jugador.getID_DIVISION());
			cv.put("ID_POSICION", jugador.getID_POSICION());

			long valor = database.insert("JUGADOR_ADEFUL", null, cv);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     *
     * Metodo que obtiene lista de jugador adeful.
     *//*
	public ArrayList<JugadorRecycler> selectListaJugadorAdeful(int division) {

		String sql = "SELECT J.ID_JUGADOR AS ID_JUGADOR, J.NOMBRE_JUGADOR AS NOMBRE_JUGADOR, J.FOTO_JUGADOR AS FOTO_JUGADOR, J.ID_DIVISION AS ID_DIVISION, "
				+ "D.DESCRIPCION AS DESCRIPCION_DIVISION, J.ID_POSICION AS ID_POSICION, P.DESCRIPCION AS DESCRIPCION_POSICION "
				+ "FROM JUGADOR_ADEFUL J , DIVISION_ADEFUL D, POSICION_ADEFUL P "
				+ "WHERE J.ID_DIVISION=" + division;
		ArrayList<JugadorRecycler> arrayJugador = new ArrayList<JugadorRecycler>();
		String nombre = null, descripcion_division = null, descripcion_posicion = null;
		byte[] foto = null;
		int id;
		int id_division;
		int id_posicion;
		Cursor cursor = null;

		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						JugadorRecycler jugador = null;

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

						jugador = new JugadorRecycler(id, nombre, foto,
								id_division, descripcion_division, id_posicion,
								descripcion_posicion);
						arrayJugador.add(jugador);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaPosicionAdeful", e.toString());
			}
		} else {

			Log.e("selectListaPosicionAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		nombre = null;

		return arrayJugador;
	}

	*//**
     * Actualizar Jugador 22/11/2015
     *//*

	public boolean actualizarJugadorAdeful(Jugador jugador)
			throws SQLiteException {

		ContentValues cv = new ContentValues();
		try {
			cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
			cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
			cv.put("ID_DIVISION", jugador.getID_DIVISION());
			cv.put("ID_POSICION", jugador.getID_POSICION());

			long valor = database.update("JUGADOR_ADEFUL", cv, "ID_JUGADOR="
					+ jugador.getID_JUGADOR(), null);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     * Eliminar Jugador
     *
     *//*
	public boolean eliminarJugadorAdeful(int id) {

		boolean res = false;
		String sql = "DELETE FROM JUGADOR_ADEFUL WHERE ID_JUGADOR = " + id;

		if (database != null && database.isOpen()) {

			try {

				database.execSQL(sql);
				res = true;

			} catch (Exception e) {

				res = false;
				Log.e("eliminarJugadorAdeful", e.toString());
			}

		} else {

			res = false;
			Log.e("eliminarJugadorAdeful", "Error Conexi�n Base de Datos");
		}

		database = null;
		sql = null;
		return res;
	}

	*//**
     * Insertar Posicion 22/11/2015
     *//*
	public boolean insertPosicionAdeful(Posicion posicion)
			throws SQLiteException {

		ContentValues cv = new ContentValues();
		try {
			cv.put("DESCRIPCION", posicion.getDESCRIPCION());

			long valor = database.insert("POSICION_ADEFUL", null, cv);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     * Actualizar Posicion 22/11/2015
     *//*

	public boolean actualizarPosicionAdeful(Posicion posicion)
			throws SQLiteException {

		ContentValues cv = new ContentValues();
		try {
			cv.put("DESCRIPCION", posicion.getDESCRIPCION());

			long valor = database.update("POSICION_ADEFUL", cv, "ID_POSICION="
					+ posicion.getID_POSICION(), null);
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}
	}

	*//**
     *
     * Metodo que obtiene lista de Posicion adeful.
     *//*
	public ArrayList<Posicion> selectListaPosicionAdeful() {

		String sql = "SELECT * FROM POSICION_ADEFUL";
		ArrayList<Posicion> arrayPosicion = new ArrayList<Posicion>();
		String descripcion = null;
		int id;
		Cursor cursor = null;

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
						posicion = new Posicion(id, descripcion);
						arrayPosicion.add(posicion);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaPuestoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaPuestoAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		descripcion = null;

		return arrayPosicion;
	}

	*//**
     * Insertar Entrenamiento 30/11/2015
     *//*
	public int insertEntrenamientoAdeful(Entrenamiento entrenamiento)
			throws SQLiteException {
int id_entrenamiento = 0;
		ContentValues cv = new ContentValues();
		try {
			cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
			cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
			cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
			
			long valor = database.insert("ENTRENAMIENTO_ADEFUL", null, cv);
			if (valor > 0) {
				return id_entrenamiento = (int)valor;
			} else {
				return id_entrenamiento;
			}
		} catch (SQLiteException e) {

			return id_entrenamiento;
		}
	}

	*//**
     * Insertar Entrenamiento 30/11/2015
     *//*
	public boolean insertEntrenamiento_DivisionAdeful(
			Entrenamiento_Division entrenamientoXDivision)
			throws SQLiteException {

		ContentValues cv = new ContentValues();

		try {
			cv.put("ID_ENTRENAMIENTO_DIVISION",
					entrenamientoXDivision.getID_ENTRENAMIENTO_DIVISION());
			cv.put("ID_DIVISION", entrenamientoXDivision.getID_DIVISION());
			long valor = database.insert("ENTRENAMIENTO_DIVISION_ADEFUL", null,
					cv);
					
			if (valor > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e) {

			return false;
		}

	}

	*//**
     *
     * Metodo que obtiene lista de Entrenamientoxdivision adeful.
     *//*
	public int selectIdEntrenamiento_Division() {

		String sql = "SELECT ID_ENTRENAMIENTO_DIVISION FROM ENTRENAMIENTO_DIVISION_ADEFUL ORDER BY ID_ENTRENAMIENTO_DIVISION DESC limit 1";
		int id = 0;
		Cursor cursor = null;

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
				Log.e("selectListaPuestoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaPuestoAdeful", "Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;

		return id;
	}

	*//**
     *
     * Metodo que obtiene lista de division adeful.
     *//*
	public ArrayList<Entrenamiento_Division> selectListaDivisionEntrenamientoAdeful() {

		String sql = "SELECT * FROM DIVISION_ADEFUL";
		ArrayList<Entrenamiento_Division> arrayDivision = new ArrayList<Entrenamiento_Division>();
		int id, id_division;
		String descripcion = null;
		Cursor cursor = null;

		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Entrenamiento_Division entrenamientoXDivision = null;
						id = 0;
						id_division = cursor.getInt(cursor
								.getColumnIndex("ID_DIVISION"));
						descripcion = cursor.getString(cursor
								.getColumnIndex("DESCRIPCION"));

						entrenamientoXDivision = new Entrenamiento_Division(id,
								id, id_division, descripcion, false);

						arrayDivision.add(entrenamientoXDivision);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaDivisionEntrenamientoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaDivisionEntrenamientoAdeful",
					"Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		descripcion = null;
		return arrayDivision;
	}

	
	*//**
     *
     * Metodo que obtiene lista de division adeful.
     *//*
	public ArrayList<Entrenamiento_Division> selectListaEditarDivisionEntrenamientoAdeful() {

		String sql = "SELECT * FROM DIVISION_ADEFUL";
		ArrayList<Entrenamiento_Division> arrayDivision = new ArrayList<Entrenamiento_Division>();
		int id, id_division;
		String descripcion = null;
		Cursor cursor = null;

		if (database != null && database.isOpen()) {

			try {
				cursor = database.rawQuery(sql, null);
				if (cursor != null && cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						Entrenamiento_Division entrenamientoXDivision = null;
						id = 0;
						id_division = cursor.getInt(cursor
								.getColumnIndex("ID_DIVISION"));
						descripcion = cursor.getString(cursor
								.getColumnIndex("DESCRIPCION"));

						entrenamientoXDivision = new Entrenamiento_Division(id,
								id, id_division, descripcion, false);

						arrayDivision.add(entrenamientoXDivision);

					}
				}

			} catch (Exception e) {
				Log.e("selectListaDivisionEntrenamientoAdeful", e.toString());
			}
		} else {

			Log.e("selectListaDivisionEntrenamientoAdeful",
					"Error Conexi�n Base de Datos");
		}

		sql = null;
		cursor = null;
		database = null;
		descripcion = null;
		return arrayDivision;
	}
	
	
	
	//
	// if (c != null && c.moveToFirst()) {
	// lastId = c.getLong(0); //The 0 is the column index, we only have 1
	// column, so the index is 0
	// }
	//

}*/

}