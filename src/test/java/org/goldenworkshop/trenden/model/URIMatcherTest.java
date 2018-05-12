package org.goldenworkshop.trenden.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class URIMatcherTest {

    private URIMatcher uriMatcher;
    @Before
    public void before(){
        uriMatcher = new URIMatcher();
    }

    @Test
    public void testMatcher(){
        uriMatcher.add("/foo/bar.*");
        assertTrue(uriMatcher.matches("/foo/bar22"));
        assertFalse(uriMatcher.matches("/bar/foo"));

    }


}