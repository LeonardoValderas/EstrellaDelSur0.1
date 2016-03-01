package com.estrelladelsur.estrelladelsur.entidad;

public class EntrenamientoAsistencia {

	private int ID_ENTRENAMIENTO_DIVISION;
	private int ID_ENTRENAMIENTO;
	private int ID_DIVISION;
	private String DESCRIPCION;
	private int ID_JUGADOR;
	private String NOMBRE;
	private boolean isSelected;

	public EntrenamientoAsistencia(int entrenamiento_division, int id_entrenamiento, int id_division, String descricpion, int id_jugador, String nombre, boolean isSelected) {

		ID_ENTRENAMIENTO_DIVISION = entrenamiento_division;
		ID_ENTRENAMIENTO = id_entrenamiento;
		ID_DIVISION = id_division;
		DESCRIPCION = descricpion;
        ID_JUGADOR = id_jugador;
        NOMBRE = nombre;
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

	public int getID_JUGADOR() {
		return ID_JUGADOR;
	}

	public void setID_JUGADOR(int ID_JUGADOR) {
		this.ID_JUGADOR = ID_JUGADOR;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}

	public void setNOMBRE(String NOMBRE) {
		this.NOMBRE = NOMBRE;
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