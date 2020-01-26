package com.hw.photomovie.aman.activityAnim;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hw.photomovie.PhotoMovie;
import com.hw.photomovie.PhotoMoviePlayer;
import com.hw.photomovie.model.PhotoSource;
import com.hw.photomovie.aman.R;
import com.hw.photomovie.segment.MovieSegment;
import com.hw.photomovie.util.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwei on 2015/7/1.
 */
public class AnimActivity extends Activity{

    private TextView mTextView;

    InterstitialAd interstitialAd;
    AdRequest adRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        setContentView(layout);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3136435737091654~2057566095");
        interstitialAd = new InterstitialAd(getApplicationContext());
        adRequests = new AdRequest.Builder().build();
        interstitialAd.setAdUnitId("ca-app-pub-3136435737091654/1059979507");

        mTextView = new TextView(this);
        mTextView.setText("testtesttest");
        mTextView.setTextColor(Color.BLUE);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTextView.setTag("text");
        mTextView.setGravity(Gravity.CENTER);
        layout.addView(mTextView,FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);

        List<MovieSegment<Activity>> segments = new ArrayList<MovieSegment<Activity>>();
        segments.add(new ActivityAnimSegment().setDuration(15000));
        PhotoMovie<Activity> photoMovie = new PhotoMovie<Activity>(new PhotoSource(null),segments);

        final PhotoMoviePlayer photoMoviePlayer = new PhotoMoviePlayer(getApplicationContext());
        photoMoviePlayer.setMovieRenderer(new ActivityMovieRenderer().setPainter(this));
        photoMoviePlayer.setMusic(getResources().openRawResourceFd(R.raw.bg).getFileDescriptor());
        photoMoviePlayer.setDataSource(photoMovie);
        photoMoviePlayer.setOnPreparedListener(new PhotoMoviePlayer.OnPreparedListener() {
            @Override
            public void onPreparing(PhotoMoviePlayer moviePlayer, float progress) {
                MLog.i("onPrepare", "" + progress);
                startAds();
            }

            @Override
            public void onPrepared(PhotoMoviePlayer moviePlayer, int prepared, int total) {
                MLog.i("onPrepare", "prepared:" + prepared + " total:" + total);
                photoMoviePlayer.start();
            }

            @Override
            public void onError(PhotoMoviePlayer moviePlayer) {
                MLog.i("onPrepare", "onPrepare error");
            }
        });
        photoMoviePlayer.prepare();
    }
    public void startAds(){
        interstitialAd.loadAd(adRequests);

        interstitialAd.show();

    }
}
