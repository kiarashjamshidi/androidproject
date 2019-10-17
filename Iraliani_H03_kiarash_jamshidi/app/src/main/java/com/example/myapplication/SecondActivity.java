package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "REPLY";
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    private EditText Reply;
    public  int incommon;
    public  String[] message ;
    public  String[] common;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Reply = findViewById(R.id.editText_second);
        common= new String[5];
        message= new String[10];
        Intent intent = getIntent();
        incommon=0;
        Log.d(LOG_TAG, ""+intent.getStringExtra(MainActivity.EXTRA_MESSAGE1));
        for (int i=0;i<10;i++){
            message[i]="sh";
        }
        message[0] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        message[1] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        message[2] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        message[3] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4);
        message[4] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE5);
        message[5] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE6);
        message[6] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE7);
        message[7] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE8);
        message[8] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE9);
        message[9] = intent.getStringExtra(MainActivity.EXTRA_MESSAGE10);
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                if (message[i]==message[j]){
                    check = true;

                    for (int z=0;z<incommon;z++){
                        if (common[z]==message[i]){
                            check=false;
                        }
                        if (check){
                            common[incommon]=message[i];
                            incommon++;
                        }
                    }
                }
            }
        }
        String temp=new String();
        for (int i=0;i<incommon;i++){
            temp = temp +common[i];

        }
        TextView textView = findViewById(R.id.text_message);
        textView.setText(temp);
    }

    public void returnReply(View view) {
        String reply = Reply.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG, "End SecondActivity");
        finish();
    }
}