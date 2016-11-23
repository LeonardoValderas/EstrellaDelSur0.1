package com.estrelladelsur.estrelladelsur.entidad;

public class Usuario {
    //USUARIO
    private int ID_USUARIO;
    private String USUARIO;
    private String PASSWORD;
    private boolean LIGA;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    //USUARIO
    public Usuario(int id_usuario, String user, String pass, String usuario, String fechaCreacion,
                   String usuario_act, String fechaActualizacion) {
        ID_USUARIO = id_usuario;
        USUARIO = user;
        PASSWORD = pass;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }
    //USUARIO
    public Usuario(int id_usuario, String user, String pass) {
        ID_USUARIO = id_usuario;
        USUARIO = user;
        PASSWORD = pass;
    }
    public int getID_USUARIO() { return ID_USUARIO;}
    public String getUSUARIO() {
        return USUARIO;
    }
    public String toString() {
        return USUARIO;
    }
    public String getPASSWORD() { return PASSWORD;}
    public String getUSUARIO_CREADOR() { return USUARIO_CREADOR; }
    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }
    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }
    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }
    public boolean isLIGA() {
        return LIGA;
    }
}