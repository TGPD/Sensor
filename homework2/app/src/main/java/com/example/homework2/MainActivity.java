package com.example.homework2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private EditText textX,textY,textZ,textYN;
    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textX = findViewById(R.id.et1);
        textY = findViewById(R.id.et2);
        textZ = findViewById(R.id.et3);
        textYN = findViewById(R.id.et4);
        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        getSensorManager();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Sensor 发生变化时,在次通过event.values获取数据
        float [] values = event.values;
        int sensorType = event.sensor.getType();
        StringBuilder stringBuilderX = null,stringBuilderY = null,stringBuilderZ = null;
        if(sensorType == Sensor.TYPE_ACCELEROMETER ) {
            stringBuilderX = new StringBuilder();
            stringBuilderY = new StringBuilder();
            stringBuilderZ = new StringBuilder();
            stringBuilderX.append(values[0]);
            stringBuilderY.append(values[1]);
            stringBuilderZ.append(values[2]);
            textX.setText(stringBuilderX.toString());
            textY.setText(stringBuilderY.toString());
            textZ.setText(stringBuilderZ.toString());
            if(values[1] <= 9.89 && values[1] >= 9.7){
                textYN.setText("静止");
            }
            else {textYN.setText("运动");}
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void getSensorManager() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager != null){
            //一般在Resume方法中注册
            /**
             * 第三个参数决定传感器信息更新速度
             * SensorManager.SENSOR_DELAY_NORMAL:一般
             * SENSOR_DELAY_FASTEST:最快
             * SENSOR_DELAY_GAME:比较快,适合游戏
             * SENSOR_DELAY_UI:慢
             */
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
