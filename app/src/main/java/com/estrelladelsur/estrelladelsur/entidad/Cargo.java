package com.estrelladelsur.estrelladelsur.entidad;

public class Cargo {

	private int ID_CARGO;
	private String CARGO;
	private String USUARIO_CREADOR;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Cargo(int id, String cargo, String usuario,
				 String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {

		ID_CARGO = id;
		CARGO = cargo;
		USUARIO_CREADOR=usuario;
		FECHA_CREACION = fecha_creacion;
		USUARIO_ACTUALIZACION = usuario_actualizacion;
		FECHA_ACTUALIZACION = fecha_actualizacion;
	   
	}

	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}

	public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
		this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
	}

	public int getID_CARGO() {
		return ID_CARGO;
	}

	public void setID_CARGO(int ID_CARGO) {
		this.ID_CARGO = ID_CARGO;
	}

	public String toString() {
		return CARGO;
	}
	public String getCARGO() {
		return CARGO;
	}

	public void setCARGO(String CARGO) {
		this.CARGO = CARGO;
	}

	public String getUSUARIO_CREADOR() {
		return USUARIO_CREADOR;
	}

	public void setUSUARIO_CREADOR(String USUARIO_CREADOR) {
		this.USUARIO_CREADOR = USUARIO_CREADOR;
	}

	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}

	public void setFECHA_CREACION(String FECHA_CREACION) {
		this.FECHA_CREACION = FECHA_CREACION;
	}

	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}

	public void setFECHA_ACTUALIZACION(String FECHA_ACTUALIZACION) {
		this.FECHA_ACTUALIZACION = FECHA_ACTUALIZACION;
	}
}