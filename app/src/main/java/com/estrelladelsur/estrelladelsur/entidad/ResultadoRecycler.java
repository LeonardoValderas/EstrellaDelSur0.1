package com.estrelladelsur.estrelladelsur.entidad;

public class ResultadoRecycler {

	private int ID_FIXTURE;
	private int ID_EQUIPO_LOCAL;
	private int ID_EQUIPO_VISITA;
	private String RESULTADO_LOCAL;
	private String RESULTADO_VISITA;
	private String EQUIPO_LOCAL;
	private String EQUIPO_VISITA;
	private byte[] ESCUDOLOCAL;
	private byte[] ESCUDOVISITA;

	public ResultadoRecycler(int id,int id_equipo_local, String equipo_local,byte[]escudoLocal,String resultado_local,int id_equipo_visita, String equipo_visita,byte[] escudovisita,String resultado_visita) {
		ID_FIXTURE = id;
		ID_EQUIPO_LOCAL = id_equipo_local;
		EQUIPO_LOCAL=equipo_local;
		ESCUDOLOCAL = escudoLocal;
		RESULTADO_LOCAL= resultado_local;
		ID_EQUIPO_VISITA = id_equipo_visita;
		EQUIPO_VISITA=equipo_visita;
		ESCUDOVISITA = escudovisita;
		RESULTADO_VISITA=resultado_visita;
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
	public String getRESULTADO_LOCAL() {
		return RESULTADO_LOCAL;
	}
	public void setRESULTADO_LOCAL(String rESULTADO_LOCAL) {
		RESULTADO_LOCAL = rESULTADO_LOCAL;
	}
	public String getRESULTADO_VISITA() {
		return RESULTADO_VISITA;
	}
	public void setRESULTADO_VISITA(String rESULTADO_VISITA) {
		RESULTADO_VISITA = rESULTADO_VISITA;
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