package com.example.co2124.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.co2124.R;
import com.example.co2124.model.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    //data source
    private List<Course> courses = new ArrayList<>();

    //click listener interfaces
    public interface OnItemClickListener { void onItemClick(Course course); }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener l) { this.listener = l; }

    public interface OnItemLongClickListener { void onItemLongClick(Course course); }
    private OnItemLongClickListener longClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener l) { this.longClickListener = l; }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course = courses.get(position);
        holder.tvCode.setText(course.courseCode);
        holder.tvName.setText(course.courseName);
        holder.tvLecturer.setText(course.lecturerName);

        //short-click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(course);
        });

        //long-click
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(course);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    //updates data and refreshes list
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        TextView tvCode, tvName, tvLecturer;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            tvCode     = itemView.findViewById(R.id.tvCourseCode);
            tvName     = itemView.findViewById(R.id.tvCourseName);
            tvLecturer = itemView.findViewById(R.id.tvLecturerName);
        }
    }
}
