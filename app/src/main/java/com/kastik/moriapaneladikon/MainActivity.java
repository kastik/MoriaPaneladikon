package com.kastik.moriapaneladikon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InterstitialAdInit();
        logIn();

        final Button baseis = findViewById(R.id.baseisViewAcivityBtn);
        final Button ypEpal = findViewById(R.id.calcEpalActivityBtn);
        final Button ypGel = findViewById(R.id.calcGelActivityBtn);
        final Button themata = findViewById(R.id.themataSearchActivityBtn);
        final Button upload_gel = findViewById(R.id.uploadActivityBtn);

        ypEpal.setOnClickListener(view -> {
            intent = new Intent(this, YpologismosGel.class);
            intent.putExtra("calcEpal", true);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(intent);
            }
        });

        ypGel.setOnClickListener(view -> {
            intent = new Intent(this, YpologismosGel.class);
            intent.putExtra("calcEpal", false);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(intent);
            }
        });

        themata.setOnClickListener(view -> {
            intent = new Intent(this, ThemataSearch.class);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(intent);
            }
        });

        if (getApplicationInfo().packageName.contains(".debug")) {
            upload_gel.setVisibility(View.VISIBLE);
            upload_gel.setOnClickListener(v -> {
                intent = new Intent(this, UploadActivity.class);
                startActivity(intent);
            });
        }

        baseis.setOnClickListener(view -> {
            intent = new Intent(this, BaseisSearch.class);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(intent);
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d("adFailedToLoad", "domain: " + loadAdError.getDomain() + " code: " + loadAdError.getCode() + " message: " + loadAdError.getMessage());
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                startActivity(intent);
            }
        });

    }


    public void InterstitialAdInit() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.InterstitialAdId));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        MobileAds.initialize(this);
    }

    private void logIn() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null && this.getApplicationInfo().packageName.contains(".debug")) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), 1);
        }
    }

}