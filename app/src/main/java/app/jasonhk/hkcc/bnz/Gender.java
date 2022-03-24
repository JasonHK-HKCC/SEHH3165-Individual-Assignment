package app.jasonhk.hkcc.bnz;

import androidx.annotation.Nullable;

enum Gender
{
    MALE("male"),
    FEMALE("female");

    private final String key;

    Gender(String key)
    {
        this.key = key;
    }

    @Nullable
    public static Gender fromKey(String key)
    {
        if (key == null) { return null; }

        for (Gender gender : Gender.values())
        {
            if (gender.key.equals(key))
            {
                return gender;
            }
        }

        return null;
    }
}
