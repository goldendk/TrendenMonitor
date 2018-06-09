package org.goldenworkshop.trenden.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RecommendationFilter {
    private String sinceId;
    private Date sinceDate;
    private int pageSize;
    private List<String> companyNames;

    public String getSinceId() {
        return sinceId;
    }

    public void setSinceId(String sinceId) {
        this.sinceId = sinceId;
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

    public void setCompanyNames(List<String> companyNames) {
        this.companyNames = companyNames;
    }

    public List<String> getCompanyNames() {
        return companyNames;
    }
}
