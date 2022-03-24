package app.jasonhk.hkcc.bnz;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener
{
    private final String BIRTHDAY_KEY = "user_birthday";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        findPreference(BIRTHDAY_KEY).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        if (!preference.hasKey()) { return false; }

        Log.d("SettingsFragment", preference.getKey());
        if (preference.getKey().equals(BIRTHDAY_KEY))
        {
            DatePickerDialog picker = new DatePickerDialog(getContext());
            picker.show();
            return false;
        }

        return false;
    }
}