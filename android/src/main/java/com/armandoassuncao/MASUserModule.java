package com.armandoassuncao;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.ca.mas.foundation.MASUser;
import com.ca.mas.foundation.MASCallback;

import com.armandoassuncao.Utils;

public class MASUserModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private static final String E_LOGIN_ERROR = "E_LOGIN_ERROR";

    public MASUserModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "CaMASUser";
    }

    @ReactMethod
    public void getAuthCredentialsType(final Promise promise) {
        promise.resolve(MASUser.getAuthCredentialsType());
    }

    @ReactMethod
    public void getCurrentUser(final Promise promise) {
        promise.resolve(MASUser.getCurrentUser());
    }

    @ReactMethod
    public void login(final String username, final String password, final Promise promise) {
        MASUser.login(username, password.toCharArray(), new MASCallback<MASUser>() {
            @Override
            public void onSuccess(MASUser user) {
                promise.resolve(true);
                //User login successful
            }

            @Override
            public void onError(Throwable e) {
                promise.reject(E_LOGIN_ERROR, e);
                //Handle the error
            }
        });
    }
}
