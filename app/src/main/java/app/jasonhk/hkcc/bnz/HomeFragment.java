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
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import lombok.val;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceClickListener, DatePickerDialog.OnDateSetListener
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
        if (birthday != null)
        {
            birthday.setSummaryProvider(new DatePickerSummaryProvider());
            birthday.setOnPreferenceClickListener(this);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        if (!preference.hasKey()) { return false; }

        if (preference.getKey().equals(BIRTHDAY_KEY))
        {
            val preferences = preference.getSharedPreferences();
            val value       = preferences.getString(preference.getKey(), null);

            DatePickerDialog dialog;
            if (value == null)
            {
                dialog = new DatePickerDialog(getContext());
                dialog.setOnDateSetListener(this);
            }
            else
            {
                val date = LocalDate.parse(value);
                dialog = new DatePickerDialog(
                        getContext(), this,
                        date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
            }

            dialog.show();
            return true;
        }

        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
    {
        val date = LocalDate.of(year, month + 1, dayOfMonth);

        val birthday = findPreference(BIRTHDAY_KEY);
        birthday.setSummaryProvider(null);
        birthday.setSummary(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        birthday.getSharedPreferences().edit().putString(BIRTHDAY_KEY, date.toString()).apply();
    }

    static class DatePickerSummaryProvider implements Preference.SummaryProvider<Preference>
    {
        private final DateTimeFormatter formatter;

        public DatePickerSummaryProvider()
        {
            this(FormatStyle.MEDIUM);
        }

        public DatePickerSummaryProvider(FormatStyle style)
        {
            this(DateTimeFormatter.ofLocalizedDate(style));
        }

        public DatePickerSummaryProvider(DateTimeFormatter formatter)
        {
            this.formatter = formatter;
        }

        @Override
        public CharSequence provideSummary(Preference preference)
        {
            val value = preference.getSharedPreferences().getString(preference.getKey(), null);
            if (value != null)
            {
                return LocalDate.parse(value).format(formatter);
            }
            else
            {
                return "Not set";
            }
        }
    }
}