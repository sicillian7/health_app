package com.nixxie.healthapp.di.module;

import android.content.Context;
import com.nixxie.healthapp.adapters.PatientsAdapter;
import com.nixxie.healthapp.di.scope.PerActivity;
import com.nixxie.healthapp.utils.DividerItemDecoration;
import com.nixxie.healthapplicationmvp.domain.AddPatientUseCase;
import com.nixxie.healthapplicationmvp.domain.GetDoctorByIdUseCase;
import com.nixxie.healthapplicationmvp.domain.GetPatientsByDoctorIdUseCase;
import com.nixxie.healthapplicationmvp.mvp.presenter.PatientsPresenter;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikolahristovski on 7/4/17.
 */

@Module
public class PatientsModule {

    @PerActivity
    @Provides
    GetPatientsByDoctorIdUseCase providesGetPatientsByDoctorIdUseCase(HealthRepository repo){
        return new GetPatientsByDoctorIdUseCase(repo);
    }

    @PerActivity
    @Provides
    AddPatientUseCase providesAddPatientUseCase(HealthRepository repo){
        return new AddPatientUseCase(repo);
    }

    @PerActivity
    @Provides
    PatientsPresenter providesPatientsPreseneter(GetDoctorByIdUseCase getDoctorByIdUseCase,
                                                 GetPatientsByDoctorIdUseCase getPatientsByDoctorIdUseCase,
                                                 AddPatientUseCase addPatientUseCase){
        return new PatientsPresenter(getDoctorByIdUseCase, getPatientsByDoctorIdUseCase, addPatientUseCase);
    }

    @PerActivity
    @Provides
    PatientsAdapter providesAdapter(){
        return new PatientsAdapter();
    }

    @PerActivity
    @Provides
    DividerItemDecoration providesItemDecorator(Context context){
        return new DividerItemDecoration(context);
    }
}
