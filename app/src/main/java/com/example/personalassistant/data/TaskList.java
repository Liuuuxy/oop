package com.example.personalassistant.data;

import com.example.personalassistant.data.Task;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList extends LitePalSupport implements Serializable {
    private List<Task> taskList=new ArrayList<>();
    private String listName;
    private String type;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
