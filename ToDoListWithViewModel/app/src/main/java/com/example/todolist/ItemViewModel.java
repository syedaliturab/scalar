package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    public MutableLiveData<ToDoList> selectedItem = new MutableLiveData<ToDoList>();
    public void selectItem(ToDoList item) {
        selectedItem.setValue(item);
    }
    public LiveData<ToDoList> getSelectedItem() {
        return selectedItem;
    }
    private final MutableLiveData<ToDoList> completedItem = new MutableLiveData<ToDoList>();
    public void completeItem(ToDoList item) {
        completedItem.setValue(item);
    }
    public LiveData<ToDoList> getcompletedItem() {
        return completedItem;
    }
}
