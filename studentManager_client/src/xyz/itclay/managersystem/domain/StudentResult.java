package xyz.itclay.managersystem.domain;

import java.io.Serializable;

/**
 *
 * @author ZhangSenmiao
 * @date 2021/1/10 16:33
 **/
public class StudentResult implements Serializable {
    private static final long serialVersionUID = 1;
    private String sid;
    private String name;
    private int c;
    private int java;
    private int network;

    public StudentResult() {
    }

    public StudentResult(String sid, String name, int c, int java, int network) {
        this.sid = sid;
        this.name = name;
        this.c = c;
        this.java = java;
        this.network = network;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return sid + "," + name + "," + c + "," + java + "," + network ;
    }
}
