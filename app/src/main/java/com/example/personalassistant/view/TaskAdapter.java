package com.example.personalassistant.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.TaskList;
import com.example.personalassistant.data.Temporary;
import com.example.personalassistant.tool.TouchCallBack;
import com.example.personalassistant.tool.taskDBHelper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements TouchCallBack {
    private List<Task> mTaskList;
    private Context context;
    private TaskList taskList;
    private TaskAdapter.OnRecyclerViewItemClickListener myClickItemListener;

    /**
     * goldTitleBeans 适配器里的数据集合
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换集合中两个数据的位置
        Collections.swap(mTaskList,fromPosition,toPosition);
        //刷新界面,局部刷新,索引会混乱
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        mTaskList.remove(position);
        //局部刷新,索引会混乱+集合越界
        notifyItemRemoved(position);
        mTaskList.get(position).delete();
    }


    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(TaskAdapter.OnRecyclerViewItemClickListener listener) {
        this.myClickItemListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskTitle;
        Button bmove;
        Button bcopy;
        private TaskAdapter.OnRecyclerViewItemClickListener mListener;
        public ViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            taskTitle = itemView.findViewById(R.id.tv_item_title);
            bmove=itemView.findViewById(R.id.btn_move);
            bcopy=itemView.findViewById(R.id.btn_copy);
        }

        @Override
        public void onClick(View v) {
            //getAdapterPosition()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public TaskAdapter(Context context, List<Task> taskList) {
        mTaskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        final ViewHolder holder = new ViewHolder(v, myClickItemListener);
        holder.bmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingDialog(mTaskList.get(holder.getAdapterPosition()));
            }
        });
        holder.bcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiChoiceDialog(mTaskList.get(holder.getAdapterPosition()));

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.taskTitle.setText(task.getName());

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    /**
     * 单选Dialog
     */
    int choice;
    private void showSingDialog(final Task task){
        final List<TaskList> list = LitePal.findAll(TaskList.class,true);

        String[] items = new String[list.size()];

        for(int i = 0; i < list.size(); i++){
            items[i] = list.get(i).getListName();
        }
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle("转移至清单");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice= which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice!=-1){
                    taskDBHelper.addTask(task);
                    TaskList targetTaskMenu = LitePal.where("listname = ?", list.get(choice).getListName()).find(TaskList.class,true).get(0);
                    targetTaskMenu.addTask(task);
                    targetTaskMenu.save();
                    mTaskList.remove(task);
                    notifyDataSetChanged();

                }
            }
        });
        singleChoiceDialog.show();
    }

    /**
     * 多选对话框
     */
    ArrayList<Integer> choices= new ArrayList<>();
    private void showMultiChoiceDialog(final Task task){
        final List<TaskList> list = LitePal.where("listname != ?", taskList.getListName()).find(TaskList.class,true);

        final int size = list.size()  ;
        final boolean initchoices[] = new boolean[size];
        final String[] items = new String[size];

        for(int i = 0; i < size;i++){

            items[i] = list.get(i).getListName();

        }

        choices.clear();
        AlertDialog.Builder multChoiceDialog = new AlertDialog.Builder(context);
        multChoiceDialog.setTitle("复制至清单");
        multChoiceDialog.setMultiChoiceItems(items, initchoices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    choices.add(which);
                }else {
                    choices.remove(which);
                }
            }
        });
        multChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = choices.size();
                String str = "";
                for(int i = 0;i<size;i++){
                    str+="  " + choices.get(i);
                    Task task1 = task.copy();
                    taskDBHelper.addTask(task1);
                    TaskList targetTaskMenu = LitePal.where("name = ?", list.get(choices.get(i)).getListName()).find(TaskList.class,true).get(0);
                    targetTaskMenu.addTask(task1);
                    targetTaskMenu.save();
                }
                Toast.makeText(context,
                        "你选中了" + str,
                        Toast.LENGTH_SHORT).show();
            }
        });
        multChoiceDialog.show();
    }
}
