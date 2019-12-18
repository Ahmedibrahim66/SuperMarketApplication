package com.example.supermarketapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class UsersCart extends AppCompatActivity {

    TextView Nametext,Emailtext;
    CollapsingToolbarLayout Colap;


    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> Type = new ArrayList<>();
    private ArrayList<String> Namear = new ArrayList<>();
    private ArrayList<String> Typear = new ArrayList<>();
    private ArrayList<Double> Price = new ArrayList<>();
    private ArrayList<Integer> Rating = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    ArrayList<RetrofitData> Data = new ArrayList<>();


    RecyclerView recyclerView;
    TextView Amount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_cart);

        Name.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();


        Data.addAll(MainActivity.responses);

        Nametext = findViewById(R.id.tv_name);
        Emailtext = findViewById(R.id.tv_email);
        Colap = findViewById(R.id.Colap);


        final String Email = getIntent().getExtras().getString("Email");

        Emailtext.setText(Email);

        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(getApplicationContext(),"Project",null,1);

        final Cursor allUsers = dataBaseHelper.getAllUsers();
        while (allUsers.moveToNext()) {

            if(allUsers.getString(0).equals(Email))
            {
                Nametext.setText(allUsers.getString(4) + " "+ allUsers.getString(5));
                Colap.setTitle(allUsers.getString(4) + " "+ allUsers.getString(5));

            }

        }


        Cursor cart = dataBaseHelper.getUserCart(Email);

        float TotalAmount = 0;

        String text = null;
        while (cart.moveToNext()) {


            for (int i = 0; i < Data.size(); i++) {
                if (cart.getString(1).equals(Data.get(i).getTitle())) {

                    Name.add(Data.get(i).getTitle());
                    Type.add(Data.get(i).getType() + " " + getString(R.string.CartFragment_Quantity) + " " + cart.getString(2));
                    Namear.add(Data.get(i).getTitlear() );
                    Typear.add(Data.get(i).getTypear() + " " + getString(R.string.CartFragment_Quantity) + " " + cart.getString(2)  );
                    Price.add(Data.get(i).getPrice());
                    Rating.add(Data.get(i).getRating());
                    images.add(Data.get(i).getImageurl());


                    TotalAmount = (float) (TotalAmount + Integer.parseInt(cart.getString(2)) * Data.get(i).getPrice());
                    text = String.format("%.2f", TotalAmount);


                }

            }

        }

        View v = getWindow().getDecorView();

        Amount = findViewById(R.id.Price);
        Amount.setText(getString(R.string.CartFragment_TotalAmount) + " " + text + "₪");
        CreateRecycleView(v);






    }

    private void CreateRecycleView(View v){

        recyclerView = findViewById(R.id.recycleview);


        GoodsRecycleView adapter = new GoodsRecycleView(Name ,Type, Namear , Typear,Price , Rating ,images, getApplicationContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(v.getContext(),  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }



}
