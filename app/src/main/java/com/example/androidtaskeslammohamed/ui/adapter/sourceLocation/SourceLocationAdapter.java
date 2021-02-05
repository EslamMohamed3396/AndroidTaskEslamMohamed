package com.example.androidtaskeslammohamed.ui.adapter.sourceLocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtaskeslammohamed.R;
import com.example.androidtaskeslammohamed.databinding.ItemLocationNameBinding;
import com.example.androidtaskeslammohamed.model.location.SourceLocation;

public class SourceLocationAdapter extends ListAdapter<SourceLocation, SourceLocationAdapter.LocationAdapterViewHolder> {

    private final SourceLocationClicked sourceLocationClicked;

    public SourceLocationAdapter(SourceLocationClicked sourceLocationClicked) {
        super(new SourceLocationDiffCallback());
        this.sourceLocationClicked = sourceLocationClicked;
    }

    @NonNull
    @Override
    public LocationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLocationNameBinding itemLocationNameBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_location_name, parent, false);
        return new LocationAdapterViewHolder(itemLocationNameBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapterViewHolder holder, int position) {
        SourceLocation sourceLocation = getItem(position);
        holder.bindData(sourceLocation);
    }

    class LocationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemLocationNameBinding itemLocationNameBinding;

        public LocationAdapterViewHolder(ItemLocationNameBinding itemLocationNameBinding) {
            super(itemLocationNameBinding.getRoot());
            this.itemLocationNameBinding = itemLocationNameBinding;
        }

        private void bindData(SourceLocation sourceLocation) {
            itemLocationNameBinding.setLocation(sourceLocation);
            itemLocationNameBinding.executePendingBindings();
            itemLocationNameBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sourceLocationClicked.onLocationClicked(getItem(getAdapterPosition()));
        }
    }
}
