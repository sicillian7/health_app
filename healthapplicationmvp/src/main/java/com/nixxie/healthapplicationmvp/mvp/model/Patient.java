package com.nixxie.healthapplicationmvp.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikolahristovski on 7/3/17.
 */

public class Patient extends ModelWrapper implements Parcelable{

    public static final String DOCTOR_ID = "doctorId";
    public static final String SYNDROM_RATE = "syndromeRate";

    private double syndromRate;
    private String doctorId;

    public Patient() {
    }

    public Patient(String name, String lastName, double syndromRate, String doctorId) {
        this.syndromRate = syndromRate;
        this.doctorId = doctorId;
        this.name = name;
        this.lastName = lastName;
    }

    public Patient(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.lastName = in.readString();
        this.doctorId = in.readString();
        this.syndromRate = in.readDouble();
    }

    public double getSyndromRate() {
        return syndromRate;
    }

    public void setSyndromRate(double syndromRate) {
        this.syndromRate = syndromRate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(doctorId);
        dest.writeDouble(syndromRate);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
