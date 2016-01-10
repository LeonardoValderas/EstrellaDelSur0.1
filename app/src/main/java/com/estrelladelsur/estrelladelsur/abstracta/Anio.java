package com.estrelladelsur.estrelladelsur.abstracta;

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


	public void setID_ANIO(int iD_ANIO) {
		ID_ANIO = iD_ANIO;
	}


	public String getANIO() {
		return ANIO;
	}

	public String toString() {
		return ANIO;
	}

	public void setANIO(String aNIO) {
		ANIO = aNIO;
	}



	
}