package com.example.androidtaskeslammohamed.ui.adapter.destnationLocation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.androidtaskeslammohamed.model.findPlace.response.Candidate;
import com.example.androidtaskeslammohamed.model.location.SourceLocation;

public class DestnationLocationDiffCallback extends DiffUtil.ItemCallback<Candidate> {

    @Override
    public boolean areItemsTheSame(@NonNull Candidate oldItem, @NonNull Candidate newItem) {
        return oldItem.getName() == newItem.getName();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Candidate oldItem, @NonNull Candidate newItem) {
        return oldItem.equals(newItem);
    }
}
