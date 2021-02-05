package com.example.androidtaskeslammohamed.api.services;

import com.example.androidtaskeslammohamed.model.findPlace.response.ResponseFindPlaces;
import com.example.androidtaskeslammohamed.utilits.constants.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET(Constants.PLACES)
    Observable<ResponseFindPlaces> RESPONSE_FIND_PLACES_OBSERVABLE(@Query("input") String input,
                                                                   @Query("inputtype") String inputtype,
                                                                   @Query("key ") String key);
}
