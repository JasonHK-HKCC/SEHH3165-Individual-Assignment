package app.jasonhk.hkcc.bnz;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import android.util.Log;
import android.widget.TextView;

import lombok.val;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener
{
    private final String NAME_KEY     = "user_name";
    private final String HEIGHT_KEY   = "user_height";
    private final String WEIGHT_KEY   = "user_weight";
    private final String BIRTHDAY_KEY = "user_birthday";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences_user, rootKey);

        EditTextPreference name = findPreference(NAME_KEY);
        if (name != null) { name.setOnBindEditTextListener(TextView::setSingleLine); }

        SeekBarPreference height = findPreference(WEIGHT_KEY);
        if (height != null)
        {
            height.setShowSeekBarValue(true);
        }

        SeekBarPreference weight = findPreference(HEIGHT_KEY);
        if (weight != null)
        {
            weight.setShowSeekBarValue(true);
        }

        val birthday = findPreference(BIRTHDAY_KEY);
        if (birthday != null) { birthday.setOnPreferenceClickListener(this); }
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        if (!preference.hasKey()) { return false; }

        Log.d("SettingsFragment", preference.getKey());
        if (preference.getKey().equals(BIRTHDAY_KEY))
        {
            val preferences = preference.getSharedPreferences();


            val picker = new DatePickerDialog(getContext());
//            picker.setOnDateSetListener();
            picker.show();
            return true;
        }

        return false;
    }

    static class HeightSummaryProvider implements Preference.SummaryProvider<SeekBarPreference>
    {

        @Override
        public CharSequence provideSummary(SeekBarPreference preference)
        {
//            preference.getValue();
            return null;
        }
    }
}