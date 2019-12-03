package com.einyun.app.common.ui.widget.selectpopview;

import java.util.List;

public class SMFilterItem {

    private String id;
    private String  name;
    private String  keyName;
    private boolean selected;
    private boolean reservedSelected;
    private Integer level;
    private boolean discardBrothers;

    private List<SMFilterItem> m_items;

    private SMFilterItem m_parent;

    private SMFilterItem    m_grandSon;
    private String          m_grandSonRelateKey;

    public SMFilterItem(String id,
                        String name,
                        String keyName,
                        Integer level,
                        boolean discardBrothers,
                        List<SMFilterItem> childs,
                        SMFilterItem parent){
        this.id = id;
        this.name = name;
        this.keyName = keyName;
        this.level = level;
        this.discardBrothers = discardBrothers;
        this.m_items = childs;
        this.m_parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        //reset grandson selected status
        resetGrandsonSelectStatus();
    }

    public void resetGrandsonSelectStatus(){
        if(m_grandSon != null){
            for (SMFilterItem item : m_grandSon.getM_items()){
                item.setSelected(false);
            }
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isDiscardBrothers() {
        return discardBrothers;
    }

    public void setDiscardBrothers(boolean discardBrothers) {
        this.discardBrothers = discardBrothers;
    }

    public List<SMFilterItem> getM_items() {
        return m_items;
    }

    public void setM_items(List<SMFilterItem> m_items) {
        this.m_items = m_items;
    }

    public SMFilterItem getM_parent() {
        return m_parent;
    }

    public void setM_parent(SMFilterItem m_parent) {
        this.m_parent = m_parent;
    }

    public boolean isReservedSelected() {
        return reservedSelected;
    }

    public void setReservedSelected(boolean reservedSelected) {
        this.reservedSelected = reservedSelected;
    }

    public SMFilterItem getM_grandSon() {
        return m_grandSon;
    }

    public void setM_grandSon(SMFilterItem m_grandSon) {
        this.m_grandSon = m_grandSon;
    }

    public String getM_grandSonRelateKey() {
        return m_grandSonRelateKey;
    }

    public void setM_grandSonRelateKey(String m_grandSonRelateKey) {
        this.m_grandSonRelateKey = m_grandSonRelateKey;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
