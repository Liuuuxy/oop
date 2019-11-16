package com.example.personalassistant.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.Temporary;

public class ItemsAdd extends Activity {
    private EditText edContent;
    private EditText edTaskTitle;
    private Toolbar tb_additem;
    private Button okBtn;
    private RadioGroup mrg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private EditText ddlTemp;
    private EditText ddlCycle;
    private EditText ddlLong;
    private EditText numCycle;
    private EditText circleCycle;

    private String name;
    private String content;
    private int type;
    private String ddl;
    private int num;
    private int repeat;

    //private Task t=new Task(name,content,type);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);

        edContent = findViewById(R.id.edit_content);
        edTaskTitle = findViewById(R.id.edit_task_title);
        tb_additem = findViewById(R.id.ad_toolbar);
        okBtn = findViewById(R.id.btn_ok);
        mrg = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.temp_task_btn);
        rb2 = findViewById(R.id.cycle_task_btn);
        rb3 = findViewById(R.id.long_task_btn);
        ddlTemp = findViewById(R.id.edit_ddl_temp);
        ddlCycle = findViewById(R.id.edit_excuteDate);
        numCycle = findViewById(R.id.edit_num);
        circleCycle = findViewById(R.id.edit_circle);
        ddlLong = findViewById(R.id.edit_ddl_long);


        mrg.setOnCheckedChangeListener(mChangeRadio);
    }

    private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, final int i) {
            //TODO:单选处理
            if (i == rb1.getId()) {
                type = 0;
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ddl = ddlTemp.getText().toString();
                        content = edContent.getText().toString();
                        name = edTaskTitle.getText().toString();
                        Temporary tt = new Temporary(name, content, type, ddl);
                        Log.d("yooo","ddl:"+tt.getDeadline()+"name:"+tt.getName()+"content:"+tt.getContent()+"type:"+tt.getType());
                        Intent intent = new Intent(ItemsAdd.this, InsideList.class);
                        intent.putExtra("task", tt);
                        startActivity(intent);
                    }
                });
            } else if (i == rb2.getId()) {
                type = 1;
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ddl = ddlCycle.getText().toString();
                        content = edContent.getText().toString();
                        name = edTaskTitle.getText().toString();
                        num = Integer.parseInt(numCycle.getText().toString().equals("") ? "0" : numCycle.getText().toString());
                        repeat = Integer.parseInt(circleCycle.getText().toString().equals("") ? "0" : circleCycle.getText().toString());
                        Cycle tt = new Cycle(name, content, type, ddl, num, repeat);
                        Log.d("yooo","ddl:"+tt.getExecuteDate()+"num:"+tt.getCount()+"name:"+tt.getName()+"content:"+tt.getContent()+"type:"+tt.getType());
                        Intent intent = new Intent(ItemsAdd.this, InsideList.class);
                        intent.putExtra("task", tt);
                        startActivity(intent);
                    }
                });
            } else if (i == rb3.getId()) {
                type = 2;
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        content = edContent.getText().toString();
                        name = edTaskTitle.getText().toString();
                        ddl = ddlLong.getText().toString();
                        Long tt = new Long(name, content, type, ddl, null);
                        Log.d("yooo","ddl:"+tt.getDdl()+"name:"+tt.getName()+"content:"+tt.getContent()+"type:"+tt.getType());
                        Intent intent = new Intent(ItemsAdd.this, InsideList.class);
                        intent.putExtra("task", tt);
                        startActivity(intent);
                    }
                });
            }
        }
    };
}
