package com.cssweb.walletaphone.login.model;

/**
 * Created by chenh on 2015/11/22.
 */
public interface IUser  {

    String getUsername();

    String getPassword();

    int checkUserValid(String uername, String password);
}
