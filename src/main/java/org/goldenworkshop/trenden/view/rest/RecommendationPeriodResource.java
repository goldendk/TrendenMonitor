package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.RecommendationPeriod;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;


@Path("/recommendation-period")
@Tag(name = "Recommendation Resource")
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



    @GET
    @Produces(value = {"application/json"})
    @Path("/open-list")
    public Response loadPeriodPage(

    ){
        return null;
    }


}
