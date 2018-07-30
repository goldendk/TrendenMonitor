package org.goldenworkshop.trenden.dao;

import java.util.Calendar;
import java.util.Date;

public class PeriodFilter {
    private Integer maxStockPrice;
    private Double feePercentage;
    private Integer feeMinimum;
    private Boolean excludeShorting;
    private Calendar toDate;
    private Date fromDate;

    public void setMaxStockPrice(Integer maxStockPrice) {
        this.maxStockPrice = maxStockPrice;
    }

    public Integer getMaxStockPrice() {
        return maxStockPrice;
    }

    public void setFeePercentage(Double feePercentage) {
        this.feePercentage = feePercentage;
    }

    public Double getFeePercentage() {
        return feePercentage;
    }

    public void setFeeMinimum(Integer feeMinimum) {
        this.feeMinimum = feeMinimum;
    }

    public Integer getFeeMinimum() {
        return feeMinimum;
    }

    public void setExcludeShorting(Boolean excludeShorting) {
        this.excludeShorting = excludeShorting;
    }

    public Boolean getExcludeShorting() {
        return excludeShorting;
    }


    public void setToDate(Calendar toDate) {
        this.toDate = toDate;
    }

    public Calendar getToDate() {
        return toDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }
}
