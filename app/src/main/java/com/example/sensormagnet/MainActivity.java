package com.example.sensormagnet;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DecimalFormat;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView value;
    private SensorManager sensorManager;
    public static DecimalFormat DECIMAL_FORMATER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        value = (TextView) findViewById(R.id.value);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATER = new DecimalFormat("#.000", symbols);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD){
            float magX = sensorEvent.values[0];
            float magY = sensorEvent.values[1];
            float magZ = sensorEvent.values[2];

            double magnitude = Math.sqrt ((magX * magX) + (magY * magY) + (magZ * magZ));

            value.setText(DECIMAL_FORMATER.format(magnitude)+"\u00B5Tesla");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}