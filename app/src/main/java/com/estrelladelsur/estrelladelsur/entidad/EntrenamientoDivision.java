package com.estrelladelsur.estrelladelsur.entidad;

public class EntrenamientoDivision {

	private int ID_ENTRENAMIENTO_DIVISION;
	private int ID_ENTRENAMIENTO;
	private int ID_DIVISION;
	private String DESCRIPCION;
	private boolean isSelected;

	public EntrenamientoDivision(int entrenamiento_division, int id_entrenamiento, int id_division, String descricpion, boolean isSelected) {

		ID_ENTRENAMIENTO_DIVISION = entrenamiento_division;
		ID_ENTRENAMIENTO = id_entrenamiento;
		ID_DIVISION = id_division;
		DESCRIPCION = descricpion;
		isSelected=isSelected;
	}
	public int getID_ENTRENAMIENTO_DIVISION() {
		return ID_ENTRENAMIENTO_DIVISION;
	}
	public void setID_ENTRENAMIENTO_DIVISION(int iD_ENTRENAMIENTO_DIVISION) {
		ID_ENTRENAMIENTO_DIVISION = iD_ENTRENAMIENTO_DIVISION;	}
	public int getID_ENTRENAMIENTO() {
		return ID_ENTRENAMIENTO;
	}
	public void setID_ENTRENAMIENTO(int iD_ENTRENAMIENTO) {
		ID_ENTRENAMIENTO = iD_ENTRENAMIENTO;
	}
	public int getID_DIVISION() {
		return ID_DIVISION;
	}
	public void setID_DIVISION(int iD_DIVISION) {
		ID_DIVISION = iD_DIVISION;
	}
	public String getDESCRIPCION() {
		return DESCRIPCION;
	}
	public String toString() {
		return DESCRIPCION;
	}
	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}