package com.kw2.kw2.gradec;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calBtn = (Button) findViewById(R.id.main_calBtn);
        Button viewBtn = (Button) findViewById(R.id.main_viewBtn);
        Button tipBtn = (Button) findViewById(R.id.main_tipBtn);
        Button endBtn = (Button) findViewById(R.id.main_endBtn);

        calBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        tipBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);
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
                break;
        }
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
