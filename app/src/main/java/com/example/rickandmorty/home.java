
package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

        /*
        try {
            MostrarNomeUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         */
        MudarTema(preferences.getString("theme", "dark"));
    }

    public void btnmenu(View view) {
        Intent intent = new Intent(getApplicationContext(),menu.class);
        startActivity(intent);
        finish();
    }

    public void btnuser(View view) {
        Intent intent = new Intent(this,usuario.class);
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

    /*

    // Intents sobre as Temporadas:

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

    /*
    private void MostrarNomeUsuario() throws IOException, ClassNotFoundException {
        TextView nometxt = findViewById(R.id.txt_nmusuario);

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File arquivo = new File(dir, "teste.obj");
        FileOutputStream fos = FileOutputStream(arquivo);

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        usuario.User retorno = (usuario.User) ois.readObject();
        String nome = retorno.GetName();

        nometxt.setText("Olá " + nome + "!");
    }

     */

    public void MudarTema(String tema){

        // SharedPreferences preferences = getSharedPreferences(menu.ARQUIVO_PREFERENCIA, 0);
        // SharedPreferences.Editor editor = preferences.edit();

        if(tema == "dark"){
            background.setBackgroundColor(Color.parseColor("#000000"));
        } else if(tema == "light"){
            background.setBackgroundColor(Color.parseColor("#3F51B5"));
        }
    }

    /*
    public static String FILENAME = "USERINFOS";
    File file = getFileStreamPath(FILENAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        try {
            MostrarNomeUsuario("");
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
        }
    }

    public void btnmenu(View view) {
        Intent intent = new Intent(this,menu.class);
        startActivity(intent);
        finish();
    }

    public class User implements Serializable {
        private String username;
        private String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public void update(String nome, String email){
            this.username = nome;
            this.email = email;
        }

        public String GetName(){
            return this.username;
        }
    }

        private void MostrarNomeUsuario(String atualizado) throws IOException, ClassNotFoundException {
        TextView nometxt = findViewById(R.id.txt_nmusuario);
        TextView username = findViewById(R.id.edttxt_nome);
        TextView useremail = findViewById(R.id.edttxt_email);

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        User retorno = (User) ois.readObject();

        nometxt.setText("Olá " + retorno.username + "!" + atualizado);
        username.setText(retorno.username);
        useremail.setText(retorno.email);
    }

    public void salvaralts(View view) throws IOException, ClassNotFoundException {
        TextView username = findViewById(R.id.txt_username);
        TextView useremail = findViewById(R.id.txt_useremail);

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        if(username.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha o seu nome", Toast.LENGTH_SHORT).show();
        }
        else {
            String nome = username.getText().toString();

            if(useremail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Preencha o seu email", Toast.LENGTH_SHORT).show();
            }
            else {
                String email = useremail.getText().toString();

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                User retorno = (User) ois.readObject();

                if (retorno == null){
                    User usuario = new User(nome, email);
                    oos.writeObject(usuario);
                }
                else{
                    retorno.update(nome, email);
                    oos.writeObject(retorno);
                }

                ois.close();
                fis.close();
                oos.close();
                fos.close();
            }
        }

        MostrarNomeUsuario(" - Atualizado");
    }
     */
}