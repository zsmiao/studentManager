package xyz.itclay.managersystem.domain;

import java.io.Serializable;
import java.util.List;

/**
 *集合类的封装
 * @author ZhangSenmiao
 * @date   2021/1/10 16:24
 **/
public class ListTest implements Serializable {

    private List<StudentResult> list;

    public ListTest() {
    }

    public ListTest(List<StudentResult> list) {
        this.list = list;
    }

    public List<StudentResult> getList() {
        return list;
    }

    public void setList(List<StudentResult> list) {
        this.list = list;
    }
}
