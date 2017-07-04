package com.nixxie.healthapp.db;

import android.database.sqlite.SQLiteException;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class HealthRepositoryImpl implements HealthRepository {

    private DatabaseAdapter dbAdapter;

    public HealthRepositoryImpl(DatabaseAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Override
    public Single<Doctor> getDoctorById(final String id) {
        return Single.create(new SingleOnSubscribe<Doctor>() {
            @Override
            public void subscribe(SingleEmitter<Doctor> e) throws Exception {
                Doctor d = dbAdapter.getDoctorById(id);
                if (d != null) {
                    e.onSuccess(d);
                }else{
                    e.onError(new SQLiteException());
                }
            }
        });
    }

    @Override
    public Single<Doctor> registerDoctor(final Doctor d) {
        return Single.create(new SingleOnSubscribe<Doctor>() {
            @Override
            public void subscribe(SingleEmitter<Doctor> e) throws Exception {
                int rowId = dbAdapter.registerDoctor(d);
                if(rowId > -1){
                    d.setId(rowId);
                    e.onSuccess(d);
                }else{
                    e.onError(new SQLiteException());
                }
            }
        });
    }

    @Override
    public Single<List<Patient>> getPatientsByDoctorId(final String id) {
        return Single.create(new SingleOnSubscribe<List<Patient>>() {
            @Override
            public void subscribe(SingleEmitter<List<Patient>> e) throws Exception {
                List<Patient> lsPatients = dbAdapter.getPatientsByDoctorId(id);
                if (lsPatients != null) {
                    e.onSuccess(lsPatients);
                } else {
                    e.onError(new SQLiteException());
                }
            }
        });
    }

    @Override
    public Single<Patient> addPatient(final Patient patient, String doctorId) {
        return Single.create(new SingleOnSubscribe<Patient>() {
            @Override
            public void subscribe(SingleEmitter<Patient> e) throws Exception {
                 int rowId = dbAdapter.addPatient(patient);
                if(rowId > -1){
                    patient.setId(rowId);
                    e.onSuccess(patient);
                }else{
                    e.onError(new SQLiteException());
                }
            }
        });
    }
}
