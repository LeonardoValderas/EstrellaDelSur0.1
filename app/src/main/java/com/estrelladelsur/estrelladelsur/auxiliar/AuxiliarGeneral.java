package com.estrelladelsur.estrelladelsur.auxiliar;

import android.content.Context;

/**
 * Created by LEO on 31/1/2016.
 */
public class AuxiliarGeneral {
    private Context context;
    public  AuxiliarGeneral(Context context){

        this.context = context;
    }

    public String setFormatoMes(String mes){
        String mesFormato = null;
        switch (mes){
            case "ENERO":
                mesFormato="01";
                break;
            case "FEBRERO":
                mesFormato="02";
                break;
            case "MARZO":
                mesFormato="03";
                break;
            case "ABRIL":
                mesFormato="04";
                break;
            case "MAYO":
                mesFormato="05";
                break;
            case "JUNIO":
                mesFormato="06";
                break;
            case "JULIO":
                mesFormato="07";
                break;
            case "AGOSTO":
                mesFormato="08";
                break;
            case "SEPTIEMBRE":
                mesFormato="09";
                break;
            case "OCTUBRE":
                mesFormato="10";
                break;
            case "NOVIEMBRE":
                mesFormato="11";
                break;
            case "DICIEMBRE":
                mesFormato="12";
                break;
        }



        return mesFormato;
    }
}
