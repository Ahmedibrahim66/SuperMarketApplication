package com.example.supermarketapplication;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class GoodsRecycleView extends RecyclerView.Adapter<GoodsRecycleView.ViewHolder> {

    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> Type = new ArrayList<>();
    private ArrayList<String> Namear = new ArrayList<>();
    private ArrayList<String> Typear = new ArrayList<>();
    private ArrayList<Double> Price = new ArrayList<>();
    private ArrayList<Integer> Rating = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private Context mContext;



    public GoodsRecycleView( ArrayList<String> name, ArrayList<String> type,ArrayList<String> namear, ArrayList<String> typear, ArrayList<Double> price, ArrayList<Integer> rating, ArrayList<String> images, Context mContext) {

        Name = name;
        Type = type;
        Price = price;
        Rating = rating;
        Namear = namear;
        Typear = typear;
        this.images = images;
        this.mContext = mContext;
    }


    @Override
    public GoodsRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goods_view_card, viewGroup, false);
        GoodsRecycleView.ViewHolder holder = new GoodsRecycleView.ViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final GoodsRecycleView.ViewHolder holder, final int position) {


        SharedPreferences preferences1 = mContext.getSharedPreferences("Settings" , Activity.MODE_PRIVATE);
        final String Langauge = preferences1.getString("My_Lang" , "");

        holder.ic_favorite.setVisibility(View.VISIBLE);
        holder.ic_favoritered.setVisibility(View.INVISIBLE);

        holder.Name.setText(Name.get(position));
        holder.Type.setText(Type.get(position));
        holder.Price.setText(Price.get(position).toString() + "₪");
        holder.rating.setRating(Rating.get(position));
        Picasso.get()
                .load(images.get(position))
                .into(holder.image);

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , GoodsFullView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("Name" , Name.get(position));

                if(Langauge.equals("ar"))
                {
                    intent.putExtra("Namear" , Namear.get(position));
                }

                intent.putExtra("Picture" , images.get(position));

                try{

                    ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation
                            ((Activity)mContext , holder.image , ViewCompat.getTransitionName(holder.image));
                    mContext.startActivity(intent  , option.toBundle());

                }catch (Exception e)
                {

                }


            }
        });

        SharedPreferences preferences = mContext.getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");

        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(mContext,"Project",null,1);


        try{

            final Cursor allUsers = dataBaseHelper.getAllFavorite(emailsaved);

            while (allUsers.moveToNext()) {

                if(emailsaved.equals(allUsers.getString(0)) && holder.Name.getText().equals(allUsers.getString(1)) )
                {
                    //Toast.makeText(mContext, allUsers.getString(1) , Toast.LENGTH_SHORT).show();
                    holder.ic_favorite.setVisibility(View.INVISIBLE);
                    holder.ic_favoritered.setVisibility(View.VISIBLE);
                }else {


                }

            }
        }catch (Exception e)
        {

        }



        holder.ic_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataBaseHelper.AddtoFavorite(emailsaved , Name.get(position));
                Toast.makeText(mContext, mContext.getString(R.string.GoodsFragmnet_adddedtoFav), Toast.LENGTH_SHORT).show();
                holder.ic_favorite.setVisibility(View.INVISIBLE);
                holder.ic_favoritered.setVisibility(View.VISIBLE);

            }
        });


        holder.ic_favoritered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataBaseHelper.removeFromFavorite(emailsaved , Name.get(position) );
                Toast.makeText(mContext, mContext.getString(R.string.GoodsFragmnet_RemovedFromFav), Toast.LENGTH_SHORT).show();

                holder.ic_favorite.setVisibility(View.VISIBLE);
                holder.ic_favoritered.setVisibility(View.INVISIBLE);
            }
        });



        if(Langauge.equals("ar"))
        {
            holder.Name.setText(Namear.get(position));
            holder.Type.setText(Typear.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return Name.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView Name;
        TextView Price;
        TextView Type;
        RatingBar rating;

        ImageView ic_favorite,ic_favoritered;

        LinearLayout olayout;
        CardView cd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_ItemImage);
            Name = itemView.findViewById(R.id.tv_ItemName);
            Price = itemView.findViewById(R.id.tv_ItemPrice);
            Type = itemView.findViewById(R.id.tv_ItemType);

            rating = itemView.findViewById(R.id.rb_ItemRating);
            olayout = itemView.findViewById(R.id.R1layout);
            cd = itemView.findViewById(R.id.cardView);

            ic_favorite = itemView.findViewById(R.id.tv_fav);
            ic_favoritered = itemView.findViewById(R.id.tv_fav1);

        }
    }


}
