package com.rental.shaltal.carrental;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import static com.rental.shaltal.carrental.constants.AppConstants.*;

import com.rental.shaltal.carrental.helpers.DatabaseHelper;
import com.rental.shaltal.carrental.helpers.Md5;
import com.rental.shaltal.carrental.helpers.SharedPrefHelper;
import com.rental.shaltal.carrental.models.User;
import com.rental.shaltal.carrental.singleton.CarSingleton;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        checkIfLoggedIn();
        checkIfSavedCred();

        Button loginBtn = (Button) findViewById(R.id.bt_Login);
        Button registerBtn = (Button) findViewById(R.id.bt_Register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterPage(v);
            }
        });
    }

    private void checkIfLoggedIn() {
        if (SharedPrefHelper.getLoggedIn(this)){
            gotToMainPage();
        }
    }

    private void checkIfSavedCred() {
        String email = SharedPrefHelper.getSavedEmail(this);
        String pass = SharedPrefHelper.getSavedPass(this);

        EditText et_LoginEmail = (EditText) findViewById(R.id.et_LoginEmail);
        EditText et_LoginPass = (EditText) findViewById(R.id.et_LoginPass);

        et_LoginEmail.setText(email);
        et_LoginPass.setText(pass);
    }

    private void login(View v) {
        Log.i(TAG, "login: Attempting to login");

        EditText et_email = (EditText) findViewById(R.id.et_LoginEmail);
        EditText et_pass = (EditText) findViewById(R.id.et_LoginPass);
        String email = et_email.getText().toString();
        String pass = et_pass.getText().toString();

        Log.i(TAG, "login: Cred:> email: " + email + ", pass: " + pass);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User user = databaseHelper.getUser(email);

        if (user == null) {
            Log.e(TAG, "login: User doesnt exist");
            displayFailedLoginMessage();
        } else {
//            ToDo: Change the pass to md5 enc
            String passHashed = Md5.md5(pass);
            if (passHashed.equals(user.getPassword())) {
                Log.i(TAG, "login: Login Successful");

                // Check if the Remember Me is checked
                CheckBox cb_RememberMe = (CheckBox) findViewById(R.id.cb_LoginRememberMe);
                if (cb_RememberMe.isChecked()){
                    SharedPrefHelper.addLoginUser(this, email , pass , user.isAdmin());
                }
                SharedPrefHelper.setLoggedIn(this);
                CarSingleton.getInstance().setUser(user);
                gotToMainPage();
            } else {
                Log.e(TAG, "login: Entered Wrong Cred");
                displayFailedLoginMessage();
            }
        }
    }

    private void displayFailedLoginMessage() {
        TextView message = (TextView) findViewById(R.id.tv_LoginMessage);
        message.setText(FAILED_LOGIN_MESSAGE);
        message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    private void goToRegisterPage(View v) {
        Log.i(TAG, "goToRegisterPage: Moving on to Register page");
        Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
        this.startActivity(intent);
    }

    private void gotToMainPage() {
        // Todo: Enter the activity for the next jump
        Intent intent = new Intent(LoginScreen.this , MainPage.class);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfLoggedIn();
    }
}
