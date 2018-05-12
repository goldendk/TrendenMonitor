package org.goldenworkshop.trenden.model;

import java.util.Date;

public class RecommendationFilter {
    private String sinceId;
    private String companyName;
    private Date sinceDate;
    private int pageSize;

    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(String sinceId) {
        this.sinceId = sinceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
