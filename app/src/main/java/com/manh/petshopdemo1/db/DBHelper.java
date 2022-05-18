package com.manh.petshopdemo1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.manh.petshopdemo1.DetailProduct;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE IF NOT EXISTS search(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "search_history NTEXT" +
                ")";
        sqLiteDatabase.execSQL(createTable);
        String createTableCart="CREATE TABLE IF NOT EXISTS cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name NTEXT," +
                "image TEXT,"+
                "quantity INT," +
                "size NTEXT," +
                "price INT" +
                ")";
        sqLiteDatabase.execSQL(createTableCart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i < i1) {
            String drop = "DROP TABLE search";
            sqLiteDatabase.execSQL(drop);
            onCreate(sqLiteDatabase);
        }

    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }
}
