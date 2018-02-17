package org.goldenworkshop.trenden;

import org.junit.BeforeClass;

public class BaseTest {
    @BeforeClass
    public static void beforeClass(){
        System.setProperty("APPENV", "junit");
    }

}
