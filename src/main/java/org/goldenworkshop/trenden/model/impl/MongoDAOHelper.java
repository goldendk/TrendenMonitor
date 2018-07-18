package org.goldenworkshop.trenden.model.impl;

import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.goldenworkshop.trenden.model.Company;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.Signal;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.util.Date;

import static org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO.FIELD_NAME_CREATED;
import static org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO.FIELD_NAME_ID;
import static org.goldenworkshop.trenden.model.impl.MongoRecommendationDAO.FIELD_NAME_UPDATED;

public class MongoDAOHelper {

    protected static Company docToCompany(Document doc) {

        Company company = new Company(doc.getObjectId(FIELD_NAME_ID).toString(),
                doc.getString(CompanyFields.FIELD_NAME));
        return company;
    }

    protected static Recommendation documentToRecommendation(Document doc) {
        Recommendation r = new Recommendation();
        r.setCreated(doc.getDate(FIELD_NAME_CREATED));
        r.setName(doc.getString(RecommendationFields.FIELD_NAME));
        r.setLatestValue(Converter.toBigDecimal(doc.getString(RecommendationFields.FIELD_VALUE)));
        r.setId(doc.getObjectId(FIELD_NAME_ID).toString());
        return r;
    }


    protected static RecommendationPeriod documentToPeriod(Document bson) {
        RecommendationPeriod period = new RecommendationPeriod();
        period.setId(bson.getObjectId(FIELD_NAME_ID).toString());
        period.setStartSignal(Signal.fromString(bson.getString(RecommendationPeriodFields.FIELD_NAME_START_SIGNAL)));
        period.setEndSignal(Signal.fromString(bson.getString(RecommendationPeriodFields.FIELD_NAME_END_SIGNAL)));
        period.setPeriodDays(bson.getInteger(RecommendationPeriodFields.FIELD_NAME_PERIOD_DAYS));
        period.setName(bson.getString(RecommendationPeriodFields.FIELD_NAME_NAME));
        period.setStartDate(bson.getDate(RecommendationPeriodFields.FIELD_NAME_START_DATE));
        period.setEndDate(bson.getDate(RecommendationPeriodFields.FIELD_NAME_END_DATE));
        period.setStartValue(Converter.toBigDecimal(bson.getString(RecommendationPeriodFields.FIELD_NAME_START_VALUE)));
        period.setEndValue(Converter.toBigDecimal(bson.getString(RecommendationPeriodFields.FIELD_NAME_END_VALUE)));
        period.setChangePercent(bson.getString(RecommendationPeriodFields.FIELD_NAME_CHANGE_PERCENT));
        period.setCreated(bson.getDate(FIELD_NAME_CREATED));
        period.setUpdated(bson.getDate(FIELD_NAME_UPDATED));
        period.setLatestValue(Converter.toBigDecimal(bson.getString(RecommendationPeriodFields.FIELD_NAME_LATEST_VALUE)));
        return period;
    }


    public static class Converter {

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
}
