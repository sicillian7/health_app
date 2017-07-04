package com.nixxie.healthapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nixxie.healthapp.R;
import com.nixxie.healthapplicationmvp.mvp.model.Patient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolahristovski on 7/4/17.
 */

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {

    public interface ActionListener{
        void onItemClick(int pos);
    }

    private List<Patient> lsItems = new ArrayList<>();
    private ActionListener mListener;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Patient item = lsItems.get(position);
        String s = item.getName() + " " + item.getLastName();
        ((TextView)holder.itemView).setText(s);
    }

    @Override
    public int getItemCount() {
        return lsItems.size();
    }

    public void notifyDataChanged(List<Patient> newItems){
        lsItems.clear();
        lsItems.addAll(newItems);
        notifyDataSetChanged();
    }

    public void addItem(Patient p){
        lsItems.add(p);
        notifyDataSetChanged();
    }

    public List<Patient> getLsItems(){
        return lsItems;
    }

    public void setActionListener (ActionListener l){
        mListener = l;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private WeakReference<ActionListener> weakListener;


        public ViewHolder(View itemView, ActionListener l) {
            super(itemView);
            weakListener = new WeakReference<ActionListener>(l);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ActionListener mListener = weakListener.get();
            if (mListener != null) {
                mListener.onItemClick(this.getAdapterPosition());
            }
        }
    }
}
