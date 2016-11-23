package com.estrelladelsur.estrelladelsur.entidad;

public class Publicidad {

	private int ID_PUBLICIDAD;
	private String TITULO;
	private byte[] LOGO;
	private String OTROS;
	private String NOMBRE_FOTO;
	private String URL_FOTO;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Publicidad(int id_publicidad, String titulo, byte[] logo, String otros, String nombre_foto, String url_foto, String usuario, String fechaCreacion, String usuario_act, String fechaActualizacion) {

		ID_PUBLICIDAD = id_publicidad;
		TITULO = titulo;
		LOGO = logo;
		OTROS = otros;
		NOMBRE_FOTO = nombre_foto;
		URL_FOTO = url_foto;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}
	public Publicidad(int id_publicidad, String titulo, byte[] logo, String otros) {

		ID_PUBLICIDAD = id_publicidad;
		TITULO = titulo;
		LOGO = logo;
		OTROS = otros;
	}

	public int getID_PUBLICIDAD() {
		return ID_PUBLICIDAD;
	}
	public byte[] getLOGO() {
		return LOGO;
	}
	public String getOTROS() {
		return OTROS;
	}
	public String getTITULO() {
		return TITULO;
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
