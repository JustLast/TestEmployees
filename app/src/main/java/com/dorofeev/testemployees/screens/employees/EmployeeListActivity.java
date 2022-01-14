package com.dorofeev.testemployees.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dorofeev.testemployees.adapters.EmployeeAdapter;
import com.dorofeev.testemployees.api.ApiFactory;
import com.dorofeev.testemployees.api.ApiService;
import com.dorofeev.testemployees.databinding.ActivityMainBinding;
import com.dorofeev.testemployees.pojo.Employee;
import com.dorofeev.testemployees.pojo.EmployeeResponse;
import com.dorofeev.testemployees.pojo.Specialty;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EmployeeAdapter employeeAdapter;

    private EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employeeAdapter = new EmployeeAdapter();
        employeeAdapter.setEmployeeList(new ArrayList<Employee>());
        binding.recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewEmployees.setAdapter(employeeAdapter);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        employeeViewModel.getEmployeesList().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employeeList) {
                employeeAdapter.setEmployeeList(employeeList);
                if (employeeList != null) {
                    for (Employee employee : employeeList) {
                        List<Specialty> specialtyList = employee.getSpecialty();
                        if (specialtyList != null) {
                            for (Specialty specialty : specialtyList) {
                                Log.i("SPECIALTY: ", "Индекс: " + specialty.getSpecialtyId() + ", " + "Название: " + specialty.getName());
                            }
                        }
                    }
                }
            }
        });
        employeeViewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if(throwable != null) {
                    Toast.makeText(EmployeeListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("ERRORS: ", throwable.getMessage());
                    employeeViewModel.clearErrors();
                }
            }
        });
        employeeViewModel.loadData();
    }
}