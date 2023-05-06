package com.mobileviw.robiinternetoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.onesignal.OneSignal;

public class Robi extends AppCompatActivity {
    private AdView mAdView;
    public static InterstitialAd mInterstitialAd;

    private AppUpdateManager appUpdateManager;
    private static int Update_Code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robi);
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("633d0bd2-44a5-4124-8392-8616904da29a");
        getSupportActionBar().setTitle("রবি ইন্টারনেট ও মিনিট অফার");

        AudienceNetworkAds.initialize(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mAdView.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        });
        I_ADS();
        inAppUpdate();
    }

    private void inAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE,
                                Robi.this, Update_Code
                        );
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        appUpdateManager.registerListener(installStateUpdatedListener);
    }
    InstallStateUpdatedListener installStateUpdatedListener = installState ->
    {
        if (installState.installStatus() == InstallStatus.DOWNLOADED){
            popUp();
        }
    };

    private void popUp() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),"App UpDate Almost Done",
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setTextColor(Color.parseColor("#F30657"));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Update_Code){
            if (resultCode != RESULT_OK){

            }
        }
    }

    private void I_ADS() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.i_ads), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        mInterstitialAd = null;
                        I_ADS();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        I_ADS();
                    }
                }, 20000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }

    public void robi_code(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Code.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_minute_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Minute_offer.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_internet_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Internet_offer.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_social_pack(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Socal_Pack.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_regular_internet_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Regular_Internet.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_minute_recharge_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Minute_Recharge.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_internet_recharge(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Internet_Recharge.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void robi_recharge(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Recharge.class));
        if (mInterstitialAd != null){
            mInterstitialAd.show(Robi.this);
        }
    }

    public void more(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Mobileviw+Private+Limited"));
        startActivity(intent);
    }

    public void star(View view) {
        try{
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
        }
    }

    public void share(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "রবি সকল ইন্টারনেট ও মিনিট অফার গুলো দেখতে এই অ্যাপটি প্লে স্টোর থেকে ফ্রি ইন্সটল করুন।\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) { //e.toString(); }
        }
    }

    public void privacy(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://mobileviw.com/privacy-policy")));
    }

    private boolean internetConneted() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return  connectivityManager.getActiveNetworkInfo()!=null &&connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    private void warning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Robi.this);
        builder.setTitle("অ্যাপটি ব্যবহার করতে ইন্টারনেট সংযোগ দিন। ");
        builder.setMessage("দয়া করে ইন্টারনেট কানেকশন করুন। তারপর অ্যাপ ওপেন  করুন।")
                .setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNegativeButton("ঠিক আছে", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),Robi.class));
                finish();
            }
        });builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (internetConneted() == true) {
            getApplicationContext();
        } else {
            warning();
        }
    }
}