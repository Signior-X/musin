package com.example.musin;

import android.content.SharedPreferences;
import android.content.Context;

public class PrefManager {
    SharedPreferences pref;  // Shared Preferences
    SharedPreferences.Editor editor; //Shared Preferences editor

    //Context for handling where it is called
    Context _context;

    //shared_pref_mode  ??
    int PRIVATE_MODE = 0;

    //shared_preferences_file_name
    private static final String PREF_NAME = "android-priyam-musin";    //Changed this according to mine

    //For checking if it is first time launch
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    // Constructor
    public PrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);  // Shared Prefernces Manager
        editor = pref.edit(); //Editor
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

}
