
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

    public static final String ARQUIVO_SCORE = "scoretable_file.txt";
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

        scoreview = findViewById(R.id.scoretxt_2);

        ChecharArquivoScore();
        try {
            fis = new FileInputStream(arquivo);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(arquivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis = new FileInputStream(arquivo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         */

        // ObTester retorno = (ObTeste) ois.readObject();ois.close();fis.close();

        if (fis != null) {
            try {
                numscore = fis.read();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                Environment.DIRECTORY_DOCUMENTS + "/"
        ));
        arquivo = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/" + ARQUIVO_SCORE
        ));

        if (arquivo == null) {
            arquivo = new File(dir, ARQUIVO_SCORE);
        }
        else{

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (respondido) {
            startActivity(new Intent(this, quiz3.class));
            finish();
        }

        scoreview.setText("Score: " + numscore.toString());
    }

    public void checarResposta(View view){

        if (respondido == false) {
            respondido = true;
            AtualizarScore(1);
        }
    }

    public void abreMenu(View vw){
        Intent menu = new Intent(this, menu.class);
        startActivity(menu);
        finish();
    }
}