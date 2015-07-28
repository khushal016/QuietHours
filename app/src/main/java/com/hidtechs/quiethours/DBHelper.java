package com.hidtechs.quiethours;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by napster on 23/7/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key autoincrement, name text,phone text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public List<Information> getAllContacts() {
        List<Information> todos = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBHelper.CONTACTS_TABLE_NAME;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Information td = new Information();
                td.setName((c.getString(c.getColumnIndex("name"))));
                td.setNumber(c.getString(c.getColumnIndex("phone")));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }
    public void deleteContact(String contact_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, CONTACTS_COLUMN_NAME + " = ?",
                new String[] {contact_name});
    }
}
