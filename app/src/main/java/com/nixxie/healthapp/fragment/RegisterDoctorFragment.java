package com.nixxie.healthapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nixxie.healthapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class RegisterDoctorFragment extends Fragment {

    public interface OnActionListener{
        void onRegisterNewDoctor(String firstName, String lastName, String ID);
    }

    @BindView(R.id.name_et)
    EditText etName;
    @BindView(R.id.last_name_et)
    EditText etLastName;
    @BindView(R.id.staff_id_et)
    EditText etID;

    Unbinder unbinder;
    private OnActionListener mListener;

    public RegisterDoctorFragment() {
    }

    public static RegisterDoctorFragment getInstance(){
        return new RegisterDoctorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_doctor_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    public void setOnActionListener(OnActionListener l){
        mListener = l;
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(){
        if (mListener != null) {
            mListener.onRegisterNewDoctor(etName.getText().toString(),
                    etLastName.getText().toString(), etID.getText().toString());
        }
    }
}
