package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;
import com.einyun.app.base.db.entity.Distribute;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DistributeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public Distribute stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Distribute>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(Distribute someObjects) {
        return gson.toJson(someObjects);
    }
}
