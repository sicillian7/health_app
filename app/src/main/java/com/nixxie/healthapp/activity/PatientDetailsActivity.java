package com.nixxie.healthapp.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nixxie.healthapp.R;
import com.nixxie.healthapp.base.BaseActivity;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import butterknife.BindView;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class PatientDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_last_name)
    TextView tvLastName;
    @BindView(R.id.tv_rate)
    TextView tvRate;


    @Override
    protected int getContentView() {
        return R.layout.activity_patient_details;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupViews();
    }

    private void setupViews(){
        Patient patient = getIntent().getParcelableExtra("patient");

        if (patient != null) {
            if (patient.getName() != null) {
                tvName.setText(patient.getName());
            }

            if (patient.getLastName() != null) {
                tvLastName.setText(patient.getLastName());
            }

            String rate = String.valueOf(patient.getSyndromRate()) + "%";
            tvRate.setText(rate);
            animate(tvRate);
        }
    }

    private void animate(TextView tv) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        a.reset();
        tv.clearAnimation();
        tv.startAnimation(a);
    }
}
