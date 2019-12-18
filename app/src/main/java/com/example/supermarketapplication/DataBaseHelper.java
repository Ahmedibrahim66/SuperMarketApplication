package com.example.supermarketapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY," +
                "PASSWORD TEXT ,CITY TEXT , PHONE TEXT, FIRSTNAME TEXT,LASTNAME TEXT," +
                "GENDER TEXT, CART TEXT,FAVORITE TEXT , IsADMIN TEXT)");

        db.execSQL("CREATE TABLE FAVORITE (EMAIL TEXT NOT NULL , TITLE TEXT NOT NULL, PRIMARY KEY(EMAIL , TITLE))");
        db.execSQL("CREATE TABLE CART (EMAIL TEXT NOT NULL, TITLE TEXT NOT NULL,  QUANTITY INTEGER , PRIMARY KEY(EMAIL , TITLE))");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void AddUser(Users users) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", users.getEmail());
        contentValues.put("PASSWORD", users.getPassword());
        contentValues.put("CITY", users.getCity());
        contentValues.put("GENDER", users.getGender());
        contentValues.put("PHONE", users.getPhone());
        contentValues.put("FIRSTNAME", users.getFirstName());
        contentValues.put("LASTNAME", users.getLastName());
        contentValues.put("CART", users.getCart().toString());
        contentValues.put("FAVORITE", users.getFavorite().toString());
        contentValues.put("IsADMIN", users.getIsAdmin());
        sqLiteDatabase.insert("USERS", null, contentValues);
    }

    public void updateUser(Users users) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", users.getEmail());
        contentValues.put("PASSWORD", users.getPassword());
        contentValues.put("CITY", users.getCity());
        contentValues.put("GENDER", users.getGender());
        contentValues.put("PHONE", users.getPhone());
        contentValues.put("FIRSTNAME", users.getFirstName());
        contentValues.put("LASTNAME", users.getLastName());
        contentValues.put("CART", users.getCart().toString());
        contentValues.put("FAVORITE", users.getFavorite().toString());
        contentValues.put("IsADMIN", users.getIsAdmin());
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {users.getEmail()}  );


    }

    public void AddtoFavorite(String Email , String Title) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", Email);
        contentValues.put("TITLE", Title);
        sqLiteDatabase.insert("FAVORITE", null, contentValues);
    }

    public void removeFromFavorite(String Email , String Title) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("FAVORITE", "EMAIL =? AND TITLE=?" , new String[] {Email , Title});
    }



    public Cursor getAllFavorite(String Email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FAVORITE WHERE EMAIL = '" + Email+"'", null);
    }



    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS", null);
    }

    public Cursor getUsersInformation(String Email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL ='"+ Email+"'", null);
    }


    public Cursor getAllUsersEmails() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT EMAIL FROM USERS", null);
    }


    public Cursor getPasswordForEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT PASSWORD FROM USERS WHERE EMAIL = '" + email+"'", null);
    }


    public Cursor getFirstNameForEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT FIRSTNAME FROM USERS WHERE EMAIL = '" + email+"'", null);
    }

    public Cursor getLastNameForEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT LASTNAME FROM USERS WHERE EMAIL = '" + email+"'", null);
    }

    public void makeAdmin(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IsADMIN", "Yes");
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {Email}  );
    }

    public void removeAdmin(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IsADMIN", "No");
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {Email}  );
    }


    public void removeUser(String Email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("USERS", "EMAIL =?" , new String[] {Email});
    }


    public void AddtoCart(String Email , String Title , int Qun) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", Email);
        contentValues.put("TITLE", Title);
        contentValues.put("QUANTITY", Qun);
        sqLiteDatabase.insert("CART", null, contentValues);
    }

    public void updateCart(String Email , String Title , int Qun) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", Email);
        contentValues.put("TITLE", Title);
        contentValues.put("QUANTITY", Qun);
        db.update("CART", contentValues,"EMAIL =?  AND TITLE=?" , new String[] {Email , Title}  );


    }


    public Cursor getUserCart(String Email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CART WHERE EMAIL ='"+ Email+"'", null);
    }


    public Boolean checkifItemCartExists(String Email , String Title) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor Yes =  sqLiteDatabase.rawQuery("SELECT * FROM CART WHERE EMAIL ='"+ Email+"' AND TITLE='" + Title+"'", null);
        return Yes.moveToNext();
    }



    public void removeFavoriteUser(String Email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("FAVORITE", "EMAIL =?" , new String[] {Email});
    }


    public void removeCartUser(String Email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("CART", "EMAIL =?" , new String[] {Email});
    }


}
