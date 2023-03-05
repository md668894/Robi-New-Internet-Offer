package com.apsmarket.robiinternetoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Robi_Minute_offer extends AppCompatActivity {
    private AdView mAdView;
    private  boolean chech = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robi_minute_offer);
        getSupportActionBar().setTitle("রবি মিনিট অফার:");
        getSupportActionBar().setSubtitle("রবি ইন্টারনেট ও মিনিট অফার");


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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdView.loadAd(new AdRequest.Builder().build());
                    }
                }, 10000);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chech = true;
            }
        }, 60000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (chech==true){
            if (Robi.mInterstitialAd != null) {
                Robi.mInterstitialAd.show(Robi_Minute_offer.this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }
}