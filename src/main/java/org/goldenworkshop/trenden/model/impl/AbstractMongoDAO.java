package org.goldenworkshop.trenden.model.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.ExternalInterface;

public class AbstractMongoDAO implements ExternalInterface {
    private Logger logger = LogManager.getLogger();
    protected MongoClient client;
    protected MongoDatabase db;

    @Override
    public void initialize() throws Exception {
        logger.info("Initializing MongoDB client");
        String url = Config.get().getMongoConnectionUrl();
        client = new MongoClient(new MongoClientURI(url));
        db = client.getDatabase(Config.get().getTrendenDatabaseName());

    }

    @Override
    public void shutdown() throws Exception {
        logger.info("Shutting down MongoDB client");
        try {
            client.close();
        } catch (Exception e) {
            logger.error("Failed to shutdown MongoDB client", e);
        }
    }


}
