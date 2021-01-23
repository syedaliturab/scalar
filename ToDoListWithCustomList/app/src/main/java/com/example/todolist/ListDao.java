package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ListDao {
    @Insert
    Long insertTask(ToDoList task);
    @Update
    void updateTask(ToDoList task);
    @Query("Select * from to_do_list")
    LiveData<List<ToDoList>> getTask();
    @Delete
    void deleteTask(ToDoList task);
}

