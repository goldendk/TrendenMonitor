package org.goldenworkshop.trenden.model;

import java.math.BigDecimal;
import java.util.Date;

public class RecommendationPeriod {

    private String name;
    private Date startDate;
    private Date endDate;
    private Signal startSignal;
    private Signal endSignal;
    private BigDecimal startValue;
    private BigDecimal endValue;
    private int periodDays;
    private String changePercent;

    private Date created;
    private Date updated;

    private boolean seen;
    private BigDecimal latestValue;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Signal getStartSignal() {
        return startSignal;
    }

    public void setStartSignal(Signal startSignal) {
        this.startSignal = startSignal;
    }

    public Signal getEndSignal() {
        return endSignal;
    }

    public void setEndSignal(Signal endSignal) {
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

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setLatestValue(BigDecimal latestValue) {
        this.latestValue = latestValue;
    }

    public BigDecimal getLatestValue() {
        return latestValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RecommendationPeriod{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startSignal=" + startSignal +
                ", endSignal=" + endSignal +
                ", startValue=" + startValue +
                ", endValue=" + endValue +
                ", periodDays=" + periodDays +
                ", changePercent='" + changePercent + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", seen=" + seen +
                ", latestValue=" + latestValue +
                ", id='" + id + '\'' +
                '}';
    }
}
