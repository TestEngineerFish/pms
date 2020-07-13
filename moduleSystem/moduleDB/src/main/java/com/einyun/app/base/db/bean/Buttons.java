package com.einyun.app.base.db.bean;

public class Buttons {
    private int id;
    private String name;
    private String alias;
    private String beforeScript;
    private String afterScript;
    private String groovyScript;
    private String urlForm;
    private boolean supportScript;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBeforeScript() {
        return beforeScript;
    }

    public void setBeforeScript(String beforeScript) {
        this.beforeScript = beforeScript;
    }

    public String getAfterScript() {
        return afterScript;
    }

    public void setAfterScript(String afterScript) {
        this.afterScript = afterScript;
    }

    public String getGroovyScript() {
        return groovyScript;
    }

    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    public String getUrlForm() {
        return urlForm;
    }

    public void setUrlForm(String urlForm) {
        this.urlForm = urlForm;
    }

    public boolean isSupportScript() {
        return supportScript;
    }

    public void setSupportScript(boolean supportScript) {
        this.supportScript = supportScript;
    }
}
