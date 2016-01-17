package com.estrelladelsur.estrelladelsur.abstracta;

public class Resultado {
	
	private int ID_FIXTURE;
	private int ID_EQUIPO_LOCAL;
	private int ID_EQUIPO_VISITA;
	private String RESULTADO_VISITA;
	private String RESULTADO_LOCAL;
	private int ID_DIVISION;
	private int ID_TORNEO;
	private int ID_CANCHA;
	private int ID_FECHA;
	private int ID_ANIO;
	private String DIA;
	private String HORA;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;


	public Resultado(int id, int id_equipo_local, int id_equipo_visita,String resultado_local,String resultado_visita, int id_division, int id_torneo,int id_cancha, int id_fecha,int id_anio,String dia,String hora, String usuario,
			String fechaCreacion,String usuarioActualizacion, String fechaActualizacion) {

		ID_FIXTURE = id;
		ID_EQUIPO_LOCAL=id_equipo_local;
		ID_EQUIPO_VISITA=id_equipo_visita;
		RESULTADO_LOCAL = resultado_local;
		RESULTADO_VISITA= resultado_visita;
		ID_DIVISION=id_division;
		ID_TORNEO=id_torneo;
		ID_CANCHA=id_cancha;
		ID_FECHA=id_fecha;
		ID_ANIO=id_anio;
		DIA = dia;
		HORA = hora;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuarioActualizacion;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}

	public String getUSUARIO_CREACION() {
		return USUARIO_CREACION;
	}

	public void setUSUARIO_CREACION(String USUARIO_CREACION) {
		this.USUARIO_CREACION = USUARIO_CREACION;
	}

	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}

	public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
		this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
	}

	public int getID_FIXTURE() {
		return ID_FIXTURE;
	}

	public void setID_FIXTURE(int iD_FIXTURE) {
		ID_FIXTURE = iD_FIXTURE;
	}

	public int getID_EQUIPO_LOCAL() {
		return ID_EQUIPO_LOCAL;
	}

	public void setID_EQUIPO_LOCAL(int iD_EQUIPO_LOCAL) {
		ID_EQUIPO_LOCAL = iD_EQUIPO_LOCAL;
	}

	public int getID_EQUIPO_VISITA() {
		return ID_EQUIPO_VISITA;
	}

	public void setID_EQUIPO_VISITA(int iD_EQUIPO_VISITA) {
		ID_EQUIPO_VISITA = iD_EQUIPO_VISITA;
	}

	public String getRESULTADO_VISITA() {
		return RESULTADO_VISITA;
	}

	public void setRESULTADO_VISITA(String rESULTADO_VISITA) {
		RESULTADO_VISITA = rESULTADO_VISITA;
	}

	public String getRESULTADO_LOCAL() {
		return RESULTADO_LOCAL;
	}

	public void setRESULTADO_LOCAL(String rESULTADO_LOCAL) {
		RESULTADO_LOCAL = rESULTADO_LOCAL;
	}

	public int getID_DIVISION() {
		return ID_DIVISION;
	}

	public void setID_DIVISION(int iD_DIVISION) {
		ID_DIVISION = iD_DIVISION;
	}

	public int getID_TORNEO() {
		return ID_TORNEO;
	}

	public void setID_TORNEO(int iD_TORNEO) {
		ID_TORNEO = iD_TORNEO;
	}

	public int getID_CANCHA() {
		return ID_CANCHA;
	}

	public void setID_CANCHA(int iD_CANCHA) {
		ID_CANCHA = iD_CANCHA;
	}

	public int getID_FECHA() {
		return ID_FECHA;
	}

	public void setID_FECHA(int iD_FECHA) {
		ID_FECHA = iD_FECHA;
	}

	public int getID_ANIO() {
		return ID_ANIO;
	}

	public void setID_ANIO(int iD_ANIO) {
		ID_ANIO = iD_ANIO;
	}

	public String getDIA() {
		return DIA;
	}

	public void setDIA(String dIA) {
		DIA = dIA;
	}

	public String getHORA() {
		return HORA;
	}

	public void setHORA(String hORA) {
		HORA = hORA;
	}

	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}

	public void setFECHA_CREACION(String fECHA_CREACION) {
		FECHA_CREACION = fECHA_CREACION;
	}

	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}

	public void setFECHA_ACTUALIZACION(String fECHA_ACTUALIZACION) {
		FECHA_ACTUALIZACION = fECHA_ACTUALIZACION;
	}

}