package com.example.smartcampusapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Edge-to-edge insets
        View root = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Title
        TextView title = findViewById(R.id.detailTitle);
        String zone = getIntent().getStringExtra("EXTRA_TITLE");
        if (title != null) {
            title.setText(zone != null ? zone : "PLANNING");
        }

        // Back Button (now ImageButton)
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Setup RecyclerView
        RecyclerView rv = findViewById(R.id.recyclerViewSchedule);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));

            List<ScheduleItem> scheduleList = new ArrayList<>();
            scheduleList.add(new ScheduleItem(
                    "Théorie des langages et automates",
                    "Lun 10:00-12:00 — Salle 17",
                    "Mer 10:00-12:00 — Salle 17",
                    "reporté"));
            scheduleList.add(new ScheduleItem(
                    "Géotechnique",
                    "Lun 14:00-16:00 — Salle 17",
                    "Lun 14:00-16:00 — Salle 18",
                    "délocalisé"));
            scheduleList.add(new ScheduleItem(
                    "Télédétection",
                    "Lun 08:00-13:00 — Salle 17",
                    "ANNULÉ — report à confirmer",
                    "annulé"));

            ScheduleAdapter adapter = new ScheduleAdapter(scheduleList);
            rv.setAdapter(adapter);
        }
    }
}