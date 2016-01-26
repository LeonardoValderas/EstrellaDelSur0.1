package com.estrelladelsur.estrelladelsur.entidad;

public class JugadorRecycler {

	private int ID_JUGADOR;
	private String NOMBRE_JUGADOR;
	private String NOMBRE_DIVISION;
	private String NOMBRE_POSICION;
	private byte[] FOTO_JUGADOR;
	private int ID_DIVISION;
	private int ID_POSICION;

	public JugadorRecycler(int id_jugador, String nombre, byte[] foto, int division, String nombre_division,
			int posicion, String nombre_posicion) {

		ID_JUGADOR = id_jugador;
		NOMBRE_JUGADOR = nombre;
		FOTO_JUGADOR = foto;
		ID_DIVISION = division;
	    NOMBRE_DIVISION=nombre_division;
	    NOMBRE_POSICION=nombre_posicion;
		ID_POSICION = posicion;
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
	public String getNOMBRE_DIVISION() {
		return NOMBRE_DIVISION;
	}
	public void setNOMBRE_DIVISION(String nOMBRE_DIVISION) {
		NOMBRE_DIVISION = nOMBRE_DIVISION;
	}
	public String getNOMBRE_POSICION() {
		return NOMBRE_POSICION;
	}
	public void setNOMBRE_POSICION(String nOMBRE_POSICION) {
		NOMBRE_POSICION = nOMBRE_POSICION;
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
}
