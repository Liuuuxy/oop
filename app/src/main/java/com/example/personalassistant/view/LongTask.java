package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.Temporary;

import java.util.ArrayList;
import java.util.List;

public class LongTask extends AppCompatActivity {
    EditText edName;
    EditText edContent;
    EditText edLevel;
    EditText edDdl;
    Button btEdit;
    Button btAdd;
    static Long task;
    List<Temporary> sontasks = new ArrayList<>();
    List<String> sonTaskName = new ArrayList<>();

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

        if (getIntent().getSerializableExtra("longtaskchoose") != null) {
            task = (Long) getIntent().getSerializableExtra("longtaskchoose");
        }
        if (getIntent().getSerializableExtra("addasontask") != null) {
            task.getTemplist().add((Temporary) getIntent().getSerializableExtra("addasontask"));
            task.save();
            sontasks = task.getTemplist();
            for (Temporary t : sontasks) {
                sonTaskName.add(t.getName());
            }
            String[] data = sonTaskName.toArray(new String[sonTaskName.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    LongTask.this, android.R.layout.simple_list_item_1, data);
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);
        }

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
                Intent intent = new Intent(LongTask.this, ItemsAdd.class);
                intent.putExtra("inlongtask", task);
                startActivity(intent);
            }
        });

    }
}
