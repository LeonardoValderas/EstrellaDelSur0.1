package com.estrelladelsur.estrelladelsur.abstracta;

public class Fecha {

	private int ID_FECHA;
	private String FECHA;
	

	public Fecha(int id, String fecha) {

		ID_FECHA = id;
		FECHA = fecha;
	   

	}


	public int getID_FECHA() {
		return ID_FECHA;
	}


	public void setID_FECHA(int iD_FECHA) {
		ID_FECHA = iD_FECHA;
	}


	public String getFECHA() {
		return FECHA;
	}

	public String toString() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	

	
}