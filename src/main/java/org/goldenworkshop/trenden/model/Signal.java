package org.goldenworkshop.trenden.model;

public enum Signal {
    BUY,SELL, MISSING;

    public static Signal fromString(String string) {
        for(Signal signal : Signal.values()){
            if(signal.name().equals(string)){
                return signal;
            }
        }
        return null;
    }
}
