package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalassistant.MainActivity;
import com.example.personalassistant.R;
import com.example.personalassistant.data.TaskList;

import org.litepal.LitePal;

public class ListAdd extends AppCompatActivity {
    private Button okButton;
    private EditText name;
    private EditText type;
    private Context context = this;
    private TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add);

        okButton = findViewById(R.id.btn_list_ok);
        name = findViewById(R.id.edit_list_name);
        type = findViewById(R.id.edit_list_type);
/*
        taskList = (TaskList) getIntent().getSerializableExtra("change");

        if (taskList != null) {
            okButton.setText("确认修改");
            name.setText(taskList.getListName());
            type.setText(taskList.getType());
           // taskList = LitePal.find(TaskList.class, taskList.getId(), true);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namel = name.getText().toString();
                    String typel = type.getText().toString();
                    taskList.setType(typel);
                    taskList.setListName(namel);
                    taskList.updateAll("listname = ? and type = ?", namel, typel);
                    Log.d("aa","listname: "+taskList.getListName());
                    getIntent().putExtra("changelist",taskList);
                    finish();
                }
            });
        } else {*/
            taskList = new TaskList();
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namel = name.getText().toString();
                    String typel = type.getText().toString();
                    taskList.setListName(namel);
                    taskList.setType(typel);
                    getIntent().putExtra("add_list", taskList);
                    Log.d("mytag", "name:" + taskList.getListName());
                    taskList.save();
                    finish();
                }
            });
        }


    }

