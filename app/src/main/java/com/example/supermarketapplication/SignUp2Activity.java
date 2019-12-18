package com.example.supermarketapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp2Activity extends AppCompatActivity {


    Spinner City;
    Spinner gender;
    ImageView imageView;
    EditText Password,ConfirmPassword,Phone,FirstName,LastName;
    Button CreateAccount;


    String Email1;






    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        String Email = "";

        changeStatusBarColor(getColor(R.color.BackGround));



        Password = findViewById(R.id.Signupactivity2_password);
        ConfirmPassword = findViewById(R.id.Signupactivity2_Confirmpassword);
        imageView = findViewById(R.id.Signupactivity2_showpassword);
        Phone = findViewById(R.id.Signupactivity2_phone);
        FirstName = findViewById(R.id.Signupactivity2_firstname);
        LastName = findViewById(R.id.Signupactivity2_lastname);
        CreateAccount = findViewById(R.id.createaccount);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             Email = extras.getString("Email");
             Toast.makeText(this, Email, Toast.LENGTH_SHORT).show();
             Email1= Email;

        }




        //Adding data to spinners
        String[] options1 = { getString(R.string.Spinner_City_Jerusalem) , getString(R.string.Spinner_City_Bethlehem) , getString(R.string.Spinner_City_Hebron),
                getString(R.string.Spinner_City_Jenin), getString(R.string.Spinner_City_Jericho) , getString(R.string.Spinner_City_Nablus) , getString(R.string.Spinner_City_Qalqilya)
        ,getString(R.string.Spinner_City_Ramallah) , getString(R.string.Spinner_City_Salfit) , getString(R.string.Spinner_City_Tulkarm) };

        City =(Spinner)
                findViewById(R.id.Signupactivity2_city);
        ArrayAdapter<String> objGenderArr1 = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, options1);
        City.setAdapter(objGenderArr1);


        String[] options2 = { getString(R.string.Spinner_gender_male) , getString(R.string.Spinner_gender_female)  };

        gender =(Spinner)
                findViewById(R.id.Signupactivity2_gender);
        ArrayAdapter<String> objGenderArr2 = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, options2);
        gender.setAdapter(objGenderArr2);



        //to show password and confrim password edit text
        imageView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        Password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }

                return true;
            }
        });




        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Phone.getText().length() == 8 && checkifnumber(Phone.getText().toString()) )
                {

                    if(checkLength(FirstName.getText().toString()) && checkLength(LastName.getText().toString()))
                    {

                        if(Password.getText().toString().equals(ConfirmPassword.getText().toString()))
                        {

                            if(Password.getText().toString().length() >=6 )
                            {

                                if(checkpassword(Password.getText().toString()))
                                {


                                    ArrayList<String> Empty = new ArrayList<>();
                                    Users user = new Users(Email1 , Password.getText().toString() , City.getSelectedItem().toString(), gender.getSelectedItem().toString() ,
                                            Phone.getText().toString(), FirstName.getText().toString() , LastName.getText().toString(), Empty , Empty, "No" );

                                    DataBaseHelper dataBaseHelper =new
                                            DataBaseHelper(getApplicationContext(),"Project",null,1);
                                    dataBaseHelper.AddUser(user);


                                    Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_AccountCreated), Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext() , LogInActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    startActivity(intent);




                                }else {
                                    Password.setError("!");
                                    ConfirmPassword.setError("!");
                                    Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_passwordnotcorrect), Toast.LENGTH_LONG).show();
                                }


                            }else{
                                Password.setError("!");
                                ConfirmPassword.setError("!");
                                Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_passwordLength), Toast.LENGTH_LONG).show();
                            }


                        }else {
                            Password.setError("!");
                            ConfirmPassword.setError("!");
                            Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_passwordnotmatch), Toast.LENGTH_LONG).show();
                        }


                    }else{
                        FirstName.setError("!");
                        LastName.setError("!");
                        Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_Namenotcorrect), Toast.LENGTH_LONG).show();
                    }



                }else {
                    Phone.setError("!");
                    Toast.makeText(getApplicationContext(), getString(R.string.SignUpActivity2_phonenotcorrect), Toast.LENGTH_LONG).show();

                }



            }
        });















    }


    public boolean checkifnumber(String s)
    {
        try {
            int num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false ;
        }

        return true;
    }

    public boolean checkLength(String s)
    {
        if(s.length() < 3)
        {
            return false;
        }else
        return true;
    }

    public boolean checkpassword(String s)
    {
            if(haveSpecialCharacter(s) && stringContainsNumber(s) && ContaintCharacter(s))
            {
                return true;
            }else return false;
    }


    public boolean haveSpecialCharacter(String s)
    {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b)
        {
            return true;
        }
        else
            return false;
    }

    public boolean stringContainsNumber( String s )
    {
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }

    public boolean ContaintCharacter( String s )
    {
        return Pattern.compile( "[^A-Za-z]" ).matcher( s ).find();
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
