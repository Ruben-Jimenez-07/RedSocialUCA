package com.example.redsocialuca;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class activity_registro extends AppCompatActivity {
    EditText Correo,Nombre,Contraseña,Edad,Apellido;
    Button RegistrarUsuario;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Nombre = findViewById(R.id.Nombre);
        Contraseña = findViewById(R.id.Contraseña);
        Edad = findViewById(R.id.Edad);
        Apellido = findViewById(R.id.Apellido);
        RegistrarUsuario =findViewById(R.id.Registro);

        firebaseAuth = firebaseAuth.getInstance();
        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String correo = Correo.getText().toString();
                String pass = Contraseña.getText().toString();
                
                if (!PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("Correo no valido");
                    Correo.setFocusable(true);
                }else if (pass.length()<6){
                    Contraseña.setError("Contraseña debe ser mayor a 6 caracteres");
                    Contraseña.setFocusable(true);
                }else{
                    Registrar(correo,pass);
                }
                
            }
        });

    }

    private void Registrar(String correo, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(correo, pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


                    assert user != null;
                    String uid = user.getUid();
                    String correo = Correo.getText().toString();
                    String pass = Contraseña.getText().toString();
                    String nombre = Nombre.getText().toString();
                    String apellido= Apellido.getText().toString();
                    String edad= Edad.getText().toString();

                    HashMap <Object,String> DatosUsuario = new HashMap<>();
                    DatosUsuario.put("uid",uid);
                    DatosUsuario.put("correo",uid);
                    DatosUsuario.put("nombre",uid);
                    DatosUsuario.put("pass",uid);
                    DatosUsuario.put("apellido",uid);
                    DatosUsuario.put("edad",uid);

                    DatosUsuario.put("imagen",uid);

                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("USUARIOS_DE_APP");
                    reference.child(uid).setValue(DatosUsuario);
                    Toast.makeText(activity_registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity_registro.this,Inicio.class));


                }else{
                    Toast.makeText(activity_registro.this, "algo salio mal", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_registro.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}