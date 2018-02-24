package org.goldenworkshop.trenden.model.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.model.Signal;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.util.*;

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
    private static final String FIELD_NAME_LATEST_VALUE = "latestValue";
    private static Logger logger = LogManager.getLogger(MongoRecommendationDAO.class);

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
        loadRecommendationCollection();
    }

    private void loadRecommendationCollection() {
        recommendationPeriodCollection = db.getCollection(Config.get().getRecommendationPeriodCollectionName());
    }

    @Override
    public void shutdown() {
        try {
            client.close();
        } catch (Exception e) {
            logger.error("Failed to shutdown MongoDB client", e);
        }
    }


    @Override
    public void upsert(RecommendationPeriod period) {
        Document theDocument = new Document()
                .append(FIELD_NAME_NAME, period.getName())
                .append(FIELD_NAME_START_SIGNAL, Converter.toString(period.getStartSignal()))
                .append(FIELD_NAME_END_SIGNAL, Converter.toString(period.getEndSignal()))
                .append(FIELD_NAME_CREATED, period.getCreated())
                .append(FIELD_NAME_UPDATED, period.getUpdated())
                .append(FIELD_NAME_START_DATE, period.getStartDate())
                .append(FIELD_NAME_END_DATE, period.getEndDate())
                .append(FIELD_NAME_CHANGE_PERCENT, period.getChangePercent())
                .append(FIELD_NAME_START_VALUE, Converter.toString(period.getStartValue()))
                .append(FIELD_NAME_END_VALUE, Converter.toString(period.getEndValue()))
                .append(FIELD_NAME_LATEST_VALUE, Converter.toString(period.getLatestValue()))
                .append(FIELD_NAME_PERIOD_DAYS, period.getPeriodDays());

        Bson update = new Document("$set",
                theDocument
        );

        if (StringUtils.isNotBlank(period.getId())) {
            Bson filter = Filters.eq("_id", new ObjectId(period.getId()));
            UpdateOptions options = new UpdateOptions().upsert(true);
            recommendationPeriodCollection
                    .updateOne(filter, update, options);
        } else {
            recommendationPeriodCollection.insertOne(theDocument);
        }
    }

    @Override
    public Map<String, RecommendationPeriod> loadOpenRecommendationPeriods() {
        MongoCursor<Document> iterator = recommendationPeriodCollection.find(Filters.eq(FIELD_NAME_END_SIGNAL, null)).iterator();
        Map<String, RecommendationPeriod> map = new HashMap<>();
        while(iterator.hasNext()){
            Document next = iterator.next();
            RecommendationPeriod recommendationPeriod = documentToPeriod(next);
            map.put(recommendationPeriod.getName(), recommendationPeriod);
        }
        return map;
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
                RecommendationPeriod period = documentToPeriod(bson);
                returnList.add(period);
            }
        } finally {
            cursor.close();
        }
        return returnList;
    }

    private RecommendationPeriod documentToPeriod(Document bson) {
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
        period.setLatestValue(Converter.toBigDecimal(bson.getString(FIELD_NAME_LATEST_VALUE)));
        return period;
    }


    private static class Converter {

        public static String toString(BigDecimal bigDecimal) {
            return (bigDecimal == null) ? null : bigDecimal.toPlainString();
        }

        public static BigDecimal toBigDecimal(String value) {
            return (value == null) ? null : new BigDecimal(value);
        }

        public static String toString(Signal endSignal) {
            return (endSignal == null) ? null : endSignal.name();
        }

        public static String toString(Date created) {
            return created == null ? null : DatatypeConverter.printDateTime(DateUtils.toCalendar(created));
        }
    }

    /**
     * Never-ever use this, it will drop the collection!
     * //     *
     * //     * @param secret must be set to the secret password
     * //     * to do the actual dropping of the collection.
     * //
     */
    public void dropTheDb(String secret) {
        if (secret.equals("yes-i-am-testing")) {
            recommendationPeriodCollection.drop();
            loadRecommendationCollection();
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
