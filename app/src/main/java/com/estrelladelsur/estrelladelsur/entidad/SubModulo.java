package com.estrelladelsur.estrelladelsur.entidad;

public class SubModulo {
    //SUBMODULO
    private int ID_SUBMODULO;
    private int ID_PERMISO_MODULO;
    private String SUBMODULO;
    private int ID_MODULO;
    private String MODULO;
    private boolean ISSELECTED;

       //SUBMODULO
    public SubModulo(int id_submodulo, String submodulo, int id_modulo, String modulo, boolean isSelected) {
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ID_MODULO = id_modulo;
        MODULO = modulo;
        ISSELECTED = isSelected;
    }
    //SUBMODULO
    public SubModulo(int id_submodulo, String submodulo, int id_modulo, boolean isSelected) {
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ID_MODULO = id_modulo;
        ISSELECTED = isSelected;
    }
    //SUBMODULO
    public SubModulo(int id_submodulo, String submodulo, boolean isSelected) {
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ISSELECTED = isSelected;
    }
    //SUBMODULO
    public SubModulo(int id,int id_submodulo, String submodulo, boolean isSelected) {
        ID_PERMISO_MODULO = id;
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ISSELECTED = isSelected;
    }
    //SUBMODULO
    public SubModulo(int id_submodulo, String submodulo, int id_modulo) {
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ID_MODULO = id_modulo;
    }
    public int getID_MODULO() {
        return ID_MODULO;
    }
    public String getMODULO() {
        return MODULO;
    }
    public int getID_SUBMODULO() {
        return ID_SUBMODULO;
    }
    public String getSUBMODULO() {
        return SUBMODULO;
    }
    public boolean ISSELECTED() {
        return ISSELECTED;
    }
    public void setISSELECTED(boolean ISSELECTED) {
        this.ISSELECTED = ISSELECTED;
    }
    public int getID_PERMISO_MODULO() { return ID_PERMISO_MODULO; }
}