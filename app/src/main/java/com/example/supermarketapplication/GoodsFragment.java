package com.example.supermarketapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.w3c.dom.Text;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsFragment extends Fragment {


    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> Type = new ArrayList<>();
    private ArrayList<String> Namear = new ArrayList<>();
    private ArrayList<String> Typear = new ArrayList<>();
    private ArrayList<Double> Price = new ArrayList<>();
    private ArrayList<Integer> Rating = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    ArrayList<RetrofitData> Data = new ArrayList<>();


    BoomMenuButton bmb;
    RecyclerView recyclerView;

    FloatingActionButton fab;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        final View v =  inflater.inflate(R.layout.fragment_goods, container, false);


        fab = v.findViewById(R.id.floatingAction);


        Name.clear();
        Namear.clear();
        Typear.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();

        Data.addAll(MainActivity.responses);

        for(int i =0 ; i<Data.size() ; i++)
        {

            Name.add(Data.get(i).getTitle());
            Type.add(Data.get(i).getType());
            Namear.add(Data.get(i).getTitlear());
            Typear.add(Data.get(i).getTypear());
            Price.add(Data.get(i).getPrice());
            Rating.add(Data.get(i).getRating());
            images.add(Data.get(i).getImageurl());

        }


        CreateRecycleView(v);









        bmb = v.findViewById(R.id.bmb);

        TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_goods)
                .normalText(getString(R.string.GoodsFragmnet_searchbytype))
                .normalColor(getResources().getColor(R.color.Test4))


                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(final int index) {
                        // When the boom-button corresponding this builder is clicked.

                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setTitle(getString(R.string.GoodsFragmnet_searchbytype));
                        String[] types = {getString(R.string.GoodsFragmnet_Vegetables), getString(R.string.GoodsFragmnet_Fruits) , getString(R.string.GoodsFragmnet_Dairies) , getString(R.string.GoodsFragmnet_Others)};
                        b.setItems(types, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                switch(which){
                                    case 0:
                                        FilterByType("vegetable" , v);
                                        Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_Filteredby) +" "+  getString(R.string.GoodsFragmnet_Vegetables), Toast.LENGTH_SHORT).show();
                                        break;

                                    case 1:
                                        FilterByType("fruit" , v);
                                        Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_Filteredby)+ " " + getString(R.string.GoodsFragmnet_Fruits), Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        FilterByType("dairy" , v);
                                        Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_Filteredby)+ " " + getString(R.string.GoodsFragmnet_Dairies), Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        FilterByType("others" , v);
                                        Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_Filteredby) + " " + getString(R.string.GoodsFragmnet_Others), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }

                        });

                        b.show();

                    }
                });

        bmb.addBuilder(builder);


        TextOutsideCircleButton.Builder builder1 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_tag)
                .normalText(getString(R.string.GoodsFragmnet_searchbyPrice))
                .normalColor(getResources().getColor(R.color.Test3))

                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        final EditText edittext = new EditText(getContext());
                        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edittext.setHint(getString(R.string.GoodsFragmnet_miniprice));
                        alert.setTitle(getString(R.string.GoodsFragmnet_searchbyPrice));

                        alert.setView(edittext);



                        alert.setPositiveButton(getString(R.string.GoodsFragmnet_confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                                if(edittext.getText().toString().equals(""))
                                {
                                    Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_enternumber), Toast.LENGTH_SHORT).show();

                                }else
                                {
                                    String YouEditTextValue = edittext.getText().toString();
                                    FilterByPrice(YouEditTextValue , v);
                                }


                            }
                        });



                        alert.show();

                    }
                });

        bmb.addBuilder(builder1);


        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_height)
                .normalText(getString(R.string.GoodsFragmnet_searchbyheight))
                .normalColor(getResources().getColor(R.color.Test3))

                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());



                        alert.setMessage("");
                        alert.setTitle(getString(R.string.GoodsFragmnet_searchbyheight));

                        final EditText ed = new EditText(getContext());
                        ed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT , 1 ));
                        ed.setHint(getString(R.string.GoodsFragmnet_MIN));
                        ed.setInputType(InputType.TYPE_CLASS_NUMBER);

                        final EditText ed1 = new EditText(getContext());
                        ed1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT , 1 ));
                        ed1.setHint(getString(R.string.GoodsFragmnet_MAX));
                        ed1.setInputType(InputType.TYPE_CLASS_NUMBER);

                        LinearLayout ly = new LinearLayout(getContext());
                        ly.addView(ed);
                        ly.addView(ed1);
                        alert.setView(ly);





                        alert.setPositiveButton(getString(R.string.GoodsFragmnet_confirm), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //Toast.makeText(getContext(), ed.getText().toString(), Toast.LENGTH_SHORT).show();
                                if(ed.getText().toString().equals("") || ed1.getText().toString().equals(""))
                                {
                                    Toast.makeText(getContext(), getString(R.string.GoodsFragmnet_enternumber), Toast.LENGTH_SHORT).show();

                                }else FilterByHeight(Integer.parseInt(ed.getText().toString()) ,Integer.parseInt(ed1.getText().toString()) , v  );

                            }
                        });



                        alert.show();


                    }
                });

        bmb.addBuilder(builder2);



        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_remove_symbol)
                .normalText(getString(R.string.GoodsFragmnet_RemoveFilter))
                .normalColor(getResources().getColor(R.color.Test4))


                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(final int index) {
                        // When the boom-button corresponding this builder is clicked.

                        Name.clear();
                        Namear.clear();
                        Typear.clear();
                        Type.clear();
                        Price.clear();
                        Rating.clear();
                        images.clear();

                        for(int i =0 ; i<Data.size() ; i++) {

                            Name.add(Data.get(i).getTitle());
                            Type.add(Data.get(i).getType());
                            Namear.add(Data.get(i).getTitlear());
                            Typear.add(Data.get(i).getTypear());
                            Price.add(Data.get(i).getPrice());
                            Rating.add(Data.get(i).getRating());
                            images.add(Data.get(i).getImageurl());
                        }
                        CreateRecycleView(v);



                    }
                });

        bmb.addBuilder(builder3);

        bmb.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bmb.boom();
            }
        });
        return v;
    }



    private void CreateRecycleView(View v){

        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview);


        GoodsRecycleView adapter = new GoodsRecycleView(Name ,Type,Namear , Typear , Price , Rating ,images, getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void FilterByType(String type , View v)
    {
        Name.clear();
        Namear.clear();
        Typear.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();



        for(int i =0 ; i<Data.size() ; i++)
        {
            if(type.equals("others"))
            {
                if(Data.get(i).getType().equals("fruit") || Data.get(i).getType().equals("vegetable") || Data.get(i).getType().equals("dairy") )
                {

                }else {


                    Name.add(Data.get(i).getTitle());
                    Type.add(Data.get(i).getType());
                    Namear.add(Data.get(i).getTitlear());
                    Typear.add(Data.get(i).getTypear());
                    Price.add(Data.get(i).getPrice());
                    Rating.add(Data.get(i).getRating());
                    images.add(Data.get(i).getImageurl());


                }
            }else {

                if (Data.get(i).getType().equals(type)) {

                    Name.add(Data.get(i).getTitle());
                    Type.add(Data.get(i).getType());
                    Namear.add(Data.get(i).getTitlear());
                    Typear.add(Data.get(i).getTypear());
                    Price.add(Data.get(i).getPrice());
                    Rating.add(Data.get(i).getRating());
                    images.add(Data.get(i).getImageurl());


                }
            }



        }

        CreateRecycleView(v);

    }


    public void FilterByPrice(String price , View v)
    {
        int priceInt = Integer.parseInt(price.trim());

        Name.clear();
        Namear.clear();
        Typear.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();
        for(int i =0 ; i<Data.size() ; i++) {
            if (Data.get(i).getPrice() < priceInt)
            {
                Name.add(Data.get(i).getTitle());
                Type.add(Data.get(i).getType());
                Namear.add(Data.get(i).getTitlear());
                Typear.add(Data.get(i).getTypear());
                Price.add(Data.get(i).getPrice());
                Rating.add(Data.get(i).getRating());
                images.add(Data.get(i).getImageurl());
            }

            }

        CreateRecycleView(v);

    }


        public void FilterByHeight(int min , int max , View v)
        {
            Name.clear();
            Namear.clear();
            Typear.clear();
            Type.clear();
            Price.clear();
            Rating.clear();
            images.clear();

            Toast.makeText(getContext(), "work" , Toast.LENGTH_LONG);


            for(int i =0 ; i<Data.size() ; i++) {
                if (Data.get(i).getHeight() >= min && Data.get(i).getHeight() <= max)
                {
                    Name.add(Data.get(i).getTitle());
                    Type.add(Data.get(i).getType());
                    Namear.add(Data.get(i).getTitlear());
                    Typear.add(Data.get(i).getTypear());
                    Price.add(Data.get(i).getPrice());
                    Rating.add(Data.get(i).getRating());
                    images.add(Data.get(i).getImageurl());
                }

            }
            CreateRecycleView(v);


        }



}
