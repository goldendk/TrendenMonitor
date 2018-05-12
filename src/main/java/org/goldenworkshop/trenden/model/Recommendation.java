package org.goldenworkshop.trenden.model;

import java.math.BigDecimal;
import java.util.Date;

public class Recommendation {
    private String id;
    private String name;//Navn;
    private Signal signal;//Signal
    private int days;//Dage
    private Date signalDate; //Signaldato
    private BigDecimal signalValue; //Signalkurs
    private BigDecimal latestValue; //Seneste kurs
    private String change;//Ændring
    private Date created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Date getSignalDate() {
        return signalDate;
    }

    public void setSignalDate(Date signalDate) {
        this.signalDate = signalDate;
    }

    public BigDecimal getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(BigDecimal signalValue) {
        this.signalValue = signalValue;
    }

    public BigDecimal getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(BigDecimal latestValue) {
        this.latestValue = latestValue;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "name='" + name + '\'' +
                ", signal=" + signal +
                ", days=" + days +
                ", signalDate=" + signalDate +
                ", signalValue=" + signalValue +
                ", latestValue=" + latestValue +
                ", change=" + change +          '}';
    }
}
