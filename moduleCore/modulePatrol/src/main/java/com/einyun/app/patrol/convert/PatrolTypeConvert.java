package com.einyun.app.patrol.convert;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.entity.Patrol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @ProjectName: redsun
 * @Package: com.example.shimaostaff.checkworkorderlist.offline.db.convert
 * @ClassName: MasterTypeConvert
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/18 12:08
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/18 12:08
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PatrolTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public Patrol stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Patrol>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectToString(Patrol someObjects) {
        return gson.toJson(someObjects);
    }
}
