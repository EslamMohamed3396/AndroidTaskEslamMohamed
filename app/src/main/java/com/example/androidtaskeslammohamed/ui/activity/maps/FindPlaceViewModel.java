package com.example.androidtaskeslammohamed.ui.activity.maps;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.androidtaskeslammohamed.api.client.mapsApi.ApiClientMaps;
import com.example.androidtaskeslammohamed.model.findPlace.response.ResponseFindPlaces;
import com.example.androidtaskeslammohamed.ui.baseViewModel.BaseViewModel;


public class FindPlaceViewModel extends BaseViewModel {

    public LiveData<ResponseFindPlaces> getNearbyRestaurantsLiveData(String input,
                                                                     String inputType,
                                                                     String key) {
        return callApi(ApiClientMaps.getInstance().responseFindPlacesObservable(input, inputType, key));
    }

}
