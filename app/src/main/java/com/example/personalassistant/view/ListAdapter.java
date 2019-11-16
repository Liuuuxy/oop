package com.example.personalassistant.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalassistant.R;
import com.example.personalassistant.data.TaskList;
import com.example.personalassistant.tool.TouchCallBack;

import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements TouchCallBack {

    private List<TaskList> mListList;
    private Context context;
    private OnRecyclerViewItemClickListener myClickItemListener;

    /**
     * mListList 适配器里的数据集合
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换集合中两个数据的位置
        Collections.swap(mListList, fromPosition, toPosition);
        //刷新界面,局部刷新,索引会混乱
        notifyItemMoved(fromPosition, toPosition);

    }

    @Override
    public void onItemDelete(int position) {
        mListList.remove(position);
        //局部刷新,索引会混乱+集合越界
        notifyItemRemoved(position);
        mListList.get(position).delete();
    }

    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.myClickItemListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listTitle;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;
            listTitle = itemView.findViewById(R.id.tv_list_title);
        }

        @Override
        public void onClick(View v) {
            //getAdapterPosition()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
            mListener.onItemClick(v, getAdapterPosition());
        }

    }

    public ListAdapter(Context context, List<TaskList> listList) {
        mListList = listList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(v, myClickItemListener);
       /* ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InsideList.class);
                context.startActivity(intent);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        TaskList tList = mListList.get(position);
        holder.listTitle.setText(tList.getListName());
    }

    @Override
    public int getItemCount() {
        return mListList.size();
    }

}
