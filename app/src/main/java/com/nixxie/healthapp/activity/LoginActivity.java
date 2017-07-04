package com.nixxie.healthapp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nixxie.healthapp.R;
import com.nixxie.healthapp.base.BaseActivity;
import com.nixxie.healthapp.di.component.DaggerDoctorsComponent;
import com.nixxie.healthapp.di.component.DoctorsComponent;
import com.nixxie.healthapp.di.module.ActivityModule;
import com.nixxie.healthapp.di.module.DoctorsModule;
import com.nixxie.healthapp.fragment.LoginDoctorFragment;
import com.nixxie.healthapp.fragment.RegisterDoctorFragment;
import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.presenter.RegisterDoctorPresenter;
import com.nixxie.healthapplicationmvp.mvp.view.LoginViewContract;

import javax.inject.Inject;


public class LoginActivity extends BaseActivity implements LoginViewContract,LoginDoctorFragment.OnActionListener, RegisterDoctorFragment.OnActionListener{

    @Inject
    RegisterDoctorPresenter presenter;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void resolveDaggerDependencies() {
        super.resolveDaggerDependencies();
        DoctorsComponent doctorsComponent = DaggerDoctorsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .doctorsModule(new DoctorsModule())
                .build();

        doctorsComponent.inject(this);

        setupViews();
    }

    private void setupViews(){
        LoginDoctorFragment loginDoctorFragment = LoginDoctorFragment.getInstance();
        loginDoctorFragment.setActionListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim,
                R.anim.exit_anim, R.anim.enter_anim);
        ft.add(R.id.login_activity_fragment_holder, loginDoctorFragment);
        ft.commit();

        presenter.attachView(this);
    }


    /* LOGIN FRAGMENT callbacks*/

    @Override
    public void onLogin(String doctorId) {
        if(doctorId.length() > 0){
            presenter.getDoctorById(doctorId);
        }else{
            Toast.makeText(this, "Please enter your Staff ID", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterNewDoctor() {
        RegisterDoctorFragment fr = RegisterDoctorFragment.getInstance();
        fr.setOnActionListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim,
                R.anim.exit_anim, R.anim.enter_anim);
        ft.replace(R.id.login_activity_fragment_holder, fr);
        ft.addToBackStack("loginFragment");
        ft.commit();
    }


    /* REGISTER FRAGMENT callbacks*/
    @Override
    public void onRegisterNewDoctor(String firstName, String lastName, String ID) {

        /* Light step of validation*/
        if(firstName.length() > 0 && lastName.length() > 0 && ID.length() > 0){
            Doctor d = new Doctor();
            d.setName(firstName);
            d.setLastName(lastName);
            d.setDoctorId(ID);
            presenter.registerDoctor(d);
        }else{
            Toast.makeText(this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void displayError(String s) {
          //  super.displayError(s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayProgressDialog(boolean isVisible) {
        //displayProgressDialog(isVisible);
    }

    @Override
    public void onDoctorReceived(Doctor d) {
        Intent i = new Intent(LoginActivity.this, DoctorActivity.class);
        i.putExtra("doctorId", d.getDoctorId());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
