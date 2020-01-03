package com.einyun.app.base.db.bean;

import androidx.room.TypeConverters;
import com.einyun.app.base.db.converter.PatrolContentConvert;

public
class PatrolMain{
    private int id;
    @TypeConverters(PatrolContentConvert.class)
    private PatrolContent zyxcgd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PatrolContent getZyxcgd() {
        return zyxcgd;
    }

    public void setZyxcgd(PatrolContent zyxcgd) {
        this.zyxcgd = zyxcgd;
    }
}
