
package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void temaLight(View view)
    {
        MudarTema("light");
    }

    public void temaDark(View view)
    {
        MudarTema("dark");
    }

    public void MudarTema(String tema){

        SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();

        if (tema == "light"){
            editor.putString("theme", "light");
            Toast.makeText(getApplicationContext(), "Tema Light ativado!", Toast.LENGTH_SHORT).show();
        } else {
            editor.putString("theme", "dark");
            Toast.makeText(getApplicationContext(), "Tema Dark ativado!", Toast.LENGTH_SHORT).show();
        }

        editor.commit();
    }

    public void btnhomemenu(View view) {
        Intent intent = new Intent(this,home.class);
        startActivity(intent);
        finish();
    }

    public void btnquiz(View view){
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btnsair(View view){
        finishAffinity();
    }
}