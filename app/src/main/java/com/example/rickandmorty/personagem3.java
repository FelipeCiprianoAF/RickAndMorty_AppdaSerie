package com.example.rickandmorty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class personagem3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagem3);
    }

    public void btnhomemenu(View view) {
        Intent intent = new Intent(this,home.class);
        startActivity(intent);
        finish();
    }
}