package com.example.supermarketapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersFragment extends Fragment {

    RecyclerView recyclerView;
    CustomersRecycleView mAdapter;
    private ArrayList<String> Name = new ArrayList<>();
    private ArrayList<String> Email = new ArrayList<>();


    CoordinatorLayout coordinatorLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_customers, container, false);


        coordinatorLayout = v.findViewById(R.id.coordinatorLayout);


        SharedPreferences preferences = v.getContext().getSharedPreferences("SignedIn" , Activity.MODE_PRIVATE);
        final String emailsaved = preferences.getString("Email" , "");

        final DataBaseHelper dataBaseHelper =new
                DataBaseHelper(getContext(),"Project",null,1);

        Cursor AllUsers = dataBaseHelper.getAllUsers();

        while (AllUsers.moveToNext())
        {

            if(!AllUsers.getString(0).equals(emailsaved))
            {
                Name.add(AllUsers.getString(4) + " " + AllUsers.getString(5));
                Email.add(AllUsers.getString(0));
            }

        }

        CreateRecycleView(v);
        enableSwipeToDeleteAndUndo();



        return v;

    }



    private void CreateRecycleView(View v){

        recyclerView =  v.findViewById(R.id.CustomersRecycleView);
        mAdapter = new CustomersRecycleView(Email, getContext());
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);

    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final String item = mAdapter.getData().get(position);


                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.Customers_wasdeleted), Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.Customers_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });


                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {


                            final DataBaseHelper dataBaseHelper = new
                                    DataBaseHelper(mContext, "Project", null, 1);
                            dataBaseHelper.removeUser(item);
                            dataBaseHelper.removeFavoriteUser(item);
                            dataBaseHelper.removeCartUser(item);
                        }
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();



            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);

    }


}
