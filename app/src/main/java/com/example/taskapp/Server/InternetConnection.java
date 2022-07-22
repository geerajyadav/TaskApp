package com.example.taskapp.Server;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class InternetConnection {
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("https://domain.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}