package com.nixxie.healthapp.di.module;

import com.nixxie.healthapp.di.scope.PerActivity;
import com.nixxie.healthapplicationmvp.domain.GetDoctorByIdUseCase;
import com.nixxie.healthapplicationmvp.domain.RegisterDoctorUseCase;
import com.nixxie.healthapplicationmvp.mvp.presenter.RegisterDoctorPresenter;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikolahristovski on 7/4/17.
 */


@Module
public class DoctorsModule {

    @PerActivity
    @Provides
    RegisterDoctorUseCase providesRegisterDoctorUseCase(HealthRepository repo){
        return new RegisterDoctorUseCase(repo);
    }

    @PerActivity
    @Provides
    GetDoctorByIdUseCase providesGetDoctorByIdUseCase(HealthRepository repo){
        return new GetDoctorByIdUseCase(repo);
    }


    @PerActivity
    @Provides
    RegisterDoctorPresenter provideRegisterDoctorPresenter(RegisterDoctorUseCase registerDoctorUseCase,
                                                           GetDoctorByIdUseCase getDoctorByIdUseCase){
        return new RegisterDoctorPresenter(registerDoctorUseCase, getDoctorByIdUseCase);
    }
}
