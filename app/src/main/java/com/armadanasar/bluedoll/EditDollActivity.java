package com.armadanasar.bluedoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

public class EditDollActivity extends AppCompatActivity {

    TextView curdoll;

    EditText edit_doll_name;
    EditText edit_doll_description;
    Spinner edit_doll_image_spinner;
    Button edit_doll_save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doll);

        final int current_doll = getIntent().getIntExtra("doll_id", -1);

        curdoll = findViewById(R.id.curdoll);
        edit_doll_name = findViewById(R.id.edit_doll_name);
        edit_doll_description = findViewById(R.id.edit_doll_description);
        edit_doll_image_spinner = findViewById(R.id.edit_doll_image_spinner);
        edit_doll_save_button = findViewById(R.id.edit_doll_save_button);


        DollImageAdapter dollImageAdapter = new DollImageAdapter(this, AppDatabase.dolls);
        dollImageAdapter.setDropDownViewResource(R.layout.image_spinner_item);
        edit_doll_image_spinner.setAdapter(dollImageAdapter);

        edit_doll_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dollName = edit_doll_name.getText().toString();
                String description = edit_doll_description.getText().toString();
                int imageId = edit_doll_image_spinner.getSelectedItemPosition();

                if (current_doll > -1) {
                    //save it
                    Doll doll = AppDatabase.dolls.get(current_doll);
                    doll.name = dollName;
                    doll.description = description;
                    doll.imageResId = AppDatabase.dolls.get(imageId).imageResId;
                }

                else {
                    //add it
                    AppDatabase.dolls.add(new Doll(AppDatabase.dollCount++, dollName, description, imageId));
                }
                finish();
            }
        });
    }
}
