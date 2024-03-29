package android.sxample.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import ketai.sensors.KetaiLocation;

//import ketai.sensors.KetaiLocation;
//import ketai.sensors.Location;

public class MainActivity extends AppCompatActivity {
    double longitude, latitude, altitude, accuracy;
    KetaiLocation location;
    Location bam;
    Location ours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bam = new Location("bam");
        ours = new Location("bam");
        bam.setLatitude(40.686818);
        bam.setLongitude(-73.977779);
//        location.getLocation().distanceTo(bam);


        int secondsDelayed = 1;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){

            Double geo1 =(Double) extras.get("geo1");
            Double geo2 =(Double) extras.get("geo2");
            Uri image =(Uri) Uri.parse(extras.getString("image"));
//        Double geo1 = extras.getDouble("geo1");
//        Double geo2 = extras.getDouble("geo2");
//        Uri image = Uri.parse(extras.getString("image"));
            ours.setLatitude(geo2);
            ours.setLongitude(geo1);
//            location.getLocation(;);
           // location = new KetaiLocation(this);


            Toast.makeText(getApplicationContext(), ours.distanceTo(bam)+"", Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(), geo2+"", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), image+"", Toast.LENGTH_SHORT).show();
        }


        //define the handler for setting delay for going to next class

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainActivity.this, Camera.class));
                finish();
            }
        }, secondsDelayed * 4000);
    }

    }

