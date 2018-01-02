package com.kw2.kw2.gradec;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by SAMSUNG on 2017-12-29.
 */

public class Tip extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        AdView mAdView = (AdView) findViewById(R.id.tip_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
