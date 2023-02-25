package com.uniminuto.computrabajomovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

public class RegisterUsuario extends AppCompatActivity {

    Button btn_register;
    EditText name, email, password;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView loginActivity = findViewById(R.id.login);
        loginActivity.setOnClickListener(this::onClick);

        name = findViewById(R.id.nameRegistroUsuario);
        email = findViewById(R.id.emailRegistroUsuario);
        password = findViewById(R.id.passwordRegistroUsuario);
        btn_register = findViewById(R.id.btn_registerUsuario);

        btn_register.setOnClickListener(view -> {
            String nameUser = name.getText().toString().trim();
            String emailUser = email.getText().toString().trim();
            String passwordUser = password.getText().toString().trim();

            if (nameUser.isEmpty() && emailUser.isEmpty() && passwordUser.isEmpty()){
                Toast.makeText(RegisterUsuario.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
            }else {
                registerUser(nameUser, emailUser, passwordUser);
            }
        });
    }

    private void registerUser(String nameUser, String emailUser, String passwordUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", nameUser);
                    map.put("email", emailUser);
                    map.put("passwrod", passwordUser);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(RegisterUsuario.this, "Usuario Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterUsuario.this, MainActivity.class));
                            }else {
                                Toast.makeText(RegisterUsuario.this, "No se pudieron crear los datos correctamentes", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegisterUsuario.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onClick(View v) {

        if (v.getId() == R.id.login) {
            Intent intent = new Intent(this, LoginUsuario.class);
            startActivity(intent);
        }
    }
}