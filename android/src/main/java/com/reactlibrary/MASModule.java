package com.reactlibrary;

import android.net.Uri;

import com.ca.mas.core.error.TargetApiException;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASRequest.MASRequestBuilder;
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MASModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private static final String E_JSON_PARSE_ERROR = "E_JSON_PARSE_ERROR";
    private static final String E_REQUEST_ERROR = "E_REQUEST_ERROR";

    public MASModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "CaMAS";
    }

    @ReactMethod
    public void debug() {
        MAS.debug();
    }

    @ReactMethod
    public void getState(final Promise promise) {
        promise.resolve(MAS.getState(MAS.getContext()));
    }

    @ReactMethod
    public void isAuthenticationListenerRegistered(final Promise promise) {
        promise.resolve(MAS.isAuthenticationListenerRegistered());
    }

    @ReactMethod
    public void invoke(final String fullPath, final ReadableMap optionsJson, final Promise promise) {
        try {
            final JSONObject optionsObject = Utils.convertReadableMapToJson(optionsJson);

            final Uri pathUri = Uri.parse(fullPath);
            final String path = pathUri.getPath();
            final Set<String> queryNames = pathUri.getQueryParameterNames();

            final Uri.Builder uriBuider = new Uri.Builder().path(path);

            // queries
            for(String queryName : queryNames) {
                final String value = pathUri.getQueryParameter(queryName);
                uriBuider.appendQueryParameter(queryName, value);
            }

            // method
            String method = "";
            if (optionsObject.has("method") && !optionsObject.isNull("method")) {
                method = optionsObject.getString("method");
            }

            // body
            MASRequestBody mASRequestBody = null;
            if (optionsObject.has("body") && !optionsObject.isNull("body")) {
                final JSONObject bodyObject = optionsObject.getJSONObject("body");
                mASRequestBody = MASRequestBody.jsonBody(bodyObject);
            }

            final Uri uri = uriBuider.build();
            MASRequestBuilder requestBuilder = new MASRequest.MASRequestBuilder(uri);

            // header
            JSONObject headerObject = null;
            if (optionsObject.has("header") && !optionsObject.isNull("header")) {
                headerObject = optionsObject.getJSONObject("header");

                final Iterator<String> headerKeysItr = headerObject.keys();
                while (headerKeysItr.hasNext()) {
                    final String key = headerKeysItr.next();
                    final String value = headerObject.getString(key);
                    requestBuilder.header(key, value);
                }
            }

            // Select Method
            switch (method.toUpperCase()) {
                case "GET":
                    requestBuilder = requestBuilder.get();
                    break;
                case "POST":
                    requestBuilder = requestBuilder.post(mASRequestBody);
                    break;
                case "PUT":
                    requestBuilder = requestBuilder.put(mASRequestBody);
                    break;
                case "DELETE":
                    requestBuilder = requestBuilder.delete(mASRequestBody);
                    break;
                default:
                    requestBuilder = requestBuilder.get();
                    break;
            }

            final MASRequest request = requestBuilder.build();

            // make request
            MAS.invoke(request, new MASCallback<MASResponse<JSONObject>>(){
                @Override
                public void onSuccess(MASResponse<JSONObject> result) {
                    final Map headers = result.getHeaders();
                    final int statusCode = result.getResponseCode();
                    final String message = result.getResponseMessage();

                    try {
                        JSONObject contentJson;
                        try {
                            contentJson = result.getBody().getContent();
                        } catch(Exception eJson) {
                            contentJson = null;
                        }

                        final Map<String, Object> response = new HashMap<>();
                        response.put("headers", headers);
                        response.put("status_code", statusCode);
                        response.put("message", message);
                        response.put("body", contentJson);
                        promise.resolve(Utils.convertMapToWritableMap(response));
                    } catch(JSONException eJson) {
                        promise.reject(E_JSON_PARSE_ERROR, eJson);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (e.getCause() instanceof TargetApiException) {
                        final TargetApiException exception = (TargetApiException) e.getCause();
                        final Map headers = exception.getResponse().getHeaders();
                        final int statusCode = exception.getResponse().getResponseCode();
                        final String message = exception.getResponse().getResponseMessage();

                        try {
                            final String rawContent = new String(exception.getResponse().getBody().getRawContent());
                            final JSONObject rawContentJson = new JSONObject(rawContent);

                            final Map<String, Object> response = new HashMap<>();
                            response.put("headers", headers);
                            response.put("status_code", statusCode);
                            response.put("message", message);
                            response.put("body", rawContentJson);
                            promise.resolve(Utils.convertMapToWritableMap(response));
                        } catch(JSONException eJson) {
                            promise.reject(E_JSON_PARSE_ERROR, eJson);
                        }
                    } else {
                        promise.reject(E_REQUEST_ERROR, e);
                    }
                }
            });
        } catch(JSONException e) {
            promise.reject(E_JSON_PARSE_ERROR, e);
        }
    }

}
