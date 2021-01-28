package net.mikaelzero.router;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MikaelZero
 * @CreateDate: 1/28/21 2:12 PM
 * @Description:
 */
public class UriHandler {
    private static final Map<String, String> mMap = new HashMap<>();

    public void register(String path, String handler) {
        String pathHandler = mMap.get(path);
        if (pathHandler == null) {
            mMap.put(path, handler);
        }
    }

    public static Intent createIntent(Context context, @NonNull String path) {
        return new Intent().setClassName(context, mMap.get(path));
    }
}
