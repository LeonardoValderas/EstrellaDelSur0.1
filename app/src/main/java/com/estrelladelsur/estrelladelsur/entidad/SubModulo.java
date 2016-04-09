package com.estrelladelsur.estrelladelsur.entidad;

public class SubModulo {
    //SUBMODULO
    private int ID_SUBMODULO;
    private String SUBMODULO;
    private int ID_MODULO;
    private String MODULO;

       //SUBMODULO
    public SubModulo(int id_submodulo, String submodulo, int id_modulo, String modulo) {
        ID_SUBMODULO = id_submodulo;
        SUBMODULO = submodulo;
        ID_MODULO = id_modulo;
        MODULO = modulo;
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
}