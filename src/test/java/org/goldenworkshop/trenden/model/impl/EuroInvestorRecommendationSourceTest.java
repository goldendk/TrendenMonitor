package org.goldenworkshop.trenden.model.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.goldenworkshop.trenden.BaseTest;
import org.goldenworkshop.trenden.model.Recommendation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static org.junit.Assert.*;
 @RunWith(MockitoJUnitRunner.class)
public class EuroInvestorRecommendationSourceTest extends BaseTest{
    private static File htmlFile =
            new File("./src/test/resources/org.goldenworkshop.trenden.model.impl" +
                    "/euroinvestor_trenden.html");
    @Rule
    public WireMockRule service1 = new WireMockRule(8080);
    @Spy
    private EuroInvestorRecommendationSource source;

    @Before
    public void before(){

    }

    @Test
    public void shouldParseHtmlContent() throws IOException {
        service1.stubFor(WireMock.get(WireMock.urlPathEqualTo("/omx25"))

                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody(FileUtils.readFileToString(htmlFile, StandardCharsets.UTF_8.name())))
        );
        Iterator<Recommendation> iterator = source.iterator();
        int i = 0;
        while(iterator.hasNext()){
            iterator.next();
            i++;
        }
        assertEquals("Should have 25 results", 25, i);
    }
}