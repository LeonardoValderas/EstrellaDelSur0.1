package com.estrelladelsur.estrelladelsur.database.general;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.estrelladelsur.estrelladelsur.database.adeful.SQLiteDBConnectionAdeful;
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

import java.util.ArrayList;

public class ControladorGeneral {

    private SQLiteDBConnectionGeneral sqLiteDBConnectionGeneral;
    private Context ourcontext;
    private SQLiteDatabase database;

    public ControladorGeneral(Context c) {
        ourcontext = c;

    }

    public ControladorGeneral abrirBaseDeDatos() throws SQLException {
        sqLiteDBConnectionGeneral = new SQLiteDBConnectionGeneral(ourcontext,
                "BaseDeDatosGeneral.db", null, 1);
        database = sqLiteDBConnectionGeneral.getWritableDatabase();
        return this;
    }

    public void cerrarBaseDeDatos() {
        sqLiteDBConnectionGeneral.close();
    }

    // INSERTAR MODULO
    public boolean insertModulo(Modulo modulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", modulo.getMODULO());

            long valor = database.insert("MODULO", null, cv);
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
    public ArrayList<Modulo> selectListaModulo() {

        String sql = "SELECT * FROM MODULO";
        ArrayList<Modulo> arrayModulo = new ArrayList<Modulo>();
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

    // ACTUALIZAR SUBMODULO TRUE
    public boolean actualizarSubModuloSelectedTrue(int id_submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ISSELECTED", true);
            long valor = database.update("SUBMODULO", cv, "ID_SUBMODULO=" + id_submodulo, null);
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
    public boolean actualizarSubModuloSelectedFalse(int id_submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ISSELECTED", false);
            long valor = database.update("SUBMODULO", cv, "ID_SUBMODULO=" + id_submodulo, null);
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
    public boolean insertSubModulo(SubModulo Submodulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE", Submodulo.getSUBMODULO());
            cv.put("ID_MODULO", Submodulo.getID_MODULO());
            cv.put("ISSELECTED", false);
            long valor = database.insert("SUBMODULO", null, cv);
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

        String sql = "SELECT * FROM SUBMODULO";
        ArrayList<SubModulo> arraySubModulo = new ArrayList<SubModulo>();
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

    //LISTA SUBMODULO
    public ArrayList<SubModulo> selectListaSubModuloPermiso(int id_permiso) {

        String sql = "SELECT P.ID_PERMISO_MODULO, P.ID_SUBMODULO, S.NOMBRE FROM PERMISO_MODULO P "
                + "INNER JOIN SUBMODULO S ON S.ID_SUBMODULO = P.ID_SUBMODULO "
                + "WHERE P.ID_PERMISO = " + id_permiso;
        ArrayList<SubModulo> arraySubModulo = new ArrayList<SubModulo>();
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
                        id = cursor.getInt(cursor.getColumnIndex("ID_PERMISO_MODULO"));
                        id_sub = cursor.getInt(cursor.getColumnIndex("ID_SUBMODULO"));
                        nombre = cursor.getString(cursor
                                .getColumnIndex("NOMBRE"));
                        //CLASE AUX
                        submodulo = new SubModulo(id, id_sub, nombre, true);
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

    //LISTA MODULO SUBMODULO
    public ArrayList<SubModulo> selectListaModuloSubModuloFalse() {

        String sql = "SELECT S.ID_SUBMODULO, S.ID_MODULO, S.NOMBRE, M.NOMBRE AS MODNOMBRE,S.ISSELECTED " +
                "FROM SUBMODULO S, MODULO M WHERE M.ID_MODULO = S.ID_MODULO AND S.ISSELECTED = 0 ";
        ArrayList<SubModulo> arraySubModulo = new ArrayList<SubModulo>();
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
                        submodulo = new SubModulo(id, nombre, id_sub, modnombre, isselected);
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

    // INSERTAR USUARIO
    public boolean insertUsuario(Usuario usuario)
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

            long valor = database.insert("USUARIO", null, cv);
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
    public boolean actualizarUsuario(Usuario usuario)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("USUARIO", usuario.getUSUARIO());
            cv.put("PASSWORD", usuario.getPASSWORD());
            cv.put("LIGA", usuario.isLIGA());
            cv.put("USUARIO_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", usuario.getUSUARIO_ACTUALIZACION());

            long valor = database.update("USUARIO", cv, "ID_USUARIO=" + usuario.getID_USUARIO(), null);

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

        String sql = "SELECT * FROM USUARIO";
        ArrayList<Usuario> arrayUsuario = new ArrayList<Usuario>();
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
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        user = null;
        pass = null;
        return arrayUsuario;
    }

    //ELIMINAR USUARIO
    public boolean eliminarUsuario(int id) {

        boolean res = false;
        String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = " + id;
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

    public int insertPermisos(Permiso permiso)
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

            long valor = database.insert("PERMISO", null, cv);
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

    public boolean insertPermisoModulo(Permiso permiso)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("ID_PERMISO", permiso.getID_PERMISO());
            cv.put("ID_MODULO", permiso.getID_MODULO());
            cv.put("ID_SUBMODULO", permiso.getID_SUBMODULO());

            long valor = database.insert("PERMISO_MODULO", null, cv);
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

    public boolean actualizarPermisos(Permiso permiso)
            throws SQLiteException {
        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("USUARIO_ACTUALIZACION", permiso.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", permiso.getFECHA_ACTUALIZACION());

            long valor = database.update("PERMISO", cv, "ID_PERMISO=" + permiso.getID_PERMISO(), null);
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
    public ArrayList<Permiso> selectListaPermiso() {

        String sql = "SELECT P.ID_PERMISO, P.ID_USUARIO,U.USUARIO"
                + " FROM PERMISO P"
                + " INNER JOIN USUARIO U ON"
                + " U.ID_USUARIO = P.ID_USUARIO";

        Cursor cursor = null;
        ArrayList<Permiso> arrayPermiso = new ArrayList<Permiso>();
        int id, id_usuario, id_modulo, id_sub;
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
                        permiso = new Permiso(id, id_usuario, usuario);
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
    public int selectIdPermisoIdUser(int id_usuario) {

        String sql = "SELECT ID_PERMISO"
                + " FROM PERMISO WHERE ID_USUARIO = " + id_usuario;

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
    }
    //ELIMINAR PERMISO
    public boolean eliminarPermisoModulo(int id) {

        boolean res = false;
        String sql = "DELETE FROM PERMISO_MODULO WHERE ID_PERMISO_MODULO = " + id;
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
    public ArrayList<Permiso> selectListaPermisoId(int id_permiso) {

        String sql = "SELECT P.ID_MODULO, M.NOMBRE, P.ID_SUBMODULO, S.NOMBRE AS SUBNOMBRE"
                + " FROM PERMISO_MODULO P INNER JOIN MODULO M ON"
                + " P.ID_MODULO = M.ID_MODULO"
                + " INNER JOIN SUBMODULO S ON"
                + " S.ID_SUBMODULO = P.ID_SUBMODULO WHERE P.ID_PERMISO = " + id_permiso + "";

        Cursor cursor = null;
        ArrayList<Permiso> arrayPermiso = new ArrayList<Permiso>();
        int id_modulo, id_sub;
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
    public ArrayList<Integer> selectListaIdModulosId(int id_permiso) {

        String sql = "SELECT ID_SUBMODULO FROM PERMISO_MODULO WHERE ID_PERMISO = " + id_permiso;

        Cursor cursor = null;
        ArrayList<Integer> arrayIdSubmodulo = new ArrayList<Integer>();
        int id_sub;
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

        String sql = "SELECT ID_USUARIO FROM PERMISO WHERE ID_USUARIO = " + id_user + "";

        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {
            try {
                cursor = database.rawQuery(sql, null);

                if (cursor != null && cursor.getCount() > 0) {
                    isPermiso = 0;
                } else {
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
    public boolean eliminarPermiso(int id) {

        boolean res = false;
        String sql = "DELETE FROM PERMISO WHERE ID_PERMISO = " + id;
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
    public boolean insertArticulo(int id, Articulo articulo)
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

            long valor = database.insert("ARTICULO", null, cv);
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
    public boolean actualizarArticulo(Articulo articulo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", articulo.getTITULO());
            cv.put("ARTICULO", articulo.getARTICULO());
            cv.put("USUARIO_ACTUALIZACION", articulo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", articulo.getFECHA_ACTUALIZACION());

            long valor = database.update("ARTICULO", cv, "ID_ARTICULO=" + articulo.getID_ARTICULO(), null);
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
    public ArrayList<Articulo> selectListaArticulo() {

        String sql = "SELECT * FROM ARTICULO";
        ArrayList<Articulo> arrayArticulo = new ArrayList<Articulo>();
        String titulo = null, artic = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
        int id;
        Cursor cursor = null;
        abrirBaseDeDatos();
        if (database != null && database.isOpen()) {

            try {
                cursor = database.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        Articulo articulo = null;

                        id = cursor.getInt(cursor.getColumnIndex("ID_ARTICULO"));
                        titulo = cursor.getString(cursor
                                .getColumnIndex("TITULO"));
                        artic = cursor.getString(cursor
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
                        articulo = new Articulo(id, titulo, artic, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY ARTICULO
                        arrayArticulo.add(articulo);
                    }
                }
            } catch (Exception e) {
                arrayArticulo = null;
            }
        } else {
            arrayArticulo = null;
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
        artic = null;
        return arrayArticulo;
    }

    //ELIMINAR ARTICULO
    public boolean eliminarArticulo(int id) {

        boolean res = false;
        String sql = "DELETE FROM ARTICULO WHERE ID_ARTICULO = " + id;
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
    public boolean insertCargo(int id, Cargo cargo)
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

            long valor = database.insert("CARGO", null, cv);
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
    public boolean actualizarCargo(Cargo cargo)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("CARGO", cargo.getCARGO());
            cv.put("USUARIO_ACTUALIZACION", cargo.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", cargo.getFECHA_ACTUALIZACION());

            long valor = database.update("CARGO", cv, "ID_CARGO=" + cargo.getID_CARGO(), null);
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
    public ArrayList<Cargo> selectListaCargo() {

        String sql = "SELECT * FROM CARGO";
        ArrayList<Cargo> arrayCargo = new ArrayList<Cargo>();
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
                        arrayCargo.add(cargo);
                    }
                }
            } catch (Exception e) {
                arrayCargo = null;
            }
        } else {
            arrayCargo = null;
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

        return arrayCargo;
    }

    ////////COMISION/////////
    //INSERTAR
    public boolean insertComision(int id, Comision comision) throws SQLiteException {

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

            long valor = database.insert("COMISION", null, cv);
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
    public ArrayList<Comision> selectComision(int id_comision) {

        String sql = "SELECT * FROM COMISION WHERE ID_COMISION=" + id_comision;
        ArrayList<Comision> arrayComision = new ArrayList<Comision>();
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
                        comision = new Comision(id, nombre, foto, nombre_foto, id_cargo, null, desde, hasta, url_comision, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayComision.add(comision);
                    }
                }
            } catch (Exception e) {
                arrayComision = null;
            }
        } else {
            arrayComision = null;
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

        return arrayComision;
    }

    //LISTA COMISION
    public ArrayList<Comision> selectListaComision() {

        String sql = "SELECT C.ID_COMISION, C.NOMBRE_COMISION, C.FOTO_COMISION, C.NOMBRE_FOTO, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.URL_COMISION, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM COMISION C INNER JOIN CARGO CA ON CA.ID_CARGO = C.ID_CARGO";
        ArrayList<Comision> arrayComision = new ArrayList<Comision>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null, url_comision = null, nombre_foto = null;
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
                        arrayComision.add(comision);
                    }
                }
            } catch (Exception e) {
                arrayComision = null;
            }
        } else {
            arrayComision = null;
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

        return arrayComision;
    }

    //ACTUALIZAR
    public boolean actualizarComision(Comision comision) throws SQLiteException {

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

            long valor = database.update("COMISION", cv, "ID_COMISION=" + comision.getID_COMISION(), null);
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
    public boolean eliminarComision(int id) {

        boolean res = false;
        String sql = "DELETE FROM COMISION WHERE ID_COMISION = " + id;
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
    public boolean insertDireccion(int id, Direccion direccion) throws SQLiteException {

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
            cv.put("URL_DIRECCION", direccion.getURL_DIRECCION());
            cv.put("USUARIO_CREADOR", direccion.getUSUARIO_CREADOR());
            cv.put("FECHA_CREACION", direccion.getFECHA_CREACION());

            long valor = database.insert("DIRECCION", null, cv);
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
    public ArrayList<Direccion> selectDireccion(int id_direccion) {

        String sql = "SELECT * FROM DIRECCION WHERE ID_DIRECCION=" + id_direccion;
        ArrayList<Direccion> arrayDireccion = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, url_direccion = null, nombre_foto = null;
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
                        direccion = new Direccion(id, nombre, foto, nombre_foto, id_cargo, null, desde, hasta, url_direccion, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY CARGO
                        arrayDireccion.add(direccion);
                    }
                }
            } catch (Exception e) {
                arrayDireccion = null;
            }
        } else {
            arrayDireccion = null;
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

        return arrayDireccion;
    }

    //LISTA COMISION
    public ArrayList<Direccion> selectListaDireccion() {

        String sql = "SELECT C.ID_DIRECCION, C.NOMBRE_DIRECCION, C.FOTO_DIRECCION, C.NOMBRE_FOTO, " +
                "C.ID_CARGO, C.PERIODO_DESDE, C.PERIODO_HASTA, C.URL_DIRECCION, C.USUARIO_CREADOR, " +
                "C.FECHA_CREACION, C.USUARIO_ACTUALIZACION, C.FECHA_ACTUALIZACION, " +
                "CA.CARGO FROM DIRECCION C INNER JOIN CARGO CA ON CA.ID_CARGO = C.ID_CARGO";
        ArrayList<Direccion> arrayDireccion = new ArrayList<Direccion>();
        String nombre = null, desde = null, hasta = null, fechaCreacion = null,
                fechaActualizacion = null, creador = null, usuario_act = null, cargo = null, url_direccion = null, nombre_foto = null;
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
                        direccion = new Direccion(id, nombre, foto, nombre_foto, id_cargo, cargo, desde, hasta, url_direccion, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
                        //ARRAY COMISION
                        arrayDireccion.add(direccion);
                    }
                }
            } catch (Exception e) {
                arrayDireccion = null;
            }
        } else {
            arrayDireccion = null;
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

        return arrayDireccion;
    }

    //ACTUALIZAR
    public boolean actualizarDireccion(Direccion direccion) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("NOMBRE_DIRECCION", direccion.getNOMBRE_DIRECCION());
            cv.put("FOTO_DIRECCION", direccion.getFOTO_DIRECCION());
            cv.put("NOMBRE_FOTO", direccion.getNOMBRE_FOTO());
            cv.put("ID_CARGO", direccion.getID_CARGO());
            cv.put("PERIODO_DESDE", direccion.getPERIODO_DESDE());
            cv.put("PERIODO_HASTA", direccion.getPERIODO_HASTA());
            cv.put("URL_DIRECCION", direccion.getURL_DIRECCION());
            cv.put("USUARIO_ACTUALIZACION", direccion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", direccion.getFECHA_ACTUALIZACION());

            long valor = database.update("DIRECCION", cv, "ID_DIRECCION=" + direccion.getID_DIRECCION(), null);
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
    public boolean eliminarDireccion(int id) {

        boolean res = false;
        String sql = "DELETE FROM DIRECCION WHERE ID_DIRECCION = " + id;
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
    public boolean insertNotificacion(Notificacion notificacion)
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

            long valor = database.insert("NOTIFICACION", null, cv);
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
    public boolean actualizarNotificacion(Notificacion notificacion)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", notificacion.getTITULO());
            cv.put("NOTIFICACION", notificacion.getNOTIFICACION());
            cv.put("USUARIO_ACTUALIZACION", notificacion.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", notificacion.getFECHA_ACTUALIZACION());

            long valor = database.update("NOTIFICACION", cv, "ID_NOTIFICACION=" + notificacion.getID_NOTIFICACION(), null);
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
    public ArrayList<Notificacion> selectListaNotificacion() {

        String sql = "SELECT * FROM NOTIFICACION";
        ArrayList<Notificacion> arrayNotificacion = new ArrayList<Notificacion>();
        String titulo = null, notifi = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        notificacion = new Notificacion(id, titulo, notifi, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
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
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;
        notifi = null;
        return arrayNotificacion;
    }

    //ELIMINAR NOTIFICACION
    public boolean eliminarNotificacion(int id) {

        boolean res = false;
        String sql = "DELETE FROM NOTIFICACION WHERE ID_NOTIFICACION = " + id;
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
    public boolean insertNoticia(Noticia noticia)
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

            long valor = database.insert("NOTICIA", null, cv);
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
    public boolean actualizarNoticia(Noticia noticia)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", noticia.getTITULO());
            cv.put("DESCRIPCION", noticia.getDESCRIPCION());
            cv.put("LINK", noticia.getLINK());
            cv.put("USUARIO_ACTUALIZACION", noticia.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", noticia.getFECHA_ACTUALIZACION());

            long valor = database.update("NOTICIA", cv, "ID_NOTICIA=" + noticia.getID_NOTICIA(), null);
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
    public ArrayList<Noticia> selectListaNoticia() {

        String sql = "SELECT * FROM NOTICIA";
        ArrayList<Noticia> arrayNoticia = new ArrayList<Noticia>();
        String titulo = null, descripcion = null, link = null, fechaCreacion = null, fechaActualizacion = null, creador = null, usuario_act = null;
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
                        creador = cursor.getString(cursor
                                .getColumnIndex("USUARIO_CREADOR"));
                        fechaCreacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_CREACION"));
                        usuario_act = cursor.getString(cursor
                                .getColumnIndex("USUARIO_ACTUALIZACION"));
                        fechaActualizacion = cursor.getString(cursor
                                .getColumnIndex("FECHA_ACTUALIZACION"));
                        //CLASE AUX
                        noticia = new Noticia(id, titulo, descripcion, link, creador,
                                fechaCreacion, usuario_act, fechaActualizacion);
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
        fechaCreacion = null;
        fechaActualizacion = null;
        usuario_act = null;
        creador = null;

        return arrayNoticia;
    }

    //ELIMINAR NOTICIA
    public boolean eliminarNoticia(int id) {

        boolean res = false;
        String sql = "DELETE FROM NOTICIA WHERE ID_NOTICIA = " + id;
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
    public boolean insertFoto(Foto foto) throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", foto.getTITULO());
            cv.put("FOTO", foto.getFOTO());
            cv.put("USUARIO_CREADOR", foto.getUSUARIO_CREACION());
            cv.put("FECHA_CREACION", foto.getFECHA_CREACION());
            cv.put("USUARIO_ACTUALIZACION", foto.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", foto.getFECHA_ACTUALIZACION());

            long valor = database.insert("FOTO", null, cv);
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
    public ArrayList<Foto> selectListaFoto() {

        String sql = "SELECT * FROM FOTO";
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
    public boolean actualizarFoto(Foto foto)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", foto.getTITULO());
            cv.put("FOTO", foto.getFOTO());
            cv.put("USUARIO_ACTUALIZACION", foto.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", foto.getFECHA_ACTUALIZACION());

            long valor = database.update("FOTO", cv, "ID_FOTO="
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
    public boolean eliminarFoto(int id) {

        boolean res = false;
        String sql = "DELETE FROM FOTO WHERE ID_FOTO = " + id;
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
    public boolean insertPublicidad(Publicidad publicidad) throws SQLiteException {

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

            long valor = database.insert("PUBLICIDAD", null, cv);
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
    public ArrayList<Publicidad> selectListaPublicidad() {

        String sql = "SELECT * FROM PUBLICIDAD";
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
    public boolean actualizarPublicidad(Publicidad publicidad)
            throws SQLiteException {

        ContentValues cv = new ContentValues();
        abrirBaseDeDatos();
        try {
            cv.put("TITULO", publicidad.getTITULO());
            cv.put("LOGO", publicidad.getLOGO());
            cv.put("OTROS", publicidad.getOTROS());
            cv.put("USUARIO_ACTUALIZACION", publicidad.getUSUARIO_ACTUALIZACION());
            cv.put("FECHA_ACTUALIZACION", publicidad.getFECHA_ACTUALIZACION());

            long valor = database.update("PUBLICIDAD", cv, "ID_PUBLICIDAD="
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
    public boolean eliminarPublicidad(int id) {

        boolean res = false;
        String sql = "DELETE FROM PUBLICIDAD WHERE ID_PUBLICIDAD = " + id;
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