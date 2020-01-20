package com.einyun.app.common.model.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.bean.PatrolButton;
import com.einyun.app.common.model.PicUrlModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PicUrlModelConvert {
    Gson gson=new Gson();

    @TypeConverter
    public PicUrlModel stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<PicUrlModel>() {}.getType();

        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public List<PicUrlModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<PicUrlModel>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<PicUrlModel> someObjects) {
        return gson.toJson(someObjects);
}

    @TypeConverter
    public String someObjectToString(PicUrlModel someObject) {
        return gson.toJson(someObject);
    }
}
