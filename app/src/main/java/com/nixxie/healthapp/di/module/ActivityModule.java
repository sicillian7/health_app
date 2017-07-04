package com.nixxie.healthapp.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.nixxie.healthapp.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Context provideContext(){
        return activity;
    }
}
