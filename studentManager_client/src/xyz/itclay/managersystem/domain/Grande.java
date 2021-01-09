package xyz.itclay.managersystem.domain;

/**
 * 成绩类
 *
 * @author ZhangSenmiao
 * @date 2021/1/9 16:22
 **/
public class Grande {
    private String sid;
    private String name;
    private int c;
    private int java;
    private int network;
    private int csharp;

    public Grande() {
    }

    public Grande(String sid, String name, int c, int java, int network, int csharp) {
        this.sid = sid;
        this.name = name;
        this.c = c;
        this.java = java;
        this.network = network;
        this.csharp = csharp;
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

    public int getCsharp() {
        return csharp;
    }

    public void setCsharp(int csharp) {
        this.csharp = csharp;
    }

    @Override
    public String toString() {
        return sid + ',' + c + "," + java + "," + network + "," + csharp;
    }
}
