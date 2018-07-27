package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.dao.RecommendationPeriodDAO;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.view.model.PotentialDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
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
            @QueryParam("investment") Integer investment
    ) {
        if(investment == null){
            investment = Config.get().getDefaultInvestmentSize();
        }
        if (days == null) {
            days = 90;
        }

        if (fromDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1 * days);
            fromDate = calendar.getTime();
        }

        Calendar toDate = Calendar.getInstance();
        toDate.setTime(fromDate);
        toDate.add(Calendar.DATE, days);

        Collection<RecommendationPeriod> window = periodDao.loadPeriodWindow(fromDate, toDate.getTime());
        TrendenMarshaller marshaller = new TrendenMarshaller();
        PotentialDTO potentialDTO = marshaller.toDto(window, investment);
        return Response.ok().entity(potentialDTO).build();
    }


}
