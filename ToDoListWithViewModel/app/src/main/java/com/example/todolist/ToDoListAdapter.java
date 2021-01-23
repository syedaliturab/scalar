package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

public class ToDoListAdapter  extends ArrayAdapter {
    private int resourceLayout;
    private Context mContext;
    Activity act;
    ItemViewModel viewModel;

    public ToDoListAdapter(@NonNull Context context, int resource, @NonNull List<ToDoList> objects, Activity activity) {
        super(context, resource, objects);
        this.resourceLayout=resource;
        this.mContext=context;
         this.act=activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        viewModel = new ViewModelProvider((ViewModelStoreOwner) act).get(ItemViewModel.class);
       if(convertView==null)
       {
           LayoutInflater vi;
           vi = LayoutInflater.from(mContext);
           view = vi.inflate(resourceLayout, null);
       }
       ToDoList item= (ToDoList) getItem(position);
       if(item!=null)
       {
           TextView title=(TextView) view.findViewById(R.id.todoTittle);
           title.setText(item.getTittle());
           //TextView id=(TextView) view.findViewById(R.id.todoId);
         //  id.setText(item.getId());
           CheckBox comp= (CheckBox) view.findViewById(R.id.checkBox);
           comp.setChecked(item.isCheck_task());
           Log.d("check","selected")  ;

           comp.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  if( comp.isChecked())
                  {

                      ToDoList t=(ToDoList) getItem(position);
                      t.setCheck_task(true);viewModel.completeItem(t);
                      viewModel.completeItem(t);
                     // taskOp.taskCheckboxOperation(t);
                  }
                  else
                  {
                      ToDoList t=(ToDoList) getItem(position);
                      t.setCheck_task(false);
                      viewModel.completeItem(t);
                     // taskOp.taskCheckboxOperation(t);
                  }
               }
           });

       }
       return view;
    }

}
