package com.sanjay.laravel.utils;

import android.content.Intent;
import android.widget.Toast;
import com.sanjay.laravel.app.MyApplication;
import com.sanjay.laravel.models.logoutmodel.LogoutSuccessResponse;
import com.sanjay.laravel.network.retroFit.ApiClient;
import com.sanjay.laravel.network.retroFit.ApiInterface;
import com.sanjay.laravel.views.activties.LoginActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sanjay.laravel.app.MyApplication.getContext;
import static com.sanjay.laravel.app.MyApplication.session;

public class CommonUsedMethods {
    public static ApiInterface apiInterface;

    public static void logoutUser() {
        session.setLogin(false);
//        db.deleteUsers();
        // Launching the login activity
        logoutcall();
//        RealmResults<LoginResponse> realmResults = realm.where(LoginResponse.class).equalTo("access_token", accesstoken).findAll();
//        realmResults.deleteAllFromRealm();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(intent);

//        MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));
    }


    public static void logoutcall() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        final ProgressDialog progressDoalog;
//        progressDoalog = new ProgressDialog(getContext().this);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Its loading....");
//        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDoalog.show();


        Observable<LogoutSuccessResponse> observable = apiInterface.LOGOUT_SUCCESS_RESPONSE_OBSERVABLE(session.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<LogoutSuccessResponse>() {

            @Override
            public void onError(Throwable e) {
//                progressDoalog.hide();
                Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
//                progressDoalog.hide();
                session.setLogin(false);
                Toast.makeText(getContext(), "Successfully logout", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LogoutSuccessResponse Listdata) {


            }

        });

    }

}
