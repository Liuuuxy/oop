package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.TaskList;

import java.util.List;

public class InsideTask extends AppCompatActivity {
    EditText edName;
    EditText edContent;
    EditText type;
    EditText level;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    Button b;

    Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_task);

        task = (Task) getIntent().getSerializableExtra("chosentask");

        edName = findViewById(R.id.ed_name_task);
        edContent = findViewById(R.id.ed_content_task);
        type=findViewById(R.id.ed_type_task);
        level=findViewById(R.id.ed_level_task);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        b=findViewById(R.id.btn_task_ok_change);

        edName.setText(task.getName());
        edContent.setText(task.getContent());
        level.setText(Integer.toString(task.getLevel()));
        switch (task.getType()){
            case 0:
                type.setText("临时任务");
                ed1.setVisibility(View.INVISIBLE);
                ed2.setVisibility(View.INVISIBLE);
                ed3.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                tv3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                type.setText("周期任务");
                ed1.setText(((Cycle)task).getExecuteDate());
                ed2.setText(Integer.toString(((Cycle)task).getCount()));
                ed3.setText(Integer.toString(((Cycle)task).getRepeatCycle()));
                break;
            case 2:
                type.setText("长期任务");
                ed1.setVisibility(View.INVISIBLE);
                ed2.setVisibility(View.INVISIBLE);
                ed3.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                tv3.setVisibility(View.INVISIBLE);
                break;
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
