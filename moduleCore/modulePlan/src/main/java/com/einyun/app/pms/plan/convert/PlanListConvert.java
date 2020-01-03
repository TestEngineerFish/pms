package com.einyun.app.pms.plan.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.Plan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PlanListConvert {
    Gson gson = new Gson();

    @TypeConverter
    public List<Plan> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Plan>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<Plan> someObjects) {
        return gson.toJson(someObjects);
    }

    public Gson getGson() {
        return gson;
    }
}
