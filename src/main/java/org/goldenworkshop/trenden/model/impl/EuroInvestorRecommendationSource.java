package org.goldenworkshop.trenden.model.impl;

import org.apache.commons.io.IOUtils;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.function.Consumer;

public class EuroInvestorRecommendationSource implements RecommendationSource{

    public Iterator<Recommendation> iterator() {

        EuroInvestorTrendenDownloader downloader = new EuroInvestorTrendenDownloader();
        downloader.loadData();
        EuroInvesterTrendenParser parser = new EuroInvesterTrendenParser();
        try {
            parser.parse(downloader.getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parser.iterator();

    }


    private class EuroInvestorTrendenDownloader {


        private String data;

          public void loadData() {
            String spec = Config.get().getEuroInvestorTrendenOmx25Url();
            HttpURLConnection httpURLConnection = null;
            try {
                URL urlConnection = new URL(spec);
                httpURLConnection = (HttpURLConnection) urlConnection.openConnection();
                httpURLConnection.setDoInput(true);
                // instead of a GET, we're going to send using method="POST"
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection = (HttpURLConnection) urlConnection.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();

                String s = IOUtils.toString(inputStream, StandardCharsets.UTF_8.toString());
                data = s;
            } catch (Exception e) {
                throw new RuntimeException("Cannot connect to: " + spec, e);
            }
            finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
        }

        public String getData() {
            return data;
        }

    }
}
