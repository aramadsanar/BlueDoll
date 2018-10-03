package com.armadanasar.bluedoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditDollActivity extends AppCompatActivity {

    TextView curdoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doll);

        String current_doll = getIntent().getStringExtra("doll");
        curdoll = findViewById(R.id.curdoll);

        curdoll.setText(current_doll);
    }
}
