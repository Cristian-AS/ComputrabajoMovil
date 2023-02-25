package com.uniminuto.computrabajomovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUsuario extends AppCompatActivity {

    EditText email, password;
    Button btn_login;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        mAuth = FirebaseAuth.getInstance();

        TextView registrarActivity = findViewById(R.id.registrar);
        registrarActivity.setOnClickListener(this::onClick);

        email = findViewById(R.id.emailUsuario);
        password = findViewById(R.id.passwordUsuario);
        btn_login = findViewById(R.id.btn_loginUsuario);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLogin = email.getText().toString().trim();
                String passwordLogin = password.getText().toString().trim();

                if(emailLogin.isEmpty() && passwordLogin.isEmpty()){
                    Toast.makeText(LoginUsuario.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    loginUser(emailLogin, passwordLogin);
                }
            }
        });
    }

    private void loginUser(String emailLogin, String passwordLogin) {
        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    Toast.makeText(LoginUsuario.this, "Usuario Ingresado Exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(LoginUsuario.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginUsuario.this,"No se pudo Iniciar Sesion, Por Favor comprueba los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void onClick(View v) {

        if (v.getId() == R.id.registrar) {
            Intent intent = new Intent(this, RegisterUsuario.class);
            startActivity(intent);
        }
    }
}