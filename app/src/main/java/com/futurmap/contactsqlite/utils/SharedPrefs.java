package com.futurmap.contactsqlite.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    String LOCALE_KEY = "locale";
    String setback_locale = "fr";
    String database_name = "database";
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    public SharedPrefs(Context context) {
        this.context = context;
        this.editor = this.context.getSharedPreferences(database_name, Context.MODE_PRIVATE).edit();
        this.preferences = this.context.getSharedPreferences(database_name, Context.MODE_PRIVATE);
    }

    public String getLocale() {
        if (this.preferences.contains(LOCALE_KEY)) {
            return preferences.getString(LOCALE_KEY, "");
        } else {
            return setback_locale;
        }
    }

    public void setLocale(String lang) {
        editor.putString(LOCALE_KEY, lang);
        editor.apply();
    }

    public String getTheme() {
        if (this.preferences.contains("theme")) {
            return preferences.getString("theme", "");
        } else {
            return "pink";
        }
    }

    public void setTheme(String theme) {
        editor.putString("theme", theme);
        editor.apply();
    }
}
