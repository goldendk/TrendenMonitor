package org.goldenworkshop.trenden.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO;

import javax.servlet.ServletContextEvent;

public class ServletContextListener implements javax.servlet.ServletContextListener {
    public static MongoRecommendationDAO dao = new MongoRecommendationDAO();
    public static Logger logger = LogManager.getLogger(ServletContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            dao.initialize();
        } catch (Exception e) {
            logger.error("Could not initialize application",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            dao.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
