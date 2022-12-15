
package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class quiz2 extends AppCompatActivity {

    String ARQUIVO_SCORE = "scoretable_file.txt";
    File dir;
    File arquivo;

    TextView scoreview;
    private Integer numscore = -99;

    FileInputStream fis = null;
    private boolean respondido = false;


//    public static String FOLDER_NAME = "user.obj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        scoreview = findViewById(R.id.scoretxt_3);

        ChecharArquivoScore();
        try {
            fis = new FileInputStream(arquivo);
            Toast.makeText(getApplicationContext(), "FIS estabelecido", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(arquivo);
            Toast.makeText(getApplicationContext(), "FOS estabelecido", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write(numscore);
            Toast.makeText(getApplicationContext(), "Numscore escrito em FOS", Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Numscore não escrito em FOS", Toast.LENGTH_SHORT).show();
        }

        try {
            fis = new FileInputStream(arquivo);
            Toast.makeText(getApplicationContext(), "FIS estabelecido", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // ObTester etorno = (ObTeste) ois.readObject();ois.close();fis.close();

        try {
            numscore = (new FileInputStream(arquivo)).read();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (numscore == -99){
            numscore = 0;
        }
        else{
            AtualizarScore(0);
        }
    }

    public void ChecharArquivoScore() {
        dir = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/" + ARQUIVO_SCORE
        ));

        if (dir != null) {
            arquivo = dir;
        }
        else{
            arquivo = new File(dir, ARQUIVO_SCORE);
        }
    }

    private void AtualizarScore(Integer sum) {

        if (sum > 0 || sum == 1)
        {
            numscore += sum;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(arquivo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (fos != null){
                try {
                    fos.write(numscore);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (respondido) {
                startActivity(new Intent(this, quiz3.class));
                finish();
            }
        }

        scoreview.setText("Score: " + numscore.toString());
    }

    public void checarResposta(View view){

        if (!respondido) {
            AtualizarScore(1);
            respondido = true;
        }
    }

    public void abreMenu(View vw){
        Intent menu = new Intent(this, menu.class);
        startActivity(menu);
        finish();
    }
}