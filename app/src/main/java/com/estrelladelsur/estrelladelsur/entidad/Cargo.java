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
	public int getID_CARGO() {
		return ID_CARGO;
	}
	public String toString() {
		return CARGO;
	}
	public String getCARGO() {
		return CARGO;
	}
	public String getUSUARIO_CREADOR() {
		return USUARIO_CREADOR;
	}
	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}
	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}
}