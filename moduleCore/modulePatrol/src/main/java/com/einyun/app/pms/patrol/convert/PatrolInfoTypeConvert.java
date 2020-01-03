package com.einyun.app.pms.patrol.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PatrolInfoTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public PatrolInfo stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<PatrolInfo>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(PatrolInfo someObjects) {
        return gson.toJson(someObjects);
    }
}
