package com.kw2.kw2.gradec;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by SAMSUNG on 2017-12-29.
 */

public class ViewItem extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_viewitem);

        String recordName = getIntent().getExtras().getString("recordName");
        String major = getIntent().getExtras().getString("majors");
        String subject = getIntent().getExtras().getString("subjects");
        String score = getIntent().getExtras().getString("scores");
        String grade = getIntent().getExtras().getString("grades");
        String allGrade = getIntent().getExtras().getString("allGrade");
        String majorGrade = getIntent().getExtras().getString("majorGrade");
        int allNum = getIntent().getExtras().getInt("allNum");
        int majorNum = getIntent().getExtras().getInt("majorNum");


        TableLayout layout = (TableLayout) findViewById(R.id.viewitem_layout);
        TextView title = (TextView) findViewById(R.id.viewitem_recordName);

        title.setText(recordName);

        String[] majors = major.split("@");
        String[] subjects = subject.split("@");
        String[] scores = score.split("@");
        String[] grades = grade.split("@");

        for(int i=0; i< majors.length; i++){
            TableRow tr = new TableRow(ViewItem.this);
            TableLayout.LayoutParams lp =new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,5,0,5);
            tr.setLayoutParams(lp);
            tr.setBackgroundResource(R.drawable.bottomline);

            TextView majorTv = new TextView(ViewItem.this);
            TextView subjectTv = new TextView(ViewItem.this);
            TextView scoreTv = new TextView(ViewItem.this);
            TextView gradeTv = new TextView(ViewItem.this);

            majorTv.setText(majors[i]);
            majorTv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            majorTv.setGravity(Gravity.CENTER);
            majorTv.setTextColor(Color.BLACK);

            subjectTv.setText(subjects[i]);
            subjectTv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, 2));
            subjectTv.setGravity(Gravity.CENTER);
            subjectTv.setTextColor(Color.BLACK);

            scoreTv.setText(scores[i]);
            scoreTv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            scoreTv.setGravity(Gravity.CENTER);
            scoreTv.setTextColor(Color.BLACK);

            gradeTv.setText(grades[i]);
            gradeTv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            gradeTv.setGravity(Gravity.CENTER);
            gradeTv.setTextColor(Color.BLACK);

            tr.addView(majorTv);
            tr.addView(subjectTv);
            tr.addView(scoreTv);
            tr.addView(gradeTv);

            layout.addView(tr);
        }

        TextView recordText = (TextView) findViewById(R.id.viewitem_recordText);
        recordText.setText("총 평점: " + allGrade + " 전공 평점: " + majorGrade + "\r\n" + "이수학점: " + allNum + " 전공이수: " + majorNum);

        AdView mAdView = (AdView) findViewById(R.id.viewitem_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
