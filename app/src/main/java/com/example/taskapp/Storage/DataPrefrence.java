package com.example.taskapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPrefrence {
    public static final String USERINFO="USERINFO";
    public static final String OFFLINEDATA="OFFLINEDATA";


    Context context;
    public DataPrefrence(Context context)
    {
        this.context=context;
    }
    public String GETOFFLINEDATA()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFO,Context.MODE_PRIVATE);
        return sharedPreferences.getString(OFFLINEDATA,"");
    }
    public void SETOFFLINEDATA( String OFFLINEDATAS)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(OFFLINEDATA,OFFLINEDATAS);
        editor.apply();
    }

}
