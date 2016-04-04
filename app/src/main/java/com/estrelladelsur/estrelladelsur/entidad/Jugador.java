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
    //JUGADOR RECYCLER
	private String NOMBRE_DIVISION;
	private String NOMBRE_POSICION;



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
	public String getNOMBRE_JUGADOR() {
		return NOMBRE_JUGADOR;
	}
	public byte[] getFOTO_JUGADOR() {
		return FOTO_JUGADOR;
	}
	public int getID_DIVISION() {
		return ID_DIVISION;
	}
	public int getID_POSICION() {
		return ID_POSICION;
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

	public Jugador(int id_jugador, String nombre, byte[] foto, int division, String nombre_division,
						   int posicion, String nombre_posicion) {

		ID_JUGADOR = id_jugador;
		NOMBRE_JUGADOR = nombre;
		FOTO_JUGADOR = foto;
		ID_DIVISION = division;
		NOMBRE_DIVISION=nombre_division;
		NOMBRE_POSICION=nombre_posicion;
		ID_POSICION = posicion;
	}

	public String toString() {
		return NOMBRE_JUGADOR;
	}
	public String getNOMBRE_DIVISION() {
		return NOMBRE_DIVISION;
	}
	public String getNOMBRE_POSICION() {
		return NOMBRE_POSICION;
	}
}
