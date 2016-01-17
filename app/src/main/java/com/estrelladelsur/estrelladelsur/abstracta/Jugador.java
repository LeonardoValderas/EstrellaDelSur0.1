package com.estrelladelsur.estrelladelsur.abstracta;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import android.graphics.Bitmap.CompressFormat;

public class Jugador {

	private int ID_JUGADOR;
	private String NOMBRE_JUGADOR;
	private byte[] FOTO_JUGADOR;
	private int ID_DIVISION;
	private int ID_POSICION;


	public Jugador(int id_jugador, String nombre, byte[] foto, int division,
			int posicion) {

		ID_JUGADOR = id_jugador;
		NOMBRE_JUGADOR = nombre;
		FOTO_JUGADOR = foto;
		ID_DIVISION = division;
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
