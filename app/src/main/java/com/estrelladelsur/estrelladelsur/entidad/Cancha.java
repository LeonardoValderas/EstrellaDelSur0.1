package com.estrelladelsur.estrelladelsur.entidad;

public class Cancha {

	private int ID_CANCHA;
	private String NOMBRE;
	private String LONGITUD;
	private String LATITUD;
	private String DIRECCION;
	private String USUARIO_CREADOR;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;


	public String getUSUARIO_CREADOR() {
		return USUARIO_CREADOR;
	}

	public void setUSUARIO_CREADOR(String USUARIO_CREADOR) {
		this.USUARIO_CREADOR = USUARIO_CREADOR;
	}

	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}

	public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
		this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
	}

	public Cancha(int id, String nombre, String logitud,String latitud,String direccion,  String usuario,
			String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_CANCHA = id;
		NOMBRE = nombre;
	    LONGITUD=logitud;
		LATITUD=latitud;
		DIRECCION=direccion;
		USUARIO_CREADOR = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}

	public int getID_CANCHA() {
		return ID_CANCHA;
	}

	public void setID_CANCHA(int iD_CANCHA) {
		ID_CANCHA = iD_CANCHA;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}
	public String toString() {
		return NOMBRE;
	}

	
	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}

	public String getLONGITUD() {
		return LONGITUD;
	}

	public void setLONGITUD(String lONGITUD) {
		LONGITUD = lONGITUD;
	}

	public String getLATITUD() {
		return LATITUD;
	}

	public void setLATITUD(String lATITUD) {
		LATITUD = lATITUD;
	}

	public String getDIRECCION() {
		return DIRECCION;
	}

	public void setDIRECCION(String dIRECCION) {
		DIRECCION = dIRECCION;
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