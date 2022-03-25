package app.jasonhk.hkcc.bnz;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.MonthDay;

import lombok.Getter;
import lombok.NonNull;

enum Zodiac
{
    ARIES("--03-21", "--04-19", R.drawable.ic_zodiac_aries,
          R.string.zodiac_aries, R.string.zodiac_aries_summary),
    TAURUS("--04-20", "--05-20", R.drawable.ic_zodiac_taurus,
           R.string.zodiac_taurus, R.string.zodiac_taurus_summary),
    GEMINI("--05-21", "--06-21", R.drawable.ic_zodiac_gemini,
           R.string.zodiac_gemini, R.string.zodiac_gemini_summary),
    CANCER("--06-22", "--07-22", R.drawable.ic_zodiac_cancer,
           R.string.zodiac_cancer, R.string.zodiac_cancer_summary),
    LEO("--07-23", "--08-22", R.drawable.ic_zodiac_leo,
        R.string.zodiac_leo, R.string.zodiac_leo_summary),
    VIRGO("--08-23", "--09-22", R.drawable.ic_zodiac_virgo,
          R.string.zodiac_virgo, R.string.zodiac_virgo_summary),
    LIBRA("--09-23", "--10-22", R.drawable.ic_zodiac_libra,
          R.string.zodiac_libra, R.string.zodiac_libra_summary),
    SCORPIUS("--10-23", "--11-22", R.drawable.ic_zodiac_scorpius,
             R.string.zodiac_scorpius, R.string.zodiac_scorpius_summary),
    SAGITTARIUS("--11-23", "--12-21", R.drawable.ic_zodiac_sagittarius,
                R.string.zodiac_sagittarius, R.string.zodiac_sagittarius_summary),
    CAPRICORNUS("--12-22", "--01-19", R.drawable.ic_zodiac_capricornus,
                R.string.zodiac_capricornus, R.string.zodiac_capricornus_summary),
    AQUARIUS("--01-20", "--02-18", R.drawable.ic_zodiac_aquarius,
             R.string.zodiac_aquarius, R.string.zodiac_aquarius_summary),
    PISCES("--02-19", "--03-20", R.drawable.ic_zodiac_pisces,
           R.string.zodiac_pisces, R.string.zodiac_pisces_summary);

    @Getter
    private final MonthDay begin;

    @Getter
    private final MonthDay until;

    @Getter
    private final int icon;

    @Getter
    private final int name;

    @Getter
    private final int summary;

    Zodiac(
            @NonNull CharSequence begin,
            @NonNull CharSequence until,
            int sign,
            int name,
            int summary)
    {
        this(MonthDay.parse(begin), MonthDay.parse(until), sign, name, summary);
    }

    Zodiac(@NonNull MonthDay begin, @NonNull MonthDay until, int sign, int name, int summary)
    {
        this.begin = begin;
        this.until = until;
        this.icon = sign;
        this.name = name;
        this.summary = summary;
    }

    @Nullable
    static Zodiac from(LocalDate date)
    {
        return from(MonthDay.from(date));
    }

    @Nullable
    static Zodiac from(MonthDay date)
    {
        if (date == null) { return null; }

        if (date.isBefore(AQUARIUS.begin))
        {
            return CAPRICORNUS;
        }
        else if (date.isBefore(PISCES.begin))
        {
            return AQUARIUS;
        }
        else if (date.isBefore(ARIES.begin))
        {
            return PISCES;
        }
        else if (date.isBefore(TAURUS.begin))
        {
            return ARIES;
        }
        else if (date.isBefore(GEMINI.begin))
        {
            return TAURUS;
        }
        else if (date.isBefore(CANCER.begin))
        {
            return GEMINI;
        }
        else if (date.isBefore(LEO.begin))
        {
            return CANCER;
        }
        else if (date.isBefore(VIRGO.begin))
        {
            return LEO;
        }
        else if (date.isBefore(LIBRA.begin))
        {
            return VIRGO;
        }
        else if (date.isBefore(SCORPIUS.begin))
        {
            return LIBRA;
        }
        else if (date.isBefore(SAGITTARIUS.begin))
        {
            return SCORPIUS;
        }
        else if (date.isBefore(CAPRICORNUS.begin))
        {
            return SAGITTARIUS;
        }
        else
        {
            return CAPRICORNUS;
        }
    }
}
