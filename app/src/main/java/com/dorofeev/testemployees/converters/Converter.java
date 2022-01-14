package com.dorofeev.testemployees.converters;

import androidx.room.TypeConverter;

import com.dorofeev.testemployees.pojo.Specialty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public String listSpecialityToString(List<Specialty> specialtyList) {

        return new Gson().toJson(specialtyList);

//        JSONArray jsonArray = new JSONArray();
//        for (Specialty specialty : specialtyList) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("specialty_id", specialty.getSpecialtyId());
//                jsonObject.put("name", specialty.getName());
//
//                jsonArray.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return jsonArray.toString();
    }

    @TypeConverter
    public List<Specialty> stringToListSpecialty(String specialitiesAsString) {
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(specialitiesAsString, ArrayList.class);
        ArrayList<Specialty> specialtyArrayList = new ArrayList<>();
        for (Object o : objects) {
            specialtyArrayList.add(gson.fromJson(o.toString(), Specialty.class));
        }
        return specialtyArrayList;
    }
}
