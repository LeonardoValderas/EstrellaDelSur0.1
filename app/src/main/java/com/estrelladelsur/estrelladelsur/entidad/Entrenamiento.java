package com.estrelladelsur.estrelladelsur.entidad;

public class Entrenamiento {
   //ENTRENAMIENTO
    private int ID_ENTRENAMIENTO;
    private int ID_CANCHA;
    private String DIA;
    private String HORA;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
     // ENTRENAMIENTO ASISTENCIA
    private int ID_ENTRENAMIENTO_ASISTENCIA;
    //private int ID_ENTRENAMIENTO;
    private int ID_DIVISION;
    private String DESCRIPCION;
    private int ID_JUGADOR;
    private String NOMBRE;
    private boolean ISSELECTED;
    //ENTRENAMIENTO DIVISION
    private int ID_ENTRENAMIENTO_DIVISION;
    private boolean isSelected;



    //ENTRENAMIENTO
    public Entrenamiento(int id, String dia, String hora, int id_cancha, String usuario,
                         String fechaCreacion, String usuario_act, String fechaActualizacion) {
        ID_ENTRENAMIENTO = id;
        DIA = dia;
        HORA = hora;
        ID_CANCHA = id_cancha;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }
    // ENTRENAMIENTO
    public String getFECHA_ACTUALIZACION() {return FECHA_ACTUALIZACION;}
    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }
    public String getFECHA_CREACION() {return FECHA_CREACION;}
    public String getUSUARIO_CREADOR() {return USUARIO_CREADOR;}
    public int getID_ENTRENAMIENTO() {
        return ID_ENTRENAMIENTO;
    }
    public int getID_CANCHA() {
        return ID_CANCHA;
    }
    public String getDIA() {return DIA;}
    public String getHORA() {
        return HORA;
    }
    // ENTRENAMIENTO ASISTENCIA
    public Entrenamiento(int entrenamiento_asistencia, int id_entrenamiento, int id_division, String descricpion, int id_jugador, String nombre, boolean isSelected) {

        ID_ENTRENAMIENTO_ASISTENCIA = entrenamiento_asistencia;
        ID_ENTRENAMIENTO = id_entrenamiento;
        ID_DIVISION = id_division;
        DESCRIPCION = descricpion;
        ID_JUGADOR = id_jugador;
        NOMBRE = nombre;
        ISSELECTED=isSelected;
    }
    // ENTRENAMIENTO ASISTENCIA 2
    public Entrenamiento(int id_entrenamiento, int id_division, int id_jugador) {

        ID_ENTRENAMIENTO = id_entrenamiento;
        ID_DIVISION = id_division;
        ID_JUGADOR = id_jugador;
    }
    // ENTRENAMIENTO ASISTENCIA 3
    public Entrenamiento(int id_entrenamiento_asistencia ,int id_entrenamiento, int id_jugador,String dato) {
        ID_ENTRENAMIENTO_ASISTENCIA = id_entrenamiento_asistencia;
        ID_ENTRENAMIENTO = id_entrenamiento;
        ID_JUGADOR = id_jugador;
    }
    // ENTRENAMIENTO ASISTENCIA
    public int getID_ENTRENAMIENTO_ASISTENCIA() {
        return ID_ENTRENAMIENTO_ASISTENCIA;
    }
    public int getID_DIVISION() {
        return ID_DIVISION;
    }
    public String getDESCRIPCION() {
        return DESCRIPCION;
    }
    public String toString() {
        return DESCRIPCION;
    }

    public int getID_JUGADOR() {
        return ID_JUGADOR;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }
    public boolean isSelected() {
        return ISSELECTED;
    }
    public void setSelected(boolean isSelected) {
        this.ISSELECTED = isSelected;
    }
    //ENTRENAMIENTO DIVISION
    public Entrenamiento(int entrenamiento_division, int id_entrenamiento, int id_division, String descricpion, boolean isSelected) {

        ID_ENTRENAMIENTO_DIVISION = entrenamiento_division;
        ID_ENTRENAMIENTO = id_entrenamiento;
        ID_DIVISION = id_division;
        DESCRIPCION = descricpion;
        isSelected=isSelected;
    }
    public int getID_ENTRENAMIENTO_DIVISION() {
        return ID_ENTRENAMIENTO_DIVISION;
    }
  }