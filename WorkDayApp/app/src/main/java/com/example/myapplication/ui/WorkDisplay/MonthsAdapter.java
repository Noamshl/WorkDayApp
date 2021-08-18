package com.example.myapplication.ui.WorkDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.MonthOfWork;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.model.WorkRepository;

import java.util.List;

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.MonthsViewHolder> {

    private List<String> months;
    private List<MonthOfWork> monthOfWorks;
    private String username;
    private Context context;
    private String year;


    public MonthsAdapter(Context context,List<MonthOfWork> monthOfWorks,String username){
        this.context = context;
        this.months = months;
        this.monthOfWorks = monthOfWorks;
        this.username = username;
        year = "";
    }

    public void setMonthOfWorks(List<MonthOfWork> monthOfWorks) {
        this.monthOfWorks = monthOfWorks;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @NonNull
    @Override
    public MonthsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.month_work,viewGroup,false);
        return new MonthsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthsViewHolder monthsViewHolder, int i) {
        monthsViewHolder.month.setText(monthOfWorks.get(i).getMonthName());
        boolean isExpanded = monthOfWorks.get(i).isExpanded();
        if(isExpanded){

            WorkRepository workRepository = new WorkRepository();

            workRepository.getMonthOfWork(username, year,monthOfWorks.get(i), new SimpleCallBack<Boolean>() {
                @Override
                public void call(Boolean data) {
                    if(data){
                        //MonthOfWorkAdapter monthOfWorkAdapter = new MonthOfWorkAdapter(monthOfWorks.get(i),context);
                        //RecyclerView recyclerView = monthsViewHolder.itemView.findViewById(R.id.mow_recycler);
                        monthsViewHolder.monthOfWorkAdapter.setMonthOfWork(monthOfWorks.get(i));
                        monthsViewHolder.monthOfWorkAdapter.notifyDataSetChanged();
                        //recyclerView.setAdapter(monthOfWorkAdapter);
                        //recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        monthsViewHolder.expandableLayout.setVisibility(View.VISIBLE);
                    }
                    else
                        monthsViewHolder.expandableLayout.setVisibility(View.GONE);

                }
            });
        }
        else{
            monthOfWorks.get(i).getWorkOfMonth().clear();
            monthsViewHolder.expandableLayout.setVisibility(View.GONE);
    }
    }

    @Override
    public int getItemCount() {
        return monthOfWorks.size();
    }

    public class MonthsViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        ConstraintLayout expandableLayout;
        MonthOfWorkAdapter monthOfWorkAdapter;
        RecyclerView recyclerView;
        public MonthsViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.monthTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            recyclerView = itemView.findViewById(R.id.mow_recycler);
            monthOfWorkAdapter = new MonthOfWorkAdapter(new MonthOfWork(),context);
            recyclerView.setAdapter(monthOfWorkAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonthOfWork monthOfWork = monthOfWorks.get(getAdapterPosition());
                    monthOfWork.setExpanded(!monthOfWork.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
