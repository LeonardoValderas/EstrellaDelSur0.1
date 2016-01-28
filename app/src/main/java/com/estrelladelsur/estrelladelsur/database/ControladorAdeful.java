package com.estrelladelsur.estrelladelsur.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento_Division;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.FixtureRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.JugadorRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.entidad.ResultadoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

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
                arrayArticuloAdeful = null;
            }
        } else {

            arrayArticuloAdeful = null;
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
                arrayCargoAdeful = null;
            }
        } else {
            arrayCargoAdeful = null;
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
                        comision = new Comision(id, nombre, foto, id_cargo, null, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayComisionAdeful.add(comision);
                    }
                }
            } catch (Exception e) {
                arrayComisionAdeful = null;
            }
        } else {

            arrayComisionAdeful = null;
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
                        comision = new Comision(id, nombre, foto, id_cargo, cargo, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY COMISION
                        arrayComisionAdeful.add(comision);
                    }
                }
            } catch (Exception e) {
                arrayComisionAdeful = null;
            }
        } else {
            arrayComisionAdeful = null;
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

    //DIRECCION POR ID
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
                        direccion = new Direccion(id, nombre, foto, id_cargo, null, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayDireccionAdeful.add(direccion);
                    }
                }
            } catch (Exception e) {
                arrayDireccionAdeful = null;
            }
        } else {
            arrayDireccionAdeful = null;
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
                        direccion = new Direccion(id, nombre, foto, id_cargo, cargo, desde, hasta, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY COMISION
                        arrayDireccionAdeful.add(direccion);
                    }
                }
            } catch (Exception e) {
                arrayDireccionAdeful = null;
            }
        } else {
            arrayDireccionAdeful = null;
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
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
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
            }

        } else {

            res = false;
        }

        database = null;
        sql = null;
        return res;
    }

    ////////LIGA ADEFUL/////////
    //EQUIPO INSERTAR
    public boolean insertEquipoAdeful(Equipo equipoAdeful)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE", equipoAdeful.getNOMBRE_EQUIPO());
            cv.put("ESCUDO", equipoAdeful.getESCUDO());
            cv.put("USUARIO_CREADOR", equipoAdeful.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", equipoAdeful.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", equipoAdeful.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", equipoAdeful.getFECHA_ACTUALIZACION());

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

    //EQUIPO lISTA
    public ArrayList<Equipo> selectListaEquipoAdeful() {

        String sql = "SELECT * FROM EQUIPO_ADEFUL";
        ArrayList<Equipo> arrayEquipoAdeful = new ArrayList<Equipo>();
        String equipo = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
        byte[] escudo = null;
        int id;
        Cursor cursor = null;

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
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));

                        equipoAdeful = new Equipo(id, equipo, escudo, usuario,
                                fechaCreacion, usuario_act, fechaActualizacion);

                        arrayEquipoAdeful.add(equipoAdeful);

                    }
                }

            } catch (Exception e) {
                arrayEquipoAdeful = null;
            }
        } else {
            arrayEquipoAdeful = null;
        }

        sql = null;
        cursor = null;
        database = null;
        equipo = null;
        usuario = null;
        escudo = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;

        return arrayEquipoAdeful;
    }

    //EQUIPO ACTUALIZAR
    public boolean actualizarEquipoAdeful(Equipo equipo) throws SQLiteException {
        boolean ban = false;

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE", equipo.getNOMBRE_EQUIPO());
            cv.put("ESCUDO", equipo.getESCUDO());
            cv.put("USUARIO_ACTUALIZACION", equipo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", equipo.getFECHA_ACTUALIZACION());

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

    //EQUIPO ELIMINAR
    public boolean eliminarEquipoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM EQUIPO_ADEFUL WHERE ID_EQUIPO = " + id;

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
        database = null;
        sql = null;
        return res;
    }

    // DIVISION INSERTAR
    public boolean insertDivisionAdeful(Division division) {
        boolean ban = false;

        String sql = "INSERT INTO DIVISION_ADEFUL ( DESCRIPCION, USUARIO_CREADOR, FECHA_CREACION," +
                " USUARIO_ACTUALIZACION, FECHA_ACTUALIZACION) VALUES ('"
                + division.getDESCRIPCION()
                + "', '"
                + division.getUSUARIO_CREADOR()
                + "', '"
                + division.getFECHA_CREACION()
                + "', '"
                + division.getUSUARIO_ACTUALIZACION()
                + "', '"
                + division.getFECHA_ACTUALIZACION()
                + "')";

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
        sql = null;
        database = null;
        return ban;
    }

    //DIVISION LISTA
    public ArrayList<Division> selectListaDivisionAdeful() {

        String sql = "SELECT * FROM DIVISION_ADEFUL";
        ArrayList<Division> arrayDivision = new ArrayList<Division>();
        String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
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
    public boolean actualizarDivisionAdeful(Division division) {

        boolean res = false;
        String sql = "UPDATE DIVISION_ADEFUL SET DESCRIPCION='"
                + division.getDESCRIPCION() + "', USUARIO_ACTUALIZACION='"
                + division.getUSUARIO_ACTUALIZACION() + "', FECHA_ACTUALIZACION='"
                + division.getFECHA_ACTUALIZACION() +
                "' WHERE ID_DIVISION ='" + division.getID_DIVISION() + "'";

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

        database = null;
        sql = null;
        return res;
    }

    //ELIMINAR DIVISION
    public boolean eliminarDivisionAdeful(int id) {

        boolean res = false;

        String sql = "DELETE FROM DIVISION_ADEFUL WHERE ID_DIVISION = " + id;

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

        database = null;
        sql = null;
        return res;
    }

    // INSERTAR TORNEO
    public boolean insertTorneoAdeful(Torneo torneo) {
        boolean ban = false;

        String sql = "INSERT INTO TORNEO_ADEFUL ( DESCRIPCION, USUARIO_CREADOR, USUARIO_ACTUALIZACION, FECHA_CREACION, FECHA_ACTUALIZACION) VALUES ('"
                + torneo.getDESCRIPCION()
                + "', '"
                + torneo.getUSUARIO_CREADOR()
                + "', '"
                + torneo.getFECHA_CREACION()
                + "', '"
                + torneo.getUSUARIO_ACTUALIZACION()
                + "', '"
                + torneo.getFECHA_ACTUALIZACION()
                + "')";

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

        sql = null;
        database = null;
        return ban;
    }

    //LISTA TORNEO
    public ArrayList<Torneo> selectListaTorneoAdeful() {

        String sql = "SELECT * FROM TORNEO_ADEFUL";
        ArrayList<Torneo> arrayTorneo = new ArrayList<Torneo>();
        String descripcion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null, tabla = null;
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
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));

                        torneo = new Torneo(id, descripcion, usuario,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        arrayTorneo.add(torneo);
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
    public boolean actualizarTorneoAdeful(Torneo torneo) {

        boolean res = false;

        String sql = "UPDATE TORNEO_ADEFUL SET DESCRIPCION='"
                + torneo.getDESCRIPCION() + "', USUARIO_ACTUALIZACION='"
                + torneo.getUSUARIO_ACTUALIZACION() + "', FECHA_ACTUALIZACION='"
                + torneo.getFECHA_ACTUALIZACION()
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

    //ELIMINAR TORNEO
    public boolean eliminarTorneoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM TORNEO_ADEFUL WHERE ID_TORNEO = " + id;

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

        database = null;
        sql = null;
        return res;
    }

    // INSERTAR CANCHA
    public boolean insertCanchaAdeful(Cancha cancha) {
        boolean ban = false;

        String sql = "INSERT INTO CANCHA_ADEFUL ( NOMBRE, LONGITUD, LATITUD, DIRECCION, USUARIO_CREADOR, FECHA_CREACION, USUARIO_ACTUALIZACION, FECHA_ACTUALIZACION) VALUES ('"
                + cancha.getNOMBRE()
                + "', '"
                + cancha.getLONGITUD()
                + "', '"
                + cancha.getLATITUD()
                + "', '"
                + cancha.getDIRECCION()
                + "', '"
                + cancha.getUSUARIO_CREADOR()
                + "', '"
                + cancha.getFECHA_CREACION()
                + "', '"
                + cancha.getUSUARIO_ACTUALIZACION()
                + "', '"
                + cancha.getFECHA_ACTUALIZACION()
                + "')";

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

        sql = null;
        database = null;
        return ban;
    }

    //LISTA CANCHA
    public ArrayList<Cancha> selectListaCanchaAdeful() {

        String sql = "SELECT * FROM CANCHA_ADEFUL";
        ArrayList<Cancha> arrayCancha = new ArrayList<Cancha>();
        String nombre = null, longitud = null, latitud = null, direccion = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
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
    public boolean actualizarCanchaAdeful(Cancha cancha) {

        boolean res = false;
        String sql = "UPDATE CANCHA_ADEFUL SET NOMBRE='" + cancha.getNOMBRE()
                + "', LONGITUD='" + cancha.getLONGITUD() + "', LATITUD='"
                + cancha.getLATITUD() + "', DIRECCION='"
                + cancha.getDIRECCION() + "', USUARIO_ACTUALIZACION='"
                + cancha.getUSUARIO_ACTUALIZACION() + "', FECHA_ACTUALIZACION='"
                + cancha.getFECHA_ACTUALIZACION()
                + "' WHERE ID_CANCHA ='" + cancha.getID_CANCHA() + "'";

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

        database = null;
        sql = null;
        return res;
    }

    // ELIMINAR CANCHA
    public boolean eliminarCanchaAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM CANCHA_ADEFUL WHERE ID_CANCHA = " + id;

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
        sql = null;
        database = null;
        return ban;
    }

    // LISTA FECHA
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
                arrayFecha = null;
            }
        } else {
            arrayFecha = null;
        }
        sql = null;
        cursor = null;
        database = null;
        fechaa = null;
        return arrayFecha;
    }

    // INSERTA ANIO
    public boolean insertAnio(Anio anio) {
        boolean ban = false;

        String sql = "INSERT INTO ANIO ( ANIO) VALUES ('" + anio.getANIO()
                + "')";
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

        sql = null;
        database = null;
        return ban;
    }

    //LISTA MES
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
                arrayMes = null;
            }
        } else {
            arrayMes = null;
        }

        sql = null;
        cursor = null;
        database = null;
        mess = null;
        return arrayMes;
    }

    // INSERTAR FIXTURE
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
            cv.put("USUARIO_CREADOR", fixture.getUSUARIO_CREACION());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

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

    //ACTUALIZAR FIXTURE
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
            cv.put("USUARIO_CREADOR", fixture.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

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


    //LISTA FIXTURE RECYCLER
    public ArrayList<FixtureRecycler> selectListaFixtureAdeful(int division,
                                                               int torneo, int fecha, int anio) {

        String sql = "SELECT F.ID_FIXTURE AS ID,F.ID_EQUIPO_LOCAL AS ID_LOCAL, LOCALE.NOMBRE AS LOCAL,LOCALE.ESCUDO AS ESCUDOLOCAL,F.RESULTADO_LOCAL AS RESULTADOLOCAL, "
                + "F.ID_EQUIPO_VISITA AS ID_VISITA, VISITAE.NOMBRE AS VISITA, VISITAE.ESCUDO AS ESCUDOVISITA, F.RESULTADO_VISITA AS RESULTADOVISITA, "
                + "C.ID_CANCHA AS ID_CANCHA, C.NOMBRE AS CANCHA, DIA, HORA "
                + "FROM FIXTURE_ADEFUL F INNER JOIN EQUIPO_ADEFUL LOCALE ON LOCALE.ID_EQUIPO = F.ID_EQUIPO_LOCAL "
                + "INNER JOIN EQUIPO_ADEFUL VISITAE ON  VISITAE.ID_EQUIPO =  F.ID_EQUIPO_VISITA "
                + "INNER JOIN CANCHA_ADEFUL C ON C.ID_CANCHA = F.ID_CANCHA "
                + "WHERE ID_DIVISION="
                + division
                + " AND ID_TORNEO="
                + torneo
                + " AND ID_FECHA=" + fecha + " AND ID_ANIO=" + anio + "";

        ArrayList<FixtureRecycler> arrayFixture = new ArrayList<FixtureRecycler>();
        String dia = null, hora = null, e_local = null, e_visita = null, cancha = null, r_local = null, r_visita = null;
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

                        fixtureRecycler = new FixtureRecycler(id_fixture,
                                id_equipo_local, e_local, escudolocal, r_local,
                                id_equipo_visita, e_visita, escudovisita, r_visita,
                                id_cancha, cancha, dia, hora);

                        arrayFixture.add(fixtureRecycler);
                    }
                }
            } catch (Exception e) {
                arrayFixture = null;
            }
        } else {
            arrayFixture = null;
        }

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

    // ELIMINAR FIXTURE
    public boolean eliminarFixtureAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM FIXTURE_ADEFUL WHERE ID_FIXTURE = " + id;

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

        database = null;
        sql = null;
        return res;
    }

    ////RESULTADOS////
    //ACTUALIZAR RESULTADO/FIXTURE
    public boolean actualizarResultadoAdeful(int id_fixture,
                                             String resultado_local,
                                             String resultado_visita,
                                             String usuario_act,
                                             String fecha_act) throws SQLiteException {

        ContentValues cv = new ContentValues();

        try {
            cv.put("RESULTADO_LOCAL", resultado_local);
            cv.put("RESULTADO_VISITA", resultado_visita);
            cv.put("USUARIO_ACTUALIZACION", usuario_act);
            cv.put("FECHA_ACTUALIZACION", fecha_act);

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

    //LISTA RECYCLER
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
        String e_local = null, e_visita = null, resultado_local = null, resultado_visita = null;
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
                arrayResultado = null;
            }
        } else {
            arrayResultado = null;
        }
        sql = null;
        cursor = null;
        database = null;
        e_local = null;
        e_visita = null;

        return arrayResultado;
    }

    // ///////////////////////////////////JUGADORES////////////////////////////////////////////

    // INSERTAR JUGADOR
    public boolean insertJugadorAdeful(Jugador jugador) throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("ID_POSICION", jugador.getID_POSICION());
            cv.put("USUARIO_CREADOR", jugador.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", jugador.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", jugador.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", jugador.getFECHA_ACTUALIZACION());

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

    // LISTA JUGADOR
    public ArrayList<JugadorRecycler> selectListaJugadorAdeful(int division) {

        String sql = "SELECT J.ID_JUGADOR AS ID_JUGADOR, J.NOMBRE_JUGADOR AS NOMBRE_JUGADOR, J.FOTO_JUGADOR AS FOTO_JUGADOR,"
                + " J.ID_DIVISION AS ID_DIVISION, D.DESCRIPCION AS DESCRIPCION_DIVISION,"
                + " J.ID_POSICION AS ID_POSICION, P.DESCRIPCION AS DESCRIPCION_POSICION"
                + " FROM JUGADOR_ADEFUL J  INNER JOIN  DIVISION_ADEFUL D ON J.ID_DIVISION = D.ID_DIVISION"
                + " INNER JOIN POSICION_ADEFUL P ON P.ID_POSICION = J.ID_POSICION"
                + " WHERE J.ID_DIVISION=" + division;

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
                arrayJugador = null;
            }
        } else {
            arrayJugador = null;
        }

        sql = null;
        cursor = null;
        database = null;
        nombre = null;

        return arrayJugador;
    }

    //ACTUALIZAR JUGADOR
    public boolean actualizarJugadorAdeful(Jugador jugador)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("NOMBRE_JUGADOR", jugador.getNOMBRE_JUGADOR());
            cv.put("FOTO_JUGADOR", jugador.getFOTO_JUGADOR());
            cv.put("ID_DIVISION", jugador.getID_DIVISION());
            cv.put("ID_POSICION", jugador.getID_POSICION());
            cv.put("USUARIO_ACTUALIZACION", jugador.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", jugador.getFECHA_ACTUALIZACION());

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

    //ELIMINAR JUGADOR
    public boolean eliminarJugadorAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM JUGADOR_ADEFUL WHERE ID_JUGADOR = " + id;

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

        database = null;
        sql = null;
        return res;
    }

    //POSICION
    //INSERTAR
    public boolean insertPosicionAdeful(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", posicion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", posicion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

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

    //ACTUALIZAR
    public boolean actualizarPosicionAdeful(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        try {
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

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

    //LISTA POSICION
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

        sql = null;
        cursor = null;
        database = null;
        descripcion = null;

        return arrayPosicion;
    }

    ////ENTRENAMIENTO//////
//INSERTAR
    public int insertEntrenamientoAdeful(Entrenamiento entrenamiento)
            throws SQLiteException {
        int id_entrenamiento = 0;
        ContentValues cv = new ContentValues();
        try {
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
            cv.put("USUARIO_CREADOR", entrenamiento.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", entrenamiento.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", entrenamiento.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", entrenamiento.getFECHA_ACTUALIZACION());

            long valor = database.insert("ENTRENAMIENTO_ADEFUL", null, cv);
            if (valor > 0) {
                return id_entrenamiento = (int) valor;
            } else {
                return id_entrenamiento;
            }
        } catch (SQLiteException e) {

            return id_entrenamiento;
        }
    }

    // INSERT TABLA INTERMEDIA
    public boolean insertEntrenamientoDivisionAdeful(Entrenamiento_Division entrenamiento_Division)
            throws SQLiteException {

        ContentValues cv = new ContentValues();

        try {
            cv.put("ID_ENTRENAMIENTO",
                    entrenamiento_Division.getID_ENTRENAMIENTO());
            cv.put("ID_DIVISION", entrenamiento_Division.getID_DIVISION());

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

    //SELECT ID TABLA INTERMEDIA
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
                id = 0;
            }
        } else {
            id = 0;
        }
        sql = null;
        cursor = null;
        database = null;

        return id;
    }

    //LISTA ENTRENAMIENTO sin division
    public ArrayList<EntrenamientoRecycler> selectListaEntrenamientoAdeful(String fecha) {

        String sql = "SELECT EA.ID_ENTRENAMIENTO, EA.DIA_ENTRENAMIENTO, EA.HORA_ENTRENAMIENTO, EA.ID_CANCHA, CA.NOMBRE"
                + " FROM ENTRENAMIENTO_ADEFUL EA INNER JOIN CANCHA_ADEFUL CA ON EA.ID_CANCHA = CA.ID_CANCHA"
                + " WHERE substr(EA.DIA_ENTRENAMIENTO , 4, 2) = '" + fecha + "'";
        //   + " WHERE substr(EA.DIA_ENTRENAMIENTO , 3, 7) = "+fecha+"";


        ArrayList<EntrenamientoRecycler> arrayEntrenamiento = new ArrayList<EntrenamientoRecycler>();
        int id_entrenamiento, id_cancha;
        String dia = null, hora = null, nombre = null;
        Cursor cursor = null;

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
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));

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

        sql = null;
        cursor = null;
        database = null;
        dia = null;
        hora = null;
        nombre = null;
        return arrayEntrenamiento;
    }

    //LISTA Division por Id
    public ArrayList<Entrenamiento_Division> selectListaDivisionEntrenamientoAdefulId(int id_entrenamiento) {

        String sql = "SELECT ED.ID_DIVISION, D.DESCRIPCION"
                + " FROM ENTRENAMIENTO_DIVISION_ADEFUL ED INNER JOIN DIVISION_ADEFUL D ON"
                + " ED.ID_DIVISION = D.ID_DIVISION"
                + " WHERE ID_ENTRENAMIENTO = " + id_entrenamiento + "";

        Cursor cursor = null;
        ArrayList<Entrenamiento_Division> arrayDivision = new ArrayList<Entrenamiento_Division>();
        int id, id_division;
        String descripcion = null;
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Entrenamiento_Division entrenamiento_division = null;
                        id = 0;
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));

                        entrenamiento_division = new Entrenamiento_Division(id,
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

        sql = null;
        cursor = null;
        database = null;
        return arrayDivision;
    }

    //lISTA ENTRENAMIENTO division
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

                        Entrenamiento_Division entrenamiento_division = null;
                        id = 0;
                        id_division = cursor.getInt(cursor
                                .getColumnIndex("ID_DIVISION"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));

                        entrenamiento_division = new Entrenamiento_Division(id,
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
        sql = null;
        cursor = null;
        database = null;
        descripcion = null;
        return arrayDivision;
    }

}