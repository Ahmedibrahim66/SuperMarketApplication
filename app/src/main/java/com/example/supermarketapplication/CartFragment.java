package com.example.supermarketapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nightonke.boommenu.BoomMenuButton;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

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



    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);


        SharedPreferences preferences = getContext().getSharedPreferences("SignedIn", Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email", "");


        final DataBaseHelper dataBaseHelper = new
                DataBaseHelper(getContext(), "Project", null, 1);


        Name.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();


        Data.addAll(MainActivity.responses);

        String text = null;
        try {

            Cursor cart = dataBaseHelper.getUserCart(emailsaved);

            float TotalAmount = 0;

            text = null;
            while (cart.moveToNext()) {


                for (int i = 0; i < Data.size(); i++) {
                    if (cart.getString(1).equals(Data.get(i).getTitle())) {

                        Name.add(Data.get(i).getTitle());
                        Type.add(Data.get(i).getType() + " " + getString(R.string.CartFragment_Quantity) + " " + cart.getString(2));
                        Namear.add(Data.get(i).getTitlear() );
                        Typear.add(Data.get(i).getTypear()+ " " + getString(R.string.CartFragment_Quantity) + " " + cart.getString(2));
                        Price.add(Data.get(i).getPrice());
                        Rating.add(Data.get(i).getRating());
                        images.add(Data.get(i).getImageurl());


                        TotalAmount = (float) (TotalAmount + Integer.parseInt(cart.getString(2)) * Data.get(i).getPrice());
                        text = String.format("%.2f", TotalAmount);


                    }

                }

            }


        } catch (Exception e) {

        }


        Amount = v.findViewById(R.id.Price);
        Amount.setText(getString(R.string.CartFragment_TotalAmount) + " " + text + "₪");
        CreateRecycleView(v);


        return v;

    }


    private void CreateRecycleView(View v){

        recyclerView = v.findViewById(R.id.recycleview);
        GoodsRecycleView adapter = new GoodsRecycleView(Name ,Type, Namear , Typear , Price , Rating ,images, getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }


}
