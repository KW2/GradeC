package com.kw2.kw2.gradec;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 2017-12-27.
 */

public class GradeC extends Activity {
    int nameNum;
    boolean checked5, pass;

    String recordName, majors, subjects, scores, grades, allGrade, majorGrade;
    int allNum, majorNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradec);
        nameNum = 8;
        checked5 = true;
        pass =true;
        final ArrayList<CheckBox> array_chk = new ArrayList<>();
        final ArrayList<EditText> array_sub = new ArrayList<>();
        final ArrayList<Spinner> array_scr = new ArrayList<>();
        final ArrayList<EditText> array_grd = new ArrayList<>();

        final LinearLayout listMain = (LinearLayout) findViewById(R.id.gradec_listMain);
        final TextView resultText = (TextView) findViewById(R.id.gradec_result);

        Button plsBtn = (Button) findViewById(R.id.gradec_plsBtn);
        final Button calBtn = (Button) findViewById(R.id.gradec_calBtn);
        final Button saveBtn = (Button) findViewById(R.id.gradec_saveBtn);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gradec_radioG);



        String chkName, subName, scrName, grdName;
        int chkId, subId, scrId, grdId;

        for(int i = 0; i<8; i++){
            chkName = "gradec_chk" + (i+1);
            subName = "gradec_sub" + (i+1);
            scrName = "gradec_scr" + (i+1);
            grdName = "gradec_grd" + (i+1);

            chkId = getResources().getIdentifier(chkName,"id",getPackageName());
            subId = getResources().getIdentifier(subName,"id",getPackageName());
            scrId = getResources().getIdentifier(scrName,"id",getPackageName());
            grdId = getResources().getIdentifier(grdName,"id",getPackageName());

            array_chk.add(i,(CheckBox) findViewById(chkId));
            array_sub.add(i,(EditText) findViewById(subId));
            array_scr.add(i,(Spinner) findViewById(scrId));
            array_grd.add(i,(EditText) findViewById(grdId));

        }


        plsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout itemL = new LinearLayout(GradeC.this);
                CheckBox newChk = new CheckBox(GradeC.this);
                EditText newSub = new EditText(GradeC.this);
                Spinner newScr = new Spinner(GradeC.this);
                EditText newGrd = new EditText(GradeC.this);

                setNewView(itemL, newChk, newSub, newScr, newGrd);

                itemL.addView(newChk);
                itemL.addView(newSub);
                itemL.addView(newScr);
                itemL.addView(newGrd);

                array_chk.add(nameNum, newChk);
                array_sub.add(nameNum, newSub);
                array_scr.add(nameNum, newScr);
                array_grd.add(nameNum, newGrd);
                nameNum++;

                listMain.addView(itemL);
            }
        });


        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean majorChecked, scoreSelected, gradeInputed;
                int majorInt = 0, allInt = 0, inputCheck = 0;
                double mValue = 0, aValue = 0;
                pass = true;

                // db값 설정용
                majors = "";
                subjects = "";
                scores = "";
                grades = "";

                Loop : for(int j = 0; j< array_chk.size(); j++){
                    majorChecked = array_chk.get(j).isChecked();
                    scoreSelected = !array_scr.get(j).getSelectedItem().toString().equals("선택");
                    gradeInputed = !array_grd.get(j).getText().toString().trim().equals("");
                    if( majorChecked && scoreSelected && gradeInputed){
                        majorInt = majorInt + Integer.parseInt(array_grd.get(j).getText().toString());
                        allInt = allInt + Integer.parseInt(array_grd.get(j).getText().toString());
                        mValue = mValue + ( getScore(array_scr.get(j).getSelectedItem().toString()) * Double.parseDouble(array_grd.get(j).getText().toString()));
                        aValue = aValue + ( getScore(array_scr.get(j).getSelectedItem().toString()) * Double.parseDouble(array_grd.get(j).getText().toString()));

                        if(j != array_chk.size()-1) {
                            majors = majors + "전공" + "@";
                            if(array_sub.get(j).getText().toString().trim().equals("")){
                                subjects = subjects + "공백" +"@";
                            }else{
                                subjects = subjects + array_sub.get(j).getText().toString() + "@";
                            }
                            scores = scores + array_scr.get(j).getSelectedItem().toString() + "@";
                            grades = grades + array_grd.get(j).getText().toString() + "@";
                        }else{
                            // 리스트 마지막 항목
                            majors = majors + "전공";
                            if(array_sub.get(j).getText().toString().trim().equals("")){
                                subjects = subjects + "공백";
                            }else{
                                subjects = subjects + array_sub.get(j).getText().toString();
                            }
                            scores = scores + array_scr.get(j).getSelectedItem().toString();
                            grades = grades + array_grd.get(j).getText().toString();
                        }

                    }else if( !majorChecked && scoreSelected && gradeInputed){
                        allInt = allInt + Integer.parseInt(array_grd.get(j).getText().toString());
                        aValue = aValue + ( getScore(array_scr.get(j).getSelectedItem().toString()) * Double.parseDouble(array_grd.get(j).getText().toString()));

                        if(j != array_chk.size()-1) {
                            majors = majors + "비전공" + "@";
                            if(array_sub.get(j).getText().toString().trim().equals("")){
                                subjects = subjects + "공백" +"@";
                            }else{
                                subjects = subjects + array_sub.get(j).getText().toString() + "@";
                            }
                            scores = scores + array_scr.get(j).getSelectedItem().toString() + "@";
                            grades = grades + array_grd.get(j).getText().toString() + "@";
                        }else{
                            // 리스트 마지막 항목
                            majors = majors + "전공";
                            if(array_sub.get(j).getText().toString().trim().equals("")){
                                subjects = subjects + "공백";
                            }else{
                                subjects = subjects + array_sub.get(j).getText().toString();
                            }
                            scores = scores + array_scr.get(j).getSelectedItem().toString();
                            grades = grades + array_grd.get(j).getText().toString();
                        }

                    }else if( scoreSelected && !gradeInputed){
                        Toast.makeText(GradeC.this, "학점 입력을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        pass = false;
                        break Loop;
                    }else if( !scoreSelected && gradeInputed){
                        Toast.makeText(GradeC.this, "점수 선택을 다시 확인해주세요,", Toast.LENGTH_SHORT).show();
                        pass = false;
                        break Loop;
                    }else if( !scoreSelected && !gradeInputed){
                        inputCheck++;
                    }
                }
                if(inputCheck == array_chk.size()){
                    Toast.makeText(GradeC.this, "입력사항을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    pass = false;
                }

                if(pass) {
                    String majorStr = "0.00";
                    if (majorInt != 0) {
                        majorStr = String.format("%.2f", mValue / majorInt);
                    }
                    String allStr = String.format("%.2f", aValue / allInt);

                    allGrade = allStr;
                    majorGrade = majorStr;
                    allNum = allInt;
                    majorNum = majorInt;

                    resultText.setText("총 평점: " + allStr + " 전공 평점: " + majorStr + "\r\n" + "이수학점: " + allInt + " 전공이수: " + majorInt);
                    saveBtn.setEnabled(true);

                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calBtn.performClick();

                if(pass) {
                    final DBHelper helper = new DBHelper(GradeC.this);
                    final Dialog cstDialog = new Dialog(GradeC.this);
                    cstDialog.setContentView(R.layout.dialog_save);
                    WindowManager.LayoutParams params = cstDialog.getWindow().getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    cstDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                    final EditText recordEdit = (EditText) cstDialog.findViewById(R.id.save_nameEdit);
                    Button cancelBtn = (Button) cstDialog.findViewById(R.id.save_cancelBtn);
                    final Button recordSaveBtn = (Button) cstDialog.findViewById(R.id.save_saveBtn);

                    recordSaveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 기록 insert 작업
                            if(recordEdit.getText().toString().trim().equals("")){
                                Toast.makeText(GradeC.this, "기록명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }else {
                                recordName = recordEdit.getText().toString();
                                helper.insertRecord(recordName, majors, subjects, scores, grades, allGrade, majorGrade, allNum, majorNum);
                                Toast.makeText(GradeC.this, "기록 저장 완료", Toast.LENGTH_SHORT).show();
                                saveBtn.setEnabled(false);
                                cstDialog.dismiss();
                            }
                        }
                    });

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cstDialog.dismiss();
                        }
                    });

                    cstDialog.show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if( checkedId == R.id.gradec_4_5){
                    if( !checked5) {
                        checked5 = true;
                        for(Spinner scr : array_scr){
                            String[] str = getResources().getStringArray(R.array.grade5);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradeC.this, R.layout.support_simple_spinner_dropdown_item, str);
                            scr.setAdapter(adapter);
                        }
                    }
                }else if( checkedId == R.id.gradec_4_3){
                    if( checked5) {
                        checked5 = false;
                        for(Spinner scr : array_scr) {
                            String[] str = getResources().getStringArray(R.array.grade3);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradeC.this, R.layout.support_simple_spinner_dropdown_item, str);
                            scr.setAdapter(adapter);
                        }
                    }
                }
            }
        });
    }


    // 과목추가버튼 클릭시 추가 view 세팅
    public void setNewView(LinearLayout itemL, CheckBox newChk, EditText newSub, Spinner newScr, EditText newGrd){
        itemL.setOrientation(LinearLayout.HORIZONTAL);
        itemL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        newChk.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

        newSub.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
        newSub.setMaxLines(2);

        newScr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.1f));
        if(checked5) {
            String[] str = getResources().getStringArray(R.array.grade5);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradeC.this, R.layout.support_simple_spinner_dropdown_item, str);
            newScr.setAdapter(adapter);
        }else if(!checked5){
            String[] str = getResources().getStringArray(R.array.grade3);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradeC.this, R.layout.support_simple_spinner_dropdown_item, str);
            newScr.setAdapter(adapter);
        }

        newGrd.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,0.3f));
        newGrd.setInputType(0x00000002);
        newGrd.setMaxLines(1);
        newGrd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
    }

    public double getScore(String srcStr){
        double value = 0.0;
        if(checked5) {
            switch (srcStr) {
                case "A+":
                    value = 4.5;
                    break;
                case "A":
                    value = 4.0;
                    break;
                case "B+":
                    value = 3.5;
                    break;
                case "B":
                    value = 3.0;
                    break;
                case "C+":
                    value = 2.5;
                    break;
                case "C":
                    value = 2.0;
                    break;
                case "D+":
                    value = 1.5;
                    break;
                case "D":
                    value = 1.0;
                    break;
                case "F":
                    value = 0.0;
                    break;
            }
        }else if(!checked5){
            switch (srcStr) {
                case "A+":
                    value = 4.3;
                    break;
                case "A":
                    value = 4.0;
                    break;
                case "A-":
                    value = 3.7;
                    break;
                case "B+":
                    value = 3.3;
                    break;
                case "B":
                    value = 3.0;
                    break;
                case "B-":
                    value = 2.7;
                    break;
                case "C+":
                    value = 2.3;
                    break;
                case "C":
                    value = 2.0;
                    break;
                case "C-":
                    value = 1.7;
                    break;
                case "D+":
                    value = 1.3;
                    break;
                case "D":
                    value = 1.0;
                    break;
                case "D-":
                    value = 0.7;
                    break;
                case "F":
                    value = 0.0;
                    break;
            }
        }
        return value;
    }
}
