package com.example.smartcampusapp;

import android.os.Bundle;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Only apply top padding to keep the bar below the status bar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        recyclerView = findViewById(R.id.recyclerViewAlerts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAlerts();

        AlertAdapter adapter = new AlertAdapter(alertList);
        recyclerView.setAdapter(adapter);

        for (Alert alert : alertList) {
            sendNotification(alert);
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

                // FIXED: Use setters instead of direct field access
                alert.setId(obj.getInt("id"));
                alert.setType(obj.getString("type"));
                alert.setClassroom(obj.getString("classroom"));
                alert.setNew_room(obj.getString("new_room"));
                alert.setMessage(obj.getString("message"));
                alert.setInstructions(obj.getString("instructions"));

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
                    "Classroom Alerts", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Classroom Alert")
                // FIXED: Use getters
                .setContentText(alert.getMessage() + " Move to: " + alert.getNew_room())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // FIXED: Use getter for ID
        notificationManager.notify(alert.getId(), builder.build());
    }
}