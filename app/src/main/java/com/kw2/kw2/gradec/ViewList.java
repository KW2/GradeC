package com.kw2.kw2.gradec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by SAMSUNG on 2017-12-29.
 */

public class ViewList extends Activity implements ListAdapter.ListBtnClickListener {
    DBHelper helper;
    SQLiteDatabase db;
    ArrayList<ListViewItem> items;
    ListAdapter adapter;
    TextView blankText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        items = new ArrayList<ListViewItem>();

        // DB 생성
        helper = new DBHelper(ViewList.this);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();
        }

        loadDB(db, items);

        adapter = new ListAdapter(ViewList.this, R.layout.viewlist_item, items, this);

        blankText = (TextView) findViewById(R.id.view_text);

        ListView listView = (ListView) findViewById(R.id.view_list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 기록 선택 -> 기록 펼처 보기
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewList.this, ViewItem.class);
                intent.putExtra("recordName", items.get(position).getRecordName());
                intent.putExtra("majors", items.get(position).getMajors());
                intent.putExtra("subjects", items.get(position).getSubjects());
                intent.putExtra("scores", items.get(position).getScores());
                intent.putExtra("grades", items.get(position).getGrades());
                intent.putExtra("allGrade", items.get(position).getAllGrade());
                intent.putExtra("majorGrade", items.get(position).getMajorGrade());
                intent.putExtra("allNum", items.get(position).getAllNum());
                intent.putExtra("majorNum", items.get(position).getMajorNum());
                startActivity(intent);
            }

        });
        if(items.size() == 0){
            blankText.setText("저장된 기록이 없습니다.");
        }

    }

    public boolean loadDB(SQLiteDatabase db, ArrayList<ListViewItem> list) {

        ListViewItem item;

        if (list == null) {
            list = new ArrayList<ListViewItem>();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM gradeC", null);
        while (cursor.moveToNext()) {
            item = new ListViewItem();
            item.setId(cursor.getInt(0));
            item.setRecordName(cursor.getString(1));
            item.setMajors(cursor.getString(2));
            item.setSubjects(cursor.getString(3));
            item.setScores(cursor.getString(4));
            item.setGrades(cursor.getString(5));
            item.setAllGrade(cursor.getString(6));
            item.setMajorGrade(cursor.getString(7));
            item.setAllNum(cursor.getInt(8));
            item.setMajorNum(cursor.getInt(9));
            list.add(item);
        }


        return true;
    }

    @Override
    public void onListBtnClick(int id) {
        final int recordId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert;
        builder.setTitle("삭제 확인")
                .setMessage("해당 기록을 삭제 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 데이터베이스 삭제
                                helper.deleteRecord(recordId);
                                Toast.makeText(getApplication(), "삭제 완료", Toast.LENGTH_SHORT).show();

                                // 해당 값 items에서 삭제
                                for (Iterator<ListViewItem> it = items.iterator(); it.hasNext(); ) {
                                    ListViewItem item = it.next();
                                    if (recordId == item.getId()) {
                                        it.remove();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                if(items.size() == 0){
                                    blankText.setText("저장된 기록이 없습니다.");
                                }

                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
        alert = builder.create();
        alert.show();
    }
}
