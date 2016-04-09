package com.estrelladelsur.estrelladelsur.entidad;

public class Permiso {
    //PERMISO
    private int ID_PERMISO;
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

    //ENTRENAMIENTO
    public Permiso(int id, int id_usuario, String user, String pass, int id_modulo, int id_submodulo,
                   String usuario, String modulo, String submodulo, boolean isSelected, String fechaCreacion,
                   String usuario_act, String fechaActualizacion) {
        ID_PERMISO = id;
        ID_USUARIO = id_usuario;
        USUARIO = user;
        PASSWORD = pass;
        ID_MODULO = id_modulo;
        ID_SUBMODULO = id_submodulo;
        MODULO = modulo;
        SUBMODULO = submodulo;
        boolean ISSELECTED = isSelected;
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
}