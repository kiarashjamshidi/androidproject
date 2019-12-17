package android.sxample.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

            Toast.makeText(getApplicationContext(), geo1+"", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), geo2+"", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), image+"", Toast.LENGTH_SHORT).show();
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

