package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.bean.DelayExtensionApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DelayExtensionApplicationBeanConvert {
    Gson gson = new Gson();

    @TypeConverter
    public DelayExtensionApplication stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<DelayExtensionApplication>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(DelayExtensionApplication someObjects) {
        return gson.toJson(someObjects);
    }
}
