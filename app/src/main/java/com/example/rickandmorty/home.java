
package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class home extends AppCompatActivity {

    ConstraintLayout background = null;
    SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        background = findViewById(R.id.background);
        preferences = getSharedPreferences(menu.PREFERENCIA_TEMA, 0);

        try {
            MostrarNomeUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String tema = preferences.getString("theme", "dark");
        MudarTema(tema);

        if (tema =="light"){
            MudarTema("light");
        }
        else if(tema == "dark"){
            MudarTema("dark");
        }
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
        Intent quiz = new Intent(this, quiz.class);
        startActivity(quiz);
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

    private void MostrarNomeUsuario() throws IOException, ClassNotFoundException {
        TextView nometxt = findViewById(R.id.txt_nmusuario);

        File dir = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/" + usuario.FOLDER_NAME));
        File arquivo = null;
        if (dir != null) {
            arquivo = dir;
        }
        else{
            arquivo = new File(Environment.DIRECTORY_DOCUMENTS, usuario.FOLDER_NAME);
        }

        FileInputStream fis = new FileInputStream(arquivo);
        ObjectInputStream ois = null;
        if (fis != null) {
            ois = new ObjectInputStream(fis);
        }
        if (ois != null) {
            user usuariocadastrado = (user) ois.readObject();
            String nome = usuariocadastrado.GetName();

            if (nome != null && nome != "") {
                nometxt.setText("Ol??, " + nome + "!");
            }
            else { nometxt.setText(("Ol??, usu??rio!")); }
        }
        fis.close();
        ois.close();
    }

    public void MudarTema(String tema){

        // SharedPreferences preferences = getSharedPreferences(menu.ARQUIVO_PREFERENCIA, 0);
        // SharedPreferences.Editor editor = preferences.edit();

        if(tema == "dark"){
            background.setBackgroundColor(Color.parseColor("#000000"));
        } else{
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

        nometxt.setText("Ol?? " + retorno.username + "!" + atualizado);
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
    // Intents sobre as Temporadas:

    public void btntemp1(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btntemp2(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btntemp3(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btntemp4(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btntemp5(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void btntemp6(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon...", Toast.LENGTH_SHORT).show();
    }
}