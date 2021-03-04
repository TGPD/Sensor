package com.example.draft;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.EditText;




public class MainActivity extends Activity implements SensorEventListener
{

    private EditText textX,textY,textZ,textA;

    SensorManager mSensorManager;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textX = findViewById(R.id.et1);
        textY = findViewById(R.id.et2);
        textZ = findViewById(R.id.et3);
        textA = findViewById(R.id.et4);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause()
    {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    protected void onStop()
    {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onStop();
    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int sensorType = event.sensor.getType();
        if(sensorType==Sensor.TYPE_ORIENTATION){


            StringBuilder stringBuilderX = new StringBuilder();
            StringBuilder stringBuilderY = new StringBuilder();
            StringBuilder stringBuilderZ = new StringBuilder();
            stringBuilderX.append(event.values[0]);
            stringBuilderY.append(event.values[1]);
            stringBuilderZ.append(event.values[2]);
            textX.setText(stringBuilderX.toString());
            textY.setText(stringBuilderY.toString());
            textZ.setText(stringBuilderZ.toString());
            double a = event.values[0];
            if(a<=22.5||a>=337.5)textA.setText("正北");
            if(a>=22.5&&a<=67.5)textA.setText("东北");
            if(a>=67.5&&a<=112.5)textA.setText("正东");
            if(a>=112.5&&a<=157.5)textA.setText("东南");
            if(a>=157.5&&a<=202.5)textA.setText("正南");
            if(a>=202.5&&a<=247.5)textA.setText("西南");
            if(a>=247.5&&a<=292.5)textA.setText("正西");
            if(a>=292.5&&a<=337.5)textA.setText("西北");



        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}

