package com.example.androidtaskeslammohamed.ui.adapter.destnationLocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtaskeslammohamed.R;
import com.example.androidtaskeslammohamed.databinding.ItemDestnationLocationNameBinding;
import com.example.androidtaskeslammohamed.model.findPlace.response.Candidate;

public class DestnationLocationAdapter extends ListAdapter<Candidate, DestnationLocationAdapter.LocationAdapterViewHolder> {

    private final DestnationLocationClicked destnationLocationClicked;

    public DestnationLocationAdapter(DestnationLocationClicked destnationLocationClicked) {
        super(new DestnationLocationDiffCallback());
        this.destnationLocationClicked = destnationLocationClicked;
    }

    @NonNull
    @Override
    public LocationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDestnationLocationNameBinding itemLocationNameBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_destnation_location_name, parent, false);
        return new LocationAdapterViewHolder(itemLocationNameBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapterViewHolder holder, int position) {
        Candidate sourceLocation = getItem(position);
        holder.bindData(sourceLocation);
    }

    class LocationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemDestnationLocationNameBinding itemLocationNameBinding;

        public LocationAdapterViewHolder(ItemDestnationLocationNameBinding itemLocationNameBinding) {
            super(itemLocationNameBinding.getRoot());
            this.itemLocationNameBinding = itemLocationNameBinding;
        }

        private void bindData(Candidate sourceLocation) {
            itemLocationNameBinding.setLocation(sourceLocation);
            itemLocationNameBinding.executePendingBindings();
            itemLocationNameBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            destnationLocationClicked.onLocationDestnationClicked(getItem(getAdapterPosition()));
        }
    }
}
