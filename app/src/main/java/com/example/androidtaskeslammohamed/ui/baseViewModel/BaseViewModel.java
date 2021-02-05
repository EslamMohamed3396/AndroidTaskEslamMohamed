package com.example.androidtaskeslammohamed.ui.baseViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidtaskeslammohamed.utilits.ProgressDialog;
import com.example.androidtaskeslammohamed.api.Network.networkCallBack.ICallBackNetwork;
import com.example.androidtaskeslammohamed.api.client.mapsApi.ApiClientMaps;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {

    private static final String TAG = "BaseViewModel";
    private Disposable disposable;

    protected <U> LiveData<U> callApi(Observable<U> api) {
        MutableLiveData<U> responseMutableLiveData = new MutableLiveData<>();
        ApiClientMaps.getInstance().request(api, new ICallBackNetwork<U>() {
            @Override
            public void onSuccess(U response) {
                responseMutableLiveData.setValue(response);
            }

            @Override
            public void onDisposable(Disposable d) {
                disposable = d;
            }

            @Override
            public void onFailed(String error) {
                Log.d(TAG, "onFailed: " + error);
            }
        });
        return responseMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
