package com.nixxie.healthapplicationmvp.source;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public interface HealthRepository {

    Single<Doctor> getDoctorById(String id);
    Single<Doctor> registerDoctor(Doctor d);
    Single<List<Patient>> getPatientsByDoctorId(String id);
    Single<Patient> addPatient(Patient p, String doctorId);
}
