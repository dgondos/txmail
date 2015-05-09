package org.tbyte.txmail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class SMTPSettingsFragment extends Fragment {

    public static SMTPSettingsFragment newInstance() {
        SMTPSettingsFragment fragment = new SMTPSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SMTPSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tx_mail_smtp, container, false);

        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.smtp_text_host), "smtp_host");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.smtp_text_port), "smtp_port");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.smtp_text_user), "smtp_user");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.smtp_text_pass), "smtp_pass");

        TxConfig.initialiseCheckBox(((CheckBox) rootView.findViewById(R.id.smtp_check_auth)), "smtp_auth");
        TxConfig.initialiseCheckBox(((CheckBox) rootView.findViewById(R.id.smtp_check_ssl)), "smtp_ssl");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
