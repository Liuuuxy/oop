package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.TaskList;

import java.util.List;

public class InsideTask extends AppCompatActivity {
    EditText edName;
    EditText edContent;
    EditText type;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    TextView tv1;
    TextView tv2;
    TextView tv3;

    Task task = (Task) getIntent().getSerializableExtra("chosentask");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_task);

        edName = findViewById(R.id.ed_name_task);
        edContent = findViewById(R.id.ed_content_task);
        type=findViewById(R.id.ed_type_task);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);


        edName.setText(task.getName());
        edContent.setText(task.getContent());
        switch (task.getType()){
            case 0:
                type.setText("临时任务");
                ed1.setVisibility(View.INVISIBLE);
                ed2.setVisibility(View.INVISIBLE);
                ed3.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                tv3.setVisibility(View.INVISIBLE);
            case 1:
                type.setText("周期任务");
                ed1.setText(((Cycle)task).getExecuteDate());
                ed2.setText(((Cycle)task).getCount());
                ed3.setText(((Cycle)task).getRepeatCycle());
        }
    }
}
