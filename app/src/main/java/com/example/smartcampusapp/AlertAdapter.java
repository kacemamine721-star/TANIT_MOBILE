package com.example.smartcampusapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Button;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private List<Alert> alertList;

    public AlertAdapter(List<Alert> alerts) { this.alertList = alerts; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alert alert = alertList.get(position);

        holder.type.setText(alert.getClassroom() + " — " + alert.getType());
        holder.message.setText(alert.getMessage());

        int color;
        String type = alert.getType().toLowerCase();

        if (type.contains("inond") || type.contains("urgent")) {
            color = holder.itemView.getContext().getColor(R.color.tanit_red);
        } else if (type.contains("risk") || type.contains("moder")) {
            color = holder.itemView.getContext().getColor(R.color.tanit_yellow);
        } else {
            color = holder.itemView.getContext().getColor(R.color.tanit_green);
        }

        holder.sideAccentBar.setBackgroundColor(color);

        // Fix: Use ColorStateList for MaterialButtons
        android.content.res.ColorStateList colorStateList = android.content.res.ColorStateList.valueOf(color);
        holder.btnAction.setTextColor(colorStateList);
        holder.btnAction.setText("Voir Planning"); // Renamed button text

        // NAVIGATION LOGIC
        holder.btnAction.setOnClickListener(v -> {
            android.content.Context context = v.getContext();
            android.content.Intent intent = new android.content.Intent(context, ScheduleActivity.class);

            // Pass the data to the next activity
            intent.putExtra("EXTRA_TITLE", alert.getClassroom() + " - " + alert.getType());
            intent.putExtra("EXTRA_INSTRUCTIONS", alert.getInstructions());
            intent.putExtra("EXTRA_COLOR", color);

            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() { return alertList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type, message; // Renamed these
        public View sideAccentBar;     // Added this for the color strip
        public Button btnAction;       // Added this for the "Voir" button

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.textViewType);
            message = itemView.findViewById(R.id.textViewMessage);
            sideAccentBar = itemView.findViewById(R.id.sideAccentBar);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
