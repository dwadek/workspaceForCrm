package com.dwadek.crm.vo;

import java.util.List;

public class PaginationVO<T> {

    private int total;
    private List<T> dataList;


    public PaginationVO() {
    }

    public PaginationVO(int total, List<T> dataList,String name) {
        this.total = total;
        this.dataList = dataList;

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


}
