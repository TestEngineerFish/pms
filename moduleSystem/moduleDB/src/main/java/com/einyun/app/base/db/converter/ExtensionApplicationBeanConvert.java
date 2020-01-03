package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.bean.ExtensionApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ExtensionApplicationBeanConvert {
    Gson gson = new Gson();

    @TypeConverter
    public ExtensionApplication stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<ExtensionApplication>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(ExtensionApplication someObjects) {
        return gson.toJson(someObjects);
    }
}
