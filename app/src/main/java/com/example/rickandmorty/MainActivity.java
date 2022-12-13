package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    public static final String ARQUIVO_PREFERENCIA = "";

    TextView txtnome;
    ImageView imgsplash;

    float rotacao = 5;
    float passartela = 35;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

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

        // txtnome = findViewById(R.id.edittxt_nome);
        imgsplash = findViewById(R.id.imgsplash);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        String nome = preferences.getString("nome", "Usuário não definido!");

        /*
        if (nome != "Usuário não definido!"){
            txtnome.setText(nome);
        }
        else{
            txtnome.setText("Nome");
        }
         */
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
    }

    private void efeitoRodar(float velocidade){
        rotacao += velocidade;
        imgsplash.setRotation(rotacao);
    }
}