package com.nixxie.healthapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public class DatabaseAdapter {

    private static final String TAG  = DatabaseAdapter.class.getName();

    private Context mContext;
    private static SQLiteDatabase myDB = null;
    private static MySQLiteHelper dbHelper;

    public DatabaseAdapter(Context mContext, MySQLiteHelper helper) {
        this.mContext = mContext;
        dbHelper = helper;
    }

    public void open() throws android.database.SQLException {
        myDB = dbHelper.getWritableDatabase();
        myDB.execSQL("PRAGMA foreign_keys=ON;");
    }

    public synchronized int registerDoctor(Doctor doctor){
        int rowId = (int) myDB.insert(MySQLiteHelper.TABLE_DOCTOR, null,
                ObjectMapper.getContentValues(doctor));
        doctor.setId(rowId);

        return rowId;
    }

    public synchronized Doctor getDoctorById(String doctorId){
        Doctor doctor = null;

        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_DOCTOR + " WHERE " + Doctor.DOCTOR_ID + "='" + doctorId + "'";

        Cursor c = null;
        try {
            c = myDB.rawQuery(sql, null);
            if (c.moveToNext()) {
                doctor = ObjectMapper.getDoctor(c);
                try {
                    doctor.setPatients(getPatientsByDoctorId(doctorId));
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return doctor;
    }

    public List<Patient> getPatientsByDoctorId(String doctorId){
        List<Patient> lsPatients = new ArrayList<>();

        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_PATIENT + " WHERE " + Doctor.DOCTOR_ID + "='" +  String.valueOf(doctorId) + "'";

        Cursor c = myDB.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            Patient p = new Patient();
            p.setId(c.getInt(c.getColumnIndex("id")));
            p.setName(c.getString(c.getColumnIndex(Patient.NAME)));
            p.setLastName(c.getString(c.getColumnIndex(Patient.LAST_NAME)));
            p.setDoctorId(c.getString(c.getColumnIndex(Patient.DOCTOR_ID)));
            p.setSyndromRate(c.getInt(c.getColumnIndex(Patient.SYNDROM_RATE)));
            lsPatients.add(p);
            c.moveToNext();
        }

        c.close();

        return lsPatients;
    }


    public int addPatient(Patient p){
        Cursor c = myDB.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_PATIENT + " WHERE " + Patient.ID + "='" + p.getId() + "'", null);

        if (c != null) {
            c.moveToFirst();

            if (c.getCount() == 0) {
                p.setId((int) myDB.insert(MySQLiteHelper.TABLE_PATIENT, null, ObjectMapper.getContentValues(p)));
            } else {
                p.setId(c.getInt(c.getColumnIndex(Patient.ID)));
                myDB.update(MySQLiteHelper.TABLE_PATIENT, ObjectMapper.getContentValues(p), Patient.ID + "=?", new String[]{String.valueOf(p.getId())});
            }

            c.close();
        }

        return p.getId();
    }
}
