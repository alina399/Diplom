package com.example.petshelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText Password;
    EditText Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Password = (EditText) findViewById(R.id.textfildPassword);
        Email = (EditText) findViewById(R.id.textFieldLogin);
    }

    public void ToMainActivity(View view) {
        Intent intent = new Intent(SignIn.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void ButtonSignIn(View view) {
        String PasswordStr = Password.getText().toString();
        String EmailStr = Email.getText().toString();

        if (PasswordStr.equals("")) {
            Toast.makeText(this, "Пароль пустой!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (EmailStr.equals("")) {
            Toast.makeText(this, "E-mail пустой!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(EmailStr, PasswordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn.this, PersonActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignIn.this, "Вход не выполнен!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}