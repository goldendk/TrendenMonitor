package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.dao.PeriodFilter;

import java.util.Calendar;
import java.util.Date;

public class PeriodFacade {
    public PeriodFilter buildDefaultFilter() {
        PeriodFilter filter = new PeriodFilter();
        filter.setInvestment(Config.get().getDefaultInvestmentSize());
        filter.setFromDate(new Date());


        int days = 90;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * days);
        filter.setFromDate(calendar.getTime());

        Calendar toDate = Calendar.getInstance();
        toDate.setTime(calendar.getTime());
        toDate.add(Calendar.DATE, days);
        filter.setToDate(toDate);
        return filter;
    }
}
