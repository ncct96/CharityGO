package org.charitygo;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class GoalPreference extends EditTextPreference {
    public GoalPreference(Context context) {
        super(context);
    }


    public GoalPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoalPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedInt(-1));
    }

    @Override
    protected boolean persistString(String value) {
        return persistInt(Integer.valueOf(value));
    }
}
