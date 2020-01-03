package com.einyun.app.base.db.converter;

import androidx.room.TypeConverter;

import com.einyun.app.base.db.bean.PatrolButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @ProjectName: redsun
 * @Package: com.example.shimaostaff.checkworkorderlist.offline.db.convert
 * @ClassName: NodeTypeConvert
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/18 12:10
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/18 12:10
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ButtonTypeConvert {
    Gson gson = new Gson();

    @TypeConverter
    public List<PatrolButton> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<PatrolButton>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<PatrolButton> someObjects) {
        return gson.toJson(someObjects);
    }
}
