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
        }



        return mesFormato;
    }
}
