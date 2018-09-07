package org.goldenworkshop.trenden.view.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class PotentialDTO {

    private BigDecimal potentialEarnings = BigDecimal.ZERO;
    private BigDecimal potentialLoss = BigDecimal.ZERO;

    private Collection<PotentialRecordDTO> records = new ArrayList<>();
    private BigDecimal fee = BigDecimal.ZERO;

    public BigDecimal getPotentialEarnings() {
        return potentialEarnings;
    }

    public void setPotentialEarnings(BigDecimal potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    public BigDecimal getPotentialLoss() {
        return potentialLoss;
    }

    public void setPotentialLoss(BigDecimal potentialLoss) {
        this.potentialLoss = potentialLoss;
    }

    public Collection<PotentialRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(Collection<PotentialRecordDTO> records) {
        this.records = records;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
