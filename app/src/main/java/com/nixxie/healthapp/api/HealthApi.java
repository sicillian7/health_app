package com.nixxie.healthapp.api;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


/** Exampele interface of an API**/

public interface HealthApi {
    @POST("/doctor")
    Single<Doctor> registerNewDoctor(@Body Doctor doctor);

    @GET("/doctor")
    Single<Doctor> getDoctorById(@Query("doctorId") String doctorId);

    @PUT
    Single<Doctor> updateDoctor(@Body Doctor doctor);

    @GET("/doctor/patients")
    Single<List<Patient>> getPatientsForDoctor(@Query("doctorId") String doctorId);

    @POST("/doctor/patients")
    Single<Patient> addPatient(@Body Patient patient);

    @PUT("/doctor/patients")
    Single<Patient> updatePatient(@Body Patient patient);

    @DELETE("/doctor/patients")
    Single<Patient> removePatient(@Body Patient patient);
}
