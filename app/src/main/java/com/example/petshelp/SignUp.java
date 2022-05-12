package com.example.petshelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText Email;
    EditText Password;
    EditText Password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Password2 = (EditText) findViewById(R.id.password2);

    }


    public void ToMainActivity(View view) {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view) {
        String EmailStr = Email.getText().toString();
        String PasswordStr = Password.getText().toString();
        String Password2Str = Password2.getText().toString();
        if (EmailStr.equals("")){Toast.makeText(SignUp.this, "Заполните Email", Toast.LENGTH_SHORT).show(); return;}
        if (PasswordStr.equals("")){Toast.makeText(SignUp.this, "Заполните Пароль", Toast.LENGTH_SHORT).show(); return;}
        if (Password2Str.equals("")){Toast.makeText(SignUp.this, "Заполните пароль2", Toast.LENGTH_SHORT).show(); return;}
        if(PasswordStr.equals(Password2Str)){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(EmailStr, PasswordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Вы зарегестрированы!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignUp.this, "Регистрация не выполнена!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }else {
            Toast.makeText(SignUp.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
        }
    }
}