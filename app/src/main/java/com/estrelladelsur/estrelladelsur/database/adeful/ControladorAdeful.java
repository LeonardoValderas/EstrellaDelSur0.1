package com.estrelladelsur.estrelladelsur.database.adeful;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;

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

    // INSERTAR MODULO
    public boolean insertModuloAdeful(Modulo modulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", modulo.getMODULO());

            long valor = database.insert("MODULO_ADEFUL", null, cv);
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
    public ArrayList<Modulo> selectListaModuloAdeful() {

        String sql = "SELECT * FROM MODULO_ADEFUL";
        ArrayList<Modulo> arrayModuloAdeful = new ArrayList<Modulo>();
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
                        arrayModuloAdeful.add(modulo);
                    }
                }
            } catch (Exception e) {
                arrayModuloAdeful = null;
            }
        } else {
            arrayModuloAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arrayModuloAdeful;
    }

    // ACTUALIZAR SUBMODULO TRUE
    public boolean actualizarSubModuloSelectedTrueAdeful(int id_submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ISSELECTED", true);
            long valor = database.update("SUBMODULO_ADEFUL", cv, "ID_SUBMODULO=" + id_submodulo, null);
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
    // ACTUALIZAR SUBMODULO FALSE
    public boolean actualizarSubModuloSelectedFalseAdeful(int id_submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ISSELECTED", false);
            long valor = database.update("SUBMODULO_ADEFUL", cv, "ID_SUBMODULO=" + id_submodulo, null);
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

    // INSERTAR SUBMODULO
    public boolean insertSubModuloAdeful(SubModulo Submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", Submodulo.getSUBMODULO());
            cv.put("ID_MODULO", Submodulo.getID_MODULO());
            cv.put("ISSELECTED", false);
            long valor = database.insert("SUBMODULO_ADEFUL", null, cv);
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
    public ArrayList<SubModulo> selectListaSubModuloAdeful() {

        String sql = "SELECT * FROM SUBMODULO_ADEFUL";
        ArrayList<SubModulo> arraySubModuloAdeful = new ArrayList<SubModulo>();
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
                        arraySubModuloAdeful.add(submodulo);
                    }
                }
            } catch (Exception e) {
                arraySubModuloAdeful = null;
            }
        } else {
            arraySubModuloAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arraySubModuloAdeful;
    }
    //LISTA SUBMODULO
    public ArrayList<SubModulo> selectListaSubModuloPermisoAdeful(int id_permiso) {

        String sql = "SELECT P.ID_PERMISO_MODULO, P.ID_SUBMODULO, S.NOMBRE FROM PERMISO_MODULO_ADEFUL P "
                +    "INNER JOIN SUBMODULO_ADEFUL S ON S.ID_SUBMODULO = P.ID_SUBMODULO "
                +    "WHERE P.ID_PERMISO = "+id_permiso;
        ArrayList<SubModulo> arraySubModuloAdeful = new ArrayList<SubModulo>();
        String nombre = null;
        int id,id_sub;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        SubModulo submodulo = null;
                        id = cursor.getInt(cursor.getColumnIndex("ID_PERMISO_MODULO"));
                        id_sub = cursor.getInt(cursor.getColumnIndex("ID_SUBMODULO"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        //CLASE AUX
                        submodulo = new SubModulo(id, id_sub, nombre,true );
                        //ARRAY SUBMODULO
                        arraySubModuloAdeful.add(submodulo);
                    }
                }
            } catch (Exception e) {
                arraySubModuloAdeful = null;
            }
        } else {
            arraySubModuloAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arraySubModuloAdeful;
    }

    //LISTA MODULO SUBMODULO
    public ArrayList<SubModulo> selectListaModuloSubModuloFalseAdeful() {

        String sql = "SELECT S.ID_SUBMODULO, S.ID_MODULO, S.NOMBRE, M.NOMBRE AS MODNOMBRE,S.ISSELECTED " +
                "FROM SUBMODULO_ADEFUL S, MODULO_ADEFUL M WHERE M.ID_MODULO = S.ID_MODULO AND S.ISSELECTED = 0 ";
        ArrayList<SubModulo> arraySubModuloAdeful = new ArrayList<SubModulo>();
        String nombre = null, modnombre = null;
        int id, id_sub;
        boolean isselected;
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
                        modnombre = cursor.getString(cursor
                                .getColumnIndex("MODNOMBRE"));
                        isselected = cursor.getInt(cursor
                                .getColumnIndex("ISSELECTED")) > 0;
                        //CLASE AUX
                        submodulo = new SubModulo(id, nombre, id_sub, modnombre,isselected);
                        //ARRAY SUBMODULO
                        arraySubModuloAdeful.add(submodulo);
                    }
                }
            } catch (Exception e) {
                arraySubModuloAdeful = null;
            }
        } else {
            arraySubModuloAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        nombre = null;
        return arraySubModuloAdeful;
    }

    // INSERTAR USUARIO
    public boolean insertUsuarioAdeful(Usuario usuario)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("USUARIO", usuario.getUSUARIO());
            cv.put("PASSWORD", usuario.getPASSWORD());
            cv.put("LIGA", usuario.isLIGA());
            cv.put("USUARIO_CREADOR", usuario.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", usuario.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());

            long valor = database.insert("USUARIO_ADEFUL", null, cv);
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
    public boolean actualizarUsuarioAdeful(Usuario usuario)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("USUARIO", usuario.getUSUARIO());
            cv.put("PASSWORD", usuario.getPASSWORD());
            cv.put("LIGA", usuario.isLIGA());
            cv.put("USUARIO_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());

            long valor = database.update("USUARIO_ADEFUL", cv, "ID_USUARIO=" + usuario.getID_USUARIO(), null);

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
    public ArrayList<Usuario> selectListaUsuarioAdeful() {

        String sql = "SELECT * FROM USUARIO_ADEFUL";
        ArrayList<Usuario> arrayUsuarioAdeful = new ArrayList<Usuario>();
        String user = null, pass = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
                        liga = cursor.getInt(cursor
                                .getColumnIndex("LIGA")) > 0;
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        usuario = new Usuario(id, user, pass, liga, creador, fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY USUARIO
                        arrayUsuarioAdeful.add(usuario);
                    }
                }
            } catch (Exception e) {
                arrayUsuarioAdeful = null;
            }
        } else {
            arrayUsuarioAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        user = null;
        pass = null;
        return arrayUsuarioAdeful;
    }
    //ELIMINAR USUARIO
    public boolean eliminarUsuarioAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM USUARIO_ADEFUL WHERE ID_USUARIO = " + id;
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

    public int insertPermisosAdeful(Permiso permiso)
            throws SQLiteException {
        int id_permiso = 0;
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_USUARIO", permiso.getID_USUARIO());
            cv.put("USUARIO_CREADOR", permiso.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", permiso.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", permiso.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", permiso.getFECHA_ACTUALIZACION());

            long valor = database.insert("PERMISO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return id_permiso = (int) valor;
            } else {
                return id_permiso;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return id_permiso;
        }
    }

    public boolean insertPermisoModuloAdeful(Permiso permiso)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_PERMISO", permiso.getID_PERMISO());
            cv.put("ID_MODULO", permiso.getID_MODULO());
            cv.put("ID_SUBMODULO", permiso.getID_SUBMODULO());

            long valor = database.insert("PERMISO_MODULO_ADEFUL", null, cv);
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

    public boolean actualizarPermisosAdeful(Permiso permiso)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("USUARIO_ACTUALIZACION", permiso.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", permiso.getFECHA_ACTUALIZACION());

            long valor = database.update("PERMISO_ADEFUL", cv, "ID_PERMISO=" + permiso.getID_PERMISO(), null);
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

    //LISTA permisos
    public ArrayList<Permiso> selectListaPermisoAdeful() {

        String sql = "SELECT P.ID_PERMISO, P.ID_USUARIO,U.USUARIO"
                + " FROM PERMISO_ADEFUL P"
                + " INNER JOIN USUARIO_ADEFUL U ON"
                + " U.ID_USUARIO = P.ID_USUARIO";

        Cursor cursor = null;
        ArrayList<Permiso> arrayPermiso = new ArrayList<Permiso>();
        int id, id_usuario, id_modulo,id_sub ;
        String nombre = null, subnombre = null, usuario = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Permiso permiso = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_PERMISO"));
                        id_usuario = cursor.getInt(cursor
                                .getColumnIndex("ID_USUARIO"));
                        usuario = cursor.getString(cursor
                                .getColumnIndex("USUARIO"));
                        permiso = new Permiso(id,id_usuario, usuario);
                        arrayPermiso.add(permiso);
                    }
                }
            } catch (Exception e) {
                arrayPermiso = null;
            }
        } else {
            arrayPermiso = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        usuario = null;
        database = null;
        return arrayPermiso;
    }

    //LISTA permisos
    public int selectIdPermisoAdefulIdUser(int id_usuario) {

        String sql = "SELECT ID_PERMISO"
                + " FROM PERMISO_ADEFUL WHERE ID_USUARIO = "+id_usuario;

        Cursor cursor = null;
        int id = 0;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Permiso permiso = null;
                        id = cursor.getInt(cursor
                                .getColumnIndex("ID_PERMISO"));
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
    }    //ELIMINAR PERMISO
    public boolean eliminarPermisoModuloAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM PERMISO_MODULO_ADEFUL WHERE ID_PERMISO_MODULO = " + id;
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
    //LISTA permisos
    public ArrayList<Permiso> selectListaPermisoAdefulId( int id_permiso) {

        String sql = "SELECT P.ID_MODULO, M.NOMBRE, P.ID_SUBMODULO, S.NOMBRE AS SUBNOMBRE"
                + " FROM PERMISO_MODULO_ADEFUL P INNER JOIN MODULO_ADEFUL M ON"
                + " P.ID_MODULO = M.ID_MODULO"
                + " INNER JOIN SUBMODULO_ADEFUL S ON"
                + " S.ID_SUBMODULO = P.ID_SUBMODULO WHERE P.ID_PERMISO = "+id_permiso+"";

        Cursor cursor = null;
        ArrayList<Permiso> arrayPermiso = new ArrayList<Permiso>();
        int id_modulo, id_sub ;
        String nombre = null, subnombre = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Permiso permiso = null;
                        id_modulo = cursor.getInt(cursor
                                .getColumnIndex("ID_MODULO"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        id_sub = cursor.getInt(cursor
                                .getColumnIndex("ID_SUBMODULO"));
                        subnombre = cursor.getString(cursor
                                .getColumnIndex("SUBNOMBRE"));
                        permiso = new Permiso(id_modulo, nombre, id_sub, subnombre);
                        arrayPermiso.add(permiso);
                    }
                }
            } catch (Exception e) {
                arrayPermiso = null;
            }
        } else {
            arrayPermiso = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        nombre = null;
        subnombre = null;
        database = null;
        return arrayPermiso;
    }
    //LISTA id submodulos
    public ArrayList<Integer> selectListaIdModulosAdefulId( int id_permiso) {

        String sql = "SELECT ID_SUBMODULO FROM PERMISO_MODULO_ADEFUL WHERE ID_PERMISO = "+id_permiso;

        Cursor cursor = null;
        ArrayList<Integer> arrayIdSubmodulo = new ArrayList<Integer>();
        int id_sub ;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        id_sub = cursor.getInt(cursor
                                .getColumnIndex("ID_SUBMODULO"));
                        arrayIdSubmodulo.add(id_sub);
                    }
                }
            } catch (Exception e) {
                arrayIdSubmodulo = null;
            }
        } else {
            arrayIdSubmodulo = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return arrayIdSubmodulo;
    }
    //LISTA permisos por Id
    public int isPermiso(int id_user) {
        int isPermiso = 0;

        String sql = "SELECT ID_USUARIO FROM PERMISO_ADEFUL WHERE ID_USUARIO = " + id_user + "";

        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    isPermiso = 0;
                }else{
                    isPermiso = 1;
                }
            } catch (Exception e) {
                isPermiso = 2;
            }
        } else {
            isPermiso = 2;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        return isPermiso;
    }

    //ELIMINAR USUARIO
    public boolean eliminarPermisoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM PERMISO_ADEFUL WHERE ID_PERMISO = " + id;
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
    public boolean insertArticuloAdeful(int id, Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ARTICULO", id);
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());
            cv.put("USUARIO_CREADOR", articulo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", articulo.getFECHA_CREACION());
            cv.put("FECHA_ACTUALIZACION", articulo.getFECHA_ACTUALIZACION());

            long valor = database.insert("ARTICULO_ADEFUL", null, cv);
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

    //ACTUALIZAR ARTICULO
    public boolean actualizarArticuloAdeful(Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());
            cv.put("USUARIO_ACTUALIZACION", articulo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", articulo.getFECHA_ACTUALIZACION());

            long valor = database.update("ARTICULO_ADEFUL", cv, "ID_ARTICULO=" + articulo.getID_ARTICULO(), null);
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

        String sql = "SELECT * FROM ARTICULO_ADEFUL";
        ArrayList<Articulo> arrayArticuloAdeful = new ArrayList<Articulo>();
        String titulo = null, articulo = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
        cerrarBaseDeDatos();
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

    /////////CARGO//////////////
    // INSERTAR CARGO
    public boolean insertCargoAdeful(int id, Cargo cargo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_CARGO", id);
            cv.put("CARGO", cargo.getCARGO());
            cv.put("USUARIO_CREADOR", cargo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", cargo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", cargo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cargo.getFECHA_ACTUALIZACION());

            long valor = database.insert("CARGO_ADEFUL", null, cv);
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

    // ACTUALIZAR CARGO
    public boolean actualizarCargoAdeful(Cargo cargo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("CARGO", cargo.getCARGO());
            cv.put("USUARIO_ACTUALIZACION", cargo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cargo.getFECHA_ACTUALIZACION());

            long valor = database.update("CARGO_ADEFUL", cv, "ID_CARGO=" + cargo.getID_CARGO(), null);
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

    //LISTA CARGOS
    public ArrayList<Cargo> selectListaCargoAdeful() {

        String sql = "SELECT * FROM CARGO_ADEFUL";
        ArrayList<Cargo> arrayCargoAdeful = new ArrayList<Cargo>();
        String cargoS = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
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
        cerrarBaseDeDatos();
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
    public boolean insertComisionAdeful(int id, Comision comision) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_COMISION", id);
            cv.put("NOMBRE_COMISION", comision.getNOMBRE_COMISION());
            cv.put("FOTO_COMISION", comision.getFOTO_COMISION());
            cv.put("NOMBRE_FOTO", comision.getNOMBRE_FOTO());
            cv.put("ID_CARGO", comision.getID_CARGO());
            cv.put("PERIODO_DESDE", comision.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", comision.getPERIODO_HASTA());
            cv.put("URL_COMISION", comision.getURL_COMISION());
            cv.put("USUARIO_CREADOR", comision.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", comision.getFECHA_CREACION());

            long valor = database.insert("COMISION_ADEFUL", null, cv);
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

    //COMISION POR ID
    public ArrayList<Comision> selectComisionAdeful(int id_comision) {

        String sql = "SELECT * FROM COMISION_ADEFUL WHERE ID_COMISION=" + id_comision;
        ArrayList<Comision> arrayComisionAdeful = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, url_comision = null, nombre_foto = null;
        int id;
        int id_cargo;
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
                        nombre_foto = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_FOTO"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        url_comision = cursor.getString(cursor
                                .getColumnIndex("URL_COMISION"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        comision = new Comision(id, nombre, foto, nombre_foto, id_cargo, null, desde, hasta,url_comision, creador,
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
        cerrarBaseDeDatos();
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
        url_comision = null;

        return arrayComisionAdeful;
    }

    //LISTA COMISION
    public ArrayList<Comision> selectListaComisionAdeful() {

        String sql = "SELECT C.ID_COMISION, C.NOMBRE_COMISION, C.FOTO_COMISION, C.NOMBRE_FOTO, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.URL_COMISION, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM COMISION_ADEFUL C INNER JOIN CARGO_ADEFUL CA ON CA.ID_CARGO=C.ID_CARGO";
        ArrayList<Comision> arrayComisionAdeful = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null, url_comision=null, nombre_foto = null;
        int id;
        int id_cargo;
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
                        nombre_foto = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_FOTO"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        url_comision = cursor.getString(cursor
                                .getColumnIndex("URL_COMISION"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        comision = new Comision(id, nombre, foto, nombre_foto, id_cargo, cargo, desde, hasta, url_comision, creador,
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
        cerrarBaseDeDatos();
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
        url_comision = null;

        return arrayComisionAdeful;
    }

    //ACTUALIZAR
    public boolean actualizarComisionAdeful(Comision comision) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE_COMISION", comision.getNOMBRE_COMISION());
            cv.put("FOTO_COMISION", comision.getFOTO_COMISION());
            cv.put("NOMBRE_FOTO", comision.getNOMBRE_FOTO());
            cv.put("ID_CARGO", comision.getID_CARGO());
            cv.put("PERIODO_DESDE", comision.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", comision.getPERIODO_HASTA());
            cv.put("URL_COMISION", comision.getURL_COMISION());
            cv.put("USUARIO_ACTUALIZACION", comision.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", comision.getFECHA_ACTUALIZACION());

            long valor = database.update("COMISION_ADEFUL", cv, "ID_COMISION=" + comision.getID_COMISION(), null);
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

    //ELIMINAR COMISION
    public boolean eliminarComisionAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM COMISION_ADEFUL WHERE ID_COMISION = " + id;
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

    ////////DIRECCION/////////
    //INSERTAR
    public boolean insertDireccionAdeful(int id, Direccion direccion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_DIRECCION", id);
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
            cv.put("NOMBRE_FOTO", direccion.getNOMBRE_FOTO());
            cv.put("ID_CARGO", direccion.getID_CARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());
            cv.put("URL_COMISION", direccion.getURL_DIRECCION());
            cv.put("USUARIO_CREADOR", direccion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", direccion.getFECHA_CREACION());

            long valor = database.insert("DIRECCION_ADEFUL", null, cv);
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

    //DIRECCION POR ID
    public ArrayList<Direccion> selectDireccionAdeful(int id_direccion) {

        String sql = "SELECT * FROM DIRECCION_ADEFUL WHERE ID_DIRECCION=" + id_direccion;
        ArrayList<Direccion> arrayDireccionAdeful = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, url_direccion=null, nombre_foto = null;
        int id;
        int id_cargo;
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
                        nombre_foto = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_FOTO"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        url_direccion = cursor.getString(cursor
                                .getColumnIndex("URL_DIRECCION"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        direccion = new Direccion(id, nombre, foto, nombre_foto,id_cargo, null, desde, hasta,url_direccion, creador,
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
        cerrarBaseDeDatos();
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

        String sql = "SELECT C.ID_DIRECCION, C.NOMBRE_DIRECCION, C.FOTO_DIRECCION, C.NOMBRE_FOTO, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.URL_DIRECCION, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM DIRECCION_ADEFUL C INNER JOIN CARGO_ADEFUL CA ON CA.ID_CARGO=C.ID_CARGO";
        ArrayList<Direccion> arrayDireccionAdeful = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null , url_direccion=null, nombre_foto = null;
        int id;
        int id_cargo;
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
                        nombre_foto = cursor.getString(cursor
                                .getColumnIndex("NOMBRE_FOTO"));
                        id_cargo = cursor.getInt(cursor.getColumnIndex("ID_CARGO"));
                        cargo = cursor.getString(cursor
                                .getColumnIndex("CARGO"));
                        desde = cursor.getString(cursor
                                .getColumnIndex("PERIODO_DESDE"));
                        hasta = cursor.getString(cursor
                                .getColumnIndex("PERIODO_HASTA"));
                        url_direccion = cursor.getString(cursor
                                .getColumnIndex("URL_DIRECCION"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        // CLASE AUX
                        direccion = new Direccion(id, nombre, foto, nombre_foto, id_cargo, cargo, desde, hasta,url_direccion, creador,
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
        cerrarBaseDeDatos();
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
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
            cv.put("NOMBRE_FOTO", direccion.getNOMBRE_FOTO());
            cv.put("ID_CARGO", direccion.getID_CARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());
            cv.put("URL_COMISION", direccion.getURL_DIRECCION());
            cv.put("USUARIO_ACTUALIZACION", direccion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", direccion.getFECHA_ACTUALIZACION());

            long valor = database.update("DIRECCION_ADEFUL", cv, "ID_DIRECCION=" + direccion.getID_DIRECCION(), null);
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

    //ELIMINAR COMISION
    public boolean eliminarDireccionAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM DIRECCION_ADEFUL WHERE ID_DIRECCION = " + id;
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

    ////////LIGA ADEFUL/////////
    //EQUIPO INSERTAR
    public boolean insertEquipoAdeful(int id, Equipo equipoAdeful)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_EQUIPO", id);
            cv.put("NOMBRE", equipoAdeful.getNOMBRE_EQUIPO());
            cv.put("ESCUDO", equipoAdeful.getESCUDO());
            cv.put("USUARIO_CREADOR", equipoAdeful.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", equipoAdeful.getFECHA_CREACION());

            long valor = database.insert("EQUIPO_ADEFUL", null, cv);
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
    public ArrayList<Equipo> selectListaEquipoAdeful() {

        String sql = "SELECT * FROM EQUIPO_ADEFUL";
        ArrayList<Equipo> arrayEquipoAdeful = new ArrayList<Equipo>();
        String equipo = null, usuario = null, fechaCreacion = null, fechaActualizacion = null, usuario_act = null;
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

        return arrayEquipoAdeful;
    }

    //EQUIPO ACTUALIZAR
    public boolean actualizarEquipoAdeful(Equipo equipo) throws SQLiteException {
        boolean ban = false;

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", equipo.getNOMBRE_EQUIPO());
            cv.put("ESCUDO", equipo.getESCUDO());
            cv.put("USUARIO_ACTUALIZACION", equipo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", equipo.getFECHA_ACTUALIZACION());

            long valor = database.update("EQUIPO_ADEFUL", cv, "ID_EQUIPO" + "="
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
    public boolean eliminarEquipoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM EQUIPO_ADEFUL WHERE ID_EQUIPO = " + id;
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

    //DIVISION LISTA
    public ArrayList<Division> selectListaDivisionAdeful() {

        String sql = "SELECT * FROM DIVISION_ADEFUL";
        ArrayList<Division> arrayDivision = new ArrayList<Division>();
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
    public boolean actualizarDivisionAdeful(Division division) {

        boolean res = false;
        String sql = "UPDATE DIVISION_ADEFUL SET DESCRIPCION='"
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
    public boolean eliminarDivisionAdeful(int id) {

        boolean res = false;

        String sql = "DELETE FROM DIVISION_ADEFUL WHERE ID_DIVISION = " + id;
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
    public boolean insertTorneoAdeful(Torneo torneo) {
        long valorActual = 0;
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            cv.put("ACTUAL", torneo.getACTUAL());
            cv.put("USUARIO_CREADOR", torneo.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", torneo.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", torneo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", torneo.getFECHA_ACTUALIZACION());
            long valor = database.insert("TORNEO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {

                if (torneo.getACTUAL()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", valor);
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    cvA.put("ISACTUAL", true);
                    valorActual = database.update("TORNEO_ACTUAL_ADEFUL", cvA, "ID_TORNEO_ACTUAL="
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
    public ArrayList<Torneo> selectListaTorneoAdeful() {

        String sql = "SELECT * FROM TORNEO_ADEFUL";
        ArrayList<Torneo> arrayTorneo = new ArrayList<Torneo>();
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
    public boolean actualizarTorneoAdeful(Torneo torneo) {
        long valorActual = 0;
        boolean updateOK = true;
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("DESCRIPCION", torneo.getDESCRIPCION());
            cv.put("ACTUAL", torneo.getACTUAL());
            cv.put("USUARIO_ACTUALIZACION", torneo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", torneo.getFECHA_ACTUALIZACION());

            long valor = database.update("TORNEO_ADEFUL", cv, "ID_TORNEO="
                    + torneo.getID_TORNEO(), null);
            cerrarBaseDeDatos();
            if (valor > 0) {

                if (!torneo.getACTUAL() && torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_TORNEO", 0);
                    cvA.put("ID_ANIO", 0);
                    cvA.put("ISACTUAL", false);
                    valorActual = database.update("TORNEO_ACTUAL_ADEFUL", cvA, "ID_TORNEO_ACTUAL="
                            + 1, null);
                    cerrarBaseDeDatos();

                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                }
                if (torneo.getACTUAL() && torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    valorActual = database.update("TORNEO_ACTUAL_ADEFUL", cvA, "ID_TORNEO_ACTUAL="
                            + 1, null);
                    cerrarBaseDeDatos();


                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                }

                if (torneo.getACTUAL() && !torneo.getISACTUAL_ANTERIOR()) {
                    abrirBaseDeDatos();
                    ContentValues cvA = new ContentValues();
                    cvA.put("ID_ANIO", torneo.getFECHA_ANIO());
                    valorActual = database.update("TORNEO_ACTUAL_ADEFUL", cvA, "ID_TORNEO_ACTUAL="
                            + 1, null);
                    cerrarBaseDeDatos();


                    if (valorActual > 0) {
                        updateOK = true;
                    } else {
                        updateOK = false;
                    }
                }
                if (!torneo.getACTUAL() && !torneo.getISACTUAL_ANTERIOR()) {
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
    public boolean eliminarTorneoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM TORNEO_ADEFUL WHERE ID_TORNEO = " + id;
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

    //ES TORNEO ACTUAL
    public Torneo selectActualTorneoAdeful() {

        String sql = "SELECT * FROM TORNEO_ACTUAL_ADEFUL";
        Torneo torneo = null;
        boolean isActual = false;
        abrirBaseDeDatos();
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
                        // arrayTorneo.add(torneo);
                        cerrarBaseDeDatos();
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

    //LISTA CANCHA
    public ArrayList<Cancha> selectListaCanchaAdeful() {

        String sql = "SELECT * FROM CANCHA_ADEFUL";
        ArrayList<Cancha> arrayCancha = new ArrayList<Cancha>();
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
    public boolean actualizarCanchaAdeful(Cancha cancha) {

        boolean res = false;
        String sql = "UPDATE CANCHA_ADEFUL SET NOMBRE='" + cancha.getNOMBRE()
                + "', LONGITUD='" + cancha.getLONGITUD() + "', LATITUD='"
                + cancha.getLATITUD() + "', DIRECCION='"
                + cancha.getDIRECCION() + "', USUARIO_ACTUALIZACION='"
                + cancha.getUSUARIO_ACTUALIZACION() + "', FECHA_ACTUALIZACION='"
                + cancha.getFECHA_ACTUALIZACION()
                + "' WHERE ID_CANCHA ='" + cancha.getID_CANCHA() + "'";
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

    // ELIMINAR CANCHA
    public boolean eliminarCanchaAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM CANCHA_ADEFUL WHERE ID_CANCHA = " + id;
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

    // INSERTAR FIXTURE
    public boolean insertFixtureAdeful(Fixture fixture) throws SQLiteException {

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
            cv.put("USUARIO_CREADOR", fixture.getUSUARIO_CREACION());
            cv.put("USUARIO_ACTUALIZACION", fixture.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_CREACION", fixture.getFECHA_CREACION());
            cv.put("FECHA_ACTUALIZACION", fixture.getFECHA_ACTUALIZACION());

            long valor = database.insert("FIXTURE_ADEFUL", null, cv);
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
    public boolean actualizarFixtureAdeful(Fixture fixture)
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

            long valor = database.update("FIXTURE_ADEFUL", cv, "ID_FIXTURE="
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
    public ArrayList<Fixture> selectListaFixtureAdeful(int division,
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

        ArrayList<Fixture> arrayFixture = new ArrayList<Fixture>();
        String dia = null, hora = null, e_local = null, e_visita = null, cancha = null, r_local = null, r_visita = null;
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

                        fixtureRecycler = new Fixture(id_fixture,
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

    ////RESULTADOS////
    //ACTUALIZAR RESULTADO/FIXTURE
    public boolean actualizarResultadoAdeful(int id_fixture,
                                             String resultado_local,
                                             String resultado_visita,
                                             String usuario_act,
                                             String fecha_act) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("RESULTADO_LOCAL", resultado_local);
            cv.put("RESULTADO_VISITA", resultado_visita);
            cv.put("USUARIO_ACTUALIZACION", usuario_act);
            cv.put("FECHA_ACTUALIZACION", fecha_act);

            long valor = database.update("FIXTURE_ADEFUL", cv, "ID_FIXTURE="
                    + id_fixture, null);
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
    public ArrayList<Resultado> selectListaResultadoAdeful(
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

        ArrayList<Resultado> arrayResultado = new ArrayList<Resultado>();
        String e_local = null, e_visita = null, resultado_local = null, resultado_visita = null;
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

                        resultadoRecycler = new Resultado(id_fixture,
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
    public boolean insertJugadorAdeful(Jugador jugador) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
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
    public ArrayList<Jugador> selectListaJugadorAdeful(int division) {

        String sql = "SELECT J.ID_JUGADOR AS ID_JUGADOR, J.NOMBRE_JUGADOR AS NOMBRE_JUGADOR, J.FOTO_JUGADOR AS FOTO_JUGADOR,"
                + " J.ID_DIVISION AS ID_DIVISION, D.DESCRIPCION AS DESCRIPCION_DIVISION,"
                + " J.ID_POSICION AS ID_POSICION, P.DESCRIPCION AS DESCRIPCION_POSICION"
                + " FROM JUGADOR_ADEFUL J  INNER JOIN  DIVISION_ADEFUL D ON J.ID_DIVISION = D.ID_DIVISION"
                + " INNER JOIN POSICION_ADEFUL P ON P.ID_POSICION = J.ID_POSICION"
                + " WHERE J.ID_DIVISION=" + division;

        ArrayList<Jugador> arrayJugador = new ArrayList<Jugador>();
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
    public boolean actualizarJugadorAdeful(Jugador jugador)
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

            long valor = database.update("JUGADOR_ADEFUL", cv, "ID_JUGADOR="
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
    public boolean eliminarJugadorAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM JUGADOR_ADEFUL WHERE ID_JUGADOR = " + id;
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
    public boolean insertPosicionAdeful(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_CREADOR", posicion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", posicion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

            long valor = database.insert("POSICION_ADEFUL", null, cv);
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
    public boolean actualizarPosicionAdeful(Posicion posicion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DESCRIPCION", posicion.getDESCRIPCION());
            cv.put("USUARIO_ACTUALIZACION", posicion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", posicion.getFECHA_ACTUALIZACION());

            long valor = database.update("POSICION_ADEFUL", cv, "ID_POSICION="
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
    public ArrayList<Posicion> selectListaPosicionAdeful() {

        String sql = "SELECT * FROM POSICION_ADEFUL";
        ArrayList<Posicion> arrayPosicion = new ArrayList<Posicion>();
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

    ////ENTRENAMIENTO//////
//INSERTAR
    public int insertEntrenamientoAdeful(Entrenamiento entrenamiento)
            throws SQLiteException {
        int id_entrenamiento = 0;
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
            cv.put("USUARIO_CREADOR", entrenamiento.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", entrenamiento.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", entrenamiento.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", entrenamiento.getFECHA_ACTUALIZACION());

            long valor = database.insert("ENTRENAMIENTO_ADEFUL", null, cv);
            cerrarBaseDeDatos();
            if (valor > 0) {
                return id_entrenamiento = (int) valor;
            } else {
                return id_entrenamiento;
            }
        } catch (SQLiteException e) {
            cerrarBaseDeDatos();
            return id_entrenamiento;
        }
    }

    // INSERT TABLA INTERMEDIA
    public boolean insertEntrenamientoDivisionAdeful(Entrenamiento entrenamiento_Division)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO",
                    entrenamiento_Division.getID_ENTRENAMIENTO());
            cv.put("ID_DIVISION", entrenamiento_Division.getID_DIVISION());

            long valor = database.insert("ENTRENAMIENTO_DIVISION_ADEFUL", null,
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
    public boolean actualizarEntrenamientoAdeful(Entrenamiento entrenamiento)
            throws SQLiteException {
        int id_entrenamiento = 0;
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("DIA_ENTRENAMIENTO", entrenamiento.getDIA());
            cv.put("HORA_ENTRENAMIENTO", entrenamiento.getHORA());
            cv.put("ID_CANCHA", entrenamiento.getID_CANCHA());
            cv.put("USUARIO_ACTUALIZACION", entrenamiento.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", entrenamiento.getFECHA_ACTUALIZACION());

            long valor = database.update("ENTRENAMIENTO_ADEFUL", cv, "ID_ENTRENAMIENTO="
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

        String sql = "SELECT ID_ENTRENAMIENTO_DIVISION FROM ENTRENAMIENTO_DIVISION_ADEFUL ORDER BY ID_ENTRENAMIENTO_DIVISION DESC limit 1";
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
    public ArrayList<EntrenamientoRecycler> selectListaEntrenamientoAdeful(String fecha) {

        String sql = "SELECT EA.ID_ENTRENAMIENTO, EA.DIA_ENTRENAMIENTO, EA.HORA_ENTRENAMIENTO, EA.ID_CANCHA, CA.NOMBRE"
                + " FROM ENTRENAMIENTO_ADEFUL EA INNER JOIN CANCHA_ADEFUL CA ON EA.ID_CANCHA = CA.ID_CANCHA"
                + " WHERE substr(EA.DIA_ENTRENAMIENTO , 4, 7) = '" + fecha + "'";

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
    public ArrayList<Entrenamiento> selectListaDivisionEntrenamientoAdefulId(int id_entrenamiento) {

        String sql = "SELECT ED.ID_ENTRENAMIENTO_DIVISION, ED.ID_DIVISION, D.DESCRIPCION"
                + " FROM ENTRENAMIENTO_DIVISION_ADEFUL ED INNER JOIN DIVISION_ADEFUL D ON"
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

    //lISTA ENTRENAMIENTO division
    public ArrayList<Entrenamiento> selectListaDivisionEntrenamientoAdeful() {

        String sql = "SELECT * FROM DIVISION_ADEFUL";
        ArrayList<Entrenamiento> arrayDivision = new ArrayList<Entrenamiento>();
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
    public boolean eliminarEntrenamientoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_ADEFUL WHERE ID_ENTRENAMIENTO = " + id;
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
    public boolean eliminarDivisionEntrenamientoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_DIVISION_ADEFUL WHERE ID_ENTRENAMIENTO_DIVISION = " + id;
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
    public boolean insertAsistenciaEntrenamientoAdeful(Entrenamiento entrenamientoAsistencia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        long valor = 0;
        abrirBaseDeDatos();
        try {
            cv.put("ID_ENTRENAMIENTO", entrenamientoAsistencia.getID_ENTRENAMIENTO());
            cv.put("ID_JUGADOR", entrenamientoAsistencia.getID_JUGADOR());

            valor = database.insert("ENTRENAMIENTO_ASISTENCIA_ADEFUL", null,
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
    public boolean eliminarAsistenciaEntrenamientoAdeful(int id_asistencia, int id_jugador) {

        boolean res = false;
        String sql = "DELETE FROM ENTRENAMIENTO_ASISTENCIA_ADEFUL WHERE ID_ENTRENAMIENTO =" + id_asistencia + " AND ID_JUGADOR=" + id_jugador;
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
    public ArrayList<Entrenamiento> selectListaJugadoresEntrenamientoAdeful(int id_entrenamiento) {

        String sql = "SELECT * FROM ENTRENAMIENTO_ASISTENCIA_ADEFUL WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
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
    public ArrayList<Entrenamiento> selectListaJugadoresEntrenamientoAdeful(ArrayList<Integer> id_division_array, int id_entrenamiento) {

        String sql = "SELECT * FROM ENTRENAMIENTO_ASISTENCIA_ADEFUL WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
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
                    sqlId += " OR ID_DIVISION=" + id_division_array.get(ids);
                }
            } else {
                sqlId = "" + id_division_array.get(ids);
            }
        }

        String sqlJ = "SELECT J.ID_JUGADOR, J.NOMBRE_JUGADOR, J.ID_DIVISION, D.DESCRIPCION " +
                "FROM JUGADOR_ADEFUL J INNER JOIN DIVISION_ADEFUL D ON D.ID_DIVISION = J.ID_DIVISION " +
                "WHERE J.ID_DIVISION=" + sqlId;
        ArrayList<Entrenamiento> arrayAsistenciaJugador = new ArrayList<Entrenamiento>();

        int id_jug, id_divi;
        String nombre = null,descripcion = null;
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
    public ArrayList<Integer> selectListaIdDivisionEntrenamientoAdeful(int id_entrenamiento) {

        String sql = "SELECT ID_DIVISION FROM ENTRENAMIENTO_DIVISION_ADEFUL WHERE ID_ENTRENAMIENTO=" + id_entrenamiento;
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
    public boolean insertSancionAdeful(Sancion sancion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_JUGADOR", sancion.getID_JUGADOR());
            cv.put("ID_TORNEO", sancion.getID_TORNEO());
            cv.put("ID_ANIO", sancion.getID_ANIO());
            cv.put("AMARILLA", sancion.getAMARILLA());
            cv.put("ROJA", sancion.getROJA());
            cv.put("FECHA_SUSPENSION", sancion.getFECHA_SUSPENSION());
            cv.put("OBSERVACIONES", sancion.getOBSERVACIONES());
            cv.put("USUARIO_CREADOR", sancion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", sancion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", sancion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", sancion.getFECHA_ACTUALIZACION());

            long valor = database.insert("SANCION_ADEFUL", null, cv);
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
    public boolean actualizarSancionAdeful(Sancion sancion) throws SQLiteException {
        abrirBaseDeDatos();
        ContentValues cv = new ContentValues();
        try {
            cv.put("ID_JUGADOR", sancion.getID_JUGADOR());
            cv.put("ID_TORNEO", sancion.getID_TORNEO());
            cv.put("ID_ANIO", sancion.getID_ANIO());
            cv.put("AMARILLA", sancion.getAMARILLA());
            cv.put("ROJA", sancion.getROJA());
            cv.put("FECHA_SUSPENSION", sancion.getFECHA_SUSPENSION());
            cv.put("OBSERVACIONES", sancion.getOBSERVACIONES());
            cv.put("USUARIO_ACTUALIZACION", sancion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", sancion.getFECHA_ACTUALIZACION());

            long valor = database.update("SANCION_ADEFUL", cv, "ID_SANCION=" + sancion.getID_SANCION(), null);
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
    public boolean eliminarSancionAdeful(int id_sancion) {

        boolean res = false;
        String sql = "DELETE FROM SANCION_ADEFUL WHERE ID_SANCION =" + id_sancion;
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
    public ArrayList<Sancion> selectListaSancionAdeful(int division,
                                                       int jugador, int torneo, int anio) {

        String sql = "SELECT S.ID_SANCION AS ID,S.ID_JUGADOR, J.NOMBRE_JUGADOR,J.FOTO_JUGADOR, J.ID_DIVISION, D.DESCRIPCION, "
                + "S.AMARILLA, S.ROJA, S.FECHA_SUSPENSION, S.OBSERVACIONES "
                + "FROM SANCION_ADEFUL S INNER JOIN JUGADOR_ADEFUL J ON J.ID_JUGADOR = S.ID_JUGADOR "
                + "INNER JOIN DIVISION_ADEFUL D ON D.ID_DIVISION = J.ID_DIVISION "
                + "WHERE D.ID_DIVISION="
                + division
                + " AND J.ID_JUGADOR="
                + jugador
                + " AND S.ID_TORNEO=" + torneo + " AND S.ID_ANIO=" + anio + "";

        ArrayList<Sancion> arraySancion = new ArrayList<Sancion>();
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

    /**
     * MODULO SOCIAL
     */

    // INSERTAR NOTIFICACION
    public boolean insertNotificacionAdeful(Notificacion notificacion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", notificacion.getTITULO());
            cv.put("NOTIFICACION", notificacion.getNOTIFICACION());
            cv.put("USUARIO_CREADOR", notificacion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", notificacion.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", notificacion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", notificacion.getFECHA_ACTUALIZACION());

            long valor = database.insert("NOTIFICACION_ADEFUL", null, cv);
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

    //ACTUALIZAR NOTIFICACION
    public boolean actualizarNotificacionAdeful(Notificacion notificacion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", notificacion.getTITULO());
            cv.put("NOTIFICACION", notificacion.getNOTIFICACION());
            cv.put("USUARIO_ACTUALIZACION", notificacion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", notificacion.getFECHA_ACTUALIZACION());

            long valor = database.update("NOTIFICACION_ADEFUL", cv, "ID_NOTIFICACION=" + notificacion.getID_NOTIFICACION(), null);
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
    public ArrayList<Notificacion> selectListaNotificacionAdeful() {

        String sql = "SELECT * FROM NOTIFICACION_ADEFUL";
        ArrayList<Notificacion> arrayNotificacionAdeful = new ArrayList<Notificacion>();
        String titulo = null, notificacion = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Notificacion notificacionAdeful = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_NOTIFICACION"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        notificacion = cursor.getString(cursor
                                .getColumnIndex("NOTIFICACION"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        notificacionAdeful = new Notificacion(id, titulo, notificacion, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY ARTICULO
                        arrayNotificacionAdeful.add(notificacionAdeful);
                    }
                }

            } catch (Exception e) {
                arrayNotificacionAdeful = null;
            }
        } else {

            arrayNotificacionAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        notificacion = null;
        return arrayNotificacionAdeful;
    }

    //ELIMINAR NOTIFICACION
    public boolean eliminarNotificacionAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM NOTIFICACION_ADEFUL WHERE ID_NOTIFICACION = " + id;
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
    public boolean insertNoticiaAdeful(Noticia noticia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", noticia.getTITULO());
            cv.put("DESCRIPCION", noticia.getDESCRIPCION());
            cv.put("LINK", noticia.getLINK());
            cv.put("USUARIO_CREADOR", noticia.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", noticia.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", noticia.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", noticia.getFECHA_ACTUALIZACION());

            long valor = database.insert("NOTICIA_ADEFUL", null, cv);
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

    //ACTUALIZAR NOTIFICACION
    public boolean actualizarNoticiaAdeful(Noticia noticia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", noticia.getTITULO());
            cv.put("DESCRIPCION", noticia.getDESCRIPCION());
            cv.put("LINK", noticia.getLINK());
            cv.put("USUARIO_ACTUALIZACION", noticia.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", noticia.getFECHA_ACTUALIZACION());

            long valor = database.update("NOTICIA_ADEFUL", cv, "ID_NOTICIA=" + noticia.getID_NOTICIA(), null);
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
    public ArrayList<Noticia> selectListaNoticiaAdeful() {

        String sql = "SELECT * FROM NOTICIA_ADEFUL";
        ArrayList<Noticia> arrayNoticiaAdeful = new ArrayList<Noticia>();
        String titulo = null, descripcion = null, link = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Noticia noticiaAdeful = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_NOTICIA"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        descripcion = cursor.getString(cursor
                                .getColumnIndex("DESCRIPCION"));
                        link = cursor.getString(cursor
                                .getColumnIndex("LINK"));
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        noticiaAdeful = new Noticia(id, titulo, descripcion, link, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY NOTICIA
                        arrayNoticiaAdeful.add(noticiaAdeful);
                    }
                }

            } catch (Exception e) {
                arrayNoticiaAdeful = null;
            }
        } else {
            arrayNoticiaAdeful = null;
        }
        cerrarBaseDeDatos();
        sql = null;
        cursor = null;
        database = null;
        titulo = null;
        link = null;
        descripcion = null;
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;

        return arrayNoticiaAdeful;
    }

    //ELIMINAR NOTICIA
    public boolean eliminarNoticiaAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM NOTICIA_ADEFUL WHERE ID_NOTICIA = " + id;
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
    public boolean insertFotoAdeful(Foto foto) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", foto.getTITULO());
            cv.put("FOTO", foto.getFOTO());
            cv.put("USUARIO_CREADOR", foto.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", foto.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", foto.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", foto.getFECHA_ACTUALIZACION());

            long valor = database.insert("FOTO_ADEFUL", null, cv);
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
    public ArrayList<Foto> selectListaFotoAdeful() {

        String sql = "SELECT * FROM FOTO_ADEFUL";
        ArrayList<Foto> arrayFoto = new ArrayList<Foto>();
        String titulo = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        foto = new Foto(id, titulo, foto_byte, creador, fechaCreacion, usuario_act,
                                fechaActualizacion);
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
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        return arrayFoto;
    }

    //ACTUALIZAR FOTO
    public boolean actualizarFotoAdeful(Foto foto)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", foto.getTITULO());
            cv.put("FOTO", foto.getFOTO());
            cv.put("USUARIO_ACTUALIZACION", foto.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", foto.getFECHA_ACTUALIZACION());

            long valor = database.update("FOTO_ADEFUL", cv, "ID_FOTO="
                    + foto.getID_FOTO(), null);
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

    //ELIMINAR FOTO
    public boolean eliminarFotoAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM FOTO_ADEFUL WHERE ID_FOTO = " + id;
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
    public boolean insertPublicidadAdeful(Publicidad publicidad) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", publicidad.getTITULO());
            cv.put("LOGO", publicidad.getLOGO());
            cv.put("OTROS", publicidad.getOTROS());
            cv.put("USUARIO_CREADOR", publicidad.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", publicidad.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", publicidad.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", publicidad.getFECHA_ACTUALIZACION());

            long valor = database.insert("PUBLICIDAD_ADEFUL", null, cv);
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
    public ArrayList<Publicidad> selectListaPublicidadAdeful() {

        String sql = "SELECT * FROM PUBLICIDAD_ADEFUL";
        ArrayList<Publicidad> arrayPublicidad = new ArrayList<Publicidad>();
        String titulo = null, otros = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        publicidad = new Publicidad(id, titulo, logo_byte, otros, creador, fechaCreacion, usuario_act,
                                fechaActualizacion);
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
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        return arrayPublicidad;
    }

    //ACTUALIZAR PUBLICIDAD
    public boolean actualizarPublicidadAdeful(Publicidad publicidad)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", publicidad.getTITULO());
            cv.put("LOGO", publicidad.getLOGO());
            cv.put("OTROS", publicidad.getOTROS());
            cv.put("USUARIO_ACTUALIZACION", publicidad.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", publicidad.getFECHA_ACTUALIZACION());

            long valor = database.update("PUBLICIDAD_ADEFUL", cv, "ID_PUBLICIDAD="
                    + publicidad.getID_PUBLICIDAD(), null);
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

    //ELIMINAR PUBLICIDAD
    public boolean eliminarPublicidadAdeful(int id) {

        boolean res = false;
        String sql = "DELETE FROM PUBLICIDAD_ADEFUL WHERE ID_PUBLICIDAD = " + id;
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
}