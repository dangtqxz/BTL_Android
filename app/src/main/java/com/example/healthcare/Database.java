package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)";
        db.execSQL(sql1);

        String sql2 = "CREATE TABLE cart (username TEXT, product TEXT, price FLOAT, otype TEXT)";
        db.execSQL(sql2);

        String sql3 = "CREATE TABLE orderplace (username TEXT, fullname TEXT, address TEXT, contactno TEXT, pincode int, date TEXT, time TEXT, amount FLOAT, otype TEXT)";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void register(String username, String email, String password) {
        String sql = "INSERT INTO user (username, email, password) VALUES ('" + username + "', '" + email + "', '" + password + "')";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'";
        SQLiteDatabase db = getReadableDatabase();
        if (db.rawQuery(sql, null).getCount() > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public int checkCart(String username, String product) {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND product=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        db.close();
        return result;
    }

    public void removeCart(String username, String otype) {
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? AND otype=?", str);
        db.close();
    }

    public ArrayList getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = db.rawQuery("SELECT * FROM cart WHERE username=? AND otype=?", str);
        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price  = c.getString(2);
                arr.add(product + "$" + price);
            } while (c.moveToNext());
        }
        db.close();
        return arr;
    }

    public void addOrder(String username, String fullname, String address, String contactno, int pincode, String date, String time, float amount, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contactno", contactno);
        cv.put("pincode", pincode);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("amount", amount);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace", null, cv);
        db.close();
    }

    public ArrayList getOrderData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[1];
        str[0] = username;
        Cursor c = db.rawQuery("SELECT * FROM orderplace WHERE username=?", str);
        if (c.moveToFirst()) {
            do {
                String fullname = c.getString(1);
                String address = c.getString(2);
                String contactno = c.getString(3);
                String pincode = c.getString(4);
                String date = c.getString(5);
                String time = c.getString(6);
                String amount = c.getString(7);
                //Check 3:48:34
                arr.add(fullname + "$" + address + "$" + contactno + "$" + pincode + "$" + date + "$" + time + "$" + amount);
            } while (c.moveToNext());
        }
        db.close();
        return arr;
    }
}
