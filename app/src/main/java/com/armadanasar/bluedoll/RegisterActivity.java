package com.armadanasar.bluedoll;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText register_editName;
    EditText register_editEmail;
    EditText register_editPassword;
    EditText register_editConfirmPassword;
    Button register_btnRegister;
    CheckBox register_agreementCheckBox;
    SQLiteDatabaseHelper dbHelper;

    private boolean validateRegistration(String name, String email, String password, String confirmPassword, boolean agreement) {
        boolean isNameValid = validateName(name);
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password, confirmPassword);
        boolean emailExists = checkForEmailExistence(email);


        if (!isEmailValid) {
            Toast.makeText(this, "Name length shal be > 0", Toast.LENGTH_SHORT).show();
        }

        if (!isEmailValid) {
            Toast.makeText(this, "Email address error", Toast.LENGTH_SHORT).show();
        }

        if (!isPasswordValid) {
            Toast.makeText(this, "Password is invalid", Toast.LENGTH_SHORT).show();
        }

        if (emailExists) {
            Toast.makeText(this, "Email is taken already", Toast.LENGTH_SHORT).show();
        }

        if (!agreement) {
            Toast.makeText(this, "You need to agree to the terms", Toast.LENGTH_SHORT).show();
        }

        return (isNameValid && isEmailValid && isPasswordValid && !emailExists && agreement);
    }

    private boolean checkForEmailExistence(String email) {
        return dbHelper.checkUserExistence(email);
//        for (User u : AppDatabase.users) {
//            if (u.email.equals(email)) return true;
//        }
//        return false;
    }

    private boolean validatePassword(String password, String confirmPassword) {
        if (password.length() < 6 || !password.equals(confirmPassword)) return false;

        int huruf = 0;
        int angka = 0;


        for (int i = 0; i < password.length(); i++) {
            if (
                    (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') ||
                    (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
               ) {
                huruf++;
            }

            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') angka++;
        }

        if (huruf >= 1 && angka >= 1) return true;
        return false;
    }

    private boolean validateEmail(String email) {
        boolean valid_email_domain = false;
        boolean valid_domain = false;
        String[] splitAt = email.split("@");
        if (splitAt.length != 2) return false;
        else valid_email_domain = true;

        String domain = splitAt[1];
        String[] splitDomain = domain.split("\\.");
        int terms = 0;

        if (splitDomain.length < 2) return false;
        else valid_domain = true;

        return valid_domain && valid_email_domain;
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
        register_agreementCheckBox = findViewById(R.id.register_agreementCheckBox);
        dbHelper = new SQLiteDatabaseHelper(this);

        register_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = register_editName.getText().toString();
                String emailAddress = register_editEmail.getText().toString();
                String password = register_editPassword.getText().toString();
                String confirmPassword = register_editConfirmPassword.getText().toString();
                boolean agreement = register_agreementCheckBox.isChecked();

                boolean isRegistrationValid = validateRegistration(name, emailAddress, password, password, agreement);
                //boolean isRegistrationValid = true;
                if (isRegistrationValid) {
                    //AppDatabase.users.add(new User(AppDatabase.userCount++, name, emailAddress, password));
                    dbHelper.registerUser(new User(-1, name, emailAddress, password));
                    Toast.makeText(RegisterActivity.this, "Register succeded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        setTitle("Register");
    }
}
