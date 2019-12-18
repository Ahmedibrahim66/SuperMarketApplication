package com.example.supermarketapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    Button Phone,email,location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        email = v.findViewById(R.id.contact_us_Email);
        Phone = v.findViewById(R.id.contact_us_Phone);
        location = v.findViewById(R.id.contact_us_Location);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject = "Android Project";
                String message = "This is amazing";
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","supermarket@super.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }
        });


        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("tel:0599000000");
                Intent it1 = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(it1);

            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceLatitude ="31.960130";
                String sourceLongitude ="35.180923";

                Uri gmmIntentUri = Uri.parse("geo:31.960130,35.180923");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        return v;

    }

}
