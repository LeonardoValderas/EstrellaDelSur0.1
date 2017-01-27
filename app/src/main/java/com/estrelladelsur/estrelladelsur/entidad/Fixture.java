package com.estrelladelsur.estrelladelsur.entidad;

public class Fixture {

    private int ID_FIXTURE;
    private int ID_EQUIPO_LOCAL;
    private int ID_EQUIPO_VISITA;
    private int ID_DIVISION;
    private int ID_TORNEO;
    private int ID_CANCHA;
    private int ID_FECHA;
    private int ID_ANIO;
    private String DIA;
    private String HORA;
    private String FECHA;
    private String ANIO;
    private String DESCRIPCION;
    private String USUARIO_CREACION;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    //FIXTURE RECYCLER
    private String EQUIPO_LOCAL;
    private String RESULTADO_LOCAL;
    private String EQUIPO_VISITA;
    private String RESULTADO_VISITA;
    private String CANCHA;
    private byte[] ESCUDOLOCAL;
    private byte[] ESCUDOVISITA;
    private String ESCUDOLOCALURL;
    private String ESCUDOVISITAURL;

    public Fixture(int id, int id_equipo_local, int id_equipo_visita, int id_division, int id_torneo, int id_cancha, int id_fecha, int id_anio, String dia, String hora, String usuario,
                   String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_FIXTURE = id;
        ID_EQUIPO_LOCAL = id_equipo_local;
        ID_EQUIPO_VISITA = id_equipo_visita;
        ID_DIVISION = id_division;
        ID_TORNEO = id_torneo;
        ID_CANCHA = id_cancha;
        ID_FECHA = id_fecha;
        ID_ANIO = id_anio;
        DIA = dia;
        HORA = hora;
        USUARIO_CREACION = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }
    public Fixture(int id, int id_equipo_local, int id_equipo_visita, int id_division, int id_torneo, int id_cancha, int id_fecha, int id_anio, String dia, String hora, String rl, String rv, String usuario,
                   String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_FIXTURE = id;
        ID_EQUIPO_LOCAL = id_equipo_local;
        ID_EQUIPO_VISITA = id_equipo_visita;
        ID_DIVISION = id_division;
        ID_TORNEO = id_torneo;
        ID_CANCHA = id_cancha;
        ID_FECHA = id_fecha;
        ID_ANIO = id_anio;
        DIA = dia;
        HORA = hora;
        RESULTADO_LOCAL = rl;
        RESULTADO_VISITA = rv;
        USUARIO_CREACION = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public String getUSUARIO_CREACION() {
        return USUARIO_CREACION;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public int getID_FIXTURE() {
        return ID_FIXTURE;
    }

    public int getID_EQUIPO_LOCAL() {
        return ID_EQUIPO_LOCAL;
    }

    public int getID_EQUIPO_VISITA() {
        return ID_EQUIPO_VISITA;
    }

    public int getID_DIVISION() {
        return ID_DIVISION;
    }

    public int getID_TORNEO() {
        return ID_TORNEO;
    }

    public int getID_CANCHA() {
        return ID_CANCHA;
    }

    public int getID_FECHA() {
        return ID_FECHA;
    }

    public int getID_ANIO() {
        return ID_ANIO;
    }

    public String getDIA() {
        return DIA;
    }

    public String getHORA() {
        return HORA;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getANIO() {
        return ANIO;
    }

    public void setANIO(String ANIO) {
        this.ANIO = ANIO;
    }

    // ENTRENAMIENTO RECYCLER
    public Fixture(int id, int id_equipo_local, String equipo_local, byte[] escudoLocal, String r_local, int id_equipo_visita, String equipo_visita, byte[] escudovisita, String r_visita, int id_cancha, String cancha, String dia, String hora, String fecha, String anio) {

        ID_FIXTURE = id;
        ID_EQUIPO_LOCAL = id_equipo_local;
        EQUIPO_LOCAL = equipo_local;
        ESCUDOLOCAL = escudoLocal;
        RESULTADO_LOCAL = r_local;
        ID_EQUIPO_VISITA = id_equipo_visita;
        EQUIPO_VISITA = equipo_visita;
        ESCUDOVISITA = escudovisita;
        RESULTADO_VISITA = r_visita;
        ID_CANCHA = id_cancha;
        CANCHA = cancha;
        DIA = dia;
        HORA = hora;
        FECHA = fecha;
        ANIO = anio;
    }

    public Fixture(int id, int id_equipo_local, String equipo_local, String url_Local, String r_local, int id_equipo_visita, String equipo_visita, String url_visita, String r_visita, int id_cancha, String cancha, String dia, String hora, String fecha, String anio) {

        ID_FIXTURE = id;
        ID_EQUIPO_LOCAL = id_equipo_local;
        EQUIPO_LOCAL = equipo_local;
        ESCUDOLOCALURL = url_Local;
        RESULTADO_LOCAL = r_local;
        ID_EQUIPO_VISITA = id_equipo_visita;
        EQUIPO_VISITA = equipo_visita;
        ESCUDOVISITAURL = url_visita;
        RESULTADO_VISITA = r_visita;
        ID_CANCHA = id_cancha;
        CANCHA = cancha;
        DIA = dia;
        HORA = hora;
        FECHA = fecha;
        ANIO = anio;
    }

    // ENTRENAMIENTO RECYCLER
    public Fixture(int id, int id_equipo_local, int id_equipo_visita, int id_division, int id_torneo, int id_cancha, String fecha, String anio, String dia, String hora, String rlocal, String rvisita) {
        ID_FIXTURE = id;
        ID_EQUIPO_LOCAL = id_equipo_local;
        ID_EQUIPO_VISITA = id_equipo_visita;
        ID_DIVISION = id_division;
        ID_TORNEO = id_torneo;
        ID_CANCHA = id_cancha;
        FECHA = fecha;
        ANIO = anio;
        DIA = dia;
        HORA = hora;
        RESULTADO_LOCAL = rlocal;
        RESULTADO_VISITA = rvisita;
    }

    // ENTRENAMIENTO USUARIO
    public Fixture(int id, String equipo_local, byte[] escudoLocal, String r_local, String equipo_visita, byte[] escudovisita, String r_visita, String cancha, String dia, String hora, String fecha, String anio) {

        ID_FIXTURE = id;
        EQUIPO_LOCAL = equipo_local;
        ESCUDOLOCAL = escudoLocal;
        RESULTADO_LOCAL = r_local;
        EQUIPO_VISITA = equipo_visita;
        ESCUDOVISITA = escudovisita;
        RESULTADO_VISITA = r_visita;
        CANCHA = cancha;
        DIA = dia;
        HORA = hora;
        FECHA = fecha;
        ANIO = anio;
    }
    // ENTRENAMIENTO RECYCLER USUARIO
    public Fixture(int id, String equipo_local, byte[] escudoLocal, String r_local, String equipo_visita, byte[] escudovisita, String r_visita, String cancha, String dia, String hora, String fecha, String anio, String descripcion) {
        ID_FIXTURE = id;
        EQUIPO_LOCAL = equipo_local;
        ESCUDOLOCAL = escudoLocal;
        RESULTADO_LOCAL = r_local;
        EQUIPO_VISITA = equipo_visita;
        ESCUDOVISITA = escudovisita;
        RESULTADO_VISITA = r_visita;
        CANCHA = cancha;
        DIA = dia;
        HORA = hora;
        FECHA = fecha;
        ANIO = anio;
        DESCRIPCION = descripcion;
    }
    public Fixture(int id, String equipo_local, String escudoLocal, String r_local, String equipo_visita, String escudovisita, String r_visita, String cancha, String dia, String hora, String fecha, String anio, String descripcion) {
        ID_FIXTURE = id;
        EQUIPO_LOCAL = equipo_local;
        ESCUDOLOCALURL = escudoLocal;
        RESULTADO_LOCAL = r_local;
        EQUIPO_VISITA = equipo_visita;
        ESCUDOVISITAURL = escudovisita;
        RESULTADO_VISITA = r_visita;
        CANCHA = cancha;
        DIA = dia;
        HORA = hora;
        FECHA = fecha;
        ANIO = anio;
        DESCRIPCION = descripcion;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    // ENTRENAMIENTO RECYCLER
    public String getRESULTADO_LOCAL() {
        return RESULTADO_LOCAL;
    }

    public String getRESULTADO_VISITA() {
        return RESULTADO_VISITA;
    }

    public String getCANCHA() {
        return CANCHA;
    }

    public String getEQUIPO_LOCAL() {
        return EQUIPO_LOCAL;
    }

    public String getEQUIPO_VISITA() {
        return EQUIPO_VISITA;
    }

    public byte[] getESCUDOLOCAL() {
        return ESCUDOLOCAL;
    }

    public byte[] getESCUDOVISITA() {
        return ESCUDOVISITA;
    }

    public String getESCUDOLOCALURL() {
        return ESCUDOLOCALURL;
    }

    public void setESCUDOLOCALURL(String ESCUDOLOCALURL) {
        this.ESCUDOLOCALURL = ESCUDOLOCALURL;
    }

    public String getESCUDOVISITAURL() {
        return ESCUDOVISITAURL;
    }

    public void setESCUDOVISITAURL(String ESCUDOVISITAURL) {
        this.ESCUDOVISITAURL = ESCUDOVISITAURL;
    }
}