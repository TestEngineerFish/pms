package com.einyun.app.patrol.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.Patrol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PatrolListTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public List<Patrol> stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<List<Patrol>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(List<Patrol> someObjects) {
        return gson.toJson(someObjects);
    }
}
