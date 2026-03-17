package com.example.smartcampusapp;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ScheduleItem> items;

    public ScheduleAdapter(List<ScheduleItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleItem item = items.get(position);

        holder.subject.setText(item.getSubject());
        holder.oldTime.setText(item.getOldTime());
        holder.newTime.setText(item.getNewTime());
        holder.status.setText(item.getStatus().toUpperCase());

        // Strikethrough on old time
        holder.oldTime.setPaintFlags(holder.oldTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // Dynamic status badge colors based on status type
        String statusLower = item.getStatus().toLowerCase();
        int textColor;
        int bgRes;

        if (statusLower.contains("annul")) {
            textColor = holder.itemView.getContext().getColor(R.color.tanit_red);
            bgRes = R.drawable.bg_status_red;
        } else if (statusLower.contains("déloc") || statusLower.contains("deloc")) {
            textColor = holder.itemView.getContext().getColor(R.color.tanit_teal);
            bgRes = R.drawable.bg_status_green;
        } else {
            // reporté / default
            textColor = holder.itemView.getContext().getColor(R.color.tanit_amber);
            bgRes = R.drawable.bg_status_badge;
        }

        holder.status.setTextColor(textColor);
        holder.status.setBackgroundResource(bgRes);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, oldTime, newTime, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.tvSubject);
            oldTime = itemView.findViewById(R.id.tvOldTime);
            newTime = itemView.findViewById(R.id.tvNewTime);
            status = itemView.findViewById(R.id.tvStatusBadge);
        }
    }
}