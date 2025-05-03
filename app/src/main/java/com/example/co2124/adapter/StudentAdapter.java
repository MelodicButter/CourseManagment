package com.example.co2124.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.co2124.R;
import com.example.co2124.model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    //data source
    private List<Student> students = new ArrayList<>();

    //click listeners
    public interface OnStudentClickListener { void onStudentClick(Student student); }
    public interface OnStudentLongClickListener { void onStudentLongClick(Student student); }
    private OnStudentClickListener clickListener;
    private OnStudentLongClickListener longClickListener;
    public void setOnStudentClickListener(OnStudentClickListener l) { clickListener = l; }
    public void setOnStudentLongClickListener(OnStudentLongClickListener l) { longClickListener = l; }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int pos) {
        Student s = students.get(pos);
        holder.tvName.setText(s.name);
        holder.tvEmail.setText(s.email);
        holder.tvMatric.setText(s.matricNumber);

        //short-press
        holder.itemView.setOnClickListener(v -> {
            if(clickListener!=null&&pos!=RecyclerView.NO_POSITION) clickListener.onStudentClick(s);
        });

        //long-press
        holder.itemView.setOnLongClickListener(v -> {
            if(longClickListener!=null&&pos!=RecyclerView.NO_POSITION) {
                longClickListener.onStudentLongClick(s);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() { return students.size(); }

    //updates data and refreshes list
    public void setStudents(List<Student> list) {
        students = list;
        notifyDataSetChanged();
    }

    //view holder
    static class StudentHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvEmail,tvMatric;
        StudentHolder(View item) {
            super(item);
            tvName   = item.findViewById(R.id.tvStudentName);
            tvEmail  = item.findViewById(R.id.tvStudentEmail);
            tvMatric = item.findViewById(R.id.tvStudentMatric);
        }
    }
}
