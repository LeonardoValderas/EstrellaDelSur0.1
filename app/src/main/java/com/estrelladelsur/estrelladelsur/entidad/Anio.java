package com.estrelladelsur.estrelladelsur.entidad;

public class Anio {

	private int ID_ANIO;
	private String ANIO;

	public Anio(int id, String anio) {
		ID_ANIO = id;
		ANIO = anio;
	}
	public int getID_ANIO() {
		return ID_ANIO;
	}
	public String getANIO() {
		return ANIO;
	}
	public String toString() {
		return ANIO;
	}
}