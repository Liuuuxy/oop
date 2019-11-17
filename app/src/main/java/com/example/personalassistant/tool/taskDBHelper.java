package com.example.personalassistant.tool;

import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.Temporary;

public class taskDBHelper {
    public static void addTask(Task task){
       task.save();
    }
}
