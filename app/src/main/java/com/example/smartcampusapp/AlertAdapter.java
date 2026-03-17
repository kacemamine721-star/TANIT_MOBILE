package com.example.smartcampusapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        // Title
        holder.type.setText(alert.getClassroom() + " — " + alert.getType());
        holder.message.setText(alert.getMessage());

        // Timestamp
        if (alert.getTimestamp() != null && holder.timestamp != null) {
            holder.timestamp.setText(alert.getTimestamp());
            holder.timestamp.setVisibility(View.VISIBLE);
        }

        // Relocation info
        if (holder.relocation != null) {
            holder.relocation.setText("Déplacé vers → " + alert.getNew_room());
        }

        // Severity-based styling
        int accentColor;
        String severityText;
        int severityBg;
        String severity = alert.getSeverity() != null ? alert.getSeverity().toLowerCase() : "moderate";

        if (severity.equals("high")) {
            accentColor = holder.itemView.getContext().getColor(R.color.tanit_red);
            severityText = "⚠ RISQUE ÉLEVÉ";
            severityBg = R.drawable.bg_status_red;
        } else if (severity.equals("moderate")) {
            accentColor = holder.itemView.getContext().getColor(R.color.tanit_amber);
            severityText = "⚡ MODÉRÉ";
            severityBg = R.drawable.bg_status_badge;
        } else {
            accentColor = holder.itemView.getContext().getColor(R.color.tanit_green);
            severityText = "✓ FAIBLE";
            severityBg = R.drawable.bg_status_green;
        }

        // Apply accent bar color
        holder.sideAccentBar.setBackgroundColor(accentColor);

        // Severity badge
        if (holder.severityBadge != null) {
            holder.severityBadge.setText(severityText);
            holder.severityBadge.setTextColor(accentColor);
            holder.severityBadge.setBackgroundResource(severityBg);
        }

        // Icon tint
        if (holder.iconSeverity != null) {
            holder.iconSeverity.setColorFilter(accentColor);
        }

        // Button styling
        android.content.res.ColorStateList colorStateList = android.content.res.ColorStateList.valueOf(
                holder.itemView.getContext().getColor(R.color.tanit_teal));
        holder.btnAction.setTextColor(colorStateList);
        holder.btnAction.setText("Voir Planning →");

        // NAVIGATION TO SCHEDULE
        holder.btnAction.setOnClickListener(v -> {
            android.content.Context context = v.getContext();
            android.content.Intent intent = new android.content.Intent(context, ScheduleActivity.class);
            intent.putExtra("EXTRA_TITLE", alert.getClassroom() + " - " + alert.getType());
            intent.putExtra("EXTRA_INSTRUCTIONS", alert.getInstructions());
            intent.putExtra("EXTRA_COLOR", accentColor);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return alertList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type, message, timestamp, relocation, severityBadge;
        public View sideAccentBar;
        public Button btnAction;
        public ImageView iconSeverity;
        public LinearLayout roomRelocInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.textViewType);
            message = itemView.findViewById(R.id.textViewMessage);
            timestamp = itemView.findViewById(R.id.textViewTimestamp);
            relocation = itemView.findViewById(R.id.textViewRelocation);
            severityBadge = itemView.findViewById(R.id.textViewSeverity);
            sideAccentBar = itemView.findViewById(R.id.sideAccentBar);
            btnAction = itemView.findViewById(R.id.btnAction);
            iconSeverity = itemView.findViewById(R.id.iconSeverity);
            roomRelocInfo = itemView.findViewById(R.id.roomRelocInfo);
        }
    }
}
