package org.charitygo;


import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.charitygo.activity.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragmentCompat {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey);

        final SwitchPreferenceCompat reminderSwitch = (SwitchPreferenceCompat) findPreference("reminder");

        reminderSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(reminderSwitch.isChecked()){
                    reminderSwitch.setEnabled(true);
                    reminderSwitch.setChecked(false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("reminder");
                    Toast.makeText(getActivity(), "Unsubscribed from notification", Toast.LENGTH_LONG).show();
                    Log.e("status", "off");
                }else{
                    reminderSwitch.setEnabled(true);
                    reminderSwitch.setChecked(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("reminder");
                    Toast.makeText(getActivity(), "Subscribed to notification", Toast.LENGTH_LONG).show();
                    Log.e("status", "on");
                }
                return false;
            }
        });
    }

}
