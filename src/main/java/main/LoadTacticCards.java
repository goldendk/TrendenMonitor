package main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.warhammer.underhive.dao.MongoUnderHiveDAO;

import java.io.File;
import java.util.Iterator;

public class LoadTacticCards {

    public static void main(String... args) throws Exception {

        System.setProperty("APPENV", "prod");

        Config.get();//initialize.

        MongoUnderHiveDAO dao = new MongoUnderHiveDAO();
        dao.initialize();

        try{

            JsonParser parser = new JsonParser();
            String json = FileUtils.readFileToString(new File("./src/main/resources/cards.json"), "utf-8");
            JsonElement parse = parser.parse(json);
            JsonArray asJsonArray = parse.getAsJsonArray();
            Gson gson = new Gson();
            Iterator<JsonElement> iterator = asJsonArray.iterator();
            while(iterator.hasNext()){
                TacticCard card = gson.fromJson(iterator.next(), TacticCard.class);
                dao.save(card);
            }


        }
        finally{
            dao.shutdown();
        }


    }


}
