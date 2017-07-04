package com.nixxie.healthapplicationmvp.mvp.model;

import java.util.List;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public class Doctor extends ModelWrapper{

    public static final String DOCTOR_ID = "doctorId";

    private String doctorId;
    private List<Patient> patients;


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
