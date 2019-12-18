package com.example.supermarketapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText Email;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        changeStatusBarColor(getColor(R.color.BackGround));


        button = findViewById(R.id.createaccount);
        Email = findViewById(R.id.LoginActivity_Email);
        textView = findViewById(R.id.Signupactivity_login);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValid(Email.getText().toString()) && isNotInDataBase(Email.getText().toString()))
                {
                    Intent intent = new Intent(getApplicationContext() , SignUp2Activity.class);
                    intent.putExtra("Email" , Email.getText().toString());
                    startActivity(intent);
                } else
                {
                    Email.setError(getString(R.string.SignUpActivity1_EmailnotVAlid));
                }



            }
        });





        textView = findViewById(R.id.Signupactivity_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext() , LogInActivity.class);
                startActivity(intent);
            }
        });



    }

    public boolean isNotInDataBase(String Email)
    {
        int test = 0 ;

        DataBaseHelper dataBaseHelper =new
                DataBaseHelper(this,"Project",null,1);

        String ed = "";


        final Cursor allUserseEmails = dataBaseHelper.getAllUsers();
        while (allUserseEmails.moveToNext()) {
            ed = allUserseEmails.getString(0);
            if(ed.equals(Email))
            {
                Toast.makeText(this, getString(R.string.SignUpActivity1_EmailisUsed), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;

    }
    public boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
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
