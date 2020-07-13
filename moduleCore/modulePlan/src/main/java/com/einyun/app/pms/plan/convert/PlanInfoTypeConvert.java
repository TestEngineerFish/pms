package com.einyun.app.pms.plan.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PlanInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PlanInfoTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public PlanInfo stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<PlanInfo>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(PlanInfo someObjects) {
        return gson.toJson(someObjects);
    }
}
