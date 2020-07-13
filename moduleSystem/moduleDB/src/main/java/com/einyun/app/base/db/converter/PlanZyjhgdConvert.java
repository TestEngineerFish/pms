package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.PlanInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PlanZyjhgdConvert {
    Gson gson = new Gson();

    @TypeConverter
    public PlanInfo.Data.Zyjhgd stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<PlanInfo.Data.Zyjhgd>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(PlanInfo.Data.Zyjhgd someObjects) {
        return gson.toJson(someObjects);
    }
}
