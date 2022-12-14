
package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    ConstraintLayout background;

    public static final String PREFERENCIA_TEMA = "com.example.android.preferencia_tema";
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        background = findViewById(R.id.background);

        // Define conexão com o arquivo (já existente) de preferência de tema
        preferences = getSharedPreferences(PREFERENCIA_TEMA, 0);
        String tema = preferences.getString("theme", "dark");
        MudarCorTema(tema);
    }

    public void temaLight(View view)
    {
        ConfigurarTema("light");
    }

    public void temaDark(View view)
    {
        ConfigurarTema("dark");
    }

    public void ConfigurarTema(String tema){
        SalvarAlteracaoDeTema(tema);
        MudarCorTema(tema);
    }

    private void SalvarAlteracaoDeTema(String tema) {
        SharedPreferences.Editor editor = preferences.edit();

        if (tema == "light"){
            editor.putString("theme", "light");
        } else if(tema == "dark"){
            editor.putString("theme", "dark");
        }

        editor.commit();
    }

    public void MudarCorTema(String tema){

        if(tema == "dark"){
            background.setBackgroundResource(R.drawable.fundooo);

        } else if(tema == "light"){
            background.setBackgroundResource(R.drawable.fundo_light_0);
        }
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