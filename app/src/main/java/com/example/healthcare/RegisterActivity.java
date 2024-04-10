package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUserName, edtEmail, edtPassword, edtRePassword;
    Button btnRegister;
    TextView tvback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edtUserName = findViewById(R.id.edtRegisterUserName);
        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        edtRePassword = findViewById(R.id.edtRegisterRePassword);
        btnRegister = findViewById(R.id.buttonRegister);
        tvback = findViewById(R.id.textViewExistingUser);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirm = edtRePassword.getText().toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if (username.length()==0 || email.length()==0 || password.length()==0 || confirm.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.compareTo(confirm)==0) {
                        if(isValid(password)) {
                            db.register(username, email, password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having letter, digit and special symbol", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password confirm didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public static boolean isValid(String password) {
        int f1=0, f2=0, f3=0;
        if (password.length() < 8) {
            return false;
        } else {
            for (int p = 0; p <password.length(); p++) {
                if(Character.isLetter(password.charAt(p))) {
                    f1=1;
                }
            }
            for (int r = 0; r <password.length(); r++) {
                if(Character.isDigit(password.charAt(r))) {
                    f2=1;
                }
            }
            for (int s = 0; s <password.length(); s++) {
                char c = password.charAt(s);
                if(c >= 33 && c <= 46 || c == 64) {
                    f3=1;
                }
            }
            if (f1==1 && f2==1 && f3==1){
                return true;
            }
            return false;
        }
    }
}