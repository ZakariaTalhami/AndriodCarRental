package com.rental.shaltal.carrental.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rental.shaltal.carrental.constants.GenderEnum;
import com.rental.shaltal.carrental.models.User;


public class DatabaseHelper extends SQLiteOpenHelper {

    private String TAG = getClass().getSimpleName();
    public DatabaseHelper(Context context) {
        super(context, "db1.0", null, 1);
        Log.i(TAG, "DatabaseHelper: Creating DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: ");

//        Creating User database
        db.execSQL("CREATE TABLE user(email TEXT PRIMARY KEY," +
                "firstName TEXT," +
                "lastName TEXT," +
                "gender TEXT," +
                "password TEXT," +
                "country TEXT," +
                "city TEXT," +
                "phonenumber TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUser(User user){
        boolean success = false;
        try{
//            Set Content Values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put("email" , user.getEmail());
            contentValues.put("firstName" , user.getFirstName());
            contentValues.put("lastName" , user.getLastName());
            contentValues.put("gender" , user.getGender().name());
            contentValues.put("password" , user.getPassword());
            contentValues.put("country" , user.getCountry());
            contentValues.put("city" , user.getCity());
            contentValues.put("phonenumber" , user.getPhoneNumber());

//            Insert User to the database
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insertOrThrow("user",null,contentValues);

            success = true;
        }catch (Exception e){

        }
        return  success;
    }


    public User getUser(String email){
        User user = null;
        Cursor cursor= null;
        try {
//              Query the Database
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE email =?", new String[]{email});
            if (cursor.getCount() > 0){
//                Read the User from the database and create a User instance
                cursor.moveToFirst();
                user = new User(
                        cursor.getString(cursor.getColumnIndex("firstName")),
                        cursor.getString(cursor.getColumnIndex("lastName")),
                        GenderEnum.valueOf(cursor.getString(cursor.getColumnIndex("gender"))),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("country")),
                        cursor.getString(cursor.getColumnIndex("city")),
                        cursor.getString(cursor.getColumnIndex("phonenumber")),
                        cursor.getString(cursor.getColumnIndex("email"))
                );
            }

        }
        catch (Exception e){
            Log.e("getUser", "getUser: "+e.getMessage());
            throw e;
        }
        finally{
            cursor.close();
        }
        return user;
    }
}
