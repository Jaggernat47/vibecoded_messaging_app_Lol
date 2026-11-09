package com.example.practiceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText usernameEditText = findViewById(R.id.UserName);
        EditText passwordEditText = findViewById(R.id.PasswordInput);
        Button signInButton = findViewById(R.id.SignInButton);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            private boolean isDeleting = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isDeleting = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDeleting) return;

                switch (s.length()) {
                    case 2:
                    case 5:
                        s.append('/');
                        break;
                }
            }
        });

        signInButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both student ID and birth date", Toast.LENGTH_SHORT).show();
            } else if (username.length() != 6) {
                Toast.makeText(MainActivity.this, "Student ID must be exactly 6 digits", Toast.LENGTH_SHORT).show();
            } else if (password.length() != 10) {
                Toast.makeText(MainActivity.this, "Please enter a valid birth date (DD/MM/YYYY)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("STUDENT_ID", username);
                intent.putExtra("BIRTH_DATE", password);
                startActivity(intent);
                finish();
            }
        });
    }
}