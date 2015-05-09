package org.tbyte.txmail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MailSettingsFragment extends Fragment {

    public static MailSettingsFragment newInstance() {
        MailSettingsFragment fragment = new MailSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MailSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tx_mail_mail, container, false);

        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_from), "mail_from");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_to), "mail_to");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_subject), "mail_subject");
        TxConfig.initialiseEditText((EditText) rootView.findViewById(R.id.mail_text_body), "mail_body");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
