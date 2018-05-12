package org.goldenworkshop.trenden.view.rest;

import io.swagger.annotations.Api;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;
@Api("RecommendationPeriod")
@Path("/recommendation-period")
public class RecommendationPeriodResource {

    @Inject @GlobalProducer
    RecommendationSyncDAO recommendationSyncDAO;

    @GET
    @Produces(value = {"application/json"})
    @Path("/open-list")
    public Response loadOpenRecommendations(){
        Map<String, RecommendationPeriod> stringRecommendationPeriodMap =
                recommendationSyncDAO.loadOpenRecommendationPeriods();
        return Response.ok().entity(stringRecommendationPeriodMap.values()).build();
    }

}
