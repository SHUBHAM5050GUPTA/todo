package com.example.shubhamgupta.todo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shubham Gupta on 03-07-2017.
 */

public class ToDoDatabase extends SQLiteOpenHelper {

    public static final String TODO_TABLE="Todo";
    public static final String TODO_NAME="Name";
    public static final String TODO_DESCRIPTION="Description";
    public static final String TODO_REMIND_DATE="Reminddate";
    public static final String TODO_REMIND_TIME="Remindtime";
    public static final String TODO_DUE_DATE="Duedate";
    public  static final String TODO_IMAGE="Image";
    public static final String TODO_ID="Id";



    public ToDoDatabase(Context context) {
        super(context, "Todo.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TODO_TABLE+" ("+TODO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TODO_NAME+" TEXT, "
                +TODO_DESCRIPTION+" TEXT, "+TODO_REMIND_DATE+" TEXT, "+TODO_REMIND_TIME+" TEXT, "+TODO_DUE_DATE+" TEXT ,"
                +TODO_IMAGE+" TEXT "+");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
