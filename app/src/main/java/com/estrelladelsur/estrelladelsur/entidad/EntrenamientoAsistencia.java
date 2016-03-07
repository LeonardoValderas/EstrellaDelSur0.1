package com.estrelladelsur.estrelladelsur.entidad;

public class EntrenamientoAsistencia {

	private int ID_ENTRENAMIENTO_ASISTENCIA;
	private int ID_ENTRENAMIENTO;
	private int ID_DIVISION;
	private String DESCRIPCION;
	private int ID_JUGADOR;
	private String NOMBRE;
	private boolean ISSELECTED;

	public EntrenamientoAsistencia(int entrenamiento_asistencia, int id_entrenamiento, int id_division, String descricpion, int id_jugador, String nombre, boolean isSelected) {

		ID_ENTRENAMIENTO_ASISTENCIA = entrenamiento_asistencia;
		ID_ENTRENAMIENTO = id_entrenamiento;
		ID_DIVISION = id_division;
		DESCRIPCION = descricpion;
        ID_JUGADOR = id_jugador;
        NOMBRE = nombre;
		ISSELECTED=isSelected;
	}
	public EntrenamientoAsistencia(int id_entrenamiento, int id_division, int id_jugador) {

		ID_ENTRENAMIENTO = id_entrenamiento;
		ID_DIVISION = id_division;
		ID_JUGADOR = id_jugador;
	}
	public EntrenamientoAsistencia(int id_entrenamiento_asistencia ,int id_entrenamiento, int id_jugador,String dato) {
		ID_ENTRENAMIENTO_ASISTENCIA = id_entrenamiento_asistencia;
		ID_ENTRENAMIENTO = id_entrenamiento;
	    ID_JUGADOR = id_jugador;
	}
	public int getID_ENTRENAMIENTO_ASISTENCIA() {
		return ID_ENTRENAMIENTO_ASISTENCIA;
	}
	public void setID_ENTRENAMIENTO_ASISTENCIA(int iD_ENTRENAMIENTO_DIVISION) {
		ID_ENTRENAMIENTO_ASISTENCIA = iD_ENTRENAMIENTO_DIVISION;	}
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
		return ISSELECTED;
	}
	public void setSelected(boolean isSelected) {
		this.ISSELECTED = isSelected;
	}
}