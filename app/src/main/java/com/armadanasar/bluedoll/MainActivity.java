package com.armadanasar.bluedoll;

import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editEmailAddress;
    EditText editPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = editEmailAddress.getText().toString();
                String password = editPassword.getText().toString();
                AppDatabase.users.add(new User(AppDatabase.userCount++, "", emailAddress, password));
                for (User u : AppDatabase.users) {
                    Log.d("User", u.email + " " + u.password);
                }
            }
        });
    }
}
