package com.example.smartcampusapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import android.os.Build;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Alert> alertList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Request notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAlerts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAlerts();

        AlertAdapter adapter = new AlertAdapter(alertList);
        recyclerView.setAdapter(adapter);

        // Update risk summary card
        updateRiskSummary();

        // Send notifications for all alerts
        for (Alert alert : alertList) {
            sendNotification(alert);
        }

        // Bottom nav: Alerts tab (active by default)
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        LinearLayout navAlerts = findViewById(R.id.navAlerts);
        LinearLayout navSchedule = findViewById(R.id.navSchedule);

        // Alerts tab is already active (teal color set in XML)

        // Schedule tab click → go to ScheduleActivity
        if (navSchedule != null) {
            navSchedule.setOnClickListener(v -> {
                Intent intent = new Intent(this, ScheduleActivity.class);
                intent.putExtra("EXTRA_TITLE", "PLANNING ACADÉMIQUE");
                startActivity(intent);
            });
        }
    }

    private void updateRiskSummary() {
        TextView riskSubtitle = findViewById(R.id.riskSubtitle);
        TextView riskLevel = findViewById(R.id.riskLevel);

        if (riskSubtitle != null) {
            riskSubtitle.setText(alertList.size() + " zones à risque détectées");
        }

        if (riskLevel != null) {
            // Count high severity alerts
            int highCount = 0;
            for (Alert a : alertList) {
                if (a.getSeverity() != null && a.getSeverity().equalsIgnoreCase("high")) {
                    highCount++;
                }
            }
            if (highCount > alertList.size() / 2) {
                riskLevel.setText("ÉLEVÉ");
                riskLevel.setTextColor(getColor(R.color.tanit_red));
                riskLevel.setBackgroundResource(R.drawable.bg_status_red);
            } else {
                riskLevel.setText("MODÉRÉ");
                riskLevel.setTextColor(getColor(R.color.tanit_amber));
                riskLevel.setBackgroundResource(R.drawable.bg_status_badge);
            }
        }
    }

    private void loadAlerts() {
        try {
            InputStream is = getAssets().open("alerts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, "UTF-8");
            JSONArray array = new JSONArray(jsonStr);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Alert alert = new Alert();

                alert.setId(obj.getInt("id"));
                alert.setType(obj.getString("type"));
                alert.setClassroom(obj.getString("classroom"));
                alert.setNew_room(obj.getString("new_room"));
                alert.setMessage(obj.getString("message"));
                alert.setInstructions(obj.getString("instructions"));

                // New fields
                if (obj.has("timestamp")) {
                    alert.setTimestamp(obj.getString("timestamp"));
                }
                if (obj.has("severity")) {
                    alert.setSeverity(obj.getString("severity"));
                }

                alertList.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Alert alert) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "alert_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Alertes Inondation", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Alertes de risque d'inondation du campus");
            notificationManager.createNotificationChannel(channel);
        }

        String severity = alert.getSeverity() != null ? alert.getSeverity() : "moderate";
        String title = severity.equalsIgnoreCase("high")
                ? "⚠ ALERTE ÉLEVÉE — " + alert.getClassroom()
                : "⚡ Alerte — " + alert.getClassroom();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(alert.getMessage() + " → " + alert.getNew_room())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(alert.getMessage() + "\n📍 Déplacé vers: " + alert.getNew_room()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(alert.getId(), builder.build());
    }
}