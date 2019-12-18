package android.sxample.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.snackbar.Snackbar;
import android.content.DialogInterface;

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


import android.widget.TextView;

import java.util.ArrayList;

public class AddNewTrash extends AppCompatActivity {




    //////
    private static final String TAG="ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    String[] strings;

    ///////
    private LocationManager locationManager;
    private LocationListener locationListener;


    int temp;



    Button saveButton;
    Uri savetheimage;
    Intent save;
    Double geolocation1,geolocation2;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private String radioButton2;
    private static final String[] TypeSelector2 = new String[]{
            "Mixed", "Plastic", "Glass", "Organic", "Paper", "Metal", "E-Waste", "Cigarettes"
    };

    ImageView mImageView;
    Button mCaptureBtn;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        /////
        mDatabaseHelper = new DatabaseHelper(this, "TrashDB", null, 6);
        strings=new String[4];
        ////
        save=new Intent(AddNewTrash.this,Compass.class);
        temp = 0;


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            //    textView.append("\n " + location.getLatitude() + " " + location.getLongitude());
                geolocation1 = location.getLatitude();
                geolocation2 = location.getLongitude();



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        /// the argument of the location manager =the first one =provider and second one = time and the location distance and
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here i implement the permission for the user from the manifest that access to the sensor of the gps
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
        } else {

        }

        //////


        ///////
        saveButton=findViewById(R.id.save_button);

        //Camera

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);

        //button click
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        openCamera();
                    }
                }
                else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates("gps", 1000, 1000, locationListener);
                Bundle extras = new Bundle();

                if (savetheimage==null){
                    temp++;
                    Toast.makeText(getApplicationContext(), "you must get the picture first", Toast.LENGTH_SHORT).show();

                }
                else  if(temp<1){
                    temp++;
                    Toast.makeText(getApplicationContext(), "press the save button again", Toast.LENGTH_SHORT).show();


                }else {


                    strings[0]=geolocation1+"";
                    strings[1]=geolocation2+"";
                    strings[2]=savetheimage.toString();
                    strings[3]="3";
                    mDatabaseHelper.addData(strings);

                    startActivity(save);

                }




            }
        });
    }

    //// add the radio button

    public void radiob2(View view) {
        int id = view.getId();
        if (id == R.id.buttonDialog2) {
            dialogRadioButton2();
        }
    }

    private void dialogRadioButton2() {
        radioButton2 = TypeSelector2[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your waste type");
        builder.setSingleChoiceItems(TypeSelector2, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                radioButton2 = TypeSelector2[i];
            }
        });
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(mImageView, "Type: " + radioButton2, Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.CANCEL, null);
        builder.show();
    }
    ///////

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }


    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED  && grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            savetheimage=image_uri;
            mImageView.setImageURI(image_uri);
        }
    }








}