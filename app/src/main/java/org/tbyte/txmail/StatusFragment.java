package org.tbyte.txmail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class StatusFragment extends Fragment {

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public StatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tx_mail_status, container, false);
        ToggleButton sync_button = (ToggleButton) rootView.findViewById(R.id.status_toggle_sync);
        sync_button.setOnCheckedChangeListener(syncButtonListener());
        return rootView;
    }

    private ToggleButton.OnCheckedChangeListener syncButtonListener() {
        return new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked && smtpConfigurationMissing(buttonView.getContext())) {
                    Toast.makeText(buttonView.getContext(),
                            "Please configure SMTP settings first",
                            Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                    return;
                }

                if (isChecked && TxConfig.getString(buttonView.getContext(), "mail_to", "").isEmpty()) {
                    Toast.makeText(buttonView.getContext(),
                                   "Please add a recipient in " + getString(R.string.mail_label_section_title),
                                   Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                    return;
                }

                TxConfig.set(buttonView.getContext(), new TxConfig.TxConfigSetter() {
                    @Override
                    public void onCallback(SharedPreferences.Editor editor) {
                        editor.putBoolean("sync_enabled", isChecked);
                    }
                });
                Toast.makeText(buttonView.getContext(),
                               "Sync " + (isChecked ? "enabled" : "disabled"),
                               Toast.LENGTH_SHORT).show();
            }
        };
    }

    private boolean smtpConfigurationMissing(Context c) {
        return TxConfig.getString(c, "smtp_host", "").isEmpty() ||
               TxConfig.getString(c, "smtp_port", "").isEmpty() ||
               (TxConfig.get(c).getBoolean("smtp_auth", false) && TxConfig.getString(c, "smtp_user", "").isEmpty());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
