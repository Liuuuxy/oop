package com.example.personalassistant.data;

import com.example.personalassistant.data.Task;

public class Temporary extends Task {

    private String deadline;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Temporary(String name, String content, int type, int level, String ddl) {
        super(name, content, type, level);
        this.deadline = ddl;
    }
}
