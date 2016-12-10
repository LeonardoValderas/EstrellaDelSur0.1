package com.estrelladelsur.estrelladelsur.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.general.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.navegador.administrador.SplashAdm;

import java.util.ArrayList;


public class Login extends AppCompatActivity {

    private EditText usuarioEdit, passEdit;
    private Button aceptar, cancelar;
    private TextView errorText;
    private ControladorGeneral controladorGeneral;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Usuario> usuarioUsuario = new ArrayList<>();
    private String usuario = null, pass = null;
    private boolean usuarioOK = false;
    private boolean passOK = false;
    private int posicion;
    private int id_usuario;
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

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        controladorGeneral = new ControladorGeneral(Login.this);
        usuarioUsuario = controladorGeneral.selectListaUsuario();
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
                                    id_usuario = usuarioUsuario.get(posicion).getID_USUARIO();
                                } else {
                                    showError();
                                }
                            }
                            if (usuarioOK && passOK) {
                            intentSplashADM();
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

    public void intentSplashADM(){
        ligaIntente = new Intent(Login.this, SplashAdm.class);
        ligaIntente.putExtra("id_usuario",id_usuario);
        ligaIntente.putExtra("usuario", usuario);
        startActivity(ligaIntente);
    }
}
