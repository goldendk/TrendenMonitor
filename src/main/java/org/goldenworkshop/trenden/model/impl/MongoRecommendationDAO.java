package org.goldenworkshop.trenden.model.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.dao.PeriodFilter;
import org.goldenworkshop.trenden.dao.RecommendationPeriodDAO;
import org.goldenworkshop.trenden.model.*;
import org.jsoup.helper.Validate;

import java.math.BigInteger;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

/**
 * Mongo implementation of DAO for trenden recommendation.
 */

public class MongoRecommendationDAO extends AbstractMongoDAO implements RecommendationSyncDAO, RecommendationPeriodDAO {

    private static Logger logger = LogManager.getLogger(MongoRecommendationDAO.class);

    public static final String FIELD_NAME_ID = "_id";
    public static final String FIELD_NAME_CREATED = "created";
    public static final String FIELD_NAME_UPDATED = "updated";

    private MongoCollection<Document> recommendationPeriodCollection;
    private MongoCollection<Document> recommendationCollection;
    private MongoCollection<Document> companiesCollection;


    @Override
    public void initialize() throws Exception {
        super.initialize();
        loadRecommendationPeriodCollection();
        loadRecommendationCollection();
        loadCompanyCollection();
    }

    @Override
    protected List<MongoCollection> handleReset() {
        recommendationPeriodCollection.drop();
        recommendationCollection.drop();
        loadRecommendationPeriodCollection();
        return null;
    }

    private void loadCompanyCollection() {
        companiesCollection = db.getCollection("companies");
    }

    private void loadRecommendationCollection() {
        recommendationCollection = db.getCollection(Config.get().getRecommendationCollectionName());
        recommendationCollection.createIndex(Indexes.ascending(FIELD_NAME_CREATED));
    }

    private void loadRecommendationPeriodCollection() {
        recommendationPeriodCollection = db.getCollection(Config.get().getRecommendationPeriodCollectionName());
    }



    @Override
    public void upsert(RecommendationPeriod period) {
        Document theDocument = new Document()
                .append(RecommendationPeriodFields.FIELD_NAME_NAME, period.getName())
                .append(RecommendationPeriodFields.FIELD_NAME_START_SIGNAL, MongoDAOHelper.Converter.toString(period.getStartSignal()))
                .append(RecommendationPeriodFields.FIELD_NAME_END_SIGNAL, MongoDAOHelper.Converter.toString(period.getEndSignal()))
                .append(FIELD_NAME_CREATED, period.getCreated())
                .append(FIELD_NAME_UPDATED, period.getUpdated())
                .append(RecommendationPeriodFields.FIELD_NAME_START_DATE, period.getStartDate())
                .append(RecommendationPeriodFields.FIELD_NAME_END_DATE, period.getEndDate())
                .append(RecommendationPeriodFields.FIELD_NAME_CHANGE_PERCENT, period.getChangePercent())
                .append(RecommendationPeriodFields.FIELD_NAME_START_VALUE, MongoDAOHelper.Converter.toString(period.getStartValue()))
                .append(RecommendationPeriodFields.FIELD_NAME_END_VALUE, MongoDAOHelper.Converter.toString(period.getEndValue()))
                .append(RecommendationPeriodFields.FIELD_NAME_LATEST_VALUE, MongoDAOHelper.Converter.toString(period.getLatestValue()))
                .append(RecommendationPeriodFields.FIELD_NAME_PERIOD_DAYS, period.getPeriodDays());

        upsertDocument(period.getId(), theDocument, recommendationPeriodCollection);
    }

