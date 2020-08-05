package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.PlanInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ExtensionApplicationPlanDBToWokerConvert {
    Gson gson = new Gson();

    @TypeConverter
    public List<PlanInfo.ExtensionApplication> stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<List<PlanInfo.ExtensionApplication>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<PlanInfo.ExtensionApplication> someObjects) {
        return gson.toJson(someObjects);
    }
}
