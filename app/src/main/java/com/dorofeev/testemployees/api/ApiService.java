package com.dorofeev.testemployees.api;

import com.dorofeev.testemployees.pojo.EmployeeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();
}
