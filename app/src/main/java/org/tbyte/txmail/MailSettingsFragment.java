package org.tbyte.txmail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_tx_mail_mail, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
