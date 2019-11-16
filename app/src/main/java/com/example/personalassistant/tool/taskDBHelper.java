package com.example.personalassistant.tool;

import com.example.personalassistant.data.Cycle;
import com.example.personalassistant.data.Long;
import com.example.personalassistant.data.Task;
import com.example.personalassistant.data.Temporary;

public class taskDBHelper {
    public static void addTask(Task task){
        if (task.getType()==0){
            Temporary tempTask = (Temporary) task;
            tempTask.save();
        }
        if (task.getType()==1){
            Cycle periodicTak = (Cycle) task;
            periodicTak.save();
        }
        if (task.getType()==2){
            Long longtermTask = (Long) task;
            longtermTask.save();
        }
    }
}
