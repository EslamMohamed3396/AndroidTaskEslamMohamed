package com.example.androidtaskeslammohamed.api.Network.networkCallBack;


import io.reactivex.disposables.Disposable;

public interface ICallBackNetwork<U> {

    void onSuccess(U response);

    void onDisposable(Disposable d);

    void onFailed(String error);

}
