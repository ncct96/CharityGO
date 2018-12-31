package org.charitygo;


import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.charitygo.activity.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment {

    final SwitchPreference reminderSwitch =  (SwitchPreference) findPreference("reminder");

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_setting);

        reminderSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(reminderSwitch.isChecked()){
                    Toast.makeText(getContext(), "Reminder Off", Toast.LENGTH_SHORT);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("reminder");

                }else{
                    Toast.makeText(getContext(), "Reminder On", Toast.LENGTH_SHORT);
                    FirebaseMessaging.getInstance().subscribeToTopic("reminder");
                }
                return false;
            }
        });

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey);

    }


}
