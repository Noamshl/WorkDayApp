package com.example.myapplication.ui.WorkDisplay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.MonthOfWork;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.WorkData;
import com.example.myapplication.data.model.WorkRepository;

public class MonthOfWorkAdapter extends RecyclerView.Adapter<MonthOfWorkAdapter.MonthOfWorkViewHolder> {


    MonthOfWork monthOfWork;
    Context context;
    public  MonthOfWorkAdapter(MonthOfWork monthOfWork,Context context){
        this.monthOfWork = monthOfWork;
        this.context = context;
    }

    public void setMonthOfWork(MonthOfWork monthOfWork) {
        this.monthOfWork = monthOfWork;
    }

    @NonNull
    @Override
    public MonthOfWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.work_details,parent,false);
        return new MonthOfWorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthOfWorkViewHolder holder, int position) {
        WorkData workData = monthOfWork.getWorkOfMonth().get(position);
        holder.dateTxt.setText(monthOfWork.getWorkOfMonth().get(position).getDate());
        holder.jobTxt.setText(monthOfWork.getWorkOfMonth().get(position).getJob());
        Toast.makeText(context,monthOfWork.getWorkOfMonth().get(position).getHour().toString(),Toast.LENGTH_SHORT).show();
        holder.hoursTxt.setText(monthOfWork.getWorkOfMonth().get(position).getHour().toString());
        holder.notesTxt.setText(monthOfWork.getWorkOfMonth().get(position).getNotes());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.deletion_alert);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            WorkRepository workRepository = new WorkRepository();
                            workRepository.deleteWorkData(monthOfWork,position, new SimpleCallBack<Boolean>() {
                                @Override
                                public void call(Boolean data) {
                                    if(!data) {
                                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return monthOfWork.getWorkOfMonth().size();
    }

    public class MonthOfWorkViewHolder  extends RecyclerView.ViewHolder {
        TextView dateTxt,jobTxt,hoursTxt,notesTxt;
        ImageButton deleteBtn;
        public MonthOfWorkViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.date_txt);
            jobTxt = itemView.findViewById(R.id.job_txt);
            hoursTxt = itemView.findViewById(R.id.hours_txt);
            notesTxt = itemView.findViewById(R.id.notes_txt);
            deleteBtn = itemView.findViewById(R.id.delete_workBtn);

//
        }

    }
}
