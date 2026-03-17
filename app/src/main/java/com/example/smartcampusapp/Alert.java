package com.example.smartcampusapp;

public class Alert {
    private int id;
    private String type;
    private String classroom;
    private String new_room;
    private String message;
    private String instructions;

    // --- GETTERS (For reading data) ---
    public int getId() { return id; }
    public String getType() { return type; }
    public String getClassroom() { return classroom; }
    public String getNew_room() { return new_room; }
    public String getMessage() { return message; }
    public String getInstructions() { return instructions; }

    // --- SETTERS (For writing data - Fixes your red errors!) ---
    public void setId(int id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
    public void setNew_room(String new_room) { this.new_room = new_room; }
    public void setMessage(String message) { this.message = message; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}