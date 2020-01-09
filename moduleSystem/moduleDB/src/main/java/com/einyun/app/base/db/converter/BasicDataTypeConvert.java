package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.bean.WorkNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BasicDataTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public Object stringToSomeObjectList(String data) {
        return data;
    }

    @TypeConverter
    public String someObjectListToString(Object someObjects) {
        return gson.toJson(someObjects);
    }
}
