package com.estrelladelsur.estrelladelsur.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerResultado;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoResultado;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.navegador.adeful.NavigationAdeful;

import java.util.ArrayList;


public class Login extends AppCompatActivity {

    private EditText usuarioEdit, passEdit;
    private Button aceptar, cancelar;
    private TextView errorText;
    private ControladorAdeful controladorAdeful;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Usuario> usuarioUsuario = new ArrayList<Usuario>();
    private String usuario = null, pass = null;
    private boolean liga = true;
    private boolean usuarioOK = false;
    private boolean passOK = false;
    private int posicion;
    private int id_usuario;
   // private ProgressBar loginProgress = new ProgressBar(Login.this);
    private Intent ligaIntente = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuarioEdit = (EditText) findViewById(R.id.user);
        passEdit = (EditText) findViewById(R.id.pass);
        aceptar = (Button) findViewById(R.id.aceptarBoton);
        cancelar = (Button) findViewById(R.id.cancelarBoton);
        errorText = (TextView) findViewById(R.id.errorText);
        init();

    }

    private void init() {
        controladorAdeful = new ControladorAdeful(Login.this);
        usuarioUsuario = controladorAdeful.selectListaUsuarioAdeful();
        auxiliarGeneral = new AuxiliarGeneral(Login.this);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuarioEdit.getText().toString().equals("")) {
                    usuarioEdit.setError("Ingresar Usuario");
                } else if (passEdit.getText().toString().equals("")) {
                    passEdit.setError("Ingresar Contraseña");
                } else {
                    if (usuarioUsuario != null) {
                        if (!usuarioUsuario.isEmpty()) {
                            usuario = usuarioEdit.getText().toString();
                            pass = passEdit.getText().toString();

                            for (int i = 0; i < usuarioUsuario.size(); i++) {

                                if (usuario.equals(usuarioUsuario.get(i).getUSUARIO().toString())) {
                                    usuarioOK = true;
                                    posicion = i;
                                    break;
                                }
                            }
                            if (usuarioOK) {
                                if (pass.equals(usuarioUsuario.get(posicion).getPASSWORD().toString())) {
                                    passOK = true;
                                    liga = usuarioUsuario.get(posicion).isLIGA();
                                    id_usuario = usuarioUsuario.get(posicion).getID_USUARIO();
                                } else {
                                    showError();
                                }
                            }
                            if (usuarioOK && passOK) {
                            intentAdeful(liga);
                            } else {
                                showError();
                            }
                        } else {
                            showError();
                        }
                    } else {
                        auxiliarGeneral.errorDataBase(Login.this);
                    }
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText("Error en los datos ingresados.");
        usuarioOK = false;
        passOK = false;
    }

    public void intentAdeful(boolean liga){
      //  Intent ligaIntente = null;
        if(liga) {
        ligaIntente = new Intent(Login.this, NavigationAdeful.class);
        }else{
       // ligaIntente = new Intent(Login.this, NavigationLifuba.class);
        }
        ligaIntente.putExtra("id_usuario",id_usuario);
        ligaIntente.putExtra("usuario", usuario);
        ligaIntente.putExtra("pass", pass);
        startActivity(ligaIntente);
    }
}
