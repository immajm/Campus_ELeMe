package com.example.eleme.data;

import java.util.HashMap;
import java.util.Map;

public class OneLine {
    public Map<Integer,String> line=new HashMap<Integer,String>();

    public OneLine(Map<Integer, String> line) {
        this.line = line;
    }

    public Map<Integer, String> getLine() {
        return line;
    }

    public void setLine(Map<Integer, String> line) {
        this.line = line;
    }
    public void addLine(Integer i, String s) {
        this.line.put(i,s);
    }
}
