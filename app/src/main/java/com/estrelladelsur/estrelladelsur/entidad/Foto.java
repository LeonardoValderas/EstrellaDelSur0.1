package com.estrelladelsur.estrelladelsur.entidad;

public class Foto {

	private int ID_FOTO;
	private String TITULO;
	private byte[] FOTO;
	private String NOMBRE_FOTO;
	private String URL_FOTO;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Foto(int id_foto, String titulo, byte[] foto, String nombre_foto, String url_foto, String usuario, String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_FOTO = id_foto;
		TITULO = titulo;
		FOTO = foto;
		NOMBRE_FOTO = nombre_foto;
		URL_FOTO = url_foto;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}
	public Foto(int id_foto, String titulo, byte[] foto) {
		ID_FOTO = id_foto;
		TITULO = titulo;
		FOTO = foto;
	}
	public Foto(int id_foto, String titulo, String foto) {
		ID_FOTO = id_foto;
		TITULO = titulo;
		URL_FOTO = foto;
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

	public String getNOMBRE_FOTO() {
		return NOMBRE_FOTO;
	}

	public void setNOMBRE_FOTO(String NOMBRE_FOTO) {
		this.NOMBRE_FOTO = NOMBRE_FOTO;
	}

	public String getURL_FOTO() {
		return URL_FOTO;
	}

	public void setURL_FOTO(String URL_FOTO) {
		this.URL_FOTO = URL_FOTO;
	}
}
