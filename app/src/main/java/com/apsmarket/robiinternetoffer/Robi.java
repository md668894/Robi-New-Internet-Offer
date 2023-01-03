package com.apsmarket.robiinternetoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.apsmarket.robiinternetoffer.service.Service;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.onesignal.OneSignal;

public class Robi extends AppCompatActivity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robi);
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("312c0a96-63df-4b15-b9ca-01e5b34d9229");

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
            public void onAdClosed() {
                super.onAdClosed();
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void robi_code(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Code.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_minute_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Minute_offer.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_internet_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Internet_offer.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_social_pack(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Socal_Pack.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_regular_internet_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Regular_Internet.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_minute_recharge_offer(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Minute_Recharge.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_internet_recharge(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Internet_Recharge.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void robi_recharge(View view) {
        startActivity(new Intent(getApplicationContext(),Robi_Recharge.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void flexiload(View view) {
        startActivity(new Intent(getApplicationContext(),Flexiload.class));
        if (Service.mInterstitialAd != null) {
            Service.mInterstitialAd.show(Robi.this);
            Service.mInterstitialAd = null;
        }
    }

    public void more(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Aps+Market"));
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

    public void facebook(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/apmarket4")));
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