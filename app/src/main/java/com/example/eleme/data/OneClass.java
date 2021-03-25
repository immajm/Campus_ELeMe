package com.example.eleme.data;

public class OneClass {
    public int class_id;
    public String className;

    public OneClass(int class_id, String className) {
        this.class_id = class_id;
        this.className = className;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
