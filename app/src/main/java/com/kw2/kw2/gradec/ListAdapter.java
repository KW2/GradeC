package com.kw2.kw2.gradec;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 2017-12-29.
 */

public class ListAdapter extends ArrayAdapter<ListViewItem> {

    public interface ListBtnClickListener {
        void onListBtnClick(int id) ;
    }

    private ListBtnClickListener listBtnClickListener ;
    private final Activity context;
    int resourceId ;
    private DBHelper helper;

    public ListAdapter(Activity context, int resource , ArrayList<ListViewItem> list, ListBtnClickListener clickListener){
        super(context, resource, list);
        this.context = context;
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
        helper = new DBHelper(context);
    }

    @Override
    public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final ListViewItem listViewItem = (ListViewItem) getItem(position);

        TextView recordName = (TextView) convertView.findViewById(R.id.item_recordName);
        TextView recordText = (TextView) convertView.findViewById(R.id.item_recordText);

        BootstrapButton deleteBtn = (BootstrapButton) convertView.findViewById(R.id.item_deleteBtn);

        recordName.setText(listViewItem.getRecordName());
        recordText.setText("총 평점: " + listViewItem.getAllGrade() + " 이수학점: " + listViewItem.getAllNum());


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 작업
                listBtnClickListener.onListBtnClick(listViewItem.getId()) ;
            }
        });

        return convertView;
    }

}
