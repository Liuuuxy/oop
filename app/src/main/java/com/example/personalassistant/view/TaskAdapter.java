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
import com.example.personalassistant.data.Long;
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

    /**
     * goldTitleBeans 适配器里的数据集合
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换集合中两个数据的位置
        Collections.swap(mTaskList, fromPosition, toPosition);
        //刷新界面,局部刷新,索引会混乱
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        mTaskList.remove(position);
        //局部刷新,索引会混乱+集合越界
        notifyItemRemoved(position);
        mTaskList.get(position).delete();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        Button bmove;
        Button bcopy;
        Button bdetail;

        public ViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_item_title);
            bmove = itemView.findViewById(R.id.btn_move);
            bcopy = itemView.findViewById(R.id.btn_copy);
            bdetail = itemView.findViewById(R.id.btn_detail);
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
        final ViewHolder holder = new ViewHolder(v);
        holder.bdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTaskList.get(holder.getAdapterPosition()).getType() == 1 || mTaskList.get(holder.getAdapterPosition()).getType() == 0) {
                    Task t=mTaskList.get(holder.getAdapterPosition());
                    mTaskList.remove(holder.getAdapterPosition());
                    Intent intent = new Intent(context, InsideTask.class);
                    intent.putExtra("taaaskchoose",t);
                    context.startActivity(intent);
                } else if (mTaskList.get(holder.getAdapterPosition()).getType() == 2) {
                    Long t= (Long) mTaskList.get(holder.getAdapterPosition());
                    mTaskList.remove(holder.getAdapterPosition());
                    Intent intent = new Intent(context, LongTask.class);
                    intent.putExtra("longtaskchoose",t);
                    context.startActivity(intent);
                }
            }
        });
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

    private void showSingDialog(final Task task) {
        final List<TaskList> list = LitePal.findAll(TaskList.class, true);

        String[] items = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getListName();
        }
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle("转移至清单");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice != -1) {
                    taskDBHelper.addTask(task);
                    TaskList targetTaskList = LitePal.where("listname = ?", list.get(choice).getListName()).find(TaskList.class, true).get(0);
                    targetTaskList.addTask(task);
                    targetTaskList.save();
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
    ArrayList<Integer> choices = new ArrayList<>();

    private void showMultiChoiceDialog(final Task task) {
        final List<TaskList> list = LitePal.findAll(TaskList.class);

        final int size = list.size();
        final boolean initchoices[] = new boolean[size];
        final String[] items = new String[size];

        for (int i = 0; i < size; i++) {

            items[i] = list.get(i).getListName();

        }

        choices.clear();
        AlertDialog.Builder multiChoiceDialog = new AlertDialog.Builder(context);
        multiChoiceDialog.setTitle("复制至清单");
        multiChoiceDialog.setMultiChoiceItems(items, initchoices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    choices.add(which);
                } else {
                    choices.remove(which);
                }
            }
        });
        multiChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = choices.size();
                String str = "";
                for (int i = 0; i < size; i++) {
                    str += "  " + choices.get(i);
                    Task task1 = task.copy();
                    taskDBHelper.addTask(task1);
                    TaskList targetTaskList = LitePal.where("listname = ?", list.get(choices.get(i)).getListName()).find(TaskList.class, true).get(0);
                    targetTaskList.addTask(task1);
                    targetTaskList.save();
                }
                Toast.makeText(context,
                        "你选中了" + str,
                        Toast.LENGTH_SHORT).show();
            }
        });
        multiChoiceDialog.show();
    }
}
