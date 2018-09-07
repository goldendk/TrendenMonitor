package org.goldenworkshop.trenden.model;

import java.math.BigDecimal;

public class FeeInfo {
    private final BigDecimal percentage;
    private final BigDecimal mininum;

    public FeeInfo(BigDecimal percentage, BigDecimal mininum) {
        this.percentage = percentage;
        this.mininum = mininum;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal getMininum() {
        return mininum;
    }

}
