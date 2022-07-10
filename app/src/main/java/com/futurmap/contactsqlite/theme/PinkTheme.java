package com.futurmap.contactsqlite.theme;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.futurmap.contactsqlite.R;

public class PinkTheme implements MyAppTheme {
    @Override
    public int id() {
        return 1;
    }

    @Override
    public int primaryBackground(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.white);
    }

    @Override
    public int addImageBg(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.pinkLight);
    }

    @Override
    public int bgInput(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.white);
    }

    @Override
    public int btnText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.white);
    }


    @Override
    public int primaryText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.primaryPinkText);
    }

    @Override
    public int secondaryText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.greyLight);
    }

    @Override
    public int othersText(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.pink);
    }

    @Override
    public int primaryBtnBackground(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.pink);
    }

    @Override
    public int primaryIcon(@NonNull Context context) {
        return ContextCompat.getColor(context, R.color.pink);
    }
}

