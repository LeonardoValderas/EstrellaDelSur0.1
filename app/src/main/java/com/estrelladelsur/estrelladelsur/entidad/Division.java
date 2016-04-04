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
	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
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
	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}
	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}
}