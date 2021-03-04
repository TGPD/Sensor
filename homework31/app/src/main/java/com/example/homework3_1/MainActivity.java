package com.example.homework3_1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import static com.example.homework3_1.R.id.compass_img;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private ImageView compass_img;
    private float lastRotateDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compass_img = (ImageView) findViewById(R.id.compass_img);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //加速度感应器
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //地磁感应器
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    float[] accelerometerValues = new float[3];

    float[] magneticValues = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 判断当前是加速度感应器还是地磁感应器
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //赋值调用clone方法
            accelerometerValues = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //赋值调用clone方法
            magneticValues = event.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R,null,accelerometerValues,magneticValues);
        sensorManager.getOrientation(R, values);
        Log.d("Main","values[0] :"+Math.toDegrees(values[0]));
        //values[0]的取值范围是-180到180度。
        //+-180表示正南方向，0度表示正北，-90表示正西，+90表示正东

        //将计算出的旋转角度取反，用于旋转指南针背景图
        float rotateDegree = -(float) Math.toDegrees(values[0]);
        if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
            RotateAnimation animation = new RotateAnimation(lastRotateDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setFillAfter(true);
            compass_img.startAnimation(animation);
            lastRotateDegree = rotateDegree;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

