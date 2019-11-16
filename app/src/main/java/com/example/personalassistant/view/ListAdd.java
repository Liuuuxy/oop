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

        taskList = new TaskList();
        String namel = name.getText().toString();
        String typel = type.getText().toString();

        //taskList = (TaskList) getIntent().getSerializableExtra("change");

        /*if (taskList != null) {
            okButton.setText("确认修改");
            name.setText(" " + taskList.getListName());
            type.setText(taskList.getType());
            taskList = LitePal.find(TaskList.class, taskList.getId(), true);

        } else {
            taskList = new TaskList();
        }*/

        taskList.setListName(namel);
        taskList.setType(typel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getIntent().putExtra("add_menu",taskList);
               // MainActivity.addList(taskList);
                taskList.save();
                finish();/*
                Toast.makeText(context, taskList.getListName(), Toast.LENGTH_SHORT).show();
                if (taskList.save()) {
                    Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
