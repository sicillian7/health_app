package com.nixxie.healthapplicationmvp.mvp.presenter;

import com.nixxie.healthapplicationmvp.domain.AddPatientUseCase;
import com.nixxie.healthapplicationmvp.domain.GetDoctorByIdUseCase;
import com.nixxie.healthapplicationmvp.domain.GetPatientsByDoctorIdUseCase;
import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;
import com.nixxie.healthapplicationmvp.mvp.view.PatientsViewContract;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class PatientsPresenter implements BasePresenter<PatientsViewContract> {

    private WeakReference<PatientsViewContract> mWeakView;
    private List<Disposable> lsDisposables = new ArrayList<>();
    private GetDoctorByIdUseCase getDoctorByIdUseCase;
    private GetPatientsByDoctorIdUseCase getPatientsByDoctorIdUseCase;
    private AddPatientUseCase addPatientUseCase;
    private Doctor doctor;

    public PatientsPresenter(GetDoctorByIdUseCase getDoctorByIdUseCase,
                             GetPatientsByDoctorIdUseCase getPatientsByDoctorIdUseCase,
                             AddPatientUseCase addPatientUseCase) {
        this.getDoctorByIdUseCase = getDoctorByIdUseCase;
        this.getPatientsByDoctorIdUseCase = getPatientsByDoctorIdUseCase;
        this.addPatientUseCase = addPatientUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void attachView(PatientsViewContract view) {
        mWeakView = new WeakReference<PatientsViewContract>(view);
    }

    @Override
    public void detachView() {

    }

    public void loadDoctor(String doctorId){
        getDoctorByIdUseCase.getDoctorById(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new GetDoctorHandler(mWeakView, this));
    }

    public void loadPatients(){
        if (doctor != null) {
            getPatientsByDoctorIdUseCase.getPatientsByDoctorId(doctor.getDoctorId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new GetPatientsForDoctor(mWeakView, this));
        }
    }

    public void addPatient(Patient p){
        if (doctor != null) {
            addPatientUseCase.addPatient(p)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new AddPatientHandler(mWeakView, this));
        }
    }

    private static class GetDoctorHandler implements SingleObserver<Doctor>{

        private WeakReference<PatientsPresenter> mWeakPresenter;
        private WeakReference<PatientsViewContract> mWeakView;

        public GetDoctorHandler(WeakReference<PatientsViewContract> mWeakView, PatientsPresenter p) {
            this.mWeakView = mWeakView;
            mWeakPresenter = new WeakReference<PatientsPresenter>(p);
        }

        @Override
        public void onSubscribe(Disposable d) {
            PatientsPresenter p = mWeakPresenter.get();

            if (p != null) {
                p.lsDisposables.add(d);
            }
        }

        @Override
        public void onSuccess(Doctor doctor) {
            PatientsPresenter p = mWeakPresenter.get();
            if (p != null) {
                p.doctor = doctor;
            }

            PatientsViewContract v = mWeakView.get();
            if (v != null) {
                v.onDoctorReceived(doctor);
            }

        }

        @Override
        public void onError(Throwable e) {
            PatientsViewContract v = mWeakView.get();
            if (v != null) {
                v.displayError("Error retrieving your doctor account!");
            }
        }
    }

    // this should extend from a single class for each handler

    private static class GetPatientsForDoctor implements SingleObserver<List<Patient>>{

        private WeakReference<PatientsPresenter> mWeakPresenter;
        private WeakReference<PatientsViewContract> mWeakView;

        public GetPatientsForDoctor(WeakReference<PatientsViewContract> mWeakView, PatientsPresenter p) {
            this.mWeakView = mWeakView;
            mWeakPresenter = new WeakReference<PatientsPresenter>(p);
        }

        @Override
        public void onSubscribe(Disposable d) {
            PatientsPresenter p = mWeakPresenter.get();

            if (p != null) {
                p.lsDisposables.add(d);
            }
        }

        @Override
        public void onSuccess(List<Patient> patients) {
            PatientsViewContract v = mWeakView.get();
            if (v != null) {
               v.onPatientsForDoctorRetrieved(patients);
            }
        }

        @Override
        public void onError(Throwable e) {
            PatientsViewContract v = mWeakView.get();
            if (v != null) {
                v.displayError("Error retrieving patients!");
            }
        }
    }

    private static class AddPatientHandler implements SingleObserver<Patient>{

        private WeakReference<PatientsPresenter> mWeakPresenter;
        private WeakReference<PatientsViewContract> mWeakView;

        public AddPatientHandler(WeakReference<PatientsViewContract> mWeakView, PatientsPresenter p) {
            this.mWeakView = mWeakView;
            mWeakPresenter = new WeakReference<PatientsPresenter>(p);
        }

        @Override
        public void onSubscribe(Disposable d) {
            PatientsPresenter p = mWeakPresenter.get();

            if (p != null) {
                p.lsDisposables.add(d);
            }
        }

        @Override
        public void onSuccess(Patient patient) {
            PatientsViewContract v = mWeakView.get();
            if (v != null) {
               v.onPatientAdded(patient);
            }
        }

        @Override
        public void onError(Throwable e) {
            PatientsViewContract v = mWeakView.get();
            if (v != null) {
                v.displayError("Error adding patient!");
            }
        }
    }
}
