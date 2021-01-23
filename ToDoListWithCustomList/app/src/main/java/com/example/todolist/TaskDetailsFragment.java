package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailsFragment extends Fragment {
    public interface TodoOperation
    {
        public void todoOp(String type,ToDoList doList);
    }
    TodoOperation taskOp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ToDoList task;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetailsFragment newInstance(String param1, ToDoList param2) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            task =(ToDoList) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_task_details, container, false);
        Button add=(Button)view.findViewById(R.id.addDb) ;
        Button back=(Button)view.findViewById(R.id.cancel) ;
        Button edit=(Button)view.findViewById(R.id.updateDb);
        Button delete=(Button)view.findViewById(R.id.delete);
        EditText tittle=(EditText)view.findViewById(R.id.tittle) ;
        EditText description=(EditText)view.findViewById(R.id.description) ;
        String check=mParam1;
        if(check.equals("update"))
        {
            tittle.setText(task.getTittle());
            description.setText(task.getDescription());
            tittle.setEnabled(false);
            description.setEnabled(false);
            add.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskOp.todoOp("back",task);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
                tittle.setEnabled(true);
                description.setEnabled(true);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskOp.todoOp("delete",task);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.equals("update")) {
                    if(tittle.getText().toString().equals("")||tittle.getText().toString()==null)
                    {
                        Toast.makeText(getActivity(), "Tittle should not be empty", Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        task.setTittle(tittle.getText().toString());
                        task.setDescription(description.getText().toString());
                        taskOp.todoOp("update",task);
                    }

                }
                else{
                    if (tittle.getText().toString().equals("") || tittle.getText().toString() == null) {
                        Toast.makeText(getActivity(), "Tittle should not be empty", Toast.LENGTH_LONG).show();
                    } else {
                        task=new ToDoList();

                        task.setTittle(tittle.getText().toString());
                        task.setDescription(description.getText().toString());
                        taskOp.todoOp("add",task);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            taskOp = (TodoOperation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }
}