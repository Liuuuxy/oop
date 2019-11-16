package com.example.personalassistant.data;

import java.util.List;

public class Long extends Task {
    String ddl;
    List<Temporary> templist;

    public List<Temporary> getTemplist() {
        return templist;
    }

    public void setTemplist(List<Temporary> templist) {
        this.templist = templist;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }


    public Long(String name, String content, int type, String ddl, List<Temporary> tlist){
        super(name,content,type);
        this.ddl=ddl;
        this.templist=tlist;
    }
}
