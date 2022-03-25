package app.jasonhk.hkcc.bnz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;

import lombok.Getter;
import lombok.val;
import lombok.var;

/**
 * The {@link Fragment} for the Zodiac page.
 */
public class ZodiacFragment extends Fragment
{
    public ZodiacFragment() {}

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zodiac, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Log.d("ZodiacFragment", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        val resources   = getResources();
        val preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        var notSetText = "Not set";
        {
            val notSetId = getResources()
                    .getIdentifier("not_set", "string", getContext().getPackageName());
            if (notSetId != 0) { notSetText = getString(notSetId); }
        }

        //<editor-fold desc="User Info">
        val name = preferences.getString("user_name", notSetText);
        ((TextView) view.findViewById(R.id.user_name)).setText(name);

        val brithdayValue = preferences.getString("user_birthday", null);
        val brithday = (brithdayValue != null)
                       ? LocalDate.parse(brithdayValue)
                                  .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                       : notSetText;

        var gender = notSetText;
        {
            val genderType = Gender.fromKey(preferences.getString("user_gender", null));
            if (genderType != null)
            {
                gender = resources.getStringArray(R.array.gender_entries)[genderType.ordinal()];
            }
        }

        TextView userSummaryView = view.findViewById(R.id.user_summary);
        userSummaryView.setText(getString(R.string.user_summary_zodiac, brithday, gender));
        //</editor-fold>

        //<editor-fold desc="Zodiac Info">
        if (brithdayValue != null)
        {
            val zodiac = Zodiac.from(LocalDate.parse(brithdayValue));

            ImageView zodiacSignView = view.findViewById(R.id.zodiac_sign);
            zodiacSignView.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), zodiac.getIcon()));

            TextView zodiacNameView = view.findViewById(R.id.zodiac_name);
            zodiacNameView.setText(getString(zodiac.getName()));

            TextView zodiacDatesView     = view.findViewById(R.id.zodiac_dates);
            val      rangeDatesFormatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
            zodiacDatesView.setText(getString(R.string.date_range,
                                              zodiac.getBegin().format(rangeDatesFormatter),
                                              zodiac.getUntil().format(rangeDatesFormatter)));

            TextView zodiacSummaryView = view.findViewById(R.id.zodiac_summary);
            zodiacSummaryView.setText(zodiac.getSummary());
        }
        //</editor-fold>
    }
}