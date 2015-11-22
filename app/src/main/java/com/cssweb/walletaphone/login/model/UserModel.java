package com.cssweb.walletaphone.login.model;

/**
 * Created by chenh on 2015/11/22.
 */
public class UserModel implements IUser{
    String username;
    String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValid(String username, String password){
        if (username==null || password==null || !username.equals(getUsername()) || !password.equals(getPassword())){
            return -1;
        }
        return 0;
    }
}