    @Override
    public Map<String, RecommendationPeriod> loadOpenRecommendationPeriods() {
        MongoCursor<Document> iterator = recommendationPeriodCollection.find(Filters.eq(RecommendationPeriodFields.FIELD_NAME_END_SIGNAL, null)).iterator();
        Map<String, RecommendationPeriod> map = new HashMap<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            RecommendationPeriod recommendationPeriod = MongoDAOHelper.documentToPeriod(next);
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
                                eq(RecommendationPeriodFields.FIELD_NAME_NAME, companyCName)).
                        iterator();
        try {
            while (cursor.hasNext()) {
                org.bson.Document bson = cursor.next();
                RecommendationPeriod period = MongoDAOHelper.documentToPeriod(bson);
                returnList.add(period);
            }
        } finally {
            cursor.close();
        }
        return returnList;
    }

    @Override
    public void saveRecommendation(Recommendation recommendation) {
        Document doc = new Document()
                .append(RecommendationFields.FIELD_NAME, recommendation.getName())
                .append(RecommendationFields.FIELD_SIGNAL, recommendation.getSignal().toString())
                .append(RecommendationFields.FIELD_VALUE, MongoDAOHelper.Converter.toString(recommendation.getLatestValue()))
                .append(RecommendationFields.FIELD_SIGNAL_VALUE, MongoDAOHelper.Converter.toString(recommendation.getSignalValue()))
                .append(FIELD_NAME_CREATED, recommendation.getCreated());

        recommendationCollection.insertOne(doc);

    }

    @Override
    public PaginatedResult<Recommendation> loadRecommendationPage(RecommendationFilter filter) {


        List<Recommendation> recommendations = new ArrayList<>();
        Bson queryFilter = null;
        if (filter.getSinceId() != null) {
            queryFilter = Filters.gte(FIELD_NAME_ID, new ObjectId(filter.getSinceId()));
        } else {
            queryFilter = Filters.gte(FIELD_NAME_CREATED, filter.getSinceDate());
        }


        if(CollectionUtils.isNotEmpty(filter.getCompanyNames())){
            queryFilter = Filters.and(Filters.in(RecommendationFields.FIELD_NAME, filter.getCompanyNames()), queryFilter);
        }

        FindIterable<Document> mongoQuery = recommendationCollection.find(queryFilter);

        MongoCursor<Document> cursor = mongoQuery.iterator();

        long rowCount = recommendationCollection.count(queryFilter);

        while (cursor.hasNext()) {
            Document next = cursor.next();
            Recommendation recommendation = MongoDAOHelper.documentToRecommendation(next);
            recommendations.add(recommendation);
        }

        PaginatedResult<Recommendation> recommendationPaginatedResult = new PaginatedResult<>();
        recommendationPaginatedResult.setDataList(recommendations);
        recommendationPaginatedResult.setTotalRows((int) rowCount);

        return recommendationPaginatedResult;
    }

    @Override
    public List<Company> loadCompanies(String sinceId, int pageSize) {
        FindIterable<Document> documents;
        if (StringUtils.isNotBlank(sinceId)) {
            documents = companiesCollection.find(Filters.gte(FIELD_NAME_ID, new ObjectId(sinceId)));
        } else {
            documents = companiesCollection.find();
        }

        List<Company> result = new ArrayList<>();
        MongoCursor<Document> iterator = documents.limit(pageSize).iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();

            result.add(MongoDAOHelper.docToCompany(doc));
        }


        return result;
    }



    @Override
    public Company getCompany(String companyName) {
        MongoCursor<Document> iterator = companiesCollection.find(Filters.eq(CompanyFields.FIELD_NAME, companyName)).iterator();

        if(iterator.hasNext()){
            return MongoDAOHelper.docToCompany(iterator.next());
        }

        return null;
    }

    @Override
    public void saveCompany(Company company) {
        Document document = new Document().append(CompanyFields.FIELD_NAME, company.getName());
        upsertDocument(company.getId(), document, companiesCollection);
    }

    @Override
    public Collection<RecommendationPeriod> loadPeriodWindow(PeriodFilter filter) {
        Validate.notNull(filter.getFromDate());
        Validate.notNull(filter.getToDate());

        List<Bson> filters = new ArrayList<>();

        filters.add(Filters.gte(RecommendationPeriodFields.FIELD_NAME_START_DATE, filter.getFromDate()));
        filters.add(Filters.lte(RecommendationPeriodFields.FIELD_NAME_START_DATE, filter.getToDate().getTime()));


        if(filter.getMaxStockPrice() != null && filter.getMaxStockPrice() > 0){
            filters.add(Filters.expr(
                    RecommendationPeriodFields.FIELD_NAME_START_VALUE + " < " +
                            filter.getMaxStockPrice().toString()));
        }
        if(filter.getExcludeShorting()){
            filters.add(Filters.eq(RecommendationPeriodFields.FIELD_NAME_START_SIGNAL, Signal.BUY.toString()));
        }

        Bson and = Filters.and(filters.toArray(new Bson[0]));

        MongoCursor<Document> iterator = recommendationPeriodCollection.find(and).iterator();
        Collection<RecommendationPeriod> result = new ArrayList<>();
        while(iterator.hasNext()){
            Document next = iterator.next();

            RecommendationPeriod recommendationPeriod = MongoDAOHelper.documentToPeriod(next);
            result.add(recommendationPeriod);
        }

        return result;
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
//    public void resetTheDb(String secret) {
//        if (secret.equals("yes-i-am-testing")) {
//            collection.drop();
//        }
//    }


}
