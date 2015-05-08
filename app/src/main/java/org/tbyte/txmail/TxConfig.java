package org.tbyte.txmail;

import android.content.Context;
import android.content.SharedPreferences;

public class TxConfig {
    public static final String PREFS_NAME = "TxMailPrefs";

    interface TxConfigSetter {
        void onCallback(SharedPreferences.Editor editor);
    }

    public static SharedPreferences get(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0);
    }

    public static void set(Context c, TxConfigSetter setterCallback) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        setterCallback.onCallback(editor);

        editor.commit();
    }

}
