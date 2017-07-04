package com.nixxie.healthapp.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.nixxie.healthapp.HealthApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {


    private HealthApp mApplication;

    public ApplicationModule(HealthApp application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    HealthApp provideApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    Context provideContext(){
        return mApplication;
    }

}
