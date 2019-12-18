package com.example.supermarketapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class LogInActivity extends AppCompatActivity {


    TextView tv;
    EditText Email,Password;
    Button Login;
    CheckBox rememberme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        changeStatusBarColor(getColor(R.color.BackGround));


        tv = findViewById(R.id.LoginActivity_createaccount);
        Email = findViewById(R.id.LoginActivity_Email);
        Password = findViewById(R.id.LoginActivity_Password);
        Login = findViewById(R.id.Login);
        rememberme = findViewById(R.id.LoginActivity_rememberme);

        SharedPreferences preferences = getSharedPreferences("Account" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");
        final String passwordsaved = preferences.getString("Password" , "");




        SharedPreferences preferencesLog = getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsavedLoged = preferencesLog.getString("Email" , "");

        if(emailsavedLoged.equals(""))
        {

        }else
        {
            Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), getString(R.string.LogInActivity_Matches), Toast.LENGTH_SHORT).show();
        }



        if(emailsaved != "" && passwordsaved != "") {
            Email.setText(emailsaved);
            Password.setText(passwordsaved);
            Email.setBackground(getDrawable(R.drawable.rounded_edittext_yellowsaved));
            Password.setBackground(getDrawable(R.drawable.rounded_edittext_yellowsaved));
        }


        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(Email.getText().toString().equals(emailsaved))
                {
                    Password.setText(passwordsaved);
                    Email.setBackground(getDrawable(R.drawable.rounded_edittext_yellowsaved));
                    Password.setBackground(getDrawable(R.drawable.rounded_edittext_yellowsaved));
                }else {

                    Email.setBackground(getDrawable(R.drawable.rounded_edittext_green));
                    Password.setBackground(getDrawable(R.drawable.rounded_edittext_green));


                }

                if(Email.getText().toString().equals(""))
                {
                    Password.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });











        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Email.getText().toString() != null && Password.getText().toString() != null && isInDataBase(Email.getText().toString()))
                {


                    DataBaseHelper dataBaseHelper =new
                            DataBaseHelper(getApplicationContext(),"Project",null,1);

                    final Cursor EmailPassword = dataBaseHelper.getPasswordForEmail(Email.getText().toString());
                    while (EmailPassword.moveToNext()) {

                        if(Password.getText().toString().equals(EmailPassword.getString(0)))
                        {
                            if(rememberme.isChecked())
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("Account" , MODE_PRIVATE ).edit();
                                editor.putString("Email", Email.getText().toString());
                                editor.putString("Password", Password.getText().toString());
                                editor.apply();

                            }

                            SharedPreferences.Editor editor = getSharedPreferences("SignedIn" , MODE_PRIVATE ).edit();
                            editor.putString("Email", Email.getText().toString());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), getString(R.string.LogInActivity_Matches), Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(getApplicationContext(), getString(R.string.LogInActivity_notmatch), Toast.LENGTH_SHORT).show();

                    }


                }else Toast.makeText(getApplicationContext(), getString(R.string.LogInActivity_Emailwrong), Toast.LENGTH_SHORT).show();




            }
        });







    }

    public Boolean isInDataBase(String Email)
    {

        DataBaseHelper dataBaseHelper =new
                DataBaseHelper(this,"Project",null,1);

        String ed = "";


        final Cursor allUserseEmails = dataBaseHelper.getAllUsers();
        while (allUserseEmails.moveToNext()) {
            ed = allUserseEmails.getString(0);
            if(ed.equals(Email))
            {
                return true;
            }
        }

        return false;

    }

    private void changeStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }


}
