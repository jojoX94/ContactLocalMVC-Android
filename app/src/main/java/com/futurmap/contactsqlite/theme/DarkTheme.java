package com.futurmap.contactsqlite.theme;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.futurmap.contactsqlite.R;

public class DarkTheme implements MyAppTheme {
    @Override
    public int id() {
        return 2;
    }

    @Override
    public int primaryBackground(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.blackbg);
    }

    @Override
    public int addImageBg(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.ebonyColor);
    }

    @Override
    public int bgInput(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.bgInputDark);
    }

    @Override
    public int btnText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.blackV);
    }

    @Override
    public int primaryText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.white);
    }

    @Override
    public int secondaryText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.inputDarkText);
    }

    @Override
    public int othersText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.inputDarkText);
    }

    @Override
    public int primaryBtnBackground(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.white);
    }

    @Override
    public int primaryIcon(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.iconDarkTing);
    }
}