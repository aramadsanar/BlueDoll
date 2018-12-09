package com.armadanasar.bluedoll;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareDollActivity extends AppCompatActivity {

    String username;
    String dollName;
    String phoneNumber;

    Button sendButton;
    EditText phoneNumberEditText;
    String templateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("username");
        dollName = getIntent().getStringExtra("dollName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_doll);

        sendButton = findViewById(R.id.share_doll_sendSMS);
        phoneNumberEditText = findViewById(R.id.share_doll_phoneNumber);



        templateText = "Hey, check this doll from BlueDoll! It’s the " + dollName + " and it’s so awesome!\n" +
                "- " + username;

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phoneNumberEditText.getText().toString();
                if (!hasSmsPermission()) {
                    requestSendSmsPermission();
                }
                else {
                    sendSms();
                }
            }
        });

    }

    boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    void requestSendSmsPermission() {
        //if (ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.SEND_SMS))
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
    }

    void sendSms() {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, templateText, null, null);
        Toast.makeText(ShareDollActivity.this, "SMS Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            sendSms();
                        }
                    });
                }
        }
    }
}
