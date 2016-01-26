package com.estrelladelsur.estrelladelsur.entidad;

public class Torneo {

	private int ID_TORNEO;
	private String DESCRIPCION;
	private String USUARIO_CREADOR;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Torneo(int id, String descripcion, String usuario,
			String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_TORNEO = id;
		DESCRIPCION = descripcion;
		USUARIO_CREADOR = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;


	}

	public int getID_TORNEO() {
		return ID_TORNEO;
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

	public void setID_TORNEO(int iD_TORNEO) {
		ID_TORNEO = iD_TORNEO;
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