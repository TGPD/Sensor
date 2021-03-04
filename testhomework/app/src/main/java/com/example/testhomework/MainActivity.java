package com.example.testhomework;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import java.text.CollationElementIterator;


public class MainActivity extends Activity implements SensorEventListener{



    private SensorManager sensorManager;
    private TextView text;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assert sensorManager != null;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n sensor name");
        stringBuffer.append(sensor.getName());
        stringBuffer.append(sensor.getPower());
        stringBuffer.append(sensor.getMaximumRange());
        text = findViewById(R.id.textView1);
        text.setText(stringBuffer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
//            getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        sensorManager.unregisterListener(this);
        super.onStop();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        float[] values = event.values;
        int sensorType = event.sensor.TYPE_LIGHT;
        if(sensorType==Sensor.TYPE_LIGHT)
        {
            text.setText(String.valueOf(values[0]));
        }
    }

    private SensorListener mySensorListener = new SensorListener(){
        @Override
        public void onAccuracyChanged(int sensor, int accuracy) {}//重写onAccuracyChanged方法　
        @Override
        public void onSensorChanged(int sensor, float[] values) { //重写onSensorChanged方法　
            if(sensor == SensorManager.SENSOR_LIGHT){//只检查光强度的变化　
                CollationElementIterator myTextView1 = null;
                myTextView1.setText("光的强度为："+values[0]);//将光的强度显示到TextView　
            }
        }
    };

   /*
    @Override
    protected void onResume() {//重写的onResume方法　
        mySensorManager.registerListener(
               mySensorListener, SensorManager.SENSOR_LIGHT,SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }
    */
}

