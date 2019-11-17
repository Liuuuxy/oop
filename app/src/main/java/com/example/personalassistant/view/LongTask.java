package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;

public class LongTask extends AppCompatActivity {
    EditText edName;
    EditText edContent;
    EditText edLevel;
    EditText edDdl;
    Button btEdit;
    Button btAdd;
    Long task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_task);
        edContent = findViewById(R.id.ed_long_show_content);
        edDdl = findViewById(R.id.ed_long_show_ddl);
        edLevel = findViewById(R.id.ed_long_show_level);
        edName = findViewById(R.id.ed_long_show_name);
        btAdd = findViewById(R.id.btn_add);
        btEdit = findViewById(R.id.btn_edit_long);

        task = (Long) getIntent().getSerializableExtra("longtaskchoose");

        edName.setText(task.getName());
        edContent.setText(task.getContent());
        edLevel.setText(Integer.toString(task.getLevel()));
        edDdl.setText(task.getDdl());
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setDdl(edDdl.getText().toString());
                task.setContent(edContent.getText().toString());
                task.setLevel(Integer.parseInt(edLevel.getText().toString()));
                task.setName(edName.getText().toString());
                task.updateAll();
                edName.setText(task.getName());
                edContent.setText(task.getContent());
                edLevel.setText(Integer.toString(task.getLevel()));
                edDdl.setText(task.getDdl());
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LongTask.this,ItemsAdd.class);
                startActivity(intent);
            }
        });

    }
}
