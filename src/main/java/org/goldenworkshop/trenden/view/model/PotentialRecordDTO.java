package org.goldenworkshop.trenden.view.model;

import java.math.BigDecimal;

public class PotentialRecordDTO {
    private String id;
    private BigDecimal potential;
    private String startSignal;
    private String endSignal;
    private BigDecimal startValue;
    private BigDecimal endValue;
    private String name;
    private int days;
    private String startDate;
    private String endDate;

    public BigDecimal getPotential() {
        return potential;
    }

    public void setPotential(BigDecimal potential) {
        this.potential = potential;
    }

    public String getStartSignal() {
        return startSignal;
    }

    public void setStartSignal(String startSignal) {
        this.startSignal = startSignal;
    }

    public String getEndSignal() {
        return endSignal;
    }

    public void setEndSignal(String endSignal) {
        this.endSignal = endSignal;
    }

    public BigDecimal getStartValue() {
        return startValue;
    }

    public void setStartValue(BigDecimal startValue) {
        this.startValue = startValue;
    }

    public BigDecimal getEndValue() {
        return endValue;
    }

    public void setEndValue(BigDecimal endValue) {
        this.endValue = endValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
