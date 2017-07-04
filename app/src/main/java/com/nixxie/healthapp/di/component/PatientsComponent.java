package com.nixxie.healthapp.di.component;

import com.google.gson.Gson;
import com.nixxie.healthapp.activity.DoctorActivity;
import com.nixxie.healthapp.di.module.ActivityModule;
import com.nixxie.healthapp.di.module.DoctorsModule;
import com.nixxie.healthapp.di.module.PatientsModule;
import com.nixxie.healthapp.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by nikolahristovski on 7/4/17.
 */

@PerActivity
@Component (dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, PatientsModule.class, DoctorsModule.class})
public interface PatientsComponent {
    void inject(DoctorActivity doctorActivity);
}
