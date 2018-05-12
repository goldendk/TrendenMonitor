package org.goldenworkshop.trenden.model;

import java.util.List;

public class PaginatedResult<T> {
    private List<T> dataList;
    private int totalRows;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
