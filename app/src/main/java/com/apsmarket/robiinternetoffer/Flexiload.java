package com.apsmarket.robiinternetoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Flexiload extends AppCompatActivity {
    private AdView mAdView;
    private  boolean chech = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexiload);
        getSupportActionBar().setTitle("ইন্টারনেট বা মিনিট কিনুন ঘরে বসেই:");
        getSupportActionBar().setSubtitle("রবি ইন্টারনেট ও মিনিট অফার");
        TextView textView = findViewById(R.id.mobile_banking);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


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
                Robi.mInterstitialAd.show(Flexiload.this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }

    public void call_us(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:01813420461"));
        startActivity(intent);
    }
}