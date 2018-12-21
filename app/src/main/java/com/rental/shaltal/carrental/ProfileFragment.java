package com.rental.shaltal.carrental;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rental.shaltal.carrental.helpers.Md5;
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;
import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.helpers.RegisterValidator;
import com.rental.shaltal.carrental.RegisterActivity;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_INVALID_PASS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView profilePicture = (ImageView) getActivity().findViewById(R.id.iv_profilePerson);
        profilePicture.setImageResource(R.drawable.ic_default_profile);

        //get user information form data base
        User loggedInUser = CarSingleton.getInstance().getUser();

        String email = loggedInUser.getEmail();
        String firstName = loggedInUser.getFirstName();
        String lastName = loggedInUser.getLastName();
        String phoneNumber = loggedInUser.getPhoneNumber();

        EditText txtMail = (EditText)getActivity().findViewById(R.id.et_email_profile);
        EditText txtFirstName = (EditText)getActivity().findViewById(R.id.et_first_name_profile);
        EditText txtLastName = (EditText)getActivity().findViewById(R.id.et_last_name_profile);
        EditText txtPhoneNumber = (EditText)getActivity().findViewById(R.id.et_PhoneNumber_profile);


        txtMail.setText(email);
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtPhoneNumber.setText(phoneNumber);



        Button btnEditProfile = (Button) getActivity().findViewById(R.id.btnEditInformation);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout passwordLayout = (LinearLayout)getActivity().findViewById(R.id.layout_password);
                passwordLayout.setVisibility(View.VISIBLE);
                Button btnSaveNewInformation = (Button)getActivity().findViewById(R.id.btnSaveNewInformation);
                btnSaveNewInformation.setVisibility(View.VISIBLE);


                EditText txtFirstName = (EditText)getActivity().findViewById(R.id.et_first_name_profile);
                EditText txtLastName = (EditText)getActivity().findViewById(R.id.et_last_name_profile);
                EditText txtPhoneNumber = (EditText)getActivity().findViewById(R.id.et_PhoneNumber_profile);


                txtFirstName.setEnabled(true);
                txtLastName.setEnabled(true);
                txtPhoneNumber.setEnabled(true);


            }
        });

        Button btnSaveNewInformation = (Button)getActivity().findViewById(R.id.btnSaveNewInformation);
        btnSaveNewInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loggedInUser = CarSingleton.getInstance().getUser();
                String oldEmail = loggedInUser.getEmail();

                EditText txtFirstName = (EditText)getActivity().findViewById(R.id.et_first_name_profile);
                EditText txtLastName = (EditText)getActivity().findViewById(R.id.et_last_name_profile);
                EditText txtPhoneNumber = (EditText)getActivity().findViewById(R.id.et_PhoneNumber_profile);
                EditText txtnewPassowrd = (EditText)getActivity().findViewById(R.id.et_new_password_profile);
                EditText txtnewPassowrdValidation = (EditText)getActivity().findViewById(R.id.et_new_password_validation_profile);

                String newPassword = txtnewPassowrd.getText().toString();
                String newPasswordValidation = txtnewPassowrdValidation.getText().toString();

                boolean valid=true ;

                if (newPassword.length()==0){
                    valid=false;
                }
                if(newPasswordValidation.length()==0) {
                    valid = false;
                }

                if(!RegisterValidator.validatePassword(newPassword)){
                    valid=false;
                }
                if (!RegisterValidator.validatePassword(newPasswordValidation)){
                    valid=false;
                }

                if (valid==true){
                System.out.println("wpdsad");
                System.out.println(txtFirstName.getText().toString());
                User editedUser = new User();
                //editedUser.setEmail(oldEmail);
                editedUser.setFirstName(txtFirstName.getText().toString());
                editedUser.setLastName(txtLastName.getText().toString());
                editedUser.setPhoneNumber(txtPhoneNumber.getText().toString());
                editedUser.setPassword(Md5.md5(newPassword));
                editedUser.setEmail(oldEmail);

                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
                databaseHelper.updateUser(oldEmail,editedUser);

                LinearLayout passwordLayout = (LinearLayout)getActivity().findViewById(R.id.layout_password);
                passwordLayout.setVisibility(View.INVISIBLE);
                Button btnSaveNewInformation = (Button)getActivity().findViewById(R.id.btnSaveNewInformation);
                btnSaveNewInformation.setVisibility(View.INVISIBLE);

                CarSingleton.getInstance().setUser(editedUser);
                User loggedInUserAfter = CarSingleton.getInstance().getUser();
                String firstName = loggedInUserAfter.getFirstName();
                String lastName = loggedInUserAfter.getLastName();
                String phoneNumber = loggedInUserAfter.getPhoneNumber();

                txtFirstName.setText(firstName);
                txtLastName.setText(lastName);
                txtPhoneNumber.setText(phoneNumber);

                txtFirstName.setEnabled(false);
                txtLastName.setEnabled(false);
                txtPhoneNumber.setEnabled(false);
                txtnewPassowrd.setText("");
                txtnewPassowrdValidation.setText("");

                Context context = getActivity().getApplicationContext();
                CharSequence text = "Update Successful";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                }else {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Invalid Password";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });


    }
}
