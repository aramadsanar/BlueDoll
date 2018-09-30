package com.armadanasar.bluedoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class DollsListActivity extends AppCompatActivity {

    TextView dolls_list_greeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolls_list);
        dolls_list_greeting = findViewById(R.id.dolls_list_greeting);

        Bundle paramsDictionary = getIntent().getExtras();
        dolls_list_greeting.setText("Hello, " + paramsDictionary.getString("name"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dolls_list_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch(itemId) {
            case R.id.dolls_list_addDollsMenu:
                Toast.makeText(this, "add dolls", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dolls_list_editDollsMenu:
                Toast.makeText(this, "edit dolls", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dolls_list_addLocationMenu:
                Toast.makeText(this, "add doll location", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
