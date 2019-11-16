package com.example.personalassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.personalassistant.tool.DBHelper;
import com.example.personalassistant.tool.SimpleItemTouchCallBack;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.TaskList;
import com.example.personalassistant.view.InsideList;
import com.example.personalassistant.view.ListAdapter;
import com.example.personalassistant.view.ListAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<TaskList> listList;
    private Context context = this;

    /*static public void addList(TaskList taskList) {
        listList.add(taskList);
        listAdapter.notifyDataSetChanged();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("个人助理");

        LitePal.getDatabase();
        DBHelper dbHelper=new DBHelper(context);
        listList = LitePal.findAll(TaskList.class,true);


        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        listAdapter = new ListAdapter(this, listList);
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "You clicked item " + listList.get(position).getListName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, InsideList.class);
                intent.putExtra("chosenlist", listList.get(position));
                startActivity(intent);
            }
        });

       /* if(getIntent().getSerializableExtra("addList")!=null){
            TaskList tl= (TaskList) getIntent().getSerializableExtra("addList");
            tl.save();
            if (tl.save()){
                Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
            }
            listAdapter.notifyDataSetChanged();
        }*/

        // 拖拽移动和左滑删除
        SimpleItemTouchCallBack simpleItemTouchCallBack = new SimpleItemTouchCallBack(listAdapter);
// 要实现侧滑删除条目，把 false 改成 true 就可以了
        simpleItemTouchCallBack.setmSwipeEnable(true);
        ItemTouchHelper helper = new ItemTouchHelper(simpleItemTouchCallBack);
        helper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, ListAdd.class);
                startActivity(intent);
            }
        });
       // initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        TaskList a = new TaskList();
        a.setListName("aaa");
        a.setType("bbb");
        Task ta = new Task("task01", "task1 content", 0);
        a.getTaskList().add(ta);
        a.save();
        // listList.add(a);
        TaskList b = new TaskList();
        b.setListName("changed");
        b.setType("bbb");
        b.getTaskList().add(ta);
        b.save();
        List<Task> abb=new ArrayList<>();
        Task c=new Task("in1","1233",1);
        b.getTaskList().add(c);
        //listList.add(b);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listList = LitePal.findAll(TaskList.class, true);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        listAdapter = new ListAdapter(this, listList);
        recyclerView.setAdapter(listAdapter);
    }
/*
    public static TaskList findList(String s){
        for (TaskList taskList0:listList){
            if (taskList0.getListName()==s){
                return taskList0;
            }
        }
        return null;
    }*/




}
