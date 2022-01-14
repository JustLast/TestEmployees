package com.dorofeev.testemployees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorofeev.testemployees.R;
import com.dorofeev.testemployees.databinding.EmployeeItemBinding;
import com.dorofeev.testemployees.pojo.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employeeList;

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.binding.textViewName.setText(employeeList.get(position).getFirstName());
        holder.binding.textViewLastName.setText(employeeList.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private EmployeeItemBinding binding;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EmployeeItemBinding.bind(itemView);
        }
    }
}
