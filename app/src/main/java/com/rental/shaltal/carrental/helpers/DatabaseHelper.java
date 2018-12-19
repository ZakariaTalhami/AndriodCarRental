package com.rental.shaltal.carrental.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.rental.shaltal.carrental.constants.DatabaseConstants.*;
import com.rental.shaltal.carrental.constants.GenderEnum;
import com.rental.shaltal.carrental.models.Car;
import com.rental.shaltal.carrental.models.FavCar;
import com.rental.shaltal.carrental.models.ReservedCar;
import com.rental.shaltal.carrental.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private String TAG = getClass().getSimpleName();
    public DatabaseHelper(Context context) {
        super(context, "db2.2", null, 1);
        Log.i(TAG, "DatabaseHelper: Creating DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: ");

//        Creating User database
        Log.i(TAG, "onCreate: Creating User");
        db.execSQL("CREATE TABLE "+TBL_USER+"("+USER_EMAIL+" TEXT PRIMARY KEY," +
                USER_FIRSTNAME+" TEXT," +
                USER_LASTNAME+" TEXT," +
                USER_GENDER+" TEXT," +
                USER_PASSWORD+" TEXT," +
                USER_COUNTRY+" TEXT," +
                USER_CITY+" TEXT," +
                USER_PHONENUMBER+" TEXT," +
                USER_ADMIN+" INTEGER," +
                USER_IMAGE+" TEXT)");
        Log.i(TAG, "onCreate: Creating Car");
        db.execSQL("CREATE TABLE "+TBL_CAR+"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAR_YEAR+" TEXT," +
                CAR_MAKE+" TEXT," +
                CAR_MODEL+" TEXT)");

        Log.i(TAG, "onCreate: Creating reserved");
        db.execSQL("CREATE TABLE "+TBL_RESERVED+"(" +
                RESERVED_USER+" TEXT," +
                RESERVED_CAR+" INTEGER," +
                RESERVED_TIME+" INTEGER," +
                "FOREIGN KEY("+RESERVED_USER+") REFERENCES user("+USER_EMAIL+")," +
                "FOREIGN KEY("+RESERVED_CAR+") REFERENCES car(id)," +
                "PRIMARY KEY("+RESERVED_USER+" , "+RESERVED_CAR+"))");

        Log.i(TAG, "onCreate: Creating Favorite");
        db.execSQL("CREATE TABLE "+TBL_FAVORITE+"(" +
                FAVORITE_USER+" TEXT," +
                FAVORITE_CAR+" INTEGER," +
                "FOREIGN KEY("+FAVORITE_USER+") REFERENCES user("+USER_EMAIL+")," +
                "FOREIGN KEY("+FAVORITE_CAR+") REFERENCES car(id)," +
                "PRIMARY KEY("+FAVORITE_USER+" , "+FAVORITE_CAR+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUser(User user){
        boolean success = false;
        try{
//            Set Content Values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_EMAIL , user.getEmail());
            contentValues.put(USER_FIRSTNAME , user.getFirstName());
            contentValues.put(USER_LASTNAME , user.getLastName());
            contentValues.put(USER_GENDER , user.getGender().name());
            contentValues.put(USER_PASSWORD , user.getPassword());
            contentValues.put(USER_COUNTRY , user.getCountry());
            contentValues.put(USER_CITY , user.getCity());
            contentValues.put(USER_PHONENUMBER , user.getPhoneNumber());
            contentValues.put(USER_ADMIN , (user.isAdmin())? 1:0);
            contentValues.put(USER_IMAGE , user.getImage());

//            Insert User to the database
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insertOrThrow(TBL_USER,null,contentValues);

            success = true;
        }catch (Exception e){

        }
        return  success;
    }

    public int deleteUser(User user){
        int affectedRows = 0;
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            affectedRows = sqLiteDatabase.delete(TBL_USER ,
                    USER_EMAIL+"=?" ,
                    new String[]{user.getEmail()});


        }
        catch (Exception e){
            Log.e(TAG, "deleteUser: Failed to detele User\n",e );
        }
        return affectedRows;
    }

//    Check if taken?
    public int updateUser(String oldEmail,User user){
        int affectedRows = 0;
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_FIRSTNAME , user.getFirstName());
            contentValues.put(USER_LASTNAME , user.getLastName());
            contentValues.put(USER_EMAIL , user.getEmail());
            contentValues.put(USER_PHONENUMBER , user.getPhoneNumber());

            SQLiteDatabase sqLiteDatabase=  getWritableDatabase();
            affectedRows = sqLiteDatabase.update(TBL_USER,
                    contentValues,
                    USER_EMAIL+"=?",
                    new String[]{oldEmail});
        }
        catch (Exception e){
            Log.e(TAG, "updateUser: Failed to update User\n", e);
        }
        return affectedRows;
    }

    public User getUser(String email){
        User user = null;
        Cursor cursor= null;
        try {
//              Query the Database
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_USER+" WHERE email =?", new String[]{email});
            if (cursor.getCount() > 0){
//                Read the User from the database and create a User instance
                cursor.moveToFirst();
                user = new User(
                        cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)),
                        cursor.getString(cursor.getColumnIndex(USER_LASTNAME)),
                        GenderEnum.valueOf(cursor.getString(cursor.getColumnIndex(USER_GENDER))),
                        cursor.getString(cursor.getColumnIndex(USER_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(USER_COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(USER_CITY)),
                        cursor.getString(cursor.getColumnIndex(USER_PHONENUMBER)),
                        cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                        (cursor.getInt(cursor.getColumnIndex(USER_ADMIN)) == 1),
                        cursor.getString(cursor.getColumnIndex(USER_IMAGE))
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

    public int insertCar(Car car ){
        int success = 0;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CAR_MAKE, car.getMake());
            contentValues.put(CAR_MODEL , car.getModel());
            contentValues.put(CAR_YEAR, car.getYear());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            long res = sqLiteDatabase.insertOrThrow(TBL_CAR , null , contentValues);

            success = (int) res;
        }
        catch (Exception e){
            Log.e(TAG, "insertCar: Failed to insert Car:> ", e  );
        }

        return success;
    }


    public Car getCar(int id){
        Car car = null;
        Cursor cursor= null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_CAR+" WHERE id=?", new String[]{id+""});
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                car = new Car(
                    cursor.getString(cursor.getColumnIndex(CAR_YEAR)),
                    cursor.getString(cursor.getColumnIndex(CAR_MAKE)),
                    cursor.getString(cursor.getColumnIndex(CAR_MODEL)),
                    "",0, false,false
                );
                car.setId(cursor.getColumnIndex("id"));
            }
        }
        catch (Exception e){
            Log.e(TAG, "getCar: Failed to read car from the database ", e);
        }
        finally {
            cursor.close();
        }

        return  car;
    }

    public int getCarID(Car car){
        int carID = 0;
        Cursor cursor= null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_CAR+" " +
                    "WHERE "+CAR_MAKE+"=? and "+CAR_MODEL+"=? and "+CAR_YEAR+"=? ",
                    new String[]{car.getMake(),car.getModel(),car.getYear()});
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                carID = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
        catch (Exception e){
            Log.e(TAG, "getCar: Failed to read car from the database ", e);

        }
        finally {
            cursor.close();
        }

        return  carID;
    }
    /*
           Assuming that the car is not reserved
     */
    public boolean insertReservedCar(User user , Car car ){
        boolean success = false;
        try{
            // Check if the car is in the database
            // if not insert
            int id = 0;
            if ((id = getCarID(car)) == 0){
                id = insertCar(car);
            }

            Log.e(TAG, "insertReservedCar: id = "+id );
            if(id != 0){
                ContentValues contentValues = new ContentValues();
                contentValues.put(RESERVED_USER , user.getEmail());
                contentValues.put(RESERVED_CAR, id);
                contentValues.put(RESERVED_TIME, new Date().getTime());
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                sqLiteDatabase.insertOrThrow(TBL_RESERVED , null , contentValues);
                success = true;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "insertReservedCar: Failed to insert reservation to the database\n", e );
        }
        return  success;
    }

    public List<ReservedCar> getReservedCars(User user){
        List<ReservedCar> carList = null;
        Cursor cursor = null;
        try{
            carList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_RESERVED+" " +
                    "INNER JOIN "+TBL_CAR+" ON "+TBL_CAR+".id = "+TBL_RESERVED+"."+RESERVED_CAR+" " +
                    "WHERE "+RESERVED_USER+"=? " , new String[]{user.getEmail()});
            while(cursor.moveToNext()){
                ReservedCar car = new ReservedCar();
                car.setModel(cursor.getString(cursor.getColumnIndex(CAR_MODEL)));
                car.setMake(cursor.getString(cursor.getColumnIndex(CAR_MAKE)));
                car.setYear(cursor.getString(cursor.getColumnIndex(CAR_YEAR)));
                long milliseconds = cursor.getLong(cursor.getColumnIndex(RESERVED_TIME));
                Date reservedDate = new Date(milliseconds);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                car.setReservedDate(dateFormat.format(reservedDate));
                car.setUser(user);
                carList.add(car);
            }
        }
        catch (Exception e){
            Log.e(TAG, "getResrvedCars: Failed to read reserved cars from the database", e);
        }
        finally {
            cursor.close();
        }

        return carList;
    }

    public boolean isReserved(Car car, User user){
        boolean reserved = false;
        try{
            int id = getCarID(car);
            if (id !=0){
                Cursor cursor;
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_RESERVED+" " +
                        "WHERE "+RESERVED_CAR+"=? and "+RESERVED_USER+"=?" , new String[]{id+"", user.getEmail()});
                if (cursor.getCount() > 0){
                    reserved = true;
                }
            }
        }
        catch (Exception e){

        }
        return reserved;
    }


    /*
           Assuming that the car is not Favored
     */
    public boolean insertFavoriteCar(User user , Car car ){
        boolean success = false;
        try{
            // Check if the car is in the database
            // if not insert
            int id = 0;
            if ((id = getCarID(car)) == 0){
                id = insertCar(car);
            }

            Log.e(TAG, "insertFavoriteCar: id = "+id );
            if(id != 0){
                ContentValues contentValues = new ContentValues();
                contentValues.put(FAVORITE_USER , user.getEmail());
                contentValues.put(FAVORITE_CAR, id);
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                sqLiteDatabase.insertOrThrow(TBL_FAVORITE, null , contentValues);
                success = true;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "insertFavoriteCar: Failed to insert reservation to the database\n", e );
        }
        return  success;
    }

    public List<FavCar> getFavoriteCars(User user){
        List<FavCar> carList = null;
        Cursor cursor = null;
        try{
            carList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_FAVORITE+" " +
                    "INNER JOIN "+TBL_CAR+" ON "+TBL_CAR+".id = "+TBL_FAVORITE+"."+FAVORITE_CAR+" " +
                    "WHERE "+FAVORITE_USER+"=? " , new String[]{user.getEmail()});
            while(cursor.moveToNext()){
                FavCar car = new FavCar();
                car.setModel(cursor.getString(cursor.getColumnIndex(CAR_MODEL)));
                car.setMake(cursor.getString(cursor.getColumnIndex(CAR_MAKE)));
                car.setYear(cursor.getString(cursor.getColumnIndex(CAR_YEAR)));
                car.setUser(user);

                carList.add(car);
            }
        }
        catch (Exception e){
            Log.e(TAG, "getFavoriteCars: Failed to read reserved cars from the database", e);
        }
        finally {
            cursor.close();
        }

        return carList;
    }

    public boolean deleteFavoriteCar(User user , Car car){
        boolean success = false;
        try{
            int id = getCarID(car);
            if (id!=0){
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                int deletedRows = sqLiteDatabase.delete(TBL_FAVORITE,
                        " "+FAVORITE_USER+"=? and "+FAVORITE_CAR+"=? ",
                        new String[]{user.getEmail() , id+""});
                if (deletedRows> 0 )
                    success = true;
            }
        }
        catch (Exception e){
            Log.e(TAG, "deleteFavoriteCar: Failed to delete favorite:> ",e );
        }

        return success;
    }

    public boolean isFavored(Car car, User user){
        boolean favored = false;
        try{
            int id = getCarID(car);
            if (id !=0){
                Cursor cursor;
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TBL_FAVORITE+" " +
                        "WHERE "+FAVORITE_CAR+"=? and "+FAVORITE_USER+"=?" , new String[]{id+"" , user.getEmail()});
                if (cursor.getCount() > 0){
                    favored = true;
                }
            }
        }
        catch (Exception e){

        }
        return favored;
    }
}
