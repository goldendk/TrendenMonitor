package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class EuroInvesterTrendenParserTest extends BaseTest{

    @Test
    public void shouldConvertNumber() {
        assertEquals("10300.00", EuroInvesterTrendenParser.convertValue("10.300,00"));
    }
}