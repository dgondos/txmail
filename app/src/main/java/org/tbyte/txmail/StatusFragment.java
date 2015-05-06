package org.tbyte.txmail;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by dgondos on 2/05/15.
 */
public class StatusFragment extends Fragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
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
        View rootView = inflater.inflate(R.layout.fragment_tx_mail, container, false);
        EditText statusLog = (EditText) rootView.findViewById(R.id.statusLog);
        statusLog.setText("This is view status");
        Button startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(startButtonClickListener());
        return rootView;
    }

    public View.OnClickListener startButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmMgr = (AlarmManager)v.getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(v.getContext(), SyncService.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000 * 5, alarmIntent);
                Log.d("txmail sync", "alarm registered");

                v.setOnClickListener(stopButtonClickListener());
                Button thisButton = (Button)v;
                thisButton.setText(R.string.action_stop);
            }
        };
    }

    public View.OnClickListener stopButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SyncService.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent, 0);
                AlarmManager alarmMgr = (AlarmManager)v.getContext().getSystemService(Context.ALARM_SERVICE);

                alarmMgr.cancel(alarmIntent);

                Log.d("txmail sync", "alarm cancelled");

                v.setOnClickListener(startButtonClickListener());
                Button thisButton = (Button)v;
                thisButton.setText(R.string.action_start);
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
