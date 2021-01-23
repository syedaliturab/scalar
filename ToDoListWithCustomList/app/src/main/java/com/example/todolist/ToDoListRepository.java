package com.example.todolist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class ToDoListRepository {
    ListDao listDao;
    ToDoListRepository (Application app)
    {
        ToDoListDatabase db=ToDoListDatabase.getDatabase(app);
        listDao=db.getListDao();
    }
    public void insertTask(ToDoList task)
    {
        ToDoListDatabase.databaseWriteExecutor.execute(
                ()->{
                    listDao.insertTask(task);
                }
        );
    }
    public void updateTask(ToDoList task)
    {
        ToDoListDatabase.databaseWriteExecutor.execute(
                ()->{
                    listDao.updateTask(task);
                }
        );
    }
    public void deleteTask(ToDoList task)
    {
        ToDoListDatabase.databaseWriteExecutor.execute(
                ()->{
                    listDao.deleteTask(task);
                }
        );
    }
   LiveData<List<ToDoList>> getTasks ()
   {
       return listDao.getTask();
   }

}
