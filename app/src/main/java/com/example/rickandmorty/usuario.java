package com.example.rickandmorty;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class usuario extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    public static String FOLDER_NAME = "user.obj";
    private Button salvabtn;

    private File dir;
    public static File arquivo;

    ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        background = findViewById(R.id.background);
        salvabtn = findViewById(R.id.btnsalvar);

        try {
            ChecharArquivoUser();
            MostrarNomeUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        }

        salvabtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkPermission() == true) {
                    createFolder();
                } else {
                    Log.d(TAG, "onClick: Permission were not granted - Requesting");
                    requestPermission();
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences(menu.PREFERENCIA_TEMA, 0);
        String tema = preferences.getString("theme", "dark");
        MudarTema(tema);
    }


    public void MudarTema(String tema){
        SharedPreferences preferences = getSharedPreferences(menu.PREFERENCIA_TEMA, 0);
        SharedPreferences.Editor editor = preferences.edit();

        if(tema == "dark"){
            background.setBackgroundColor(Color.parseColor("#000000"));
        } else if(tema == "light"){
            background.setBackgroundColor(Color.parseColor("#3F51B5"));
        }
    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                Log.d(TAG, "Permissão fornecida");
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
            catch (Exception e){
                Log.e(TAG,"requestPermission: catch ", e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }
    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "OnActivityResult: ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (Environment.isExternalStorageManager()) {
                            Log.d(TAG, "OnActivityResult: Manage External Storage Permission is granted");
                        } else {
                            Log.d(TAG, "OnActivityResult: Manage External Storage Permission is denied");
                        }
                    } else {
                        Log.d(TAG, "Alguma coisa haver com a vesrão?");
                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        usuario.super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0){
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (write && read){
                    createFolder();
                }
                else{
                    Toast.makeText(this, "External Storage permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void createFolder()
    {
        ChecharArquivoUser();
        ConfirmarAlteracoes();
        try {
            MostrarNomeUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ConfirmarAlteracoes() {
        if (arquivo != null) {
            // Toast.makeText(getApplicationContext(), "Diretório:" + dir.getAbsolutePath() + ";" +
            // " Arquivo: " + arquivo.getAbsolutePath(), Toast.LENGTH_LONG).show();
            SalvarAlteracoes(arquivo);
        }else {
            Toast.makeText(getApplicationContext(), "'Folder' não criada", Toast.LENGTH_LONG).show();
        }
    }

    public void ChecharArquivoUser() {
        dir = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/" + FOLDER_NAME));
        if (dir != null) {
            arquivo = dir;
        }
        else{
            arquivo = new File(Environment.DIRECTORY_DOCUMENTS, FOLDER_NAME);
        }
    }


    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else{
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void SalvarAlteracoes(File file) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fos != null) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TextView vw_username = findViewById(R.id.edttxt_nome);
            TextView vw_useremail = findViewById(R.id.edttxt_email);
            String username = vw_username.getText().toString();
            String useremail = vw_useremail.getText().toString();

            user usuario = new user(username, useremail);
            if (oos != null){
                try {
                    oos.writeObject(usuario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else { Toast.makeText(getApplicationContext(), "fos nulo", Toast.LENGTH_LONG).show(); }
    }

    public void btnhome(View view) throws IOException {
        // fos.close();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
        finish();
    }

    private void MostrarNomeUsuario() throws IOException
    {
        InputStream fis = new FileInputStream(arquivo);
        ObjectInputStream ois= new ObjectInputStream(fis);

        try {
            user usuariosalvo = (user) ois.readObject();

            String nomesalvo = usuariosalvo.GetName();

            TextView txtnome = findViewById(R.id.txt_nmusuario2);
            if (nomesalvo != null || nomesalvo != "") {
                txtnome.setText("Olá, " + nomesalvo + "!");
            }
            else{
                txtnome.setText("Olá, salve seu usuário.");
            }
        } catch (ClassNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Não possível foi mostrar nome de usuário pelo edttext", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }

        // nometxt.setText(nomesalvo);
    }

    /*
    public void salvaralts(View view) throws IOException {
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
                oos.writeObject(usuario);
                /*
                try {
                    oos.writeObject(usuario);
                    Toast.makeText(getApplicationContext(), "Usuario Salvo!!!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível salvar o usuário", Toast.LENGTH_SHORT).show();
                }

                 */
                    /*
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    Toast.makeText(getApplicationContext(), "Foi possível ler o arquivo de usuário com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível ler o arquivo de usuário", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                     */
/*
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(fis);
                    Toast.makeText(getApplicationContext(), "Foi possível atribuir 'ois' (ObjectInputStremam) de usuário com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Não foi possível atribuir 'ois' (ObjectInputStremam) de usuário", Toast.LENGTH_SHORT).show();
                }

 */
/*
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

 */
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
        /*
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

         */


    // ois.close();
    // fis.close();
    // oos.close();
        /*
        startActivity(new Intent(this,home.class));
        finish();
         */

}

// MostrarNomeUsuario(" - Atualizado");
