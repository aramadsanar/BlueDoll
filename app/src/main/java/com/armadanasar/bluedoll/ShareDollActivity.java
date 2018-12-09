package com.armadanasar.bluedoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShareDollActivity extends AppCompatActivity {

    String username;
    String dollName;
    String phoneNumber;

    Button sendButton;
    EditText phoneNumberEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("username");
        dollName = getIntent().getStringExtra("dollName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_doll);

        sendButton = findViewById(R.id.share_doll_sendSMS);
        phoneNumberEditText = findViewById(R.id.share_doll_phoneNumber);

        phoneNumber = phoneNumberEditText.getText().toString();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
