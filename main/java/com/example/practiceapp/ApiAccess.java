package com.example.practiceapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiAccess {
    @GET("/users")
    Call<List<Contact>> getContacts();
}