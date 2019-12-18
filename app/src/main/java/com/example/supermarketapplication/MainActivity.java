package com.example.supermarketapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView ed;
    Button bt, getStarted;


    static ArrayList<RetrofitData> responses = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLoacale();
        setContentView(R.layout.activity_main);


        ArrayList<String> Cart = new ArrayList<>();
        ArrayList<String> Favorite = new ArrayList<>();


        changeStatusBarColor(getColor(R.color.BackGround));



        Users user = new Users("Admin" , "Admin","nablus", "male"
                , "059929292" , "Ahmed" ,"Ibrahim" , Cart , Favorite, "Yes");


        DataBaseHelper dataBaseHelper =new
                DataBaseHelper(this,"Project",null,1);
        dataBaseHelper.AddUser(user);




        getStarted = findViewById(R.id.MainActivity_getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Create handle for the RetrofitInstance interface*/
                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<List<RetrofitData>> call = service.getAllData();
                call.enqueue(new Callback<List<RetrofitData>>() {
                    @Override
                    public void onResponse(Call<List<RetrofitData>> call, Response<List<RetrofitData>> response) {
                        //ed.setText(ed.getText() + response.body().get(0).getTitle());

                        responses.clear();

                        responses.addAll(response.body());

                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, getString(R.string.MainActivity_Connected), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<RetrofitData>> call, Throwable t) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(getString(R.string.MainActivity_NotConnected))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.MainActivity_ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                });


            }
        });


/*

        ed = findViewById(R.id.TextField);

        final Cursor allUsers = dataBaseHelper.getAllUsers();
        while (allUsers.moveToNext()) {
            ed.setText( ed.getText() +
                    "Id= " + allUsers.getString(0)
                            + "\nName= " + allUsers.getString(1)
                            + "\nPhone= " + allUsers.getString(2)
                            + "\nGender= " + allUsers.getString(3)
                            + "\n\n"
            );
        }
*/










    }


    public void changeLangauge(){
        String [] LanguagesList = {"English" , "Arabic"};
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(LanguagesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0 )
                {
                    setLocale("en");

                }else
                if(which == 1)
                {
                    setLocale("ar");

                }

                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mBuilder.show();

    }

    private void setLocale(String ar) {

        Locale locale = new Locale(ar);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings" , MODE_PRIVATE ).edit();
        editor.putString("My_Lang", ar);
        editor.apply();

        recreate();

    }


    public  void loadLoacale(){
        SharedPreferences preferences = getSharedPreferences("Settings" , Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang" , "");

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings" , MODE_PRIVATE ).edit();
        editor.putString("My_Lang", language);
        editor.apply();


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
