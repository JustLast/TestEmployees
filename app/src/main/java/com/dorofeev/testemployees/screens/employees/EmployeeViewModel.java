package com.dorofeev.testemployees.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dorofeev.testemployees.api.ApiFactory;
import com.dorofeev.testemployees.api.ApiService;
import com.dorofeev.testemployees.data.AppDatabase;
import com.dorofeev.testemployees.pojo.Employee;
import com.dorofeev.testemployees.pojo.EmployeeResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel {

    private static AppDatabase db;
    private LiveData<List<Employee>> employeesList;
    private MutableLiveData<Throwable> errors;

    private CompositeDisposable compositeDisposable;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        employeesList = db.getEmployeeDAO().getAllEmployees();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Employee>> getEmployeesList() {
        return employeesList;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.setValue(null);
    }

    private void insertEmployees(List<Employee> employeeList) {
        new InsertEmployeesTask().execute(employeeList);
//        db.getEmployeeDAO().insertEmployees(employeeList);
    }

    private void deleteAllEmployees() {
        new DeleteAllEmployeesTask().execute();
//        db.getEmployeeDAO().deleteAllEmployees();
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();

        compositeDisposable = new CompositeDisposable();

        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Throwable {
                        deleteAllEmployees();
                        insertEmployees(employeeResponse.getEmployeeList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        errors.setValue(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {

        @Override
        protected Void doInBackground(List<Employee>... lists) {
            if (lists != null && lists.length > 0) {
                db.getEmployeeDAO().insertEmployees(lists[0]);
            }
            return null;
        }
    }

    private static class DeleteAllEmployeesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.getEmployeeDAO().deleteAllEmployees();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
