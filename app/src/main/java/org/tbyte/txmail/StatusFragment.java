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

    public ToggleButton.OnCheckedChangeListener syncButtonListener() {
        return new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked && configurationMissing(buttonView.getContext())) {
                    Toast.makeText(buttonView.getContext(),
                            "Please configure SMTP settings first",
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

    private boolean configurationMissing(Context c) {
        return TxConfig.get(c).getString("smtp_host", "").isEmpty() ||
               TxConfig.get(c).getString("smtp_port", "").isEmpty() ||
               (TxConfig.get(c).getBoolean("smtp_auth", false) && TxConfig.get(c).getString("smtp_user", "").isEmpty());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
