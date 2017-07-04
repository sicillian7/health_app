package com.nixxie.healthapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nixxie.healthapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class LoginDoctorFragment extends Fragment {

    public interface OnActionListener{
        void onLogin(String doctorId);
        void onRegisterNewDoctor();
    }

    @BindView(R.id.doctor_id_et)
    EditText etStaffID;
    @BindView(R.id.btn_enter)
    Button btnLogin;
    @BindView(R.id.register_txt)
    TextView tvRegisterAsDoctor;

    Unbinder unbinder;

    private OnActionListener mListener;

    public LoginDoctorFragment() {
    }

    public static LoginDoctorFragment getInstance(){
        return new LoginDoctorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_doctor_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    public void setActionListener(OnActionListener l){
        mListener = l;
    }

    @OnClick(R.id.btn_enter)
    public void onEnterClick(){
        if (mListener != null) {
            mListener.onLogin(etStaffID.getText().toString());
        }
    }

    @OnClick
    public void onRegisterNewDoctorClick(){
        if (mListener != null) {
            mListener.onRegisterNewDoctor();
        }
    }
}
