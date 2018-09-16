package org.goldenworkshop.warhammer.underhive.view.rest;

import org.goldenworkshop.necromunda.underhive.GangEnum;
import org.goldenworkshop.trenden.view.model.GangView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UnderHiveMeta implements Serializable {
    private GangView[] gangs = Arrays.stream(GangEnum.values())
            .map(e->new GangView(e.getGangSymbol(), e.getLabel()))
            .collect(Collectors.toList()).toArray(new GangView[0]);

    public GangView[] getGangs() {
        return gangs;
    }

    public void setGangs(GangView[] gangs) {
        this.gangs = gangs;
    }
}
