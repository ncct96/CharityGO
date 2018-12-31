package org.charitygo.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import org.charitygo.R;
import org.charitygo.SettingFragment;

import java.util.List;

public class SettingActivity extends PreferenceActivity {

    public static final String KEY_REMINDER = "reminder";

    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.pref_setting, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return SettingFragment.class.getName().equals(fragmentName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
