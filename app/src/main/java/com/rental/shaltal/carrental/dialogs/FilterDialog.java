package com.rental.shaltal.carrental.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rental.shaltal.carrental.R;


public class FilterDialog extends DialogFragment {


    public interface FilterDialogInterfae{
        public void filterData(String price , String model , String make );
    }

    private EditText et_make;
    private EditText et_model;
    private EditText et_price;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.filter_dialog, null))
                // Add action buttons
                .setPositiveButton( "Filter" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        et_make = (EditText) getDialog().findViewById(R.id.et_dialogMake);
                        et_model = (EditText) getDialog().findViewById(R.id.et_dialogModel);
                        et_price = (EditText)  getDialog().findViewById(R.id.et_dialogPrice);
                        String make = et_make.getText().toString();
                        String model = et_model.getText().toString();
                        String price = et_price.getText().toString();
                        Log.i("", "FilterButton: "+price+" "+model+" "+make+" ");
                        FilterDialogInterfae dialogInterfae =(FilterDialogInterfae) getTargetFragment();
                        dialogInterfae.filterData(price , model , make);
                        FilterDialog.this.getDialog().cancel();

                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FilterDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_dialog, container, false);

        return view;
    }
}
