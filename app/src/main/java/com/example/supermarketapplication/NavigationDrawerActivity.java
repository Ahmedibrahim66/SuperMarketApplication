package com.example.supermarketapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.shreyaspatil.material.navigationview.MaterialNavigationView;

import java.util.Locale;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLoacale();
        setContentView(R.layout.activity_navigation_drawer);

        //changeStatusBarColor(getColor(R.color.BackGround));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);



        SharedPreferences preferences = getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");

        DataBaseHelper dataBaseHelper =new
                DataBaseHelper(getApplicationContext(),"Project",null,1);

        final Cursor Users = dataBaseHelper.getAllUsers();



        while(Users.moveToNext())
        {
            if(Users.getString(0).equals(emailsaved))
            {
                if(Users.getString(9).equals("Yes"))
                {

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.drawer_menu_admin);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CustomersFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_Customers);

                }
            }
        }


        //navigation drawer header
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_username);



        //get username first and last name from data base according to the signed in user account
        SharedPreferences preferences1 = getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved1 = preferences1.getString("Email" , "");
        navUsername.setText(getUserFullName(emailsaved1));

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences preferences2 = getSharedPreferences("Settings" , Activity.MODE_PRIVATE);
        final String Lang = preferences2.getString("My_Lang" , "");


            toolbar.setTitle(getString(R.string.app_name));

        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();

        //Toast.makeText(this, Lang, Toast.LENGTH_SHORT).show();
        if(Lang.equals("ar"))
        {


            headerView.setBackground(getDrawable(R.drawable.nav_header_rounded_corner_ar));

            navViewBackground.setShapeAppearanceModel(
                    navViewBackground.getShapeAppearanceModel()
                            .toBuilder()
                            .setTopLeftCorner(CornerFamily.ROUNDED,200)
                            .setBottomLeftCorner(CornerFamily.ROUNDED,100)
                            .build());

           // navigationView.setItemStyle(MaterialNavigationView.ITEM_STYLE_ROUND_RECTANGLE);



        }else {

            headerView.setBackground(getDrawable(R.drawable.nav_header_rounded_corner_en));


            navViewBackground.setShapeAppearanceModel(
                    navViewBackground.getShapeAppearanceModel()
                            .toBuilder()
                            .setTopRightCorner(CornerFamily.ROUNDED,200)
                            .setBottomRightCorner(CornerFamily.ROUNDED,100)
                            .build());

           //navigationView.setItemStyle(MaterialNavigationView.ITEM_STYLE_ROUND_RECTANGLE);


        }






    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_goods:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GoodsFragment()).commit();
                break;

            case R.id.nav_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CartFragment()).commit();
                break;

            case R.id.nav_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoriteFragment()).commit();
                break;

            case R.id.nav_sales:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SalesFragment()).commit();
                break;


            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_logout:

                SharedPreferences.Editor editor = getSharedPreferences("SignedIn" , MODE_PRIVATE ).edit();
                editor.putString("Email", "");
                editor.apply();
                Intent intent = new Intent(this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;



            case R.id.nav_contactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactUsFragment()).commit();
                break;


            case R.id.nav_Customers:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CustomersFragment()).commit();

                break;

            case R.id.nav_arabic:

                setLocale("ar");


                break;


            case R.id.nav_english:

                setLocale("en");


                break;




        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public String getUserFullName(String Email) {

        DataBaseHelper dataBaseHelper = new
                DataBaseHelper(this, "Project", null, 1);

        String FirstName = "";

        final Cursor allUserseEmails = dataBaseHelper.getFirstNameForEmail(Email);
        while (allUserseEmails.moveToNext()) {
            FirstName = allUserseEmails.getString(0);
        }


        String LastName = "";

        final Cursor allUserseEmails1 = dataBaseHelper.getLastNameForEmail(Email);
        while (allUserseEmails1.moveToNext()) {
            LastName = allUserseEmails1.getString(0);


        }


        return FirstName + " " + LastName;

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

    private void changeStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }






}
