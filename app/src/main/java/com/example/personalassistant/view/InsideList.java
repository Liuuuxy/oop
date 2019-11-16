/*
 * 这是一个任务清单，显示的是各个任务
 * */

package com.example.personalassistant.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.tool.SimpleItemTouchCallBack;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.TaskList;
import com.example.personalassistant.data.Temporary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InsideList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView t1;
    private TextView t2;
    Context context=this;
    static private TaskAdapter taskAdapter;
    TaskList taskList= (TaskList) getIntent().getSerializableExtra("chosenlist");
    List<Task> listOfTask=taskList.getTaskList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_list);

        View include = findViewById(R.id.r2);
        recyclerView = include.findViewById(R.id.rv_task);

        t1=findViewById(R.id.tv_list_show_name);
        t2=findViewById(R.id.tv_list_show_type);

        t1.setText(taskList.getListName());
        t2.setText(taskList.getType());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        taskAdapter = new TaskAdapter(this, listOfTask);
        recyclerView.setAdapter(taskAdapter);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "You clicked item " + listOfTask.get(position).getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InsideList.this, InsideTask.class);
                intent.putExtra("chosentask", listOfTask.get(position));
                startActivity(intent);
            }
        });

        // 拖拽移动和左滑删除
        SimpleItemTouchCallBack simpleItemTouchCallBack = new SimpleItemTouchCallBack(taskAdapter);
        // 要实现侧滑删除条目，把 false 改成 true 就可以了
        simpleItemTouchCallBack.setmSwipeEnable(false);
        ItemTouchHelper helper = new ItemTouchHelper(simpleItemTouchCallBack);
        helper.attachToRecyclerView(recyclerView);

        for (Task t : taskList.getTaskList()) {
            listOfTask.add(t);
            taskAdapter.notifyDataSetChanged();
        }

        if (getIntent().getSerializableExtra("task") != null) {
            if (((Task) getIntent().getSerializableExtra("task")).getType() == 0) {
                Temporary t = (Temporary) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.save();
                if (taskList.save()) {
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                }
                listOfTask.add(t);
                taskAdapter.notifyDataSetChanged();
            } else if (((Task) getIntent().getSerializableExtra("task")).getType() == 1) {
                Cycle t = (Cycle) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.save();
                taskAdapter.notifyDataSetChanged();
            } else if (((Task) getIntent().getSerializableExtra("task")).getType() == 2) {
                Long t = (Long) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.save();
                taskAdapter.notifyDataSetChanged();
            }
        }

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsideList.this, ItemsAdd.class);
                startActivity(intent);
            }
        });
    }
}
