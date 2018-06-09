package org.goldenworkshop.trenden.view.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.PaginatedResult;
import org.goldenworkshop.trenden.model.Recommendation;
import org.goldenworkshop.trenden.model.RecommendationFilter;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.jsoup.helper.Validate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api("Recommendation")
@Path("/recommendation")
public class RecommendationResource {

    @Inject
    @GlobalProducer
    RecommendationSyncDAO dao;

    @GET
    @Path(value = "/paging")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Loads daily data for recommendations",

            notes = "Multiple status values can be provided with comma seperated strings",
            response = Recommendation.class,
            responseContainer = "List")
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "OK, data returned"),
                    @ApiResponse(code = 500, message = "Server error")
            })
    public Response fetchDataPage(@QueryParam("companyNames") List<String> companyNames,
                                  @QueryParam("sinceId") String sinceId,
                                  @QueryParam("sinceDate") String sinceDate,
                                  @QueryParam("pageSize") int pageSize) throws ParseException {
        Validate.notNull(companyNames, "companyName must be filled out.");
        Validate.isFalse(companyNames.isEmpty() , "At least one company must be queried");
        Validate.isTrue(sinceId != null
                        || sinceDate != null
                , "sinceId or sinceDate must be provided.");

        RecommendationFilter filter = new RecommendationFilter();
        filter.setPageSize(pageSize);
        filter.setCompanyNames(companyNames);

        if (sinceDate != null) {
            filter.setSinceDate(new SimpleDateFormat("yyyy-MM-dd").parse(sinceDate));
        }

        filter.setSinceId(sinceId);

        if (pageSize == 0) {
            filter.setPageSize(100);
        }

        PaginatedResult<Recommendation> result = dao.loadRecommendationPage(filter);
        return Response.ok().entity(result).build();
    }

}
