package com.rental.shaltal.carrental;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage_Fragment extends Fragment {


    public HomePage_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page_, container, false);
    }
    public void onActivityCreated( @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnContactUsByNumber = (Button) getActivity().findViewById(R.id.btnContactUsByNumber);
        btnContactUsByNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContactUsByNumber = new Intent();
                intentContactUsByNumber.setAction(Intent.ACTION_DIAL);
                intentContactUsByNumber.setData(Uri.parse("tel:0599000000"));
                startActivity(intentContactUsByNumber);
            }
        });

        Button btnContactUsByMail = (Button) getActivity().findViewById(R.id.btnContactUsByMail);
        btnContactUsByMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContactUsByMail = new Intent();
                intentContactUsByMail.setAction(Intent.ACTION_SENDTO);
                intentContactUsByMail.setType("message/rfc822");
                intentContactUsByMail.setData(Uri.parse("mailto:"));
                intentContactUsByMail.putExtra(Intent.EXTRA_EMAIL, "intentContactUsByMail");
                intentContactUsByMail.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                intentContactUsByMail.putExtra(Intent.EXTRA_TEXT, "Thank You For contacting us ?");
                startActivity(intentContactUsByMail);
            }
        });

    }
}
