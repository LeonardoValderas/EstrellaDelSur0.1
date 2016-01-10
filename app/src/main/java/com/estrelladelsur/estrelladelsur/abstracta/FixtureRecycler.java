package com.estrelladelsur.estrelladelsur.abstracta;

public class FixtureRecycler {

	private int ID_FIXTURE;
	private int ID_EQUIPO_LOCAL;
	private int ID_EQUIPO_VISITA;
	private int ID_CANCHA;
	private String EQUIPO_LOCAL;
	private String EQUIPO_VISITA;
	private String CANCHA;
	private String DIA;
	private String HORA;
	private byte[] ESCUDOLOCAL;
	private byte[] ESCUDOVISITA;

	public FixtureRecycler(int id,int id_equipo_local, String equipo_local,byte[]escudoLocal,int id_equipo_visita, String equipo_visita,byte[] escudovisita,int id_cancha,String cancha, String dia,String hora) {

		ID_FIXTURE = id;
		ID_EQUIPO_LOCAL = id_equipo_local;
		EQUIPO_LOCAL=equipo_local;
		ESCUDOLOCAL = escudoLocal;
		ID_EQUIPO_VISITA = id_equipo_visita;
		EQUIPO_VISITA=equipo_visita;
		ESCUDOVISITA = escudovisita;
		ID_CANCHA = id_cancha;
		CANCHA=cancha;
		DIA = dia;
		HORA = hora;

	}
	public String getCANCHA() {
		return CANCHA;
	}
	public void setCANCHA(String cANCHA) {
		CANCHA = cANCHA;
	}
	public void setID_CANCHA(int iD_CANCHA) {
		ID_CANCHA = iD_CANCHA;
	}
	public int getID_CANCHA() {
		return ID_CANCHA;
	}
	public int getID_FIXTURE() {
		return ID_FIXTURE;
	}
	public void setID_FIXTURE(int iD_FIXTURE) {
		ID_FIXTURE = iD_FIXTURE;
	}
	public int getID_EQUIPO_LOCAL() {
		return ID_EQUIPO_LOCAL;
	}
	public void setID_EQUIPO_LOCAL(int iD_EQUIPO_LOCAL) {
		ID_EQUIPO_LOCAL = iD_EQUIPO_LOCAL;
	}
	public int getID_EQUIPO_VISITA() {
		return ID_EQUIPO_VISITA;
	}
	public void setID_EQUIPO_VISITA(int iD_EQUIPO_VISITA) {
		ID_EQUIPO_VISITA = iD_EQUIPO_VISITA;
	}
	public String getEQUIPO_LOCAL() {
		return EQUIPO_LOCAL;
	}
	public void setEQUIPO_LOCAL(String eQUIPO_LOCAL) {
		EQUIPO_LOCAL = eQUIPO_LOCAL;
	}
	public String getEQUIPO_VISITA() {
		return EQUIPO_VISITA;
	}
	public void setEQUIPO_VISITA(String eQUIPO_VISITA) {
		EQUIPO_VISITA = eQUIPO_VISITA;
	}

	public String getDIA() {
		return DIA;
	}
	public void setDIA(String dIA) {
		DIA = dIA;
	}
	public String getHORA() {
		return HORA;
	}
	public void setHORA(String hORA) {
		HORA = hORA;
	}
	public byte[] getESCUDOLOCAL() {
		return ESCUDOLOCAL;
	}
	public void setESCUDOLOCAL(byte[] eSCUDOLOCAL) {
		ESCUDOLOCAL = eSCUDOLOCAL;
	}
	public byte[] getESCUDOVISITA() {
		return ESCUDOVISITA;
	}
	public void setESCUDOVISITA(byte[] eSCUDOVISITA) {
		ESCUDOVISITA = eSCUDOVISITA;
	}

	
	
}