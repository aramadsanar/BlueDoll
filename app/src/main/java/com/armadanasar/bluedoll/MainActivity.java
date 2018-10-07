package com.armadanasar.bluedoll;

import android.content.Intent;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editEmailAddress;
    EditText editPassword;
    Button btnLogin;
    Button btnRegister;

    private void initDollsDb() {
        AppDatabase.dolls.add(new Doll(AppDatabase.dollCount++, "Boneka Kucing", "Boneka Kucing Lucu", R.drawable.boneka_kucing));
        AppDatabase.dolls.add(new Doll(AppDatabase.dollCount++, "Boneka Rusa", "Boneka Rusa Lucu", R.drawable.boneka_rusa));
        AppDatabase.dolls.add(new Doll(AppDatabase.dollCount++, "Boneka Panda", "Boneka Panda Lucu", R.drawable.boneka_panda));
        AppDatabase.dolls.add(new Doll(AppDatabase.dollCount++, "Boneka Babi", "Boneka Babi Lucu", R.drawable.boneka_babi));
    }
    private User authenticateUser(String emailAddress, String password) {
        if (emailAddress.isEmpty() || password.isEmpty()) return null;

        boolean userFound = false;
        User currentUser = null;
        for (User u : AppDatabase.users) {
            if (u.email.equals(emailAddress) && u.password.equals(password)) {
                userFound = true;
                currentUser = u;
            }
        }

        return currentUser;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDollsDb();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = editEmailAddress.getText().toString();
                String password = editPassword.getText().toString();
                //AppDatabase.users.add(new User(AppDatabase.userCount++, "", emailAddress, password));
                boolean userFound = false;
                User currentUser = authenticateUser(emailAddress, password);


                if (currentUser != null) {
                    Intent intent = new Intent(MainActivity.this, DollsListActivity.class);
                    intent.putExtra("username", currentUser.email);
                    intent.putExtra("name", currentUser.name);

                    startActivity(intent);
                }

                else {
                    Toast.makeText(MainActivity.this, "Login failed. You need to register this account!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
