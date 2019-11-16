package com.example.personalassistant.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.tool.TouchCallBack;

import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements TouchCallBack {
    private List<Task> mTaskList;
    private Context context;
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
        private TaskAdapter.OnRecyclerViewItemClickListener mListener;
        public ViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            taskTitle = itemView.findViewById(R.id.tv_item_title);
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
        ViewHolder holder = new ViewHolder(v, myClickItemListener);
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
}
