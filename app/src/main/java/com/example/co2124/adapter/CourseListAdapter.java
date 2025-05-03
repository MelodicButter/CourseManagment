package com.example.co2124.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.co2124.model.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.VH> {
    //data source
    private List<Course> courses = new ArrayList<>();

    //update data and refresh list
    public void setCourses(List<Course> list) {
        this.courses = list;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        //display course name
        holder.tv.setText(courses.get(pos).courseName);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    //view holder
    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(View item) {
            super(item);
            tv = item.findViewById(android.R.id.text1);
        }
    }
}
