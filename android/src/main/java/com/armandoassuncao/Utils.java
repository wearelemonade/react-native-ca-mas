package com.armandoassuncao;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public final class Utils {

    private Utils () {}

    public static WritableMap convertJsonToWritableMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof  JSONArray) {
                map.putArray(key, convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof  Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof  Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof  Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String)  {
                map.putString(key, (String) value);
            } else if (value == null) {
                map.putNull(key);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }

    public static WritableArray convertJsonToWritableArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof  JSONArray) {
                array.pushArray(convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof  Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof  Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof  Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String)  {
                array.pushString((String) value);
            } else if (value != null) {
                array.pushString(value.toString());
            }
        }
        return array;
    }

    public static JSONObject convertReadableMapToJson(ReadableMap readableMap) throws JSONException {
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertReadableMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertReadableArrayToJson(readableMap.getArray(key)));
                    break;
            }
        }
        return object;
    }

    public static JSONArray convertReadableArrayToJson(ReadableArray readableArray) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
                case Boolean:
                    array.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.put(readableArray.getDouble(i));
                    break;
                case String:
                    array.put(readableArray.getString(i));
                    break;
                case Map:
                    array.put(convertReadableMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertReadableArrayToJson(readableArray.getArray(i)));
                    break;
            }
        }
        return array;
    }

    public static WritableMap convertMapToWritableMap(final Map<String, Object> map) throws JSONException {
        WritableMap wMap = new WritableNativeMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                wMap.putMap(key, convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                wMap.putArray(key, convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> valueMap = (Map<String, Object>) value;
                wMap.putMap(key, convertMapToWritableMap(valueMap));
            } else if (value instanceof  List) {
                @SuppressWarnings("unchecked")
                List<Object> valueList = (List<Object>) value;
                wMap.putArray(key, convertListToWritableArray(valueList));
            } else if (value instanceof  Boolean) {
                wMap.putBoolean(key, (Boolean) value);
            } else if (value instanceof  Integer) {
                wMap.putInt(key, (Integer) value);
            } else if (value instanceof  Double) {
                wMap.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                wMap.putString(key, (String) value);
            } else if (value == null) {
                wMap.putNull(key);
            } else {
                wMap.putString(key, value.toString());
            }
        }
        return wMap;
    }

    public static WritableArray convertListToWritableArray(final List<Object> list) throws JSONException {
        WritableArray wArray = new WritableNativeArray();

        for (Object value : list) {
            if (value instanceof JSONObject) {
                wArray.pushMap(convertJsonToWritableMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                wArray.pushArray(convertJsonToWritableArray((JSONArray) value));
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> valueMap = (Map<String, Object>) value;
                wArray.pushMap(convertMapToWritableMap(valueMap));
            } else if (value instanceof  List) {
                @SuppressWarnings("unchecked")
                List<Object> valueList = (List<Object>) value;
                wArray.pushArray(convertListToWritableArray(valueList));
            } else if (value instanceof  Boolean) {
                wArray.pushBoolean((Boolean) value);
            } else if (value instanceof  Integer) {
                wArray.pushInt((Integer) value);
            } else if (value instanceof  Double) {
                wArray.pushDouble((Double) value);
            } else if (value instanceof String)  {
                wArray.pushString((String) value);
            } else if (value != null) {
                wArray.pushString(value.toString());
            }
        }
        return wArray;
    }

}
