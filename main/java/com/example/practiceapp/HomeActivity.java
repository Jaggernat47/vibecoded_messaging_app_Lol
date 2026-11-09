package com.example.practiceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView studentIdTextView = findViewById(R.id.student_id_textView);
        ListView listViewMessages = findViewById(R.id.listViewMessages);

        Intent intent = getIntent();
        String studentId;

        if (intent != null && intent.hasExtra("STUDENT_ID")) {
            String extraId = intent.getStringExtra("STUDENT_ID");
            if (extraId != null && !extraId.trim().isEmpty()) {
                studentId = extraId;
            } else {
                studentId = "1232133";
            }
        } else {
            studentId = "1232133";
        }

        studentIdTextView.setText(studentId);

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("Lorenzo Victor", "hi", "10/08/2026"));
        messageList.add(new Message("Aaron Novales", "yo boi", "10/01/2026"));
        messageList.add(new Message("Fergus Alarcon", "wasap negga", "10/01/2026"));

        MessageAdapter adapter = new MessageAdapter(this, messageList);
        listViewMessages.setAdapter(adapter);

        findViewById(R.id.buttonNewMessage).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ContactsActivity.class)));
    }
}