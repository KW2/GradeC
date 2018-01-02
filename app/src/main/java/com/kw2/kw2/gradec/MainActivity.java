package com.kw2.kw2.gradec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CaulyCloseAd closeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        initClose();

        BootstrapButton calBtn = (BootstrapButton) findViewById(R.id.main_calBtn);
        BootstrapButton viewBtn = (BootstrapButton) findViewById(R.id.main_viewBtn);
        BootstrapButton tipBtn = (BootstrapButton) findViewById(R.id.main_tipBtn);
        BootstrapButton endBtn = (BootstrapButton) findViewById(R.id.main_endBtn);

        calBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        tipBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);

        AdView mAdView = (AdView) findViewById(R.id.main_adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2916712541842638~2399853742");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.main_calBtn:
                // 계산 작업
                intent = new Intent(getApplicationContext(), GradeC.class);
                startActivity(intent);
                break;
            case R.id.main_viewBtn:
                // 기록보기 작업
                intent = new Intent(getApplicationContext(), ViewList.class);
                startActivity(intent);
                break;
            case R.id.main_tipBtn:
                // 계산방법 설명
                intent = new Intent(getApplicationContext(), Tip.class);
                startActivity(intent);
                break;
            case R.id.main_endBtn:
                // 어플 종료
                // 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
                if (closeAd.isModuleLoaded()) {
                    // 종료 광고 띄움
                    closeAd.show(this);
                } else {
                    // 광고에 필요한 리소스를 한번만  다운받는데 실패했을 때 앱의 종료팝업 구현
                    showDefaultClosePopup();
                }
                break;
        }
    }

    private void initClose(){
        CaulyAdInfo adInfo = new CaulyAdInfoBuilder("GbBLXUpR").build();       // CaulyAdInfo 생성, "CAULY"에 발급 ID 입력
        closeAd =new CaulyCloseAd();                                        // CaulyCloseAd 생성
        closeAd.setAdInfo(adInfo);                                         // CaulyAdView에 AdInfo 적용
        closeAd.setButtonText("아니요","네");                             // 버튼 텍스트 사용자 지정
        closeAd.setDescriptionText("종료 할까요?");                       // 질문 텍스트 사용자 지정
        // 종료 광고 리스너 작성
        closeAd.setCloseAdListener(new CaulyCloseAdListener() {
            // 종료 광고 수신 시
            @Override
            public void onReceiveCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {}

            // 종료 광고가 보여질 시
            @Override
            public void onShowedCloseAd(CaulyCloseAd caulyCloseAd, boolean b) {}

            // 종료 광고 수신 실패 시
            @Override
            public void onFailedToReceiveCloseAd(CaulyCloseAd caulyCloseAd, int i, String s) {}

            // 종료 광고 왼쪽 버튼 클릭 시
            @Override
            public void onLeftClicked(CaulyCloseAd caulyCloseAd) {}

            // 종료 광고 오른쪽 버튼 클릭 시
            @Override
            public void onRightClicked(CaulyCloseAd caulyCloseAd) { finish(); }

            // 광고 클릭으로 앱을 벗어 날 시
            @Override
            public void onLeaveCloseAd(CaulyCloseAd caulyCloseAd) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(closeAd!=null) closeAd.resume(this);  // 종료 광고 구현 시 반드시!! 호출
    }

    // Back Key가 눌러졌을 때, CloseAd 호출
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {  // Back 키이면
            // 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
            if (closeAd.isModuleLoaded()) {
                // 종료 광고 띄움
                closeAd.show(this);
            } else {
                // 광고에 필요한 리소스를 한번만  다운받는데 실패했을 때 앱의 종료팝업 구현
                showDefaultClosePopup();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 기본 종료 팝업
    private void showDefaultClosePopup(){
        new AlertDialog.Builder(this).setTitle("").setMessage("종료 할까요?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("아니요",null)
                .show();
    }
}

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GradeCDB.db";
    private static final int DATABASE_VERSION = 2;
    SQLiteDatabase myDb = this.getWritableDatabase();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gradeC ( Id INTEGER PRIMARY KEY" + " AUTOINCREMENT, recordName TEXT, majors TEXT, subjects TEXT, scores TEXT, grades TEXT" +
                ", allGrade TEXT, majorGrade TEXT, allNum INTEGER, majorNum INTEGER );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS gradeC");
        onCreate(db);
    }

    public void insertRecord(String recordName, String majors, String subjects, String scores, String grades, String allGrade, String majorGrade, int allNum, int majorNum){
        myDb.execSQL("INSERT INTO gradeC VALUES (null, '" + recordName + "', '" + majors + "', '" + subjects + "', '" +
                scores + "', '" + grades + "', '" + allGrade + "', '" + majorGrade + "', '" + allNum + "', '" + majorNum + "');");
    }

    public void deleteRecord(int id){
        myDb.delete("gradeC", "id = ? ", new String[]{Integer.toString(id)});
    }
}
