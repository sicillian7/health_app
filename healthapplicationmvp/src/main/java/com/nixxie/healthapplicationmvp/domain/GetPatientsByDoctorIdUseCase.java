package com.nixxie.healthapplicationmvp.domain;

import com.nixxie.healthapplicationmvp.mvp.model.Patient;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class GetPatientsByDoctorIdUseCase {

    private HealthRepository repo;

    public GetPatientsByDoctorIdUseCase(HealthRepository repo) {
        this.repo = repo;
    }

    public Single<List<Patient>> getPatientsByDoctorId(String doctorsId){
        return repo.getPatientsByDoctorId(doctorsId);
    }
}
