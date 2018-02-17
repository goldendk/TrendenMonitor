package org.goldenworkshop.trenden;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest extends BaseTest{

    @Test
    public void shouldInitConfig() {
        String euroInvestorTrendenOmx25Url = Config.get().getEuroInvestorTrendenOmx25Url();
        assertEquals("http://localhost:8080/omx25", euroInvestorTrendenOmx25Url);
    }
}