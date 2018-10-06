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

    private boolean validateRegistration(String name, String email, String password, String confirmPassword) {
        boolean isNameValid = validateName(name);
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password, confirmPassword);
        boolean emailExists = checkForEmailExistence(email);


        return (isNameValid && isEmailValid && isPasswordValid && !emailExists);
    }

    private boolean checkForEmailExistence(String email) {
        for (User u : AppDatabase.users) {
            if (u.email.equals(email)) return true;
        }
        return false;
    }

    private boolean validatePassword(String password, String confirmPassword) {
        return (
            password.length() >= 6 &&
            password.matches("([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*") &&
            password.equals(confirmPassword)
        );
    }

    private boolean validateEmail(String email) {
        String[] splitAt = email.split("@");
        if (splitAt.length != 2) return false;

        String[] splitDomain = splitAt[1].split(".");
        if (splitAt.length < 2) return false;

        return true;
    }

    private boolean validateName(String name) {
        return (name.length() > 0) ? true : false;
    }



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

                boolean isRegistrationValid = validateRegistration(name, emailAddress, password, password);
                //boolean isRegistrationValid = true;
                if (isRegistrationValid) {
                    AppDatabase.users.add(new User(AppDatabase.userCount++, name, emailAddress, password));
                    ;
                    Toast.makeText(RegisterActivity.this, "Register succeded", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else {
                    Toast.makeText(RegisterActivity.this, "Registration invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
