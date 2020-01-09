package com.einyun.app.base.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.einyun.app.base.db.bean.ExtensionApplication;
import com.einyun.app.base.db.bean.PatrolButton;
import com.einyun.app.base.db.bean.PatrolMain;
import com.einyun.app.base.db.converter.BasicDataTypeConvert;
import com.einyun.app.base.db.converter.ButtonTypeConvert;
import com.einyun.app.base.db.converter.DataBeanTypeConvert;
import com.einyun.app.base.db.converter.ExtensionApplicationBeanConvert;
import com.google.gson.Gson;

import java.util.List;

@Entity(tableName = "basic_data_table", indices = {@Index(value = {"basicDataTypeEnum"}, unique = true)})
public class BasicDataDb {
    @NonNull
    @PrimaryKey
    private String basicDataTypeEnum;

    private String basicData;

    public BasicDataDb(@NonNull String basicDataTypeEnum, Object basicData) {
        this.basicDataTypeEnum = basicDataTypeEnum;
        this.basicData = new Gson().toJson(basicData);
    }

    public BasicDataDb(@NonNull String basicDataTypeEnum, String basicData) {
        this.basicDataTypeEnum = basicDataTypeEnum;
        this.basicData = basicData;
    }

    @NonNull
    public String getBasicDataTypeEnum() {
        return basicDataTypeEnum;
    }

    public void setBasicDataTypeEnum(@NonNull String basicDataTypeEnum) {
        this.basicDataTypeEnum = basicDataTypeEnum;
    }

    public String getBasicData() {
        return basicData;
    }

    public void setBasicData(String basicData) {
        this.basicData = basicData;
    }
}

