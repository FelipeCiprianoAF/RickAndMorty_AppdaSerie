package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class usuario extends AppCompatActivity {

    public static String FILENAME = "USERINFO";
    File file;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        file = getFileStreamPath(FILENAME);

        /*
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         */

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

    public void btnhome(View view) throws IOException {
        // fos.close();
        Intent intent = new Intent(this,home.class);
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

    public void salvaralts(View view)
    {
        TextView username = findViewById(R.id.txt_username);
        TextView useremail = findViewById(R.id.txt_useremail);

        try {
            fos = new FileOutputStream(file);
            Toast.makeText(getApplicationContext(), "Atribuido pathway do 'file', para 'fos' com sucesso!", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Não foi possível atribuir o pathway do 'file'", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            Toast.makeText(getApplicationContext(), "Atribuido ObjectOutputStream 'oos' com sucesso!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Não foi possível atribuir ObjectOutputStream 'oos'", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if(username.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Preencha o seu nome", Toast.LENGTH_SHORT).show();
        }
        else {
            String nome = username.getText().toString();

            if (useremail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Preencha o seu email", Toast.LENGTH_SHORT).show();
            } else {
                String email = useremail.getText().toString();
                User usuario = new User(nome,email);
                try {
                    oos.writeObject(usuario);
                    Toast.makeText(getApplicationContext(), "Usuario Salvo!!!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível salvar o usuário", Toast.LENGTH_SHORT).show();
                }

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    Toast.makeText(getApplicationContext(), "Foi possível ler o arquivo de usuário com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível ler o arquivo de usuário", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(fis);
                    Toast.makeText(getApplicationContext(), "Foi possível atribuir 'ois' (ObjectInputStremam) de usuário com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível atribuir 'ois' (ObjectInputStremam) de usuário", Toast.LENGTH_SHORT).show();
                }

                User retorno = null;
                try {
                    retorno = (User) ois.readObject();
                    Toast.makeText(getApplicationContext(), "Lido classe usuário no arquivo (FILE) com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível ler classe de usuário no arquivo (FILE) :: ERRO: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível atribuir 'ois' (ObjectInputStremam) de usuário  :: ERRO: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                /*
                if (retorno == null){
                    User usuario = new User(nome, email);
                    oos.writeObject(usuario);
                }
                else{
                    retorno.update(nome, email);
                    oos.writeObject(retorno);
                }
                */
                try {
                    oos.close();
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


             // ois.close();
           // fis.close();
         // oos.close();
        /*
        startActivity(new Intent(this,home.class));
        finish();
         */
    }
}
        // MostrarNomeUsuario(" - Atualizado");