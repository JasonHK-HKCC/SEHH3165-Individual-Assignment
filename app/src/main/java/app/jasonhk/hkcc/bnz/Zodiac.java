package app.jasonhk.hkcc.bnz;

import java.time.LocalDate;
import java.time.MonthDay;

import lombok.Getter;
import lombok.NonNull;

enum Zodiac
{
    ARIES("--03-21", "--04-19", R.drawable.ic_zodiac_aries, R.string.zodiac_aries),
    TAURUS("--04-20", "--05-20", R.drawable.ic_zodiac_taurus, R.string.zodiac_taurus),
    GEMINI("--05-21", "--06-21", R.drawable.ic_zodiac_gemini, R.string.zodiac_gemini),
    CANCER("--06-22", "--07-22", R.drawable.ic_zodiac_cancer, R.string.zodiac_cancer),
    LEO("--07-23", "--08-22", R.drawable.ic_zodiac_leo, R.string.zodiac_leo),
    VIRGO("--08-23", "--09-22", R.drawable.ic_zodiac_virgo, R.string.zodiac_virgo),
    LIBRA("--09-23", "--10-22", R.drawable.ic_zodiac_libra, R.string.zodiac_libra),
    SCORPIUS("--10-23", "--11-22", R.drawable.ic_zodiac_scorpius, R.string.zodiac_scorpius),
    SAGITTARIUS("--11-23", "--12-21", R.drawable.ic_zodiac_sagittarius,
                R.string.zodiac_sagittarius),
    CAPRICORNUS("--12-22", "--01-19", R.drawable.ic_zodiac_capricornus,
                R.string.zodiac_capricornus),
    AQUARIUS("--01-20", "--02-18", R.drawable.ic_zodiac_aquarius, R.string.zodiac_aquarius),
    PISCES("--02-19", "--03-20", R.drawable.ic_zodiac_pisces, R.string.zodiac_pisces);

    @Getter
    private final MonthDay begin;

    @Getter
    private final MonthDay until;

    @Getter
    private final int icon;

    @Getter
    private final int name;

    Zodiac(@NonNull CharSequence begin, @NonNull CharSequence until, int sign, int name)
    {
        this(MonthDay.parse(begin), MonthDay.parse(until), sign, name);
    }

    Zodiac(MonthDay begin, MonthDay until, int sign, int name)
    {
        this.begin = begin;
        this.until = until;
        this.icon = sign;
        this.name = name;
    }

    static Zodiac from(LocalDate date)
    {
        return from(MonthDay.from(date));
    }

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
