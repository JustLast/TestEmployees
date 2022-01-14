package com.dorofeev.testemployees.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dorofeev.testemployees.pojo.Employee;

import java.util.List;

@Dao
public interface EmployeeDAO {
    @Query("SELECT * FROM employees")
    LiveData<List<Employee>> getAllEmployees();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmployees(List<Employee> employeeList);

    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}
