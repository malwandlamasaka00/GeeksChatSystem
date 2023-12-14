package com.example.geekschatsystem.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private final SharedPreferences sharedPreferences;
    public PreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    //Saves a boolean value in the shared preferences using the specified key.
    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }


    // Retrieves a boolean value from the shared preferences using the specified key.
    // If the key is not found, it returns a default value of false.
    public Boolean getBoolean(String key){
        return  sharedPreferences.getBoolean(key, false);
    }


    // Saves a string value in the shared preferences using the specified key.
    public  void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }



    // Retrieves a string value from the shared preferences using the specified key.
    // If the key is not found, it returns null
    public String getString(String key){
        return sharedPreferences.getString(key,null);
    }


    //Clears all the stored preferences.
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}


/*

this is a utility class that helps manage shared preferences.
Shared preferences is a way to store and retrieve small amounts of data persistently.
utility class is a class that contains static methods
 */