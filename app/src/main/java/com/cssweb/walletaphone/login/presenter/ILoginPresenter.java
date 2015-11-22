package com.cssweb.walletaphone.login.presenter;

/**
 * Created by chenh on 2015/11/22.
 */
public interface ILoginPresenter {
    void reset();
    void login(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
