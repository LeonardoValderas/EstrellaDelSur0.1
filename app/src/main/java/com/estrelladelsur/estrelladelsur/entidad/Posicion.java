package com.estrelladelsur.estrelladelsur.entidad;

public class Posicion {

	private int ID_POSICION;
	private String DESCRIPCION;
	private String USUARIO_CREADOR;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Posicion(int id, String descripcion,String usuario,
					String fechaCreacion, String usuario_act, String fechaActualizacion) {
		ID_POSICION = id;
		DESCRIPCION = descripcion;
		USUARIO_CREADOR = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}
	public Posicion(int id, String descripcion) {
		ID_POSICION = id;
		DESCRIPCION = descripcion;

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

	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}

	public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
		this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
	}

	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}

	public void setFECHA_ACTUALIZACION(String FECHA_ACTUALIZACION) {
		this.FECHA_ACTUALIZACION = FECHA_ACTUALIZACION;
	}

	public int getID_POSICION() {
		return ID_POSICION;
	}
	public void setID_POSICION(int iD_POSICION) {
		ID_POSICION = iD_POSICION;
	}
	public String getDESCRIPCION() {
		return DESCRIPCION;
	}
	public String toString() {
		return DESCRIPCION;
	}
	
	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}
}