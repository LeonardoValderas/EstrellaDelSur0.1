package com.estrelladelsur.estrelladelsur.entidad;

public class Mes {

	private int ID_MES;
	private String MES;
	

	public Mes(int id, String mes) {

		ID_MES = id;
		MES = mes;
	   
	}


	public int getID_MES() {
		return ID_MES;
	}


	public void setID_MES(int iD_MES) {
		ID_MES = iD_MES;
	}


	public String getMES() {
		return MES;
	}

	public String toString(){
		return MES;
	}

	public void setMES(String mES) {
		MES = mES;
	}



	
}