package com.example.personalassistant.data;

public class Cycle extends Task {
    private String executeDate;
    private int count;
    private int repeatCycle;

    public Cycle(String name, String content, int type,int level, String ddl, int num, int repeatCycle) {
        super(name, content, type,level);
        this.count = num;
        this.executeDate = ddl;
        this.repeatCycle = repeatCycle;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRepeatCycle() {
        return repeatCycle;
    }

    public void setRepeatCycle(int repeatCycle) {
        this.repeatCycle = repeatCycle;
    }

    public String Type() {
        return "周期任务";
    }
}
