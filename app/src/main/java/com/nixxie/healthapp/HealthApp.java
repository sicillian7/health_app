package com.nixxie.healthapp;

import android.app.Application;

import com.nixxie.healthapp.di.component.DaggerApplicationComponent;
import com.nixxie.healthapp.di.module.ApplicationModule;
import com.nixxie.healthapp.di.module.DatabaseModule;
import com.nixxie.healthapp.di.component.ApplicationComponent;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public class HealthApp extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule())
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
