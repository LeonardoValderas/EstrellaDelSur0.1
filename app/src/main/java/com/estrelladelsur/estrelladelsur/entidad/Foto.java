package com.estrelladelsur.estrelladelsur.entidad;

public class Foto {

	private int ID_FOTO;
	private String TITULO;
	private byte[] FOTO;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Foto(int id_foto, String titulo, byte[] foto, String usuario, String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_FOTO = id_foto;
		TITULO = titulo;
		FOTO = foto;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}

	public int getID_FOTO() {
		return ID_FOTO;
	}

	public String getTITULO() {
		return TITULO;
	}

	public byte[] getFOTO() {
		return FOTO;
	}

	public String getUSUARIO_CREACION() {
		return USUARIO_CREACION;
	}

	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}

	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}

	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}
}
