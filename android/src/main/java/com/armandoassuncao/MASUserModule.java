package com.armandoassuncao;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.ca.mas.foundation.MASUser;
import com.ca.mas.foundation.MASCallback;
import org.json.JSONException;
import com.armandoassuncao.Utils;

public class MASUserModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private static final String E_LOGIN_ERROR = "E_LOGIN_ERROR";
    private static final String E_LOGOUT_ERROR = "E_LOGOUT_ERROR";
    private static final String E_GET_USER_ERROR = "E_GET_USER_ERROR";

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
        MASUser user = MASUser.getCurrentUser();
        if (user == null) {
            promise.resolve(null);
        } else {
            try {
                WritableMap map = Utils.convertJsonToWritableMap(user.getAsJSONObject());
                promise.resolve(map);
            } catch (JSONException e) {
                promise.reject(E_GET_USER_ERROR, e);
            }
        }
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

    @ReactMethod
    public void logout(final Promise promise) {
        if (MASUser.getCurrentUser() == null) {
            promise.resolve(true);
            return;
        }

        MASUser.getCurrentUser().logout(new MASCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                promise.resolve(true);
            }

            @Override
            public void onError(Throwable e) {
                promise.reject(E_LOGOUT_ERROR, e);
            }
        });
    }
}
