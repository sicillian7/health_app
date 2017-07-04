package com.nixxie.healthapplicationmvp.mvp.view;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import java.util.List;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public interface PatientsViewContract extends BaseView {

    void onDoctorReceived(Doctor doctor);
    void onPatientsForDoctorRetrieved(List<Patient> lsPatients);
    void onPatientAdded(Patient p);
}
