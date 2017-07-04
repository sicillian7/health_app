package com.nixxie.healthapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nixxie.healthapp.AddPatientActivity;
import com.nixxie.healthapp.R;
import com.nixxie.healthapp.adapters.PatientsAdapter;
import com.nixxie.healthapp.base.BaseActivity;
import com.nixxie.healthapp.di.component.DaggerPatientsComponent;
import com.nixxie.healthapp.di.component.PatientsComponent;
import com.nixxie.healthapp.di.module.ActivityModule;
import com.nixxie.healthapp.di.module.DoctorsModule;
import com.nixxie.healthapp.di.module.PatientsModule;
import com.nixxie.healthapp.utils.DividerItemDecoration;
import com.nixxie.healthapplicationmvp.mvp.model.Doctor;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;
import com.nixxie.healthapplicationmvp.mvp.presenter.PatientsPresenter;
import com.nixxie.healthapplicationmvp.mvp.view.PatientsViewContract;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class DoctorActivity extends BaseActivity implements PatientsViewContract, PatientsAdapter.ActionListener {

    private static final int ADD_PATIENT_REQUEST_CODE = 1020;

    @BindView(R.id.add_button)
    FloatingActionButton fab;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.no_content_view)
    TextView tvNoContent;

    @Inject
    PatientsPresenter presenter;
    @Inject
    PatientsAdapter adapter;
    @Inject
    DividerItemDecoration itemDecoration;

    private Doctor doctor;

    @Override
    protected int getContentView() {
        return R.layout.activity_doctor;
    }

    @Override
    protected void resolveDaggerDependencies() {
        super.resolveDaggerDependencies();
        PatientsComponent patientsComponent = DaggerPatientsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .patientsModule(new PatientsModule())
                .doctorsModule(new DoctorsModule())
                .build();

        patientsComponent.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        String id = getIntent().getStringExtra("doctorId");
        if (id != null) {
            presenter.loadDoctor(id);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(adapter);
    }

    @Override
    public void displayError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayProgressDialog(boolean isVisible) {
    }

    @Override
    public void onDoctorReceived(Doctor doctor) {
        this.doctor = doctor;
        presenter.loadPatients();
    }

    @Override
    public void onPatientsForDoctorRetrieved(List<Patient> lsPatients) {
        if(lsPatients.size() > 0){
            adapter.notifyDataChanged(lsPatients);
        }else{
            rv.setVisibility(View.GONE);
            tvNoContent.setVisibility(View.VISIBLE);
        }
        adapter.setActionListener(this);
    }

    @Override
    public void onPatientAdded(Patient p) {
        rv.setVisibility(View.VISIBLE);
        adapter.addItem(p);
        tvNoContent.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int pos) {
        Intent i = new Intent(DoctorActivity.this, PatientDetailsActivity.class);
        i.putExtra("patient", adapter.getLsItems().get(pos));
        startActivity(i);
    }

    @OnClick(R.id.add_button)
    public void onFabClick(){
        Intent i = new Intent(DoctorActivity.this, AddPatientActivity.class);
        startActivityForResult(i, ADD_PATIENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_PATIENT_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    String name = data.getStringExtra("name");
                    String lastName = data.getStringExtra("lastName");
                    double syndromeRate = data.getDoubleExtra("rate", 0);
                    Patient p = new Patient(name, lastName, syndromeRate, doctor.getDoctorId());
                    presenter.addPatient(p);
                }
            }
        }
    }
}
