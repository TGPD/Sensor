package com.example.homework21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements SensorEventListener {  // 实现Sensor Event Listener接口
    private EditText textX,textY,textZ;   //编辑框组件
    private SensorManager sensorManager;  //传感器管理器组件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textX = findViewById(R.id.et1);
        textY = findViewById(R.id.et2);
        textZ = findViewById(R.id.et3);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);    // 获取传感器管理器
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置传感器类型及采样率
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);  // 暂停采集
    }

    @Override
    public void onSensorChanged(SensorEvent event) {   // 重写SensorEventListener接口的方法
        float [] values = event.values;
        int sensorType = event.sensor.getType();
        StringBuilder stringBuilderX = null;
        StringBuilder stringBuilderY = null;
        StringBuilder stringBuilderZ = null;
        if(sensorType == Sensor.TYPE_ACCELEROMETER ){   //  判断是否所需传感器
            stringBuilderX = new StringBuilder();
            stringBuilderY = new StringBuilder();
            stringBuilderZ = new StringBuilder();
            stringBuilderX.append(values[0]);
            stringBuilderY.append(values[1]);
            stringBuilderZ.append(values[2]);
            textX.setText(stringBuilderX.toString());   // 编辑框内显示
            textY.setText(stringBuilderY.toString());
            textZ.setText(stringBuilderZ.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {   //  重写SensorEventListener接口的方法

    }
}
