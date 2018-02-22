package org.goldenworkshop.trenden.model.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.model.Signal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

/**
 * Mongo implementation of DAO for trenden recommendation.
 */
public class MongoRecommendationDAO implements RecommendationSyncDAO {
    private static final String FIELD_NAME_START_SIGNAL = "startSignal";
    private static final String FIELD_NAME_END_SIGNAL = "endSignal";
    private static final String FIELD_NAME_PERIOD_DAYS = "periodDays";
    private static final String FIELD_NAME_START_DATE = "startDate";
    private static final String FIELD_NAME_END_DATE = "endDate";
    private static final String FIELD_NAME_START_VALUE = "startValue";
    private static final String FIELD_NAME_END_VALUE = "endValue";
    private static final String FIELD_NAME_CHANGE_PERCENT = "changePercent";
    private static final String FIELD_NAME_CREATED = "created";
    private static final String FIELD_NAME_UPDATED = "updated";
    private static final String FIELDNAME_LATEST_VALUE = "latestValue";
    private static Logger logger = Logger.getLogger(MongoRecommendationDAO.class.getName());

    private static final Object FIELD_NAME_ID = "_id";

    private static final String FIELD_NAME_NAME = "name";


    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> recommendationPeriodCollection;

    @Override
    public void initialize() throws Exception {
        String url = Config.get().getMongoConnectionUrl();
        client = new MongoClient(new MongoClientURI(url));
        db = client.getDatabase(Config.get().getTrendenDatabaseName());
        recommendationPeriodCollection = db.getCollection(Config.get().getRecommendationPeriodCollectionName());
    }

    @Override
    public void shutdown() {
        try {
            client.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to shutdown mongo client", e);
        }
    }


    @Override
    public void save(RecommendationPeriod period) {

    }

    @Override
    public Map<String, RecommendationPeriod> loadOpenRecommendationPeriods() {
        return null;
    }

    @Override
    public List<RecommendationPeriod> loadPeriodsByName(String companyCName) {

        List<RecommendationPeriod> returnList = new ArrayList<>();
        MongoCursor<Document> cursor =
                recommendationPeriodCollection.
                        // Find with given personID and in time interval
                                find(
                                eq(FIELD_NAME_NAME, companyCName)).
                        iterator();
        try {
            while (cursor.hasNext()) {
                org.bson.Document bson = cursor.next();
                RecommendationPeriod period = new RecommendationPeriod();
                period.setId(bson.getObjectId(FIELD_NAME_ID).toString());
                period.setStartSignal(Signal.fromString(bson.getString(FIELD_NAME_START_SIGNAL)));
                period.setEndSignal(Signal.fromString(bson.getString(FIELD_NAME_END_SIGNAL)));
                period.setPeriodDays(bson.getInteger(FIELD_NAME_PERIOD_DAYS));
                period.setName(bson.getString(FIELD_NAME_NAME));
                period.setStartDate(bson.getDate(FIELD_NAME_START_DATE));
                period.setEndDate(bson.getDate(FIELD_NAME_END_DATE));
                period.setStartValue(Converter.toBigDecimal(bson.getString(FIELD_NAME_START_VALUE)));
                period.setEndValue(Converter.toBigDecimal(bson.getString(FIELD_NAME_END_VALUE)));
                period.setChangePercent(bson.getString(FIELD_NAME_CHANGE_PERCENT));
                period.setCreated(bson.getDate(FIELD_NAME_CREATED));
                period.setUpdated(bson.getDate(FIELD_NAME_UPDATED));
                period.setLatestValue(Converter.toBigDecimal(bson.getString(FIELDNAME_LATEST_VALUE)));
                returnList.add(period);
            }
        } finally {
            cursor.close();
        }
        return returnList;
    }


    private static class Converter {

        public static String toString(BigDecimal bigDecimal) {
            return (bigDecimal == null) ? null : bigDecimal.toPlainString();
        }

        public static BigDecimal toBigDecimal(String value) {
            return (value == null) ? null : new BigDecimal(value);
        }
    }

//
//
//    @Override
//    public String provideAndRegisterDocument(MetaData metaData,
//                                             org.w3c.dom.Document observationAsHL7) {
//        org.bson.Document d = new org.bson.Document();
//        d.append(PID_KEY, metaData.getPersonID());
//        d.append(TIMESTAMP_KEY, metaData.getTimestamp());
//        d.append(HL7_KEY, XMLUtility.convertXMLDocumentToString(observationAsHL7));
//
//        collection.insertOne(d);
//
//        // Mongo assigns a unique id, we just return that
//        String id = d.get("_id").toString();
//
//        return id;
//    }
//
//    @Override
//    public List<org.w3c.dom.Document> retriveDocumentSet(String personID, LocalDateTime startTime,
//                                                         LocalDateTime endTime) {
//        Instant instant; Date d;
//
//        // TODO: Duplicated code, same code is in the FakeObject implementation
//        ZoneId cet = ZoneId.of("Europe/Paris");
//        ZonedDateTime start2 = ZonedDateTime.of(startTime, cet);
//        instant = start2.toInstant();
//        d = Date.from(instant);
//        long start = d.getTime();
//
//        ZonedDateTime end2 = ZonedDateTime.of(endTime, cet);
//        instant = end2.toInstant();
//        d = Date.from(instant);
//        long end = d.getTime();
//
//        List<org.w3c.dom.Document> returnList = new ArrayList<org.w3c.dom.Document>();
//        MongoCursor<Document> cursor =
//                collection.
//                        // Find with given personID and in time interval
//                                find(
//                                and(
//                                        eq(PID_KEY, personID),
//                                        and(
//                                                gt(TIMESTAMP_KEY, start),
//                                                lte(TIMESTAMP_KEY, end)))
//                        ).
//                        iterator();
//        try {
//            while (cursor.hasNext()) {
//                org.bson.Document bson = cursor.next();
//                String hl7 = bson.getString(HL7_KEY);
//                org.w3c.dom.Document doc = XMLUtility.convertXMLStringToDocument(hl7);
//                returnList.add(doc);
//            }
//        } finally {
//            cursor.close();
//        }
//        return returnList;
//    }
//
//    @Override
//    public org.w3c.dom.Document retriveDocument(String uniqueId) {
//        ObjectId asBsonID = new ObjectId(uniqueId);
//        org.bson.Document doc = collection.find(eq("_id", asBsonID)).first();
//        if ( doc == null ) { return null; }
//        String hl7 = doc.getString(HL7_KEY);
//        return XMLUtility.convertXMLStringToDocument(hl7);
//    }
//
//    @Override
//    public boolean correctDocument(String uniqueId, Operation operation,
//                                   org.w3c.dom.Document doc) {
//        ObjectId asBsonID = new ObjectId(uniqueId);
//
//        if (operation == Operation.UPDATE) {
//            UpdateResult ur =
//                    collection.updateOne(eq("_id", asBsonID),
//                            set(HL7_KEY, XMLUtility.convertXMLDocumentToString(doc)));
//            return ur.getModifiedCount() == 1;
//        } else {
//            DeleteResult dr =
//                    collection.deleteOne(eq("_id", asBsonID));
//            return dr.getDeletedCount() == 1;
//        }
//    }
//
//
//    /** Never-ever use this, it will drop the collection!
//     *
//     * @param secret must be set to the secret password
//     * to do the actual dropping of the collection.
//     */
//    public void dropTheDb(String secret) {
//        if (secret.equals("yes-i-am-testing")) {
//            collection.drop();
//        }
//    }


}
