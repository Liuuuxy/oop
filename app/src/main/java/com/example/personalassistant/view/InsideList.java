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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InsideList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText t1;
    private EditText t2;
    private Button b;
    private Button sort1;
    private Button sort2;
    private Button sort3;

    private Button btfind;
    private TextView tvresult;
    private EditText edinput;

    Context context = this;
    private TaskAdapter taskAdapter;
    static TaskList taskList = new TaskList();
    List<Task> listOfTask;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_list);
        if(getIntent().getSerializableExtra("chosenlist")!=null) {
            taskList = (TaskList) getIntent().getSerializableExtra("chosenlist");
            //Log.d("yooo", "listname: " + taskList.getListName());
        }
        if (taskList.getTaskList() != null) {
         listOfTask = taskList.getTaskList();
        } else {
        listOfTask = new ArrayList<>();
         }

        View include = findViewById(R.id.r2);
        recyclerView = include.findViewById(R.id.rv_task);

        t1 = findViewById(R.id.ed_list_show_name);
        t2 = findViewById(R.id.ed_list_show_type);
        b=findViewById(R.id.btn_edit);
        sort1=findViewById(R.id.btn_sort_name);
        sort2=findViewById(R.id.btn_sort_type);
        sort3=findViewById(R.id.btn_sort_level);
        btfind=findViewById(R.id.btn_search_ok);
        tvresult=findViewById(R.id.tv_show_result);
        edinput=findViewById(R.id.ed_search);

        t2.setText(taskList.getType());
        t1.setText(taskList.getListName());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        taskAdapter = new TaskAdapter(this, listOfTask);
        recyclerView.setAdapter(taskAdapter);

//        taskAdapter.setOnItemClickListener(new TaskAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(context, "You clicked item " + listOfTask.get(position).getName(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(InsideList.this, InsideTask.class);
//                intent.putExtra("chosentask", listOfTask.get(position));
//                startActivity(intent);
//            }
//        });

        // 拖拽移动和左滑删除
       SimpleItemTouchCallBack simpleItemTouchCallBack = new SimpleItemTouchCallBack(taskAdapter);
        // 要实现侧滑删除条目，把 false 改成 true 就可以了
        simpleItemTouchCallBack.setmSwipeEnable(true);
        ItemTouchHelper helper = new ItemTouchHelper(simpleItemTouchCallBack);
        helper.attachToRecyclerView(recyclerView);

//        for (Task t : taskList.getTaskList()) {
//            listOfTask.add(t);
//            taskAdapter.notifyDataSetChanged();
//        }

        if (getIntent().getSerializableExtra("task") != null) {
            if (((Task) getIntent().getSerializableExtra("task")).getType() == 0) {
                Temporary t = (Temporary) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.getTaskList().add(t);
                taskList.save();
                taskAdapter.notifyDataSetChanged();
            } else if (((Task) getIntent().getSerializableExtra("task")).getType() == 1) {
                Cycle t = (Cycle) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.getTaskList().add(t);
                taskList.save();
                taskAdapter.notifyDataSetChanged();
            } else if (((Task) getIntent().getSerializableExtra("task")).getType() == 2) {
                Long t = (Long) getIntent().getSerializableExtra("task");
                listOfTask.add(t);
                taskList.getTaskList().add(t);
                taskList.save();
                taskAdapter.notifyDataSetChanged();
            }
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskList.setListName(t1.getText().toString());
                taskList.setType(t2.getText().toString());
                taskList.updateAll("listname = ? and type = ?", t1.getText().toString(), t2.getText().toString());
                t1.setText(taskList.getListName());
                t2.setText(taskList.getType());
            }
        });

        sort1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listOfTask, new Comparator<Task>() {
                    @Override
                    public int compare(Task task, Task t1) {
                        return task.getName().compareTo(t1.getName());
                    }
                });
                taskAdapter.notifyDataSetChanged();
            }
        });

        sort2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listOfTask, new Comparator<Task>() {
                    @Override
                    public int compare(Task task, Task t1) {
                        return task.getType()-t1.getType();
                    }
                });
                taskAdapter.notifyDataSetChanged();
            }
        });

        sort3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listOfTask, new Comparator<Task>() {
                    @Override
                    public int compare(Task task, Task t1) {
                        return task.getLevel()-t1.getLevel();
                    }
                });
                taskAdapter.notifyDataSetChanged();
            }
        });

        btfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edinput.getText().toString();
                Log.d("finda",name);
                Log.d("finda","listOfTak"+listOfTask.get(0).getName());
                for(Task t:listOfTask){
                    if(t.getName().equals(name)){
                        Log.d("finda",Boolean.toString(t.getName()==name));
                        Log.d("finda",t.getName()+" "+t.getRealType());
                        tvresult.setText(t.getName()+" "+t.getContent()+" "+t.getRealType()+" "+t.getLevel());
                       break;
                    }
                    else {Log.d("finda","不等于");}
                }
            }
        });

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
