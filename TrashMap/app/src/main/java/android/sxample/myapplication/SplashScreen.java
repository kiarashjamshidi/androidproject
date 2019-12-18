package android.sxample.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class SplashScreen extends AppCompatActivity {
    private static final String TAG="ListDataActivity";
    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabaseHelper = new DatabaseHelper(this, "TrashDB", null, 6);
//        String[] strings=new String[4];
//        strings[0]="0";
//        strings[1]="1";
//        strings[2]="2";
//        strings[3]="3";
//        addData(strings);
        Log.d(TAG,"hey");
        Cursor data =mDatabaseHelper.getData();
        ArrayList<String> listdata = new ArrayList<>();
//        int i=1;
//        while (data.moveToNext()){
//            listdata.add(data.getString(i));
//            i++;
//        }
//        Log.d(TAG,listdata.get(1)+"");

       // location.getLocation().distanceTo(bam);


        int secondsDelayed = 1;




        //define the handler for setting delay for going to next class

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreen.this, Compass.class));
                finish();
            }
        }, secondsDelayed * 4000);
    }

    public void addData(String[] newentry) {
        boolean insertdata = mDatabaseHelper.addData(newentry);
        if (insertdata) {
            toastmessage("Data Successfully inserted ");

        } else {
            toastmessage("something went wrong");

        }


    }

    private void toastmessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}

