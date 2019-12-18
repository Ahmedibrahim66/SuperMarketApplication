package com.example.supermarketapplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {



    Spinner City;
    Spinner gender;
    ImageView imageView;
    EditText Password,ConfirmPassword,Phone,FirstName,LastName;
    Button CreateAccount;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_profile, container, false);

        final String[] YouEditTextValue = {""};
        String PasswordEmail ="";


        SharedPreferences preferences = v.getContext().getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");

        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(v.getContext(),"Project",null,1);




        final Cursor EmailPassword = dataBaseHelper.getPasswordForEmail(emailsaved);
        while (EmailPassword.moveToNext()) {

            PasswordEmail= EmailPassword.getString(0);
        }


        final String finalPasswordEmail = PasswordEmail;

        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setNegativeButton(getString(R.string.ProfileFragment_cancel), null)
                .setPositiveButton(getString(R.string.GoodsFragmnet_confirm), null)
                .create();

        final EditText edittext = new EditText(getContext());
        edittext.setInputType(InputType.TYPE_CLASS_TEXT);
        edittext.setHint("***********");
        builder.setTitle(getString(R.string.ProfileFragment_EnterPassword));

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getContext(), getString(R.string.ProfileFragment_EnterPassword), Toast.LENGTH_SHORT).show();
                ((AlertDialog) dialog).show();
            }
        });


        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button b = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if(edittext.getText().toString().equals(""))
                        {
                            Toast.makeText(getContext(), getString(R.string.ProfileFragment_EnterPassword), Toast.LENGTH_SHORT).show();

                        }else
                        {
                            YouEditTextValue[0] = edittext.getText().toString();
                        }


                        if(finalPasswordEmail.equals(YouEditTextValue[0])) {
                            dialog.dismiss();
                        }else
                        {
                            Toast.makeText(getContext(), getString(R.string.LogInActivity_notmatch), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                Button n = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                n.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        dialog.dismiss();

                    }

                });



            }
            });





            builder.setView(edittext);
            builder.show();

            Password = v.findViewById(R.id.Signupactivity2_password);
            ConfirmPassword = v.findViewById(R.id.Signupactivity2_Confirmpassword);
            imageView = v.findViewById(R.id.Signupactivity2_showpassword);
            Phone = v.findViewById(R.id.Signupactivity2_phone);
            FirstName = v.findViewById(R.id.Signupactivity2_firstname);
            LastName = v.findViewById(R.id.Signupactivity2_lastname);
            CreateAccount = v.findViewById(R.id.createaccount);




        //Adding data to spinners
        String[] options1 = { getString(R.string.Spinner_City_Jerusalem) , getString(R.string.Spinner_City_Bethlehem) , getString(R.string.Spinner_City_Hebron),
                getString(R.string.Spinner_City_Jenin), getString(R.string.Spinner_City_Jericho) , getString(R.string.Spinner_City_Nablus) , getString(R.string.Spinner_City_Qalqilya)
                ,getString(R.string.Spinner_City_Ramallah) , getString(R.string.Spinner_City_Salfit) , getString(R.string.Spinner_City_Tulkarm) };

        City =(Spinner)
                v.findViewById(R.id.Signupactivity2_city);
        ArrayAdapter<String> objGenderArr1 = new
                ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_dropdown_item, options1);
        City.setAdapter(objGenderArr1);


        String[] options2 = { getString(R.string.Spinner_gender_male) , getString(R.string.Spinner_gender_female)  };

        gender =(Spinner)
                v.findViewById(R.id.Signupactivity2_gender);
        ArrayAdapter<String> objGenderArr2 = new
                ArrayAdapter<>(v.getContext(),android.R.layout.simple_spinner_dropdown_item, options2);
        gender.setAdapter(objGenderArr2);


        final Cursor UserInformation = dataBaseHelper.getUsersInformation(emailsaved);
        while (UserInformation.moveToNext()) {

            FirstName.setText(UserInformation.getString(4));
            Password.setText(UserInformation.getString(1));
            ConfirmPassword.setText(UserInformation.getString(1));
            Phone.setText(UserInformation.getString(3));
            LastName.setText(UserInformation.getString(5));

            if(UserInformation.getString(6).equals(getString(R.string.Spinner_gender_male)))
            {
                gender.setSelection(0);

            }else gender.setSelection(1);


        }


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
                                    Users user = new Users(emailsaved , Password.getText().toString() , City.getSelectedItem().toString(), gender.getSelectedItem().toString() ,
                                            Phone.getText().toString(), FirstName.getText().toString() , LastName.getText().toString(), Empty , Empty, "No" );


                                    dataBaseHelper.updateUser(user);


                                    Toast.makeText(getContext(), getString(R.string.ProfileFragment_UpdatedAccount), Toast.LENGTH_LONG).show();


                                }else {
                                    Password.setError("!");
                                    ConfirmPassword.setError("!");
                                    Toast.makeText(getContext(), getString(R.string.SignUpActivity2_passwordnotcorrect), Toast.LENGTH_LONG).show();
                                }


                            }else{
                                Password.setError("!");
                                ConfirmPassword.setError("!");
                                Toast.makeText(getContext(), getString(R.string.SignUpActivity2_passwordLength), Toast.LENGTH_LONG).show();
                            }


                        }else {
                            Password.setError("!");
                            ConfirmPassword.setError("!");
                            Toast.makeText(getContext(), getString(R.string.SignUpActivity2_passwordnotmatch), Toast.LENGTH_LONG).show();
                        }


                    }else{
                        FirstName.setError("!");
                        LastName.setError("!");
                        Toast.makeText(getContext(), getString(R.string.SignUpActivity2_Namenotcorrect), Toast.LENGTH_LONG).show();
                    }



                }else {
                    Phone.setError("!");
                    Toast.makeText(getContext(), getString(R.string.SignUpActivity2_phonenotcorrect), Toast.LENGTH_LONG).show();

                }



            }
        });









        return v;

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




}
