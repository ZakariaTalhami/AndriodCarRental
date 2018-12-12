package com.rental.shaltal.carrental.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.rental.shaltal.carrental.LoginScreen;
import static com.rental.shaltal.carrental.constants.RestConstants.*;

import com.rental.shaltal.carrental.R;

public class ConnectionAsyncTask extends AsyncTask<String,String,Boolean>{
    private static String TAG = "ConnectionAsyncTask";

    private Activity activity;
    public ConnectionAsyncTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        Log.i(TAG, "onPostExecute: "+bool);
        super.onPostExecute(bool);
        if (bool){
            moveToNextPage();
        }
        else{
            moveToNextPage();
//            stayAndDisplayError();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return RestConnection.testConnection(strings[0]);
    }


    private void moveToNextPage() {
        Intent intent = new Intent(this.activity , LoginScreen.class);
        this.activity.startActivity(intent);
    }

    private void stayAndDisplayError(){
        TextView tv_ErrorMessage = (TextView) this.activity.findViewById(R.id.tv_InitScreenMessageBox);
        tv_ErrorMessage.setText(REST_FAILED_CONNETION_MESSAGE);
        tv_ErrorMessage.startAnimation(AnimationUtils.loadAnimation(this.activity , R.anim.shake));
        tv_ErrorMessage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.disconnect , 0 , 0);

        // Create an anim to shake the textView
    }
}
