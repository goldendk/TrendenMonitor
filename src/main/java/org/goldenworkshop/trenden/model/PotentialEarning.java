package org.goldenworkshop.trenden.model;

import java.math.BigDecimal;

public class PotentialEarning<T> {
    private T measurement;
    private BigDecimal earning;
    private BigDecimal fee;

    public T getMeasurement() {
        return measurement;
    }

    public void setMeasurement(T measurement) {
        this.measurement = measurement;
    }

    public BigDecimal getEarning() {
        return earning;
    }

    public void setEarning(BigDecimal earning) {
        this.earning = earning;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
