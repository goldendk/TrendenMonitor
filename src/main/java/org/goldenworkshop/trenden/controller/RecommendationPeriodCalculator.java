package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.model.FeeInfo;
import org.goldenworkshop.trenden.model.PotentialEarning;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.Signal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

public class RecommendationPeriodCalculator {

    public Collection<PotentialEarning<RecommendationPeriod>> calculateEarnings(Collection<RecommendationPeriod> periods,
                                                                                int investment,
    FeeInfo feeInfo
    ){
        Collection<PotentialEarning<RecommendationPeriod>> result = new ArrayList<>();

        for(RecommendationPeriod period : periods){
            BigDecimal divide = BigDecimal.valueOf(investment).divide(period.getStartValue(), RoundingMode.HALF_UP);
            BigDecimal stocks = divide.setScale(0, RoundingMode.FLOOR);


            PotentialEarning<RecommendationPeriod> potential = new PotentialEarning<>();

            if(period.getStartSignal() == Signal.BUY){
                BigDecimal amount = period.getLatestValue().subtract(period.getStartValue())
                        .multiply(stocks).setScale(2, RoundingMode.HALF_UP);
                potential.setEarning(amount);

                BigDecimal fee = amount.multiply( BigDecimal.ONE.add(feeInfo.getPercentage()) );
                if(feeInfo.getMininum() != null && feeInfo.getMininum().compareTo(fee) > 0){
                    fee = feeInfo.getMininum();
                }
                potential.setFee(fee);
            }
            else{
                potential.setEarning(BigDecimal.ZERO);
                potential.setFee(BigDecimal.ZERO);
            }
            result.add(potential);
        }

        return result;


    }
}
