package com.example.androidtaskeslammohamed.ui.adapter.sourceLocation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.androidtaskeslammohamed.model.location.SourceLocation;

public class SourceLocationDiffCallback extends DiffUtil.ItemCallback<SourceLocation> {

    @Override
    public boolean areItemsTheSame(@NonNull SourceLocation oldItem, @NonNull SourceLocation newItem) {
        return oldItem.getName() == newItem.getName();
    }

    @Override
    public boolean areContentsTheSame(@NonNull SourceLocation oldItem, @NonNull SourceLocation newItem) {
        return oldItem.equals(newItem);
    }
}
