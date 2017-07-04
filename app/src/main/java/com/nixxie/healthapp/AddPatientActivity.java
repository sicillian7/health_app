package com.nixxie.healthapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.SparseBooleanArray;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nixxie.healthapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class AddPatientActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.question1_radio_group)
    RadioGroup rg1;
    @BindView(R.id.question2_radio_group)
    RadioGroup rg2;
    @BindView(R.id.question3_radio_group)
    RadioGroup rg3;
    @BindView(R.id.question4_radio_group)
    RadioGroup rg4;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_last_name)
    EditText etLastName;

    private SparseBooleanArray values = new SparseBooleanArray();

    @Override
    protected int getContentView() {
        return R.layout.activity_add_patient;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupArray();
        setListeners();
    }

    private void setListeners() {
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg3.setOnCheckedChangeListener(this);
        rg4.setOnCheckedChangeListener(this);
    }

    private void setupArray(){
        values.append(1, false);
        values.append(2, false);
        values.append(3, false);
        values.append(4, false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()){
            case R.id.question1_radio_group:
                if(checkedId == R.id.question1_yes){
                    values.append(1, true);
                }else{
                    values.append(1, false);
                }
                break;

            case R.id.question2_radio_group:
                if(checkedId == R.id.question2_yes){
                    values.append(2, true);
                }else{
                    values.append(2, false);
                }
                break;

            case R.id.question3_radio_group:
                if(checkedId == R.id.question3_yes){
                    values.append(3, true);
                }else{
                    values.append(3, false);
                }
                break;

            case R.id.question4_radio_group:
                if(checkedId == R.id.question4_yes){
                    values.append(4, true);
                }else{
                    values.append(4, false);
                }
                break;
        }
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitClick(){
        String name = etName.getText().toString();
        String lastName = etLastName.getText().toString();
        double syndromeRate = calculateSyndromeRate();

        if(name.length() > 0 && lastName.length() > 0){
            Intent data = new Intent();
            data.putExtra("name",name);
            data.putExtra("lastName",lastName);
            data.putExtra("rate",syndromeRate);
            setResult(RESULT_OK,data);
            finish();
        }else{
            Toast.makeText(this, "Patient's data is required", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateSyndromeRate(){
        double count = 0;

        for(int i = 0; i < values.size(); i++){
            if(values.get(i)){
               count++;
            }
        }

        return (count/4) * 100;
    }
}
