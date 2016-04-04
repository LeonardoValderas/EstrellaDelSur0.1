package com.estrelladelsur.estrelladelsur.entidad;

public class Resultado {
	
	private int ID_FIXTURE;
	private int ID_EQUIPO_LOCAL;
	private int ID_EQUIPO_VISITA;
	private String RESULTADO_VISITA;
	private String RESULTADO_LOCAL;
	private int ID_DIVISION;
	private int ID_TORNEO;
	private int ID_CANCHA;
	private int ID_FECHA;
	private int ID_ANIO;
	private String DIA;
	private String HORA;
	private String USUARIO_CREACION;
	private String FECHA_CREACION;
	private String USUARIO_ACTUALIZACION;
	private String FECHA_ACTUALIZACION;
    //RESULTADO RECYCLER
	private String EQUIPO_LOCAL;
	private String EQUIPO_VISITA;
	private byte[] ESCUDOLOCAL;
	private byte[] ESCUDOVISITA;

	public Resultado(int id, int id_equipo_local, int id_equipo_visita,String resultado_local,String resultado_visita, int id_division, int id_torneo,int id_cancha, int id_fecha,int id_anio,String dia,String hora, String usuario,
			String fechaCreacion,String usuarioActualizacion, String fechaActualizacion) {

		ID_FIXTURE = id;
		ID_EQUIPO_LOCAL=id_equipo_local;
		ID_EQUIPO_VISITA=id_equipo_visita;
		RESULTADO_LOCAL = resultado_local;
		RESULTADO_VISITA= resultado_visita;
		ID_DIVISION=id_division;
		ID_TORNEO=id_torneo;
		ID_CANCHA=id_cancha;
		ID_FECHA=id_fecha;
		ID_ANIO=id_anio;
		DIA = dia;
		HORA = hora;
		USUARIO_CREACION = usuario;
		FECHA_CREACION = fechaCreacion;
		USUARIO_ACTUALIZACION = usuarioActualizacion;
		FECHA_ACTUALIZACION = fechaActualizacion;
	}

	public String getUSUARIO_CREACION() {
		return USUARIO_CREACION;
	}
	public String getUSUARIO_ACTUALIZACION() {
		return USUARIO_ACTUALIZACION;
	}
	public int getID_FIXTURE() {
		return ID_FIXTURE;
	}
	public int getID_EQUIPO_LOCAL() {
		return ID_EQUIPO_LOCAL;
	}
	public int getID_EQUIPO_VISITA() {
		return ID_EQUIPO_VISITA;
	}
	public String getRESULTADO_VISITA() {
		return RESULTADO_VISITA;
	}
	public String getRESULTADO_LOCAL() {
		return RESULTADO_LOCAL;
	}
	public int getID_DIVISION() {
		return ID_DIVISION;
	}
	public int getID_TORNEO() {
		return ID_TORNEO;
	}
	public int getID_CANCHA() {
		return ID_CANCHA;
	}
	public int getID_FECHA() {
		return ID_FECHA;
	}
	public int getID_ANIO() {
		return ID_ANIO;
	}
	public String getDIA() {
		return DIA;
	}
	public String getHORA() {
		return HORA;
	}
	public String getFECHA_CREACION() {
		return FECHA_CREACION;
	}
	public String getFECHA_ACTUALIZACION() {
		return FECHA_ACTUALIZACION;
	}
   // RESULTADO RECYCLER
	public Resultado(int id,int id_equipo_local, String equipo_local,byte[]escudoLocal,String resultado_local,int id_equipo_visita, String equipo_visita,byte[] escudovisita,String resultado_visita) {
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

	public String getEQUIPO_LOCAL() {
		return EQUIPO_LOCAL;
	}
	public String getEQUIPO_VISITA() {
		return EQUIPO_VISITA;
	}
	public byte[] getESCUDOLOCAL() {
		return ESCUDOLOCAL;
	}
	public byte[] getESCUDOVISITA() {
		return ESCUDOVISITA;
	}
}