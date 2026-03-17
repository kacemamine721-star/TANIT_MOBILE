package com.example.smartcampusapp;

public class ScheduleItem {
    private String subject;
    private String oldTime;
    private String newTime;
    private String status;

    public ScheduleItem(String subject, String oldTime, String newTime, String status) {
        this.subject = subject;
        this.oldTime = oldTime;
        this.newTime = newTime;
        this.status = status;
    }

    // Getters
    public String getSubject() { return subject; }
    public String getOldTime() { return oldTime; }
    public String getNewTime() { return newTime; }
    public String getStatus() { return status; }
}
