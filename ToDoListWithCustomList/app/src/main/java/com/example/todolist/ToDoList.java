package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "to_do_list")
public class ToDoList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tittle;
    private String description;

    public boolean isCheck_task() {
        return check_task;
    }

    public void setCheck_task(boolean check_task) {
        this.check_task = check_task;
    }

    private boolean check_task;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
