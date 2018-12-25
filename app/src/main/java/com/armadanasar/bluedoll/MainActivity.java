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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editEmailAddress;
    EditText editPassword;
    Button btnLogin;
    TextView btnRegister;
    SQLiteDatabaseHelper dbHelper;

//    private void initDollsDb() {
//        AppDatabase.dolls.add(new Doll(-1, "Boneka Kucing", "Boneka Kucing Lucu", R.drawable.boneka_kucing));
//        AppDatabase.dolls.add(new Doll(-1, "Boneka Rusa", "Boneka Rusa Lucu", R.drawable.boneka_rusa));
//        AppDatabase.dolls.add(new Doll(-1, "Boneka Panda", "Boneka Panda Lucu", R.drawable.boneka_panda));
//        AppDatabase.dolls.add(new Doll(-1, "Boneka Babi", "Boneka Babi Lucu", R.drawable.boneka_babi));
//    }
    private User authenticateUser(String emailAddress, String password) {
        if (emailAddress.isEmpty() || password.isEmpty()) return null;

        //boolean userFound = false;
        User currentUser = null;
        for (User u : AppDatabase.users) {
            if (u.email.equals(emailAddress) && u.password.equals(password)) {
                //userFound = true;
                currentUser = u;
            }
        }

        return currentUser;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initDollsDb();
//        Intent i = new Intent(MainActivity.this, AddDollLocationMap.class);
//        startActivity(i);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmailAddress = findViewById(R.id.editEmailAddress);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new SQLiteDatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = editEmailAddress.getText().toString();
                String password = editPassword.getText().toString();
                //AppDatabase.users.add(new User(AppDatabase.userCount++, "", emailAddress, password));
                boolean userFound = false;
                //User currentUser = authenticateUser(emailAddress, password);
                User currentUser = dbHelper.authenticateUser(new User(-1, "", emailAddress, password));

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
        setTitle("Login");
    }
}
