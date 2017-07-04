package com.nixxie.healthapp.di.module;

import android.content.Context;

import com.nixxie.healthapp.db.DatabaseAdapter;
import com.nixxie.healthapp.db.HealthRepositoryImpl;
import com.nixxie.healthapp.db.MySQLiteHelper;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikolahristovski on 7/3/17.
 */


@Module
public class DatabaseModule {

    @Singleton
    @Provides
    MySQLiteHelper providesDbHelper(Context context){
        return new MySQLiteHelper(context);
    }

    @Singleton
    @Provides
    DatabaseAdapter providesDatabaseAdapter(Context context, MySQLiteHelper helper){
        DatabaseAdapter instance = new DatabaseAdapter(context, helper);
        instance.open();

        return instance;
    }

    @Singleton
    @Provides
    HealthRepository providesHealthRepository(DatabaseAdapter databaseAdapter){
        return new HealthRepositoryImpl(databaseAdapter);
    }
}
