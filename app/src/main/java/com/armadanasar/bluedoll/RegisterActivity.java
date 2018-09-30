package com.armadanasar.bluedoll;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText register_editName;
    EditText register_editEmail;
    EditText register_editPassword;
    EditText register_editConfirmPassword;
    Button register_btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_editName = findViewById(R.id.register_editName);
        register_editEmail = findViewById(R.id.register_editEmail);
        register_editPassword = findViewById(R.id.register_editPassword);
        register_editConfirmPassword = findViewById(R.id.register_editConfirmPassword);
        register_btnRegister = findViewById(R.id.register_btnRegister);



        register_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = register_editName.getText().toString();
                String emailAddress = register_editEmail.getText().toString();
                String password = register_editPassword.getText().toString();
                String confirmPassword = register_editConfirmPassword.getText().toString();
                AppDatabase.users.add(new User(AppDatabase.userCount++, name, emailAddress, password));;
                Toast.makeText(RegisterActivity.this, "Register succeded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
