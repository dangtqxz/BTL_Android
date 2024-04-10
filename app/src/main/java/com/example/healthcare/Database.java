package com.example.healthcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)";
        db.execSQL(sql1);
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
}
