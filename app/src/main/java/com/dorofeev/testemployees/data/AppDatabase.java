package com.dorofeev.testemployees.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dorofeev.testemployees.pojo.Employee;

@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "employees_db";
    private static AppDatabase instance;
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return instance;
    }

    public abstract EmployeeDAO getEmployeeDAO();
}
