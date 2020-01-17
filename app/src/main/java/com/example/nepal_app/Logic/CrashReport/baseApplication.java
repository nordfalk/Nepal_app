package com.example.nepal_app.Logic.CrashReport;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.example.nepal_app.BuildConfig;
import com.example.nepal_app.R;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraHttpSender;
import org.acra.annotation.AcraToast;
import org.acra.data.StringFormat;
import org.acra.sender.HttpSender;

//Crash reporting to http domain, change this to your http, using ACRA 3rd part software.
@AcraCore(buildConfigClass = BuildConfig.class,
            reportFormat = StringFormat.JSON)
@AcraHttpSender(uri = "https://yourdomain.com/acra/report",
        httpMethod = HttpSender.Method.POST,
        basicAuthLogin = "*****",
        basicAuthPassword = "*****")
@AcraToast(resText = R.string.resToastText)
public class baseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        ACRA.init(this);
    }

}
