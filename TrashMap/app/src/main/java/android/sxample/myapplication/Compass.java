package android.sxample.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import com.google.android.material.snackbar.Snackbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Compass extends AppCompatActivity  implements SensorEventListener {
    private ImageView imageView;
    private float[] mGravity=new float[3];
    private float[] mGeomagnetic=  new float[3];
    private float azimuth = 0f;
    private float currentazimuth =0f;
    private SensorManager mSensorManager;

    private static final String[] TypeSelector = new String[]{
            "Mixed", "Plastic", "Glass", "Organic", "Paper", "Metal", "E-Waste", "Cigarettes"
    };

    private String radioButton;




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
                Intent intent = new Intent(Compass.this, AddNewTrash.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab_btn2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Compass.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }

    public void radiob(View view) {
        int id = view.getId();
        if (id == R.id.buttonDialog) {
            dialogRadioButton();
        }
    }

    private void dialogRadioButton() {
        radioButton = TypeSelector[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your waste type");
        builder.setSingleChoiceItems(TypeSelector, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                radioButton = TypeSelector[i];
            }
        });
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(imageView, "Type: " + radioButton, Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.CANCEL, null);
        builder.show();
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
