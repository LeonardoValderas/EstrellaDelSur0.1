package com.estrelladelsur.estrelladelsur.entidad;

public class Division {

	private int ID_DIVISION;
	private String DESCRIPCION;
	private String USUARIO_CREADOR;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Division(int id, String descripcion, String usuario,
			String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_DIVISION = id;
		DESCRIPCION = descripcion;
		USUARIO_CREADOR = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}

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

	public int getID_DIVISION() {
		return ID_DIVISION;
	}

	public void setID_DIVISION(int iD_DIVISION) {
		ID_DIVISION = iD_DIVISION;
	}

	public String getDESCRIPCION() {
		return DESCRIPCION;
	}

	public String toString() {
		return DESCRIPCION;
	}

	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}

	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}

	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}

	public void setFECHA_CREACION(String fECHA_CREACION) {
		FECHA_CREACION = fECHA_CREACION;
	}

	public void setFECHA_ACTUALIZACION(String fECHA_ACTUALIZACION) {
		FECHA_ACTUALIZACION = fECHA_ACTUALIZACION;
	}

}