package com.apsmarket.robiinternetoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Robi_Recharge extends AppCompatActivity {
    private AdView mAdView;
    private AdView mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robi_recharge);
        getSupportActionBar().setTitle("রবি রিচার্জ অফার:");
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
            public void onAdClosed() {
                super.onAdClosed();
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        });
        mAdView2 = findViewById(R.id.adView2);
        AdRequest request = new AdRequest.Builder().build();
        mAdView2.loadAd(request);
        mAdView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mAdView2.loadAd(new AdRequest.Builder().build());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Robi.interstitialAd.showAd();
    }

    public void flexiload(View view) {
        startActivity(new Intent(getApplicationContext(),Flexiload.class));
        if (Robi.mInterstitialAd != null) {
            Robi.mInterstitialAd.show(Robi_Recharge.this);
        }else {Robi.interstitialAd.showAd();}
    }
}