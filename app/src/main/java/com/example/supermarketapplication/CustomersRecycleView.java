package com.example.supermarketapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Rating;
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

public class CustomersRecycleView extends RecyclerView.Adapter<CustomersRecycleView.ViewHolder> {


    private ArrayList<String> Email = new ArrayList<>();
    private Context mContext;




    public CustomersRecycleView(  ArrayList<String> Email, Context mContext) {

        this.Email = Email;
        this.mContext = mContext;
    }


    @Override
    public CustomersRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customers_view_card, viewGroup, false);
        CustomersRecycleView.ViewHolder holder = new CustomersRecycleView.ViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomersRecycleView.ViewHolder holder, final int position) {

        SharedPreferences preferences = mContext.getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");

        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(mContext,"Project",null,1);


        Cursor AllUsers = dataBaseHelper.getAllUsers();

        holder.CusEmail.setText(Email.get(position));


        while (AllUsers.moveToNext())
        {
            if(AllUsers.getString(0).equals(Email.get(position)))
            {
                holder.Name.setText(AllUsers.getString(4) + " " + AllUsers.getString(5));

                if(AllUsers.getString(9).equals("Yes"))
                {
                    holder.ic_favorite.setVisibility(View.INVISIBLE);
                    holder.ic_favoritered.setVisibility(View.VISIBLE);
                }
            }
        }


        holder.ic_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBaseHelper.makeAdmin(Email.get(position));
                Toast.makeText(mContext, mContext.getString(R.string.CustomersRV_isAdmin), Toast.LENGTH_SHORT).show();
                holder.ic_favorite.setVisibility(View.INVISIBLE);
                holder.ic_favoritered.setVisibility(View.VISIBLE);

            }
        });

        holder.ic_favoritered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBaseHelper.removeAdmin(Email.get(position));
                Toast.makeText(mContext, mContext.getString(R.string.CustomersRV_isnotAdmin), Toast.LENGTH_SHORT).show();
                holder.ic_favorite.setVisibility(View.VISIBLE);
                holder.ic_favoritered.setVisibility(View.INVISIBLE);

            }
        });



        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext , UsersCart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Email" , Email.get(position));

                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation
                        ((Activity)mContext , holder.imageo , ViewCompat.getTransitionName(holder.imageo));
                mContext.startActivity(intent  , option.toBundle());

                //Toast.makeText(mContext, "Click", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return Email.size();
    }


    public void removeItem(int position) {
        Email.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        Email.add(position , item);
        notifyItemInserted(position);
    }

    public ArrayList<String> getData() {
        return Email;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView Name;
        TextView CusEmail;


        ImageView ic_favorite,ic_favoritered, delete,imageo;

        LinearLayout olayout;
        CardView cd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.tv_CustomerName);
            CusEmail = itemView.findViewById(R.id.tv_CustomerEmail);
            imageo = itemView.findViewById(R.id.iv_ItemImage);

            olayout = itemView.findViewById(R.id.R1layout);
            cd = itemView.findViewById(R.id.cardView);

            ic_favorite = itemView.findViewById(R.id.tv_fav);
            ic_favoritered = itemView.findViewById(R.id.tv_fav1);
            delete = itemView.findViewById(R.id.im_delete);


        }
    }


}
