package com.cssweb.walletaphone.login.view;

/**
 * Created by chenh on 2015/11/22.
 */
public interface ILoginView {
    public void onReset();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
}
