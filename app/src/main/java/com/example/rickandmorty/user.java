package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class user extends AppCompatActivity {

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
}