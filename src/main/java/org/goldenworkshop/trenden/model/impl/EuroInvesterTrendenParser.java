package org.goldenworkshop.trenden.model.impl;

import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationSource;
import org.goldenworkshop.trenden.model.Signal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EuroInvesterTrendenParser implements RecommendationSource {


    private List<Recommendation> list = new ArrayList<>();

    @Override
    public Iterator<Recommendation> iterator() {
        return list.iterator();
    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public void parse(String data) throws Exception {

        Document doc = Jsoup.parse(data);

        Elements select = doc.select("#table1");
        Elements rows = select.select("tr");

        for(int i = 0 ; i < rows.size() ; i ++){
            Element row = rows.get(i);

            Elements cellList = row.select("td");

            int col = 0;
            if(cellList.size() > 0){
                String name = cellList.get(col++).text();
                String signal = cellList.get(col++).text();
                String days = cellList.get(col++).text();
                String signalDate = cellList.get(col++).text();
                String signalValue = cellList.get(col++).text();
                String latestValue = cellList.get(col++).text();
                String change = cellList.get(col++).text();

                Recommendation recommendation = new Recommendation();
                recommendation.setCreated(new Date());
                recommendation.setName(name);

                String buyValue = Config.get().getSignalValue(Signal.BUY);
                String sellValue = Config.get().getSignalValue(Signal.SELL);
                Signal signalEnum = null;
                if(buyValue.equalsIgnoreCase(signal)){
                    signalEnum = Signal.BUY;
                }
                else if(sellValue.equalsIgnoreCase(signal)){
                    signalEnum = Signal.SELL;
                }
                else{
                    throw new RuntimeException("Did not understand signal: " + signal);
                }

                recommendation.setSignal(signalEnum);
                recommendation.setDays(Integer.parseInt(days));
                recommendation.setSignalDate(dateFormat.parse(signalDate.trim()));
                recommendation.setSignalValue(new BigDecimal(convertValue(signalValue)));
                recommendation.setLatestValue(new BigDecimal(convertValue(latestValue)));
                recommendation.setChange(change);
                list.add(recommendation);
            }
        }
    }


    public static String convertValue(String value){
        value = value.replaceAll("\\.", "");
        value = value.replaceAll(",", ".");
        return value;
    }
}
