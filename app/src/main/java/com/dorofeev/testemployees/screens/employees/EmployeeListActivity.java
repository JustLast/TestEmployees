package com.dorofeev.testemployees.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.dorofeev.testemployees.adapters.EmployeeAdapter;
import com.dorofeev.testemployees.api.ApiFactory;
import com.dorofeev.testemployees.api.ApiService;
import com.dorofeev.testemployees.databinding.ActivityMainBinding;
import com.dorofeev.testemployees.pojo.Employee;
import com.dorofeev.testemployees.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity implements EmployeesListView{

    private ActivityMainBinding binding;
    private EmployeeAdapter employeeAdapter;
    private EmployeeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new EmployeeListPresenter(this);
        employeeAdapter = new EmployeeAdapter();
        employeeAdapter.setEmployeeList(new ArrayList<>());
        binding.recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewEmployees.setAdapter(employeeAdapter);

        presenter.loadData();
    }

    public void showData(List<Employee> employeeList) {
        employeeAdapter.setEmployeeList(employeeList);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }
}