package org.goldenworkshop.trenden.model;

import java.io.Serializable;

public interface ExternalInterface  extends Serializable{
    void initialize() throws Exception;
    void shutdown() throws Exception;
}
