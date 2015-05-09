package org.tbyte.txmail;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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

    public static void initialiseEditText(EditText editText, String configKey) {
        editText.setText(TxConfig.get(editText.getContext()).getString(configKey, ""));
        editText.setOnFocusChangeListener(getEditTextOnFocusChangeListener(configKey));
    }

    public static void initialiseCheckBox(CheckBox checkBox, String configKey) {
        checkBox.setChecked(TxConfig.get(checkBox.getContext()).getBoolean(configKey, false));
        checkBox.setOnCheckedChangeListener(getCheckBoxOnCheckedChangeListener(configKey));
    }

    private static  View.OnFocusChangeListener getEditTextOnFocusChangeListener(final String config_key) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final EditText editText = (EditText) v;
                if (!editText.getText().toString().equals(TxConfig.get(v.getContext()).getString(config_key, ""))) {
                    TxConfig.set(v.getContext(), new TxConfig.TxConfigSetter() {
                        @Override
                        public void onCallback(SharedPreferences.Editor editor) {
                            editor.putString(config_key, editText.getText().toString());
                        }
                    });
                }
            }
        };
    }

    private static CheckBox.OnCheckedChangeListener getCheckBoxOnCheckedChangeListener(final String config_key) {
        return new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked != TxConfig.get(buttonView.getContext()).getBoolean(config_key, false)) {
                    TxConfig.set(buttonView.getContext(), new TxConfig.TxConfigSetter() {
                        @Override
                        public void onCallback(SharedPreferences.Editor editor) {
                            editor.putBoolean(config_key, isChecked);
                        }
                    });
                }
            }
        };
    }
}
