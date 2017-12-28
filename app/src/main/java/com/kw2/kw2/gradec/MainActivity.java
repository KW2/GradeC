package com.kw2.kw2.gradec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void mainOnClick(View view){
        switch (view.getId()){
            case R.id.main_calBtn:
                // 계산 작업
                Intent intent = new Intent(getApplicationContext(), GradeC.class);
                startActivity(intent);
                break;
            case R.id.main_viewBtn:
                // 기록보기 작업
                break;
            case R.id.main_endBtn:
                // 어플 종료
                break;
        }
    }
}
