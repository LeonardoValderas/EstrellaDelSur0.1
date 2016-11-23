package com.estrelladelsur.estrelladelsur.entidad;

import java.util.ArrayList;

public class Permiso {
    //PERMISO
    private int ID_PERMISO;
    private int ID_PERMISO_MODULO;
    private int ID_USUARIO;
    private String USUARIO;
    private String PASSWORD;
    private int ID_MODULO;
    private int ID_SUBMODULO;
    private String MODULO;
    private String SUBMODULO;
    private boolean ISSELECTED;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    private ArrayList<SubModulo> submoduloArrayFalse;
    private ArrayList<SubModulo> subModulosTrue;
    private  ArrayList<Integer> subModulosdelete;
    //PERMISO X MODULO
    public Permiso(int id, int id_permiso, int id_modulo, int id_submodulo) {
        ID_PERMISO_MODULO = id;
        ID_PERMISO = id_permiso;
        ID_MODULO = id_modulo;
        ID_SUBMODULO = id_submodulo;
    }

    //PERMISO X MODULO
    public Permiso(int id, int id_usuario, String nombre) {
        ID_PERMISO = id;
        ID_USUARIO = id_usuario;
        USUARIO = nombre;
    }

    //PERMISO X MODULO
    public Permiso(int id_modulo, String modulo, int id_submodulo, String submodulo) {
        ID_MODULO = id_modulo;
        MODULO = modulo;
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
    }
    //PERMISO
    public Permiso(int id, int id_usuario, int id_modulo, int id_submodulo
                   ,String nombre, String modulo, String submodulo) {
        ID_PERMISO = id;
        ID_USUARIO = id_usuario;
        ID_MODULO = id_modulo;
        ID_SUBMODULO = id_submodulo;
        USUARIO = nombre;
        MODULO = modulo;
        SUBMODULO = submodulo;
    }
    //PERMISO
    public Permiso(int id, int id_usuario, ArrayList<SubModulo> subModulosTrue, ArrayList<SubModulo> submoduloArrayFalse, String usuario, String fechaCreacion,
                   String usuario_act, String fechaActualizacion) {
        ID_PERMISO = id;
        ID_USUARIO = id_usuario;
        this.subModulosTrue = subModulosTrue;
        this.submoduloArrayFalse = submoduloArrayFalse;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public Permiso(int id,  ArrayList<Integer> subModulosdelete, String fechaActualizacion) {
        ID_PERMISO = id;
        this.subModulosdelete = subModulosdelete;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }


    //PERMISO
    public Permiso(int id, int id_usuario, String usuario, String fechaCreacion,
                   String usuario_act, String fechaActualizacion) {
        ID_PERMISO = id;
        ID_USUARIO = id_usuario;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public int getID_PERMISO() {
        return ID_PERMISO;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public int getID_MODULO() {
        return ID_MODULO;
    }

    public int getID_SUBMODULO() {
        return ID_SUBMODULO;
    }

    public String getMODULO() {
        return MODULO;
    }

    public String getSUBMODULO() {
        return SUBMODULO;
    }

    public boolean ISSELECTED() {
        return ISSELECTED;
    }

    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }

    public void setISSELECTED(boolean ISSELECTED) {
        this.ISSELECTED = ISSELECTED;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public int getID_PERMISO_MODULO() {
        return ID_PERMISO_MODULO;
    }

    public ArrayList<SubModulo> getSubmoduloArrayFalse() {
        return submoduloArrayFalse;
    }

    public void setSubmoduloArrayFalse(ArrayList<SubModulo> submoduloArrayFalse) {
        this.submoduloArrayFalse = submoduloArrayFalse;
    }

    public ArrayList<SubModulo> getSubModulosTrue() {
        return subModulosTrue;
    }

    public void setSubModulosTrue(ArrayList<SubModulo> subModulosTrue) {
        this.subModulosTrue = subModulosTrue;
    }

    public ArrayList<Integer> getSubModulosdelete() {
        return subModulosdelete;
    }

    public void setSubModulosdelete(ArrayList<Integer> subModulosdelete) {
        this.subModulosdelete = subModulosdelete;
    }
}