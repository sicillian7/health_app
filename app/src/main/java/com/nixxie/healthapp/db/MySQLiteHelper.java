package com.nixxie.healthapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DOCTOR = "doctor";
    public static final String TABLE_PATIENT = "patient";

    public static final String DATABASE_NAME = "swarmer_data.db";

    private static final int DATABASE_VERSION = 5;

    private static final String DOCTOR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR + " (\n" +
            "id INTEGER PRIMARY KEY,\n" +
            "name VARCHAR(50) NOT NULL,\n"+
            "doctorId VARCHAR(50) UNIQUE,\n"+
            "lastName VARCHAR(50) null);";


    private static final String PATIENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PATIENT + " (\n" +
            "id INTEGER PRIMARY KEY,\n" +
            "name VARCHAR(50) NOT NULL,\n"+
            "lastName VARCHAR(50) NOT NULL,\n"+
            "syndromeRate DOUBLE DEFAULT NULL,\n" +
            "doctorId VARCHAR(50),\n"+
            "FOREIGN KEY (doctorId) REFERENCES doctor(doctorId) ON DELETE CASCADE);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DOCTOR_TABLE);
        database.execSQL(PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            dropDatabase(db);
            onCreate(db);
        }
    }

    public void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
    }
}
