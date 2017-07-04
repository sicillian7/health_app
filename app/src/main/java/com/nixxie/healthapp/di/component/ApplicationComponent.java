package com.nixxie.healthapp.di.component;

import com.nixxie.healthapp.db.DatabaseAdapter;
import com.nixxie.healthapp.di.module.ApplicationModule;
import com.nixxie.healthapp.di.module.DatabaseModule;
import com.nixxie.healthapplicationmvp.source.HealthRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikolahristovski on 7/3/17.
 */


@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface ApplicationComponent {
    DatabaseAdapter exposeDatabaseAdapter();
    HealthRepository exposeRepository();
}
