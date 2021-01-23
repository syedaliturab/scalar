package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.todolist.R.id.fragment_container_view;
import static com.example.todolist.R.id.fragment_container_view2;
import static com.example.todolist.R.id.visible;

public class MainActivity extends AppCompatActivity implements  TaskDetailsFragment.TodoOperation {
    private ToDoListRepository mRepository;
    FragmentContainerView fag1;
    FragmentContainerView fag2;
    ToDoList selectedItem=null,completedItem=null;
  ItemViewModel viewModel;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRepository = new ToDoListRepository(this.getApplication());
        ArrayList<String> tittleList = new ArrayList<String>();
         add = (Button) findViewById(R.id.addTask);
         fag1=(FragmentContainerView)findViewById(fragment_container_view);
         fag2=(FragmentContainerView)findViewById(fragment_container_view2);
        ArrayList<ToDoList> tasks = new ArrayList<ToDoList>();

                mRepository.getTasks().observe(this, new Observer<List<ToDoList>>() {
            @Override
            public void onChanged(List<ToDoList> toDoLists) {

                tasks.clear();
                ArrayList<ToDoList> incomplete=new ArrayList<ToDoList>();
                ArrayList<ToDoList> complete=new ArrayList<ToDoList>();
                for (ToDoList iten:toDoLists
                     ) {
                    if(iten.isCheck_task())
                    {
                        complete.add(iten);
                    }
                    else
                    {
                        incomplete.add(iten);
                    }
                }
                tasks.addAll(incomplete);
                tasks.addAll(complete);

                FragmentManager fragmentManager =getSupportFragmentManager();
                fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view,ListFragment.newInstance(tasks),null).commit();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fag1.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                fag2.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager =getSupportFragmentManager();
                fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view2,TaskDetailsFragment.newInstance("add",null),null).commit();

            }
        });

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(this, new Observer<ToDoList>() {
            @Override
            public void onChanged(ToDoList doList) {
                if(doList!=null) {
                    selectedItem = doList;
                    fag1.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                    fag2.setVisibility(View.VISIBLE);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view2, TaskDetailsFragment.newInstance("update", selectedItem), null).commit();
                }
            }
        });
        viewModel.getcompletedItem().observe(this, new Observer<ToDoList>() {
            @Override
            public void onChanged(ToDoList doList) {
                completedItem=doList;
                if (completedItem.getTittle() != null) {

            try {
                mRepository.updateTask(completedItem);
            } catch (Exception ex) {
                Toast.makeText(getParent(), "Error occured: " + ex, Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(getParent(), "Tittle should not be empty: error occured ", Toast.LENGTH_LONG).show();

                }
        });

        if(selectedItem!=null)
        {
            fag1.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            fag2.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager =getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view2,TaskDetailsFragment.newInstance("update",selectedItem),null).commit();

        }
    }



    @Override
    public void onBackPressed() {
        Log.d("2feg",""+getFragmentManager().getBackStackEntryCount())  ;
        if (getFragmentManager().getBackStackEntryCount() >1) {
            getFragmentManager().popBackStack();
            Log.d("2feg",""+getFragmentManager().getBackStackEntryCount())  ;
            fag2.setVisibility(View.GONE);
            fag1.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            selectedItem=null;
           viewModel.selectItem(null);
        } else {
            this.finish();

        }
    }

    @Override
    public void todoOp(String type, ToDoList task) {
        if(type.equals("add"))
        {
            if (task.getTittle() != null) {
                    try {
                        mRepository.insertTask(task);
                    } catch (Exception ex) {
                        Toast.makeText(this, "Error occured: " + ex, Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, "Tittle should not be empty: error occured ", Toast.LENGTH_LONG).show();
        }
        if(type.equals("delete"))
        {
            try {
                        mRepository.deleteTask(task);
                    } catch (Exception ex) {
                        Toast.makeText(this, "Error occured: " + ex, Toast.LENGTH_LONG).show();
                    }
        }
        if(type.equals("update"))
        {
            if (task.getTittle() != null) {
                    try {
                        mRepository.updateTask(task);
                    } catch (Exception ex) {
                        Toast.makeText(this, "Error occured: " + ex, Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, "Tittle should not be empty: error occured ", Toast.LENGTH_LONG).show();
        }
        getFragmentManager().popBackStack();
        Log.d("2feg","returned")  ;
        fag2.setVisibility(View.GONE);
        fag1.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        selectedItem=null;
        viewModel.selectItem(null);
    }





}
