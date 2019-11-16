package com.example.personalassistant.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalassistant.data.TaskList;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    Context context;
    private SQLiteDatabase db;

    //数据库的名称
    private String DB_NAME = "db.db";
    //数据库的地址
    private String DB_PATH = "/data/data/com.example.personalassistant/databases/";

    public DBHelper(Context context){
        this.context = context;
        initFile();
        db = SQLiteDatabase.openDatabase("/data/data/com.example.personalassistant/databases/db.db",
                null, SQLiteDatabase.OPEN_READWRITE);
        LitePal.saveAll(this.getPDQuestion());//把查到的数据保存到LitePal中，方便使用查询
    }
    // 获取数据库中的表（这里只写入了一张表）
    public List<TaskList> getPDQuestion() {
        List<TaskList> list = new ArrayList<>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from tasklist", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                TaskList pdBean = new TaskList();
                pdBean.setListName(cursor.getString(cursor.getColumnIndex("listname")));//题目内容
                pdBean.setType(cursor.getString(cursor.getColumnIndex("type")));//A答案
                list.add(pdBean);
            }
        }
        return list;
    }

    private void initFile(){
        //判断数据库是否拷贝到相应的目录下
        if (new File(DB_PATH + DB_NAME).exists() == false) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //复制文件
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];//用来复制文件
                int length;//保存已经复制的长度
                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}