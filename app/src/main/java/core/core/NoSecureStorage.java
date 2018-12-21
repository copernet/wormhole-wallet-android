package core.core;

import com.blankj.utilcode.util.LogUtils;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.IOException;

import core.util.Files;

public class NoSecureStorage {
    private static NoSecureStorage sNoSecureStorage = null;

    private final Integer FINAL_SEED_VERSION = new Integer(17);

    private byte[] raw;
    private String path;
    private boolean modified = false;
    JSONObject data = new JSONObject();

    private NoSecureStorage(String path) throws IOException {
        this.path = path;
        if (Files.isExist(path)) {
            raw = Files.read(path);
            loadData(raw);
        } else {
            put("seed_version", FINAL_SEED_VERSION);
        }
        write();
    }

    public static NoSecureStorage getInstance(String path) throws IOException {
        if (null != sNoSecureStorage && sNoSecureStorage.getPath().equals(path)) {
            return sNoSecureStorage;
        } else {
            return sNoSecureStorage = new NoSecureStorage(path);
        }

    }

    public String getPath() {
        return path;
    }

    public JSONObject get(String key, JSONObject defaultValue) {
        JSONObject v = data.optJSONObject(key);
        if (v != null) {
            return v;
        }
        return defaultValue;
    }

    public JSONArray get(String key, JSONArray defaultValue) {
        JSONArray v = data.optJSONArray(key);
        if (v != null) {
            return v;
        }
        return defaultValue;
    }

    public boolean get(String key, boolean defaultValue) {
        return data.optBoolean(key, defaultValue);
    }

    public boolean has(String key) {
        return data.has(key);
    }

    public String get(String key, String defaultValue) {
        return data.optString(key, defaultValue);
    }

    public void put(String key, Integer value) {
        try {

            if (value != null) {
                if (data.has(key)) {
                    if (!value.equals(data.get(key))) {
                        modified = true;
                        data.put(key, value);
                    }
                } else {
                    modified = true;
                    data.put(key, value);
                }
            } else if (data.has(key)) {
                modified = true;
                data.remove(key);
            }
        } catch (JSONException e) {
            LogUtils.d("Can not save json");
        }

    }

    public void put(String key, String value) {
        try {

            if (value != null) {
                if (data.has(key)) {
                    if (!value.equals(data.get(key))) {
                        modified = true;
                        data.put(key, value);
                    }
                } else {
                    modified = true;
                    data.put(key, value);
                }
            } else if (data.has(key)) {
                modified = true;
                data.remove(key);
            }
        } catch (JSONException e) {
            LogUtils.d("Can not save json");
        }

    }

    public void put(String key, JSONObject value) {
        try {

            if (value != null) {

                if (data.has(key)) {
                    modified = true;
                    if (!value.equals(data.get(key))) {
                        modified = true;
                        data.put(key, value);
                    }
                } else {
                    modified = true;
                    data.put(key, value);
                }
            } else if (data.has(key)) {
                modified = true;
                data.remove(key);
            }
        } catch (JSONException e) {
            LogUtils.d("Can not save json");
        }

    }

    private void loadData(byte[] rawByte) {
        if (null == rawByte) return;
        try {
            if (rawByte.length == 0) {
                data = new JSONObject();
            } else {
                data = new JSONObject(new String(rawByte));
            }
            String wallet_type = data.optString("wallet_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void write() {
        if (!modified) {
            return;
        }
        Files.write(data, path);
        modified = false;
    }


}
