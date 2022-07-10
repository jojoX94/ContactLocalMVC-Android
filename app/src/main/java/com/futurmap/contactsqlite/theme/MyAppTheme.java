package com.futurmap.contactsqlite.theme;

import android.content.Context;

import com.dolatkia.animatedThemeManager.AppTheme;

import org.jetbrains.annotations.NotNull;

public interface MyAppTheme extends AppTheme {

    int primaryBackground(@NotNull Context context);

    int addImageBg(@NotNull Context context);

    int bgInput(@NotNull Context context);

    int btnText(@NotNull Context context);

    int primaryText(@NotNull Context context);

    int secondaryText(@NotNull Context context);

    int othersText(@NotNull Context context);

    int primaryBtnBackground(@NotNull Context context);

    int primaryIcon(@NotNull Context context);
}