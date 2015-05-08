package org.tbyte.txmail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MailFragment extends Fragment {

    public static MailFragment newInstance() {
        MailFragment fragment = new MailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tx_mail_mail, container, false);

        initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_host), "mail_host");
        initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_port), "mail_port");
        initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_user), "mail_user");
        initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_pass), "mail_pass");

        initialiseCheckBox(((CheckBox) rootView.findViewById(R.id.mail_check_auth)), "mail_auth");
        initialiseCheckBox(((CheckBox) rootView.findViewById(R.id.mail_check_ssl)), "mail_ssl");

        return rootView;
    }

    private void initialiseEditText(EditText editText, String configKey) {
        editText.setText(TxConfig.get(editText.getContext()).getString(configKey, ""));
        editText.setOnFocusChangeListener(getEditTextOnFocusChangeListener(configKey));
    }

    private void initialiseCheckBox(CheckBox checkBox, String configKey) {
        checkBox.setChecked(TxConfig.get(checkBox.getContext()).getBoolean(configKey, false));
        checkBox.setOnCheckedChangeListener(getCheckBoxOnCheckedChangeListener(configKey));
    }

    private View.OnFocusChangeListener getEditTextOnFocusChangeListener(final String config_key) {
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

    private CheckBox.OnCheckedChangeListener getCheckBoxOnCheckedChangeListener(final String config_key) {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
