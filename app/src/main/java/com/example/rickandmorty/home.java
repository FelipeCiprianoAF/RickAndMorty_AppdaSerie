package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class home extends AppCompatActivity {

    ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        background = findViewById(R.id.background);
        SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIA, 0);

        try {
            MostrarNomeUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MudarTema(preferences.getString("theme", "Sem Tema"));
    }

    public void btnmenu(View view) {
        Intent intent = new Intent(this,menu.class);
        startActivity(intent);
        finish();
    }

    public void btnuser(View view) {
        Intent intent = new Intent(this,user.class);
        startActivity(intent);
        finish();
    }

    public void btnquiz(View view){
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    // Intents sobre os Personagens:

    public void btnpers1(View view) {
        Intent intent = new Intent(this,personagem1.class);
        startActivity(intent);
    }

    public void btnpers2(View view) {
        Intent intent = new Intent(this,personagem2.class);
        startActivity(intent);
    }

    public void btnpers3(View view) {
        Intent intent = new Intent(this,personagem3.class);
        startActivity(intent);
    }

    public void btnpers4(View view) {
        Intent intent = new Intent(this,personagem4.class);
        startActivity(intent);
    }

    public void btnpers5(View view) {
        Intent intent = new Intent(this,personagem5.class);
        startActivity(intent);
    }

    // Intents sobre as Temporadas:
    /*
    public void btntemp1(View view) {
        Intent intent = new Intent(this,temp1.class);
        startActivity(intent);
    }

    public void btntemp2(View view) {
        Intent intent = new Intent(this,temp2.class);
        startActivity(intent);
    }

    public void btntemp3(View view) {
        Intent intent = new Intent(this,temp3.class);
        startActivity(intent);
    }

    public void btntemp4(View view) {
        Intent intent = new Intent(this,temp4.class);
        startActivity(intent);
    }

    public void btntemp5(View view) {
        Intent intent = new Intent(this,temp5.class);
        startActivity(intent);
    }

    public void btntemp6(View view) {
        Intent intent = new Intent(this,temp6.class);
        startActivity(intent);
    }

    */

    private void MostrarNomeUsuario() throws IOException, ClassNotFoundException {
        TextView nometxt = findViewById(R.id.txt_nmusuario);

        File file = getFileStreamPath(user.FILENAME);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        user.User retorno = (user.User) ois.readObject();
        String nome = retorno.GetName();

        nometxt.setText("Olá " + nome + "!");
    }

    public void MudarTema(String tema){

        SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();

        if(tema == "dark" || tema == "indefinido"){
            background.setBackgroundColor(Color.parseColor("#000000"));
        } else if (tema == "light"){
            background.setBackgroundColor(Color.parseColor("#3F51B5"));
        }
    }
}