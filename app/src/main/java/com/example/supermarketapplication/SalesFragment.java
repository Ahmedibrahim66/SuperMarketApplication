package com.example.supermarketapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {





    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> Type = new ArrayList<>();
    private ArrayList<String> Namear = new ArrayList<>();
    private ArrayList<String> Typear = new ArrayList<>();
    private ArrayList<Double> Price = new ArrayList<>();
    private ArrayList<Integer> Rating = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    ArrayList<RetrofitData> Data = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sales, container, false);




        Name.clear();
        Type.clear();
        Price.clear();
        Rating.clear();
        images.clear();



        Data.addAll(MainActivity.responses);



        for(int i =0; i<Data.size() ; i++)
        {

            if(Data.get(i).getRating() >= 4)
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
        }
        return v;

    }



    private void CreateRecycleView(View v){

        recyclerView = v.findViewById(R.id.recycleview);


        GoodsRecycleView adapter = new GoodsRecycleView(Name ,Type, Namear , Typear, Price , Rating ,images, getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }


}
