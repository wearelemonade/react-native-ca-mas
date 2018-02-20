package com.armandoassuncao;

import com.ca.mas.foundation.MASDevice;
import com.ca.mas.foundation.MASCallback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class MASDeviceModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private static final String E_REQUEST_ERROR = "E_REQUEST_ERROR";

    public MASDeviceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "CaMASDevice";
    }

    @ReactMethod
    public void deregister(final Promise promise) {
        MASDevice.getCurrentDevice().deregister(new MASCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                promise.resolve(true);
            }

            @Override
            public void onError(Throwable e) {
                promise.reject(E_REQUEST_ERROR, e);
            }
        });
    }

    @ReactMethod
    public void getIdentifier(final Promise promise) {
        promise.resolve(MASDevice.getCurrentDevice().getIdentifier());
    }

    @ReactMethod
    public void isRegistered(final Promise promise) {
        promise.resolve(MASDevice.getCurrentDevice().isRegistered());
    }

    @ReactMethod
    public void resetLocally() {
        MASDevice.getCurrentDevice().resetLocally();
    }
}
