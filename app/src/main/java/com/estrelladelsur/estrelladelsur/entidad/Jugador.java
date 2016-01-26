package com.estrelladelsur.estrelladelsur.entidad;

public class Jugador {

	private int ID_JUGADOR;
	private String NOMBRE_JUGADOR;
	private byte[] FOTO_JUGADOR;
	private int ID_DIVISION;
	private int ID_POSICION;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;

	public Jugador(int id_jugador, String nombre, byte[] foto, int division,
			int posicion,String usuario, String fechaCreacion,String usuario_act, String fechaActualizacion) {

		ID_JUGADOR = id_jugador;
		NOMBRE_JUGADOR = nombre;
		FOTO_JUGADOR = foto;
		ID_DIVISION = division;
		ID_POSICION = posicion;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuario_act;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}
	public int getID_JUGADOR() {
		return ID_JUGADOR;
	}
	public void setID_JUGADOR(int iD_JUGADOR) {
		ID_JUGADOR = iD_JUGADOR;
	}
	public String getNOMBRE_JUGADOR() {
		return NOMBRE_JUGADOR;
	}
	public void setNOMBRE_JUGADOR(String nOMBRE_JUGADOR) {
		NOMBRE_JUGADOR = nOMBRE_JUGADOR;
	}
	public byte[] getFOTO_JUGADOR() {
		return FOTO_JUGADOR;
	}
	public void setFOTO_JUGADOR(byte[] fOTO_JUGADOR) {
		FOTO_JUGADOR = fOTO_JUGADOR;
	}
	public int getID_DIVISION() {
		return ID_DIVISION;
	}
	public void setID_DIVISION(int iD_DIVISION) {
		ID_DIVISION = iD_DIVISION;
	}
	public int getID_POSICION() {
		return ID_POSICION;
	}
	public void setID_POSICION(int iD_POSICION) {
		ID_POSICION = iD_POSICION;
	}

	public String getUSUARIO_CREACION() {
		return USUARIO_CREACION;
	}

	public void setUSUARIO_CREACION(String USUARIO_CREACION) {
		this.USUARIO_CREACION = USUARIO_CREACION;
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
}
