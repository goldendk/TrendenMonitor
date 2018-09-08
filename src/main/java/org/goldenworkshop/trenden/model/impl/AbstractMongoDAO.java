package org.goldenworkshop.trenden.model.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.ExternalInterface;
import org.goldenworkshop.warhammer.underhive.dao.StringToObjectIdCodec;

import java.util.Collection;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class AbstractMongoDAO implements ExternalInterface {
    private Logger logger = LogManager.getLogger();
    protected MongoClient client;
    protected MongoDatabase db;

    @Override
    public void initialize() throws Exception {
        logger.info("Initializing MongoDB client");
        String url = Config.get().getMongoConnectionUrl();

        ClassModelBuilder<TacticCard> tacticCardClassModelBuilder = ClassModel.builder(TacticCard.class).enableDiscriminator(true);
        PropertyModelBuilder<String> id = (PropertyModelBuilder<String>) tacticCardClassModelBuilder.getProperty("id");
        id.codec(new StringToObjectIdCodec());

        ClassModel<TacticCard> build = tacticCardClassModelBuilder.build();
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).register(build).build()));
        client = new MongoClient(new MongoClientURI(url));
        db = client.getDatabase(Config.get().getTrendenDatabaseName());
        db = db.withCodecRegistry(pojoCodecRegistry);

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


    protected void upsertDocument(String id, Document document, MongoCollection<Document> collection) {
        Bson update = new Document("$set",
                document
        );
        if (StringUtils.isNotBlank(id)) {
            Bson filter = Filters.eq("_id", new ObjectId(id));
            UpdateOptions options = new UpdateOptions().upsert(true);
            collection
                    .updateOne(filter, update, options);
        } else {
            collection.insertOne(document);
        }
    }

    protected abstract List<MongoCollection> handleReset();

    /**
     * Never-ever use this, it will drop the collection!
     * //     *
     * //     * @param secret must be set to the secret password
     * //     * to do the actual dropping of the collection.
     * //
     */
    public void resetTheDb(String secret) {
        final String realSecret = "yes-i-am-testing";
        if (secret.equals(realSecret)) {
            handleReset();
        } else {
            throw new IllegalArgumentException("Wrong secret: " + realSecret);
        }
    }

}
