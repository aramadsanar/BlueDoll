package com.armadanasar.bluedoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class DollsListActivity extends AppCompatActivity {

    TextView dolls_list_greeting;
    ListView dolls_list_dolls_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolls_list);

        dolls_list_greeting = findViewById(R.id.dolls_list_greeting);
        dolls_list_dolls_list_view = findViewById(R.id.dolls_list_dolls_list_view);

        Bundle paramsDictionary = getIntent().getExtras();
        dolls_list_greeting.setText("Hello, " + paramsDictionary.getString("name"));

        dolls_list_dolls_list_view.setAdapter(new DollAdapter(this, AppDatabase.dolls));
        dolls_list_dolls_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(DollsListActivity.this, "masukj pak eko", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), ViewDollActivity.class);
                i.putExtra("doll_id", position);

                startActivity(i);
            }
        });
        setTitle("Our Dolls");
    }

    @Override
    protected void onResume() {
        super.onResume();
        dolls_list_dolls_list_view.invalidateViews();
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
                Intent i = new Intent(DollsListActivity.this, EditDollActivity.class);
                i.putExtra("doll_id", -1);
                startActivity(i);
                break;
            case R.id.dolls_list_addLocationMenu:
                //Toast.makeText(this, "add doll location", Toast.LENGTH_SHORT).show();
                Intent x = new Intent(DollsListActivity.this, AddDollLocationMap.class);
                startActivity(x);
                break;
            case R.id.dolls_list_logoutMenu:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
