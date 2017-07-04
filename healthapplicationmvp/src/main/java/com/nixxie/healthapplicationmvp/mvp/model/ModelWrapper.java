package com.nixxie.healthapplicationmvp.mvp.model;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public abstract class ModelWrapper {

    public static final String NAME = "name";
    public static final String LAST_NAME = "lastName";
    public static final String ID = "id";

    public String name, lastName;
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
