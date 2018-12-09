package com.armadanasar.bluedoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDollActivity extends AppCompatActivity {

    ImageView view_doll_doll_image;
    TextView view_doll_doll_name;
    TextView view_doll_doll_description;
    String username;
    String dollName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doll);

        view_doll_doll_image = findViewById(R.id.view_doll_doll_image);
        view_doll_doll_name = findViewById(R.id.view_doll_doll_name);
        view_doll_doll_description = findViewById(R.id.view_doll_doll_description);

        int dollId = getIntent().getIntExtra("doll_id", -1);
        username = getIntent().getStringExtra("username");

        //reject get into activity
        if (dollId == -1) finish();

        Log.d("id boneka", "" + dollId);

        Doll doll = AppDatabase.dolls.get(dollId);

        view_doll_doll_image.setImageResource(doll.imageResId);
        view_doll_doll_name.setText(doll.name);
        view_doll_doll_description.setText(doll.description);
        setTitle(doll.name);
        dollName = doll.name;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_doll_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        int k = 0;
        switch(itemId) {
            case R.id.view_doll_shareMenu:
                Intent i = new Intent(ViewDollActivity.this, ShareDollActivity.class);
                i.putExtra("username", username);
                i.putExtra("dollName", dollName);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
