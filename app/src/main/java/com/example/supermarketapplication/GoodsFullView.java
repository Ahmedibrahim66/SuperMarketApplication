package com.example.supermarketapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Picture;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GoodsFullView extends AppCompatActivity {


    CollapsingToolbarLayout Colap;

    TextView Type,price,tabletype,tableheigh,tablewidth,tabledescription,specification;
    Button cart,favorite;
    RatingBar rb;

    ArrayList<RetrofitData> Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_full_view);

        Data.addAll(MainActivity.responses);


        ///setTitle("Activity 1");

        ImageView iv = findViewById(R.id.imageView2);
        Colap = findViewById(R.id.Colap);

        Type= findViewById(R.id.tv_type);
        price = findViewById(R.id.tv_price);
        tabletype = findViewById(R.id.table_type);
        tableheigh = findViewById(R.id.table_height);
        tablewidth = findViewById(R.id.table_width);
        tabledescription = findViewById(R.id.table_description);
        rb = findViewById(R.id.rb_ItemRating);
        specification = findViewById(R.id.table_specification);



        cart = findViewById(R.id.button1);
        favorite = findViewById(R.id.button2);




        final String Name = getIntent().getExtras().getString("Name");
        String URL = getIntent().getExtras().getString("Picture");

        final String Namear = getIntent().getExtras().getString("Namear");

        SharedPreferences preferences1 = getSharedPreferences("Settings" , Activity.MODE_PRIVATE);
        final String Langauge = preferences1.getString("My_Lang" , "");



        for(int i =0 ; i<Data.size(); i++)
        {
                if(Data.get(i).getTitle().equals(Name))
                {
                    Type.setText(Data.get(i).getType());
                    price.setText(Double.toString(Data.get(i).getPrice()) + "₪");
                    tabletype.setText(Data.get(i).getType());
                    tableheigh.setText(Integer.toString(Data.get(i).getHeight()));
                    tablewidth.setText(Integer.toString(Data.get(i).getWidth()));
                    tabledescription.setText(Data.get(i).getDescription());
                    rb.setRating(Data.get(i).getRating());

                    if(Langauge.equals("ar"))
                    {
                        Type.setText(Data.get(i).getTypear());
                        price.setText(Double.toString(Data.get(i).getPrice()) + "₪");
                        tabletype.setText(Data.get(i).getTypear());
                        tableheigh.setText(Integer.toString(Data.get(i).getHeight()));
                        tablewidth.setText(Integer.toString(Data.get(i).getWidth()));
                        tabledescription.setText(Data.get(i).getDescriptionar());
                        rb.setRating(Data.get(i).getRating());
                    }

                }

        }





        Picasso.get()
                .load(URL)
                .into(iv);

        Colap.setTitle(Name);

        if(Langauge.equals("ar"))
        {
            Colap.setTitle(Namear);

        }




        favorite.setText(getString(R.string.GoodsFullView_addtofav));




        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(getApplicationContext(),"Project",null,1);


        SharedPreferences preferences = getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");


        final Cursor allUsers = dataBaseHelper.getAllFavorite(emailsaved);
        while (allUsers.moveToNext()) {

            if(emailsaved.equals(allUsers.getString(0)) && Name.equals(allUsers.getString(1)) )
            {
                //Toast.makeText(mContext, allUsers.getString(1) , Toast.LENGTH_SHORT).show();
                favorite.setText(getString(R.string.GoodsFullView_removefromfav));
            }else {


            }



        }


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(favorite.getText().toString().equals(getString(R.string.GoodsFullView_addtofav)))
                {
                    //to add it
                    dataBaseHelper.AddtoFavorite(emailsaved , Name);
                    Toast.makeText(getApplicationContext(), getString(R.string.GoodsFragmnet_adddedtoFav), Toast.LENGTH_SHORT).show();
                    favorite.setText(getString(R.string.GoodsFullView_removefromfav));

                }else
                {
                    // to delete it
                    dataBaseHelper.removeFromFavorite(emailsaved , Name );
                    Toast.makeText(getApplicationContext(), getString(R.string.GoodsFragmnet_RemovedFromFav), Toast.LENGTH_SHORT).show();
                    favorite.setText(getString(R.string.GoodsFullView_addtofav));


                }

            }
        });





        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to cart
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                final EditText edittext = new EditText(getApplicationContext());
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                edittext.setHint(getString(R.string.GoodsFullView_Enter_Amount));
                alert.setTitle(getString(R.string.GoodsFullView_Enter_Amount));

                alert.setView(edittext);



                alert.setPositiveButton(getString(R.string.GoodsFragmnet_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        if(edittext.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(), getString(R.string.GoodsFragmnet_enternumber), Toast.LENGTH_SHORT).show();

                        }else
                        {

                            String EditTextValue = edittext.getText().toString();
                            Cursor UserCart = dataBaseHelper.getUserCart(emailsaved);

                            if(dataBaseHelper.checkifItemCartExists(emailsaved , Name))
                            {
                                while(UserCart.moveToNext())
                                {
                                    if(UserCart.getString(1).equals(Name))
                                    {
                                        int newQun = Integer.parseInt(UserCart.getString(2)) + Integer.parseInt(EditTextValue);
                                        Toast.makeText(GoodsFullView.this, getString(R.string.CustomerCartFullView_Addedtocart), Toast.LENGTH_SHORT).show();
                                        dataBaseHelper.updateCart(emailsaved , Name , newQun );
                                    }

                                }
                            }else
                            {
                                int Quan = Integer.parseInt(EditTextValue);
                                Toast.makeText(GoodsFullView.this, getString(R.string.CustomerCartFullView_Addedtocart), Toast.LENGTH_SHORT).show();
                                dataBaseHelper.AddtoCart(emailsaved , Name , Quan);

                            }




                        }


                    }
                });



                alert.show();

            }
        });










    }



}
