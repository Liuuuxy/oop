package com.example.personalassistant.data;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Task extends LitePalSupport implements Serializable {
    private int id;
    private String name;
    private String Content;
    private TaskList tlist;
    private int type;
    private int level;

    public Task copy() {
        return new Task(this.name, this.Content, this.type, this.level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskList getTlist() {
        return tlist;
    }

    public void setTlist(TaskList tlist) {
        this.tlist = tlist;
    }

    public Task(String name, String content, int type, int level) {
        this.name = name;
        this.Content = content;
        this.type = type;
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
