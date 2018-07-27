package org.goldenworkshop.trenden.view.rest;

import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.Signal;
import org.goldenworkshop.trenden.view.model.PotentialDTO;
import org.goldenworkshop.trenden.view.model.PotentialRecordDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class TrendenMarshaller {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public PotentialDTO toDto(Collection<RecommendationPeriod> period, int investment){
        PotentialDTO dto = new PotentialDTO();

        for(RecommendationPeriod p : period){
            PotentialRecordDTO e = toDto(p, investment);
            if(e.getPotential().compareTo(BigDecimal.ZERO) > 0){
                dto.setPotentialEarnings(dto.getPotentialEarnings().add(e.getPotential()));
            }else{
               dto.setPotentialLoss(dto.getPotentialLoss().add(e.getPotential()));
            }
            dto.getRecords().add(e);
        }

        return dto;

    }

    private PotentialRecordDTO toDto(RecommendationPeriod p, int investment) {
        PotentialRecordDTO dto = new PotentialRecordDTO();

        BigDecimal divide = BigDecimal.valueOf(investment).divide(p.getStartValue(), RoundingMode.HALF_UP);
        BigDecimal stocks = divide.setScale(0, RoundingMode.FLOOR);


        if(p.getStartSignal() == Signal.BUY){
            BigDecimal potential = p.getLatestValue().subtract(p.getStartValue()).multiply(stocks).setScale(2, RoundingMode.HALF_UP);
            dto.setPotential(potential);
        }
        else{
            dto.setPotential(BigDecimal.ZERO);
        }
        dto.setId( p.getId());
        dto.setName(p.getName());
        dto.setDays(p.getPeriodDays());
        dto.setEndSignal(Signal.safeToString(p.getEndSignal()));
        dto.setStartSignal(Signal.safeToString(p.getStartSignal()));
        dto.setStartValue(p.getStartValue());
        dto.setEndValue(p.getLatestValue());
        dto.setStartDate(safeDateToString(p.getStartDate()));
        dto.setEndDate(safeDateToString(p.getEndDate()));

        return dto;
    }

    private String safeDateToString(Date endDate) {
        if (endDate == null){
            return null;
        }

        return simpleDateFormat.format(endDate);

    }


}
