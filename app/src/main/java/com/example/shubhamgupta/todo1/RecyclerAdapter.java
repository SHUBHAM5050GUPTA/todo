package com.example.shubhamgupta.todo1;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import static android.R.attr.button;

/**
 * Created by Shubham Gupta on 03-02-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {
    private Context mContext;
    private ArrayList<Task> mTaskArrayList;
    private TaskClickListner mListner;

    public interface TaskClickListner
    {
        void OnItemClick(View view , int position);
        void OnRemoveClick(int position);

    }

    public RecyclerAdapter(Context mContext, ArrayList<Task> mNotesArrayList,TaskClickListner mListner) {
        this.mContext = mContext;
        this.mTaskArrayList = mNotesArrayList;
        this.mListner=mListner;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.recylerview_item,parent,false);
        return  new TaskViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        Task mTask=mTaskArrayList.get(position);
        holder.titleTextView.setText(mTask.getTitle());
        holder.descTextView.setText(mTask.getDescription());
        holder.remindDateTextView.setText(mTask.getRemindDate());
        holder.remindTimeTextView.setText(mTask.getRemindTime());
        holder.duedateTextView.setText(mTask.getDueDate());
        String letter=String.valueOf(mTask.getTitle().charAt(0)).toUpperCase();

        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable textDrawable=TextDrawable.builder().buildRound(letter,generator.getRandomColor());
        holder.imageView.setImageDrawable(textDrawable);

    }

    @Override
    public int getItemCount() {
        return mTaskArrayList.size();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTextView;
        TextView descTextView;
        CheckBox chexkBox;
        TextView remindDateTextView;
        TextView remindTimeTextView;
        TextView duedateTextView;
        ImageView imageView;
        TaskClickListner mTaskClickListner;


        public TaskViewHolder(View itemView,TaskClickListner mListner) {
            super(itemView);
            mTaskClickListner=mListner;
            itemView.setOnClickListener(this);
            titleTextView= (TextView) itemView.findViewById(R.id.title);
            descTextView= (TextView) itemView.findViewById(R.id.description);
            remindDateTextView= (TextView) itemView.findViewById(R.id.remind_date);
            remindTimeTextView= (TextView) itemView.findViewById(R.id.remind_time);
            duedateTextView= (TextView) itemView.findViewById(R.id.due_date);
            imageView= (ImageView) itemView.findViewById(R.id.task_image);
            chexkBox= (CheckBox) itemView.findViewById(R.id.task_checkbox);
            chexkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            int position=getAdapterPosition();
            if(position!= RecyclerView.NO_POSITION) {
                if (id == R.id.task_checkbox) {
                    mTaskClickListner.OnRemoveClick(position);

                } else if (id == R.id.task_view) {
                    mTaskClickListner.OnItemClick(view, position);

                }
            }

        }
    }
}
