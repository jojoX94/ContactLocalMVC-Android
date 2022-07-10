package com.futurmap.contactsqlite.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dolatkia.animatedThemeManager.AppTheme;
import com.dolatkia.animatedThemeManager.ThemeActivity;
import com.dolatkia.animatedThemeManager.ThemeManager;
import com.futurmap.contactsqlite.R;
import com.futurmap.contactsqlite.adapter.ContactAdapter;
import com.futurmap.contactsqlite.database.UserContactDatabase;
import com.futurmap.contactsqlite.databinding.ActivityRootBinding;
import com.futurmap.contactsqlite.model.UserContact;
import com.futurmap.contactsqlite.theme.DarkTheme;
import com.futurmap.contactsqlite.theme.PinkTheme;
import com.futurmap.contactsqlite.utils.LanguageConfig;
import com.futurmap.contactsqlite.utils.SharedPrefs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class RootActivity extends ThemeActivity {
    public static ContactAdapter adapter;
    public static List<UserContact> contactList;
    private final CharSequence[] langOptions = {"Anglais", "Francais"};
    private final CharSequence[] themeOptions = {"Dark", "Pink"};
    ActivityRootBinding binding;
    SharedPrefs sharedPreferences;
    NavHostFragment navHostFragment;
    boolean isShowBottomDialog = false;
    List<UserContact> list = new ArrayList<>();
    UserContactDatabase db;

    @Override
    protected void attachBaseContext(Context newBase) {
        sharedPreferences = new SharedPrefs(newBase);
        String languageCode = sharedPreferences.getLocale();
        Context context = LanguageConfig.changeLanguage(newBase, languageCode);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRootBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        binding.myRecylerDrawer.setLayoutManager(new LinearLayoutManager(this));
        binding.myRecylerDrawer.setHasFixedSize(true);
        adapter = new ContactAdapter(getApplicationContext(), contactList, new ContactAdapter.DetailsAdapterListener() {
            @Override
            public void delete(View v, int position, UserContact userContact) {
            }

            @Override
            public void handleCall(View v, int position, UserContact userContact) {
                dialPhoneNumber(userContact.getPhoneNumber());
            }

            @Override
            public void handleMessage(View v, int position, UserContact userContact) {
//                composeMmsMessage(userContact.getPhoneNumber());
                Log.i("array", "=>" + position);
            }

            @Override
            public boolean longClick(View v, int position) {

                binding.actionsExtras.setVisibility(View.VISIBLE);
                binding.btnAnnul.setVisibility(View.VISIBLE);
                binding.btnAnnul.setOnClickListener(vi -> {
                    binding.actionsExtras.setVisibility(View.GONE);
                    binding.btnAnnul.setVisibility(View.GONE);
                });
                binding.btnDelete.setOnClickListener(vi -> {
                    for (UserContact item : list) {
                        contactList.remove(item);
                        db.deleteContact(item);
                        adapter.notifyDataSetChanged();
                    }
                });
                return false;
            }


            @Override
            public void checkChanged(boolean b, int position, UserContact userContact) {
                if (b) list.add(userContact);
                else list.remove(userContact);
                Log.i("check", position + "" + b);
            }
        });
        binding.myRecylerDrawer.setAdapter(adapter);
        searchView();

    }


    public void init() {
        db = new UserContactDatabase(getApplicationContext());
        contactList = new ArrayList<>();
        contactList.addAll(db.getAllContacts());
        setSupportActionBar(binding.toolbarRoot);
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this, binding.drawerLayout, binding.toolbarRoot, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.setting_lang:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Langage");
                builder.setItems(langOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (langOptions[which].equals("Anglais")) {
                            sharedPreferences.setLocale("en");

                        } else if (langOptions[which].equals("Francais")) {
                            sharedPreferences.setLocale("fr");
                        } else {
                            dialog.dismiss();
                        }
                        RootActivity.this.recreate();
                    }
                });
                builder.show();
                break;
            case R.id.setting_theme:
                AlertDialog.Builder builderTheme = new AlertDialog.Builder(this);
                builderTheme.setTitle("Select Theme");
                builderTheme.setItems(themeOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (themeOptions[which].equals("Dark")) {
                            ThemeManager.Companion.getInstance().changeTheme(new DarkTheme(), binding.toolbarRoot, 600);
                            sharedPreferences.setTheme("dark");
                        } else if (themeOptions[which].equals("Pink")) {
                            ThemeManager.Companion.getInstance().changeTheme(new PinkTheme(), binding.toolbarRoot, 600);
                            sharedPreferences.setTheme("pink");

                        } else {
                            dialog.dismiss();
                        }
                        RootActivity.this.recreate();
                    }
                });
                builderTheme.show();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchView() {
        binding.drawerSearch.setSubmitButtonEnabled(true);
        binding.drawerSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    Log.i("search", s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null) {
                    Log.i("search", s);
//                    viewModel.searchQuery("jim");
                }
                return true;
            }
        });
    }


    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(@Nullable View view, @NonNull Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
//        if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
//        }
    }

    public void composeMmsMessage(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phoneNumber));
//        if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
//        }
    }

    @NonNull
    @Override
    public AppTheme getStartTheme() {

        sharedPreferences = new SharedPrefs(getApplicationContext());
        String theme = sharedPreferences.getTheme();
        if (theme.equals("pink"))
            return new PinkTheme();
        else
            return new DarkTheme();
    }

    @Override
    public void syncTheme(@NonNull AppTheme appTheme) {
    }
}