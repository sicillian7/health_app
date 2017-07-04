package com.nixxie.healthapp.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.ModelWrapper;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public class ObjectMapper {

    public static ContentValues getContentValues(Doctor d){
        ContentValues cv = new ContentValues();

        cv.put(ModelWrapper.NAME, d.getName());
        cv.put(ModelWrapper.LAST_NAME, d.getLastName());
        cv.put(Doctor.DOCTOR_ID, d.getDoctorId());

        return cv;
    }

    public static Doctor getDoctor(Cursor c){
        Doctor d = new Doctor();
        d.setName(c.getString(c.getColumnIndex(Doctor.NAME)));
        d.setLastName(c.getString(c.getColumnIndex(Doctor.LAST_NAME)));
        d.setDoctorId(c.getString(c.getColumnIndex(Doctor.DOCTOR_ID)));

        return d;
    }

    public static ContentValues getContentValues(Patient p){
        ContentValues cv = new ContentValues();
        cv.put(Patient.NAME, p.getName());
        cv.put(Patient.LAST_NAME, p.getLastName());
        cv.put(Doctor.DOCTOR_ID, p.getDoctorId());
        cv.put(Patient.SYNDROM_RATE, p.getSyndromRate());
        return cv;
    }

    public static Patient getPatient(Cursor c){
        Patient p = new Patient();
        p.setName(c.getString(c.getColumnIndex(Patient.NAME)));
        p.setLastName(c.getString(c.getColumnIndex(Patient.LAST_NAME)));
        p.setDoctorId(c.getString(c.getColumnIndex(Patient.DOCTOR_ID)));
        p.setSyndromRate(c.getInt(c.getColumnIndex(Patient.SYNDROM_RATE)));

        return p;
    }
}
