package br.net.techsoft.techsoftmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jhef3y on 09/09/2016.
 */
public class Login extends AppCompatActivity {

    private Button btnLogar, btnVoltar;
    private EditText edtUser, edtSenha;
    private final String user = "admin";
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        btnLogar = (Button)findViewById(R.id.btnLogar);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MenuInicial.class);
                startActivity(intent);
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userD = String.valueOf(edtUser.getText());
                String senhaD = String.valueOf(edtSenha.getText());
                senha = getSenha();

                Log.e(userD + " " + senhaD, user + " " + senha);
                if (userD.equals(user) && senhaD.equals(senha)){
                    Intent intent = new Intent(Login.this, Configs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    edtUser.setText("");
                    edtSenha.setText("");
                    Toast.makeText(Login.this, "Usuario ou Senha Incorretos!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private String getSenha(){
        Date date = new Date();
        String password = new SimpleDateFormat("ddHHmm").format(date.getTime());

        return password;
    }
    }
