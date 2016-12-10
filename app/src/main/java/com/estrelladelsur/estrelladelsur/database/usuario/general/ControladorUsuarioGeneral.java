package com.estrelladelsur.estrelladelsur.database.usuario.general;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Tabla;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarioGeneral {

    private SQLiteDBConnectionUsuarioGeneral sqLiteDBConnectionGeneral;
    private Context ourcontext;
    private SQLiteDatabase database;

    public ControladorUsuarioGeneral(Context c) {
        ourcontext = c;

    }

    public ControladorUsuarioGeneral abrirBaseDeDatos() throws SQLException {
        sqLiteDBConnectionGeneral = new SQLiteDBConnectionUsuarioGeneral(ourcontext,
                "BaseDeDatosUsuarioGeneral.db", null, 1);
        database = sqLiteDBConnectionGeneral.getWritableDatabase();
        return this;
    }

    public void cerrarBaseDeDatos() {
        sqLiteDBConnectionGeneral.close();
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

    public List<Tabla> selectListaTablaGeneral() {

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

    // INSERTAR MODULO
    public boolean insertModuloUsuario(Modulo modulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_MODULO", modulo.getID_MODULO());
            cv.put("NOMBRE", modulo.getMODULO());

            long valor = database.insert("MODULO_USUARIO", null, cv);
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

    //LISTA MODULO
    public ArrayList<Modulo> selectListaModuloUsuario() {

        String sql = "SELECT * FROM MODULO_USUARIO";
        ArrayList<Modulo> arrayModulo = new ArrayList<>();
        String nombre = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Modulo modulo = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_MODULO"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        //CLASE AUX
                        modulo = new Modulo(id, nombre);
                        //ARRAY MODULO
                        arrayModulo.add(modulo);
                    }
                }
            } catch (Exception e) {
                arrayModulo = null;
            }
        } else {
            arrayModulo = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arrayModulo;
    }

    //ELIMINAR USUARIO
    public boolean eliminarModuloUsuario() {

        boolean res = false;
        String sql = "DELETE FROM MODULO_USUARIO";
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

    // INSERTAR SUBMODULO
    public boolean insertSubModuloUsuario(SubModulo Submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_SUBMODULO", Submodulo.getID_SUBMODULO());
            cv.put("NOMBRE", Submodulo.getSUBMODULO());
            cv.put("ID_MODULO", Submodulo.getID_MODULO());

            long valor = database.insert("SUBMODULO_USUARIO", null, cv);
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

    //LISTA SUBMODULO
    public ArrayList<SubModulo> selectListaSubModulo() {

        String sql = "SELECT * FROM SUBMODULO_USUARIO";
        ArrayList<SubModulo> arraySubModulo = new ArrayList<>();
        String nombre = null;
        int id, id_sub;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        SubModulo submodulo = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_SUBMODULO"));
                        id_sub = cursor.getInt(cursor.getColumnIndex("ID_MODULO"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        //CLASE AUX
                        submodulo = new SubModulo(id, nombre, id_sub);
                        //ARRAY SUBMODULO
                        arraySubModulo.add(submodulo);
                    }
                }
            } catch (Exception e) {
                arraySubModulo = null;
            }
        } else {
            arraySubModulo = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arraySubModulo;
    }

    //ELIMINAR USUARIO
    public boolean eliminarSubmoduloUsuario() {

        boolean res = false;
        String sql = "DELETE FROM SUBMODULO_USUARIO";
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

    // INSERTAR USUARIO
    public boolean insertUsuario(Usuario usuario)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_USUARIO", usuario.getID_USUARIO());
            cv.put("USUARIO", usuario.getUSUARIO());
            cv.put("PASSWORD", usuario.getPASSWORD());

            long valor = database.insert("USUARIO_USUARIO", null, cv);
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
    public ArrayList<Usuario> selectListaUsuario() {

        String sql = "SELECT * FROM USUARIO_USUARIO ORDER BY ID_USUARIO DESC";
        ArrayList<Usuario> arrayUsuario = new ArrayList<>();
        String user = null, pass = null;
        int id;
        boolean liga;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Usuario usuario = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_USUARIO"));
                        user = cursor.getString(cursor
                                .getColumnIndex("USUARIO"));
                        pass = cursor.getString(cursor
                                .getColumnIndex("PASSWORD"));

                        //CLASE AUX
                        usuario = new Usuario(id, user, pass);
                        //ARRAY USUARIO
                        arrayUsuario.add(usuario);
                    }
                }
            } catch (Exception e) {
                arrayUsuario = null;
            }
        } else {
            arrayUsuario = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        user = null;
        pass = null;
        return arrayUsuario;
    }

    //ELIMINAR USUARIO
    public boolean eliminarUsuario() {

        boolean res = false;
        String sql = "DELETE FROM USUARIO_USUARIO";
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

    // INSERTAR ARTICULO
    public boolean insertArticuloUsuario(Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ARTICULO", articulo.getID_ARTICULO());
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());

            long valor = database.insert("ARTICULO_USUARIO", null, cv);
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

    //LISTA ARTICULO
    public ArrayList<Articulo> selectListaArticuloAdeful() {

        String sql = "SELECT * FROM ARTICULO_USUARIO";
        ArrayList<Articulo> arrayArticuloAdeful = new ArrayList<Articulo>();
        String titulo = null, articulo = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
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

                        articuloAdeful = new Articulo(id, titulo, articulo);
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
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        articulo = null;
        return arrayArticuloAdeful;
    }

    // ELIMINAR TODOS ARTICULO
    public boolean eliminarArticuloUsuario() {

        boolean res = false;
        String sql = "DELETE FROM ARTICULO_USUARIO";
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

    ////////COMISION/////////
    //INSERTAR
    public boolean insertComisionUsuario(Comision comision) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_COMISION", comision.getID_COMISION());
            cv.put("NOMBRE_COMISION", comision.getNOMBRE_COMISION());
            cv.put("FOTO_COMISION", comision.getFOTO_COMISION());
            cv.put("CARGO", comision.getCARGO());
            cv.put("PERIODO_DESDE", comision.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", comision.getPERIODO_HASTA());

            long valor = database.insert("COMISION_USUARIO", null, cv);
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


    //LISTA COMISION
    public ArrayList<Comision> selectListaComisionUsuario() {

        String sql = "SELECT * FROM COMISION_USUARIO";
        ArrayList<Comision> arrayComisionAdeful = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, cargo = null;
        int id;
        byte[] foto = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
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
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        // CLASE AUX
                        comision = new Comision(id, nombre, foto, cargo, desde, hasta);
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
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;

        return arrayComisionAdeful;
    }

    //
//    //ELIMINAR COMISION
    public boolean eliminarComisionUsuario() {

        boolean res = false;
        String sql = "DELETE FROM COMISION_USUARIO";
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


    //    ////////DIRECCION/////////
//    //INSERTAR
    public boolean insertDireccionUsuario(Direccion direccion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIRECCION", direccion.getID_DIRECCION());
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
            cv.put("CARGO", direccion.getCARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());

            long valor = database.insert("DIRECCION_USUARIO", null, cv);
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

    //    //LISTA COMISION
    public ArrayList<Direccion> selectListaDireccionUsuario() {

        String sql = "SELECT * FROM DIRECCION_USUARIO";
        ArrayList<Direccion> arrayDireccionAdeful = new ArrayList<>();
        String nombre = null, desde = null, hasta = null, cargo = null;
        int id;
        byte[] foto = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
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
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        // CLASE AUX
                        direccion = new Direccion(id, nombre, foto, cargo, desde, hasta);
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
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        desde = null;
        hasta = null;
        foto = null;

        return arrayDireccionAdeful;
    }

    //    //ELIMINAR DIRECCIOIN
//
    public boolean eliminarDireccionUsuario() {
        boolean res = false;
        String sql = "DELETE FROM DIRECCION_USUARIO";
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

    /**
     * MODULO SOCIAL
     */

    // INSERTAR NOTIFICACION
    public boolean insertNotificacionUsuario(Notificacion notificacion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_NOTIFICACION", notificacion.getID_NOTIFICACION());
            cv.put("TITULO", notificacion.getTITULO());
            cv.put("NOTIFICACION", notificacion.getNOTIFICACION());

            long valor = database.insert("NOTIFICACION_USUARIO", null, cv);
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

    //LISTA NOTIFICACION
    public ArrayList<Notificacion> selectListaNotificacionUsuario() {

        String sql = "SELECT * FROM NOTIFICACION_USUARIO ORDER BY ID_NOTIFICACION DESC";
        ArrayList<Notificacion> arrayNotificacion = new ArrayList<Notificacion>();
        String titulo = null, notifi = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Notificacion notificacion = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_NOTIFICACION"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        notifi = cursor.getString(cursor
                                .getColumnIndex("NOTIFICACION"));

                        //CLASE AUX
                        notificacion = new Notificacion(id, titulo, notifi);
                        //ARRAY ARTICULO
                        arrayNotificacion.add(notificacion);
                    }
                }

            } catch (Exception e) {
                arrayNotificacion = null;
            }
        } else {

            arrayNotificacion = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        notifi = null;
        return arrayNotificacion;
    }

    //ELIMINAR NOTIFICACION
    public boolean eliminarNotificacionUsuario() {

        boolean res = false;
        String sql = "DELETE FROM NOTIFICACION_USUARIO";
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

    // INSERTAR NOTICIA
    public boolean insertNoticiaUsuario(Noticia noticia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_NOTICIA", noticia.getID_NOTICIA());
            cv.put("TITULO", noticia.getTITULO());
            cv.put("DESCRIPCION", noticia.getDESCRIPCION());
            cv.put("LINK", noticia.getLINK());

            long valor = database.insert("NOTICIA_USUARIO", null, cv);
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


    //LISTA NOTIFICACION
    public ArrayList<Noticia> selectListaNoticiaUsuario() {

        String sql = "SELECT * FROM NOTICIA_USUARIO ORDER BY ID_NOTICIA DESC";
        ArrayList<Noticia> arrayNoticia = new ArrayList<Noticia>();
        String titulo = null, descripcion = null, link = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Noticia noticia = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_NOTICIA"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        link = cursor.getString(cursor
                                .getColumnIndex("LINK"));
                        //CLASE AUX
                        noticia = new Noticia(id, titulo, descripcion, link);
                        //ARRAY NOTICIA
                        arrayNoticia.add(noticia);
                    }
                }

            } catch (Exception e) {
                arrayNoticia = null;
            }
        } else {
            arrayNoticia = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        link = null;
        descripcion = null;

        return arrayNoticia;
    }

    //ELIMINAR NOTICIA
    public boolean eliminarNoticiaUsuario() {

        boolean res = false;
        String sql = "DELETE FROM NOTICIA_USUARIO";
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

    //FOTO
    // INSERTAR FOTO
    public boolean insertFotoUsuario(Foto foto) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_FOTO", foto.getID_FOTO());
            cv.put("TITULO", foto.getTITULO());
            cv.put("FOTO", foto.getFOTO());

            long valor = database.insert("FOTO_USUARIO", null, cv);
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

    // LISTA FOTO
    public ArrayList<Foto> selectListaFotoUsuario() {

        String sql = "SELECT * FROM FOTO_USUARIO ORDER BY ID_FOTO DESC";
        ArrayList<Foto> arrayFoto = new ArrayList<Foto>();
        String titulo = null;
        int id;
        byte[] foto_byte = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        Foto foto = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_FOTO"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        foto_byte = cursor.getBlob(cursor
                                .getColumnIndex("FOTO"));

                        foto = new Foto(id, titulo, foto_byte);
                        arrayFoto.add(foto);
                    }
                }
            } catch (Exception e) {
                arrayFoto = null;
            }
        } else {
            arrayFoto = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        foto_byte = null;
        return arrayFoto;
    }

    //ELIMINAR FOTO
    public boolean eliminarFotoUsuario() {

        boolean res = false;
        String sql = "DELETE FROM FOTO_USUARIO";
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

    //PUBLICIDAD
    // INSERTAR PUBLICIDAD
    public boolean insertPublicidadUsuario(Publicidad publicidad) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_PUBLICIDAD", publicidad.getID_PUBLICIDAD());
            cv.put("TITULO", publicidad.getTITULO());
            cv.put("LOGO", publicidad.getLOGO());
            cv.put("OTROS", publicidad.getOTROS());

            long valor = database.insert("PUBLICIDAD_USUARIO", null, cv);
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

    // LISTA PUBLICIDAD
    public ArrayList<Publicidad> selectListaPublicidadUsuario() {

        String sql = "SELECT * FROM PUBLICIDAD_USUARIO ORDER BY ID_PUBLICIDAD DESC";
        ArrayList<Publicidad> arrayPublicidad = new ArrayList<>();
        String titulo = null, otros = null;
        int id;
        byte[] logo_byte = null;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        Publicidad publicidad = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_PUBLICIDAD"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        logo_byte = cursor.getBlob(cursor
                                .getColumnIndex("LOGO"));
                        otros = cursor.getString(cursor
                                .getColumnIndex("OTROS"));
                        publicidad = new Publicidad(id, titulo, logo_byte, otros);
                        arrayPublicidad.add(publicidad);
                    }
                }
            } catch (Exception e) {
                arrayPublicidad = null;
            }
        } else {
            arrayPublicidad = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        logo_byte = null;
        otros = null;
        return arrayPublicidad;
    }

    //ELIMINAR PUBLICIDAD
    public boolean eliminarPublicidadUsuario() {

        boolean res = false;
        String sql = "DELETE FROM PUBLICIDAD_USUARIO";
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
        ArrayList<Fecha> arrayFecha = new ArrayList<Fecha>();
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

        String sql = "INSERT INTO ANIO ( ANIO) VALUES ('" + anio.getANIO()
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
        ArrayList<Mes> arrayMes = new ArrayList<Mes>();
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
}