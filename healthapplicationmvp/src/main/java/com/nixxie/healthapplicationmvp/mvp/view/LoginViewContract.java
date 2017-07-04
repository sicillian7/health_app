package com.nixxie.healthapplicationmvp.mvp.view;

import com.nixxie.healthapplicationmvp.mvp.model.Doctor;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public interface LoginViewContract extends BaseView {

    void onDoctorReceived(Doctor d);
}
