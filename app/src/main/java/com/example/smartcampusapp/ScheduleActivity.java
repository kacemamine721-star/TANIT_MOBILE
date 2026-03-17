package com.example.smartcampusapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // 1. Safe Insets handling (Use the content view if header ID is unsure)
        View root = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Initialize UI with Null Checks (Prevents crashes if ID is wrong)
        TextView title = findViewById(R.id.detailTitle);
        Button btnBack = findViewById(R.id.btnBack);
        RecyclerView rv = findViewById(R.id.recyclerViewSchedule);

        // Set Title
        String zone = getIntent().getStringExtra("EXTRA_TITLE");
        if (title != null) {
            title.setText(zone != null ? zone : "PLANNING");
        }

        // Back Button
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Setup RecyclerView
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));

            List<ScheduleItem> scheduleList = new ArrayList<>();
            scheduleList.add(new ScheduleItem("Theorie des langages et automates", "Lun 10:00-12:00-C17", "Mer 10:00-12:00-C17", "reporté"));
            scheduleList.add(new ScheduleItem("Geotechnical engineering", "Lun 14:00-16:00-C17", "Lun 14:00-16:00-C18", "📍 DÉLOCALISÉ"));
            scheduleList.add(new ScheduleItem("télédetection", "Lun 08:00-13:00-C17", "ANNULÉ - report TBD", "❌ ANNULÉ"));

            ScheduleAdapter adapter = new ScheduleAdapter(scheduleList);
            rv.setAdapter(adapter);
        }
    }
}