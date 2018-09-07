package org.goldenworkshop.trenden.view.rest;

import org.goldenworkshop.trenden.model.PotentialEarning;
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
    public PotentialDTO toDto(Collection<PotentialEarning<RecommendationPeriod>> earnings){
        PotentialDTO dto = new PotentialDTO();

        for(PotentialEarning<RecommendationPeriod> pot : earnings){
            PotentialRecordDTO e = toDto(pot);
            if(e.getPotential().compareTo(BigDecimal.ZERO) > 0){
                dto.setPotentialEarnings(dto.getPotentialEarnings().add(e.getPotential()));
                dto.setFee(dto.getFee().add((pot.getFee())));
            }else{
               dto.setPotentialLoss(dto.getPotentialLoss().add(e.getPotential()));
            }
            dto.getRecords().add(e);
        }
        return dto;

    }

    private PotentialRecordDTO toDto(PotentialEarning<RecommendationPeriod> p) {
        PotentialRecordDTO dto = new PotentialRecordDTO();

        dto.setId( p.getMeasurement().getId());
        dto.setName(p.getMeasurement().getName());
        dto.setDays(p.getMeasurement().getPeriodDays());
        dto.setEndSignal(Signal.safeToString(p.getMeasurement().getEndSignal()));
        dto.setStartSignal(Signal.safeToString(p.getMeasurement().getStartSignal()));
        dto.setStartValue(p.getMeasurement().getStartValue());
        dto.setEndValue(p.getMeasurement().getLatestValue());
        dto.setStartDate(safeDateToString(p.getMeasurement().getStartDate()));
        dto.setEndDate(safeDateToString(p.getMeasurement().getEndDate()));
        return dto;
    }

    private String safeDateToString(Date endDate) {
        if (endDate == null){
            return null;
        }
        return simpleDateFormat.format(endDate);
    }


}
