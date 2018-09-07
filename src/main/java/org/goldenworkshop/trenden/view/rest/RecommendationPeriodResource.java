package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.time.DateUtils;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.controller.PeriodFacade;
import org.goldenworkshop.trenden.controller.RecommendationPeriodCalculator;
import org.goldenworkshop.trenden.dao.PeriodFilter;
import org.goldenworkshop.trenden.dao.RecommendationPeriodDAO;
import org.goldenworkshop.trenden.model.FeeInfo;
import org.goldenworkshop.trenden.model.PotentialEarning;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.view.model.PotentialDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


@Path("/recommendation-period")
@Tag(name = "Recommendation Resource")
public class RecommendationPeriodResource {

    @Inject
    @GlobalProducer
    RecommendationSyncDAO recommendationSyncDAO;

    @Inject
    @GlobalProducer
    RecommendationPeriodDAO periodDao;

    @GET
    @Produces(value = {"application/json"})
    @Path("/open-list")
    public Response loadOpenRecommendations() {
        Map<String, RecommendationPeriod> stringRecommendationPeriodMap =
                recommendationSyncDAO.loadOpenRecommendationPeriods();
        return Response.ok().entity(stringRecommendationPeriodMap.values()).build();
    }


    @GET
    @Produces(value = {"application/json"})
    @Path("/")
    public Response loadPeriodPage(
            @QueryParam("fromDate") Date fromDate,
            @QueryParam("pageSizeDays") Integer days,
            @QueryParam("investment") Integer investment,
            @QueryParam("maxStockPrice") Integer maxStockPrice,
            @QueryParam("feePercentage") Double feePercentage,
            @QueryParam("feeMinimum") Integer feeMinimum,
            @QueryParam("excludeShorting") Boolean excludeShorting
    ) {

        PeriodFilter periodFilter = new PeriodFacade().buildDefaultFilter();
        if (maxStockPrice != null) {
            periodFilter.setMaxStockPrice(maxStockPrice);
        }
        if (feePercentage != null) {
            periodFilter.setFeePercentage(feePercentage);
        }
        if (feeMinimum != null) {
            periodFilter.setFeeMinimum(feeMinimum);

        }
        if (excludeShorting != null) {
            periodFilter.setExcludeShorting(excludeShorting);
        }
        if (fromDate != null) {
            periodFilter.setFromDate(fromDate);

        }
        if (days != null) {
            Calendar calendar = DateUtils.toCalendar(periodFilter.getFromDate());
            calendar.add(Calendar.DATE, days);
            periodFilter.setToDate(calendar);
        }

        Collection<RecommendationPeriod> window = periodDao.loadPeriodWindow(periodFilter);

        RecommendationPeriodCalculator calculator = new RecommendationPeriodCalculator();
        Collection<PotentialEarning<RecommendationPeriod>> potentialEarnings =
                calculator.calculateEarnings(window, investment,
                        new FeeInfo(BigDecimal.valueOf(feePercentage), BigDecimal.valueOf(feeMinimum)));

        TrendenMarshaller marshaller = new TrendenMarshaller();
        PotentialDTO potentialDTO = marshaller.toDto(potentialEarnings);
        return Response.ok().entity(potentialDTO).build();
    }


}
