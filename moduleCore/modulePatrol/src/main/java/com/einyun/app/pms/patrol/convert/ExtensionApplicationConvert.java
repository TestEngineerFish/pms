package com.einyun.app.pms.patrol.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ExtensionApplicationConvert {
    Gson gson = new Gson();

    @TypeConverter
    public ExtensionApplication stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<ExtensionApplication>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(ExtensionApplication someObjects) {
        return gson.toJson(someObjects);
    }

    public Gson getGson(){
        return gson;
    }
}
