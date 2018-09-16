package org.goldenworkshop.trenden.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.necromunda.underhive.TacticParser;
import org.goldenworkshop.necromunda.underhive.deck.TacticCardDAO;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;
import org.goldenworkshop.warhammer.underhive.dao.MongoUnderHiveDAO;

import javax.servlet.ServletContextEvent;
import java.io.FileReader;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    public static MongoRecommendationDAO dao = new MongoRecommendationDAO();
    public static Logger logger = LogManager.getLogger(ServletContextListener.class);
    public static MongoUnderHiveDAO underHiveDAO = new MongoUnderHiveDAO();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            dao.initialize();
            underHiveDAO.initialize();
        } catch (Exception e) {
            logger.error("Could not initialize application",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            dao.shutdown();
            underHiveDAO.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
