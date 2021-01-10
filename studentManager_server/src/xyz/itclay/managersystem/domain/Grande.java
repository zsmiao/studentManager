package xyz.itclay.managersystem.domain;

/**
 * 成绩类
 *
 * @author ZhangSenmiao
 * @date 2021/1/9 16:22
 **/
public class Grande {
    private String sid;
    private int c;
    private int java;
    private int network;

    public Grande() {
    }

    public Grande(String sid, int c, int java, int network) {
        this.sid = sid;
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
        return sid + ',' + c + "," + java + "," + network ;
    }
}
