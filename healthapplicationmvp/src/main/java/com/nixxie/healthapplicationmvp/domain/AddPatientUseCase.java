package com.nixxie.healthapplicationmvp.domain;

import com.nixxie.healthapplicationmvp.mvp.model.Patient;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import io.reactivex.Single;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class AddPatientUseCase {

    private HealthRepository repo;

    public AddPatientUseCase(HealthRepository repo) {
        this.repo = repo;
    }

    public Single<Patient> addPatient(Patient p){
        return repo.addPatient(p, p.getDoctorId());
    }
}
