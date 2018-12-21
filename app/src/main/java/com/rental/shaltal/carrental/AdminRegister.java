package com.rental.shaltal.carrental;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rental.shaltal.carrental.constants.GenderEnum;
import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.helpers.Md5;
import com.rental.shaltal.carrental.helpers.RegisterValidator;
import com.rental.shaltal.carrental.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_EMPTY_CITY;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_EMPTY_COUNTRY;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_EMPTY_GENDER;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_INVALID_EMAIL;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_INVALID_NAME;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_INVALID_PASS;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_MATCH_PASS;
import static com.rental.shaltal.carrental.constants.AppConstants.REG_ERR_PHONE_NUMBER;
import static com.rental.shaltal.carrental.constants.DatabaseConstants.USER_DEFAULT_IMAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminRegister extends Fragment {

    private static final String TAG = "AdminRegister";
    private static View fragView;
    public AdminRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_admin_register, container, false);
        Button btnRegister = (Button) view.findViewById(R.id.bt_RegisterAccept);
        final Button btnBack = (Button) view.findViewById(R.id.bt_RegisterBack);
        final Spinner countrySpinner = (Spinner) view.findViewById(R.id.sp_RegisterCountry);
        final Spinner citySpinner = (Spinner) view.findViewById(R.id.sp_RegisterCity);
        final TextView tv_phonePrefex = (TextView) view.findViewById(R.id.tv_RegisterPhonePrefex);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = countrySpinner.getItemAtPosition(position).toString();
                Log.i(TAG, "onItemSelected: SELECTED:> "+selected+", id :> "+id);
                List<String> phonePrefex = Arrays.asList(getResources().getStringArray(R.array.countryPhoneMap));
                List<String> list = new ArrayList<>();
                String prefex = "";
                switch ((int)id){
                    case 0:
                        Log.i(TAG, "onItemSelected: id:> "+id+" loading  palestine cities");
                        list = Arrays.asList(getResources().getStringArray(R.array.palestineCities));

                        break;
                    case 1:
                        Log.i(TAG, "onItemSelected: id:> "+id+" loading  Jordan cities");
                        list = Arrays.asList(getResources().getStringArray(R.array.JordanCities));
                        break;
                    case 2:
                        Log.i(TAG, "onItemSelected: id:> "+id+" loading  Egypt cities");
                        list = Arrays.asList(getResources().getStringArray(R.array.EgyptCities));
                        break;
                    case 3:
                        Log.i(TAG, "onItemSelected: id:> "+id+" loading  Syria cities");
                        list = Arrays.asList(getResources().getStringArray(R.array.SyriaCities));
                        break;
                }
                prefex = phonePrefex.get((int)id);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item , list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(arrayAdapter);
                citySpinner.setGravity(Gravity.CENTER);

                tv_phonePrefex.setText(prefex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                goToLoginPage();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToRegister(view);
            }
        });
        return view;
    }

    private void attemptToRegister(View view) {
        boolean valid = true;
        // Get Selection from the spinners
        Spinner sp_country = (Spinner) view.findViewById(R.id.sp_RegisterCity);
        Spinner sp_city = (Spinner) view.findViewById(R.id.sp_RegisterCity);

        //todo: FIX This
        String country = sp_country.getSelectedItem().toString();
        String city = sp_city.getSelectedItem().toString();

        // Validate Entries
        if (country.isEmpty()){
            Log.e(TAG, "attemptToRegister: Country field is empty" );
            setInvalidStyleOnView(sp_country , REG_ERR_EMPTY_COUNTRY);
            valid = false;
        }
        if (city.isEmpty()){
            Log.e(TAG, "attemptToRegister: City field is empty" );
            setInvalidStyleOnView(sp_city, REG_ERR_EMPTY_CITY);
            valid = false;
        }


        // Validate First Name
        EditText et_firstName = (EditText)view.findViewById(R.id.et_RegisterFirstName);
        String firstName = et_firstName.getText().toString();
        if (!RegisterValidator.validateName(firstName)){
            Log.e(TAG, "attemptToRegister: firstname field is invalid : "+firstName );
            setInvalidStyleOnView(et_firstName , REG_ERR_INVALID_NAME);
            valid = false;
        }

        // Validate Last name
        EditText et_lastname = (EditText)view.findViewById(R.id.et_RegisterLastName);
        String lastName = et_lastname.getText().toString();
        if(!RegisterValidator.validateName(lastName)){
            Log.e(TAG, "attemptToRegister: lastname field is invalid : "+lastName );
            setInvalidStyleOnView(et_lastname , REG_ERR_INVALID_NAME);
            valid = false;
        }

        // Validate Last name
        Spinner sp_gender = (Spinner) view.findViewById(R.id.sp_RegisterGender);
        String gender = sp_gender.getSelectedItem().toString();
        if(!RegisterValidator.validateGender(gender)){
            Log.e(TAG, "attemptToRegister: Gender field is invalid : "+gender );
            setInvalidStyleOnView(sp_gender , REG_ERR_EMPTY_GENDER);
            valid = false;
        }

        // Validate email
        EditText et_email = (EditText)view.findViewById(R.id.et_RegisterEmail);
        String email = et_email.getText().toString();
        if(!RegisterValidator.validateEmail(email)){
            Log.e(TAG, "attemptToRegister: email field is invalid : "+email );
            setInvalidStyleOnView(et_email , REG_ERR_INVALID_EMAIL);
            valid = false;
        }

        //Validate Password
        EditText et_password = (EditText)view.findViewById(R.id.et_RegisterPassword);
        String password = et_password.getText().toString();
        if(!RegisterValidator.validatePassword(password)){
            Log.e(TAG, "attemptToRegister: password field is invalid : "+password );
            setInvalidStyleOnView(et_password , REG_ERR_INVALID_PASS);
            valid = false;
        }

        //Validate Confirm Password
        EditText et_confirmPassword = (EditText) view.findViewById(R.id.et_RegisterConfirmRegister);
        String confirmPassword = et_confirmPassword.getText().toString();
        if(!confirmPassword.equals(password)){
            Log.e(TAG, "attemptToRegister: Confirm password field doesnt match the password  : "+confirmPassword + " : "+password );
            setInvalidStyleOnView(et_confirmPassword ,REG_ERR_MATCH_PASS);
            valid = false;
        }

        //Validate Phone Number
        EditText et_phone = (EditText) view.findViewById(R.id.et_RegisterPhone);
        TextView tv_prefex = (TextView) view.findViewById(R.id.tv_RegisterPhonePrefex);
        String phone = tv_prefex.getText().toString()+et_phone.getText().toString();
        if(RegisterValidator.validatePhone(phone , country)){
            Log.e(TAG, "attemptToRegister: phone field is invalid : "+phone );
            setInvalidStyleOnView(et_phone , REG_ERR_PHONE_NUMBER);
            valid = false;
        }


        // Check Valid flag
        if(valid){
            Log.i(TAG, "attemptToRegister: All register fields are valid, saving user to database.");
            // Persist user to the database
            User user = new User(
                    firstName,
                    lastName,
                    GenderEnum.valueOf(gender.toUpperCase()),
                    Md5.md5(password),
                    country,
                    city,
                    phone,
                    email,
                    true,
                    USER_DEFAULT_IMAGE
            );
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            databaseHelper.insertUser(user);

            // Return to the login page
            Toast.makeText(getActivity().getApplicationContext(), "Admin added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Register Failed, Invalid fields", Toast.LENGTH_LONG).show();
        }
    }

    private void setInvalidStyleOnView(View view , String message) {
        if (view instanceof EditText){
//            ((EditText)view).setTextColor(getColor(R.color.colorErrorMessage));
//            ((EditText)view).setTextColor(0xcc332e2e);
            ((EditText)view).setError(message);
        }
    }

}
