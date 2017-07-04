package com.nixxie.healthapplicationmvp.mvp.presenter;

import com.nixxie.healthapplicationmvp.domain.GetDoctorByIdUseCase;
import com.nixxie.healthapplicationmvp.domain.RegisterDoctorUseCase;
import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.view.LoginViewContract;

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

public class RegisterDoctorPresenter implements BasePresenter<LoginViewContract> {

    private WeakReference<LoginViewContract> mWeakView;
    private List<Disposable> lsDisposables = new ArrayList<>();
    private RegisterDoctorUseCase registerDoctorUseCase;
    private GetDoctorByIdUseCase getDoctorByIdUseCase;

    public RegisterDoctorPresenter(RegisterDoctorUseCase registerDoctorUseCase,
                                   GetDoctorByIdUseCase getDoctorByIdUseCase) {
        this.registerDoctorUseCase = registerDoctorUseCase;
        this.getDoctorByIdUseCase = getDoctorByIdUseCase;
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
        lsDisposables.clear();
    }

    @Override
    public void attachView(LoginViewContract view) {
        mWeakView = new WeakReference<LoginViewContract>(view);
    }

    @Override
    public void detachView() {

    }

    public void getDoctorById(String doctorId){
        LoginViewContract view = mWeakView.get();

        if (view != null) {
            view.displayProgressDialog(true);
        }

        getDoctorByIdUseCase.getDoctorById(doctorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new GetDoctorByIdHandler(mWeakView, this, 0));

    }

    public void registerDoctor(Doctor d){
        LoginViewContract view = mWeakView.get();

        if (view != null) {
            view.displayProgressDialog(true);
        }
        registerDoctorUseCase.registerDoctor(d)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new GetDoctorByIdHandler(mWeakView, this, 1));
    }


    /**
     * Observer that handles the callbacks when retrieving a doctor from the local database.
     * This handler holds weak references to the presenter and the view so that memory
     * leaks are avoided. The same handler is used when a doctor is registered for the
     * first time because the code to be executed is practically the same,
     * the view needs to receive a doctor whether is just created or already existing*/

    private static class GetDoctorByIdHandler implements SingleObserver<Doctor>{

        private WeakReference<RegisterDoctorPresenter> mWeakPresenter;
        private WeakReference<LoginViewContract> mWeakView;
        private int uscase;

        public GetDoctorByIdHandler(WeakReference<LoginViewContract> weakView, RegisterDoctorPresenter p, int usecase) {
            mWeakView  = weakView;
            mWeakPresenter = new WeakReference<RegisterDoctorPresenter>(p);
            this.uscase = usecase;
        }

        @Override
        public void onSubscribe(Disposable d) {
            RegisterDoctorPresenter p = mWeakPresenter.get();
            if (p != null) {
                p.lsDisposables.add(d);
            }
        }

        @Override
        public void onSuccess(Doctor doctor) {
            LoginViewContract view = mWeakView.get();
            if (view != null) {
                view.onDoctorReceived(doctor);
            }
        }

        @Override
        public void onError(Throwable e) {
            LoginViewContract view = mWeakView.get();
            if (view != null) {
                switch (uscase){
                    case 0:
                        view.displayError("No doctor with that Staff Id is registered!");
                        break;

                    case 1:
                        view.displayError("Error registering a new doctor!");
                        break;
                }
            }
        }
    }
}
