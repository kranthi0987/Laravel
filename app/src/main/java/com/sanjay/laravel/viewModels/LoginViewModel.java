package com.sanjay.laravel.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.sanjay.laravel.BR;
import com.sanjay.laravel.models.loginModel.LoginPassRequest;

public class LoginViewModel extends BaseObservable {

    @Bindable
    public String toastMessage = null;
    private LoginPassRequest loginPassRequest;
    private String successMessage = "Login was successful";
    private String errorMessage = "Email or Password not valid";

    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    //    public LoginViewModel() {
//        loginPassRequest = new LoginPassRequest("", "",true);
//    }
    public void afterEmailTextChanged(CharSequence s) {
        loginPassRequest.setEmail(s.toString());
    }

    public void afterPasswordTextChanged(CharSequence s) {
        loginPassRequest.setPassword(s.toString());
    }

    public void onLoginClicked() {
        if (loginPassRequest.isInputDataValid())
            setToastMessage(successMessage);
        else
            setToastMessage(errorMessage);
    }
}
