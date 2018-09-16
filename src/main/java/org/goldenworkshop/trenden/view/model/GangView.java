package org.goldenworkshop.trenden.view.model;

import java.io.Serializable;

public class GangView implements Serializable {
    private String gangSymbol;
    private String label;

    public GangView(String gangSymbol, String label) {
        this.gangSymbol = gangSymbol;
        this.label = label;
    }

    public String getGangSymbol() {
        return gangSymbol;
    }

    public void setGangSymbol(String gangSymbol) {
        this.gangSymbol = gangSymbol;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
