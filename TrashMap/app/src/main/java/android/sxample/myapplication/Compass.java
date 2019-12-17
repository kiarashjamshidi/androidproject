package android.sxample.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;

import android.content.DialogInterface;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Compass extends AppCompatActivity  implements SensorEventListener {
    private ImageView imageView;
    private float[] mGravity=new float[3];
    private float[] mGeomagnetic=  new float[3];
    private float azimuth = 0f;
    private float currentazimuth =0f;
    private SensorManager mSensorManager;

    Button mType2;
    TextView mTypesSelected2;
    String[] listTypes2;
    boolean[] checkedTypes2;
    ArrayList<Integer> mUserTypes2 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        imageView =(ImageView)findViewById(R.id.compass);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        FloatingActionButton fab = findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compass.this, Camera.class);
                startActivity(intent);
            }
        });

        mType2 = findViewById(R.id.btnTypes2);
        mTypesSelected2 = findViewById(R.id.tvTypesSelected2);

        listTypes2 = getResources().getStringArray(R.array.trash_type);
        checkedTypes2 = new boolean[listTypes2.length];

        mType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Compass.this);
                mBuilder.setMultiChoiceItems(listTypes2, checkedTypes2, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserTypes2.add(position);
                        }else{
                            mUserTypes2.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String type = "";
                        for (int i = 0; i < mUserTypes2.size(); i++) {
                            type = type + listTypes2[mUserTypes2.get(i)];
                            if (i != mUserTypes2.size() - 1) {
                                type = type + ", ";
                            }
                        }
                        mTypesSelected2.setText(type);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedTypes2.length; i++) {
                            checkedTypes2[i] = false;
                            mUserTypes2.clear();
                            mTypesSelected2.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha =0.97f;
        synchronized (this){
            if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
                mGravity[0]=alpha*mGravity[0]+(1-alpha)*event.values [0];
                mGravity[1]=alpha*mGravity[1]+(1-alpha)*event.values [1];
                mGravity[2]=alpha*mGravity[2]+(1-alpha)*event.values [2];
            }
            if (event.sensor.getType()== Sensor.TYPE_MAGNETIC_FIELD){
                mGeomagnetic[0]=alpha*mGeomagnetic[0]+(1-alpha)*event.values [0];
                mGeomagnetic[1]=alpha*mGeomagnetic[1]+(1-alpha)*event.values [1];
                mGeomagnetic[2]=alpha*mGeomagnetic[2]+(1-alpha)*event.values [2];
            }
            float R[]= new float[9];
            float I[]= new float[9];
            boolean success= SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if (success){
                float Orientation[] =new float[3];
                SensorManager.getOrientation(R,Orientation);
                azimuth=(float)Math.toDegrees(Orientation[0]);
                azimuth =(azimuth+360)%360;

                //

                Animation anim =new RotateAnimation(-currentazimuth,-azimuth,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                currentazimuth=azimuth;
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                //

                imageView.startAnimation(anim);

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
