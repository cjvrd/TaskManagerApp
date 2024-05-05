package com.example.taskmanagerapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaskDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "task";

    private static final String DB_NAME = "taskDatabase.db";

    public TaskDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE task (id TEXT PRIMARY KEY, title TEXT,  description TEXT, date DATE)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle any database schema changes in future versions of your app
    }

    public Boolean addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", task.getId());
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        values.put("date", task.getDate());


        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowId > -1) {
            System.out.println("Data Added. Row: " + rowId);
            return true;
        } else {
            System.out.println("Database insert failed.");
            return false;
        }
    }

    public Task getTask(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { "id", "title", "description", "date"},
                "id=?", new String[] { id }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Task task = new Task(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return task;
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        List<Task> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            result.add(new Task(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        db.close();
        return result;
    }

    public Boolean deleteTask(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id" + " = ?";
        String[] whereArgs = { id };
        int numRowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        if (numRowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }
}