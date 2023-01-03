package com.apsmarket.robiinternetoffer.service;

import android.content.Context;
import android.os.Handler;

import com.apsmarket.robiinternetoffer.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Service {
    public static InterstitialAd mInterstitialAd;

    public static void I_ADS(Context context){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context.getApplicationContext(), context.getString(R.string.i_ads), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        mInterstitialAd = null;
                        Service.I_ADS(context.getApplicationContext());
                    }
                });
            }
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                mInterstitialAd = null;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Service.I_ADS(context.getApplicationContext());
                    }
                }, 10000);
            }
        });
    }
}