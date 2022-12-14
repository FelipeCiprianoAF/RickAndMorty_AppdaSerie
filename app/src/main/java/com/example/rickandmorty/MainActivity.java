package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    ImageView imgsplash;

    float rotacao = 5;
    float passartela = 35;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    SharedPreferences preferences = null;
    ConstraintLayout background = null;


    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = Math.sqrt(x * x + y * x + z * z);
            accelerationPreviousValue = Math.abs(accelerationCurrentValue - accelerationPreviousValue);

            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;


            efeitoRodar(2);
            if (changeInAcceleration > passartela){
                abreHome();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgsplash = findViewById(R.id.imgsplash);
        // Pega o link do background para mudar a imagem sendo correspondente ao tema salvo
        background = findViewById(R.id.splash_background);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Pega o tema configurado para mudar o layout dop splash
        preferences = getSharedPreferences(menu.PREFERENCIA_TEMA, 0);


        ChecarArquivoPreferencia();
    }

    private void ChecarArquivoPreferencia() {
        // Checar se o arquivo de preferência existe e se não existir criar-lo
        // Caso o arquivo j´pa esteja criado:
        if (preferences != null){
            String tema = (preferences.getString("theme", "dark")).toString();
            // Passa o String 'tema' que recupera o valor 'theme' do arquivo preferência para mudar o tema do aplash
            MudarTema(tema);
        }
            // Caso não esteja: criar o arquivo:
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("theme", "dark");
            editor.commit();
        }
    }

    public void MudarTema(String tema){

        Toast.makeText(getApplicationContext(), "Tema: " + tema, Toast.LENGTH_SHORT);

        if(tema == "dark"){
            background.setBackgroundResource(R.drawable.fundooo);
            Toast.makeText(getApplicationContext(), "Dark", Toast.LENGTH_SHORT);
        }
        else if(tema == "light"){
            background.setBackgroundResource(R.drawable.fundo_light_0);
            Toast.makeText(getApplicationContext(), "Light", Toast.LENGTH_SHORT);
        }
        else{
            Toast.makeText(getApplicationContext(), "Tema nem é Dark nem é Light", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void abreHome(){
        Intent intent = new Intent(this,home.class);
        startActivity(intent);

        /*
        SharedPreferences preferences = getSharedPreferences(menu.ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();


        if(txtnome.getText().toString().equals("") || txtnome.getText().toString().equals("Nome")) {
                Toast.makeText(getApplicationContext(), "Preencha o seu nome", Toast.LENGTH_LONG).show();
            }
        else {
                String nome = txtnome.getText().toString();
                editor.putString("nome", nome);
                editor.putString("theme", "dark");
                editor.commit();

                startActivity(intent);
                finish();
            }
         */
    }

    public void btnabrehome(View view) {
        abreHome();
        finish();
    }

    private void efeitoRodar(float velocidade){
        rotacao += velocidade;
        imgsplash.setRotation(rotacao);
    }
}