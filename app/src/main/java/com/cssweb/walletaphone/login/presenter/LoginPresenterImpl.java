package com.cssweb.walletaphone.login.presenter;

import android.os.Handler;
import android.os.Looper;

import com.cssweb.walletaphone.login.model.IUser;
import com.cssweb.walletaphone.login.model.UserModel;
import com.cssweb.walletaphone.login.view.ILoginView;

/**
 * Created by chenh on 2015/11/22.
 */
public class LoginPresenterImpl implements ILoginPresenter{
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterImpl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void reset() {
        iLoginView.onReset();
    }

    @Override
    public void login(String name, String passwd) {
        Boolean isLoginSuccess = true;

        final int code = user.checkUserValid(name, passwd);
        if (code!=0) isLoginSuccess = false;

        final Boolean result = isLoginSuccess;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);

    }



    @Override
    public void setProgressBarVisiblity(int visiblity){
        iLoginView.onSetProgressBarVisibility(visiblity);
    }


    private void initUser(){
        user = new UserModel("mvp","mvp");
    }
}
