package com.example.androidtaskeslammohamed.firebase;

import android.util.Log;

import com.example.androidtaskeslammohamed.model.location.SourceLocation;
import com.example.androidtaskeslammohamed.utilits.constants.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    private static final String TAG = "FirebaseUtilits";
    private FirebaseFirestore db;


    public FirebaseManager() {
        db = FirebaseFirestore.getInstance();
    }


    //region add 10 dummy location to firestore

    public void addLocationDummyToFireStore() {
        List<SourceLocation> sourceLocations = new ArrayList<>();
        sourceLocations.add(new SourceLocation("منشأة البكاري", 30.011461, 31.170967));
        sourceLocations.add(new SourceLocation("ابو ربيع الجيزي", 30.012349, 31.175953));
        sourceLocations.add(new SourceLocation("الشهيد أحمد حمدي", 30.010975, 31.179757));
        sourceLocations.add(new SourceLocation("صفط اللبن", 30.014577, 31.159551));
        sourceLocations.add(new SourceLocation("طريق المريوطية", 30.014196, 31.161965));
        sourceLocations.add(new SourceLocation("قاعة لمار للأفراح", 30.012411, 31.163076));
        sourceLocations.add(new SourceLocation("صيدلية د. نادية فاروق", 30.011965, 31.164315));
        sourceLocations.add(new SourceLocation("جيم سندريلا", 30.011723, 31.164197));
        sourceLocations.add(new SourceLocation("مغسلة المحروسة لغسيل السيارات", 30.011412, 31.164063));
        sourceLocations.add(new SourceLocation("Gusto Crepe", 30.010871, 31.164661));

        for (SourceLocation location : sourceLocations) {
            db.collection(Constants.COLLECTION_NAME_SOURCE).add(location)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

        }
    }

    //endregion
    // region add selected destination location to firestore

    public void addLocationDestnationToFireStore(SourceLocation sourceLocation) {
        db.collection(Constants.COLLECTION_NAME_DESTINATION).add(sourceLocation)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));


    }

    //endregion


    //region get source location


    public void getSourceLocation(IReadLocations iReadLocations) {
        List<SourceLocation> sourceLocations = new ArrayList<>();
        db.collection(Constants.COLLECTION_NAME_SOURCE)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    sourceLocations.add(document.toObject(SourceLocation.class));
                }
                iReadLocations.readLocations(sourceLocations);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }

    //endregion

}
