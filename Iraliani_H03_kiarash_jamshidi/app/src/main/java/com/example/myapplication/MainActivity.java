package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE1 = "MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "MESSAGE5";
    public static final String EXTRA_MESSAGE6 = "MESSAGE6";
    public static final String EXTRA_MESSAGE7 = "MESSAGE7";
    public static final String EXTRA_MESSAGE8 = "MESSAGE8";
    public static final String EXTRA_MESSAGE9 = "MESSAGE9";
    public static final String EXTRA_MESSAGE10 = "MESSAGE10";



    public static final int TEXT_REQUEST = 1;
    private EditText product1;
    private EditText product2;
    private EditText product3;
    private EditText product4;
    private EditText product5;
    private EditText product6;
    private EditText product7;
    private EditText product8;
    private EditText product9;
    private EditText product10;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        product1 = findViewById(R.id.et_product1);
        product2 = findViewById(R.id.et_product2);
        product3 = findViewById(R.id.et_product3);
        product4 = findViewById(R.id.et_product4);
        product5 = findViewById(R.id.et_product5);
        product6 = findViewById(R.id.et_product6);
        product7 = findViewById(R.id.et_product7);
        product8 = findViewById(R.id.et_product8);
        product9 = findViewById(R.id.et_product9);
        product10 = findViewById(R.id.et_product10);


        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);

        if (savedInstanceState != null) {
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");
            if (isVisible) {
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(savedInstanceState.getString("reply_text"));
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }
    @Override
    public void onRestart(){
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        String message1 = product1.getText().toString();
        String message2 = product2.getText().toString();
        String message3 = product3.getText().toString();
        String message4 = product4.getText().toString();
        String message5 = product5.getText().toString();
        String message6 = product6.getText().toString();
        String message7 = product7.getText().toString();
        String message8 = product8.getText().toString();
        String message9 = product9.getText().toString();
        String message10 = product10.getText().toString();



        intent.putExtra(EXTRA_MESSAGE1, message1);
        intent.putExtra(EXTRA_MESSAGE2, message2);
        intent.putExtra(EXTRA_MESSAGE3, message3);
        intent.putExtra(EXTRA_MESSAGE4, message4);
        intent.putExtra(EXTRA_MESSAGE5, message5);
        intent.putExtra(EXTRA_MESSAGE6, message6);
        intent.putExtra(EXTRA_MESSAGE7, message7);
        intent.putExtra(EXTRA_MESSAGE8, message8);
        intent.putExtra(EXTRA_MESSAGE9, message9);
        intent.putExtra(EXTRA_MESSAGE10, message10);

        startActivityForResult(intent, TEXT_REQUEST);
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", mReplyTextView.getText().toString());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
