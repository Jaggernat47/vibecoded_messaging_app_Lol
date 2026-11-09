package com.example.practiceapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsActivity extends AppCompatActivity implements OnItemClickListener {

    RecyclerView contactList;
    List<Contact> contacts;
    ContactListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contactList = findViewById(R.id.recycleViewContacts);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        contactList.setLayoutManager(lm);

        adapter = new ContactListRecyclerViewAdapter(this);
        contactList.setAdapter(adapter);

        getContacts();
    }

    private void getContacts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiAccess apiAccess = retrofit.create(ApiAccess.class);
        Call<List<Contact>> call = apiAccess.getContacts();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contacts = response.body();
                    for (Contact contact : contacts) {
                        if (contact.Name != null && !contact.Name.isEmpty()) {
                            String[] nameParts = contact.Name.split(" ");
                            contact.Name = nameParts[0];
                        }
                    }
                    adapter.contacts = contacts;
                    adapter.notifyItemRangeInserted(0, contacts.size());
                } else {
                    Toast.makeText(ContactsActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Toast.makeText(ContactsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Contact contact) {
        Toast.makeText(this, "Clicked: " + contact.Name, Toast.LENGTH_SHORT).show();
    }
}