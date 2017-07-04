package com.nixxie.healthapplicationmvp.domain;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import io.reactivex.Single;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class RegisterDoctorUseCase {

    private HealthRepository repo;

    public RegisterDoctorUseCase(HealthRepository repo) {
        this.repo = repo;
    }

    public Single<Doctor> registerDoctor(Doctor d){
        return repo.registerDoctor(d);
    }
}
