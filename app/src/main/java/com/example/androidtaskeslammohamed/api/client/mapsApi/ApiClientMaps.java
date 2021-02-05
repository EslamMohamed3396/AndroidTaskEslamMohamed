package com.example.androidtaskeslammohamed.api.client.mapsApi;

import android.app.ProgressDialog;

import com.example.androidtaskeslammohamed.R;
import com.example.androidtaskeslammohamed.api.Network.networkCallBack.ICallBackNetwork;
import com.example.androidtaskeslammohamed.api.services.ApiServices;
import com.example.androidtaskeslammohamed.model.findPlace.response.ResponseFindPlaces;
import com.example.androidtaskeslammohamed.utilits.application.App;
import com.example.androidtaskeslammohamed.utilits.constants.Constants;
import com.example.androidtaskeslammohamed.utilits.utilits.Utilis;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientMaps {
    private static final String TAG = "ApiClient";
    private static ApiClientMaps SINSTANCE;
    private static OkHttpClient.Builder sBuilder;
    private final ApiServices apiService;
    private ProgressDialog progressDialog;

    private ApiClientMaps() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_MAPS)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiServices.class);
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            if (sBuilder == null) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                sBuilder = new OkHttpClient.Builder()
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor);
            }
            return sBuilder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static synchronized ApiClientMaps getInstance() {
        if (SINSTANCE == null) {
            SINSTANCE = new ApiClientMaps();
        }
        return SINSTANCE;
    }


    public <U> void request(Observable<U> api, ICallBackNetwork<U> callBackNetwork) {
        if (Utilis.isNetworkAvailable(App.getAppContext())) {
            api.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<U>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            callBackNetwork.onDisposable(d);
                        }

                        @Override
                        public void onNext(U u) {
                            callBackNetwork.onSuccess(u);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof HttpException) {
                                switch (((HttpException) e).code()) {
                                    case 401:
                                        callBackNetwork.onFailed("UnAuthorization");
                                        break;
                                    case 500:
                                        callBackNetwork.onFailed("Internal Server Error");
                                        break;
                                    default:
                                        callBackNetwork.onFailed("Error");
                                }
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            Utilis.toastMessage(App.getAppContext().getResources().getString(R.string.no_network));
        }
    }


    //region places

    public Observable<ResponseFindPlaces> responseFindPlacesObservable(String input,
                                                                       String inputType,
                                                                       String key) {
        return apiService.RESPONSE_FIND_PLACES_OBSERVABLE(input, inputType, key);
    }

    //endregion
}
