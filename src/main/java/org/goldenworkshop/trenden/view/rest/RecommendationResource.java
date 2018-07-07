package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.*;
import org.jsoup.helper.Validate;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/recommendation")
@Tag(name = "Recommendation Resource", description = "API for recommendations")
public class RecommendationResource {

    @Inject
    @GlobalProducer
    RecommendationSyncDAO dao;

    @GET
    @Path(value = "/paging")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Loads daily data for recommendations",
            description = "Multiple status values can be provided with comma seperated strings",
            responses = {
                    @ApiResponse(description = "The user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "User not found")})

    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "OK, data returned"),
                    @ApiResponse(responseCode = "500", description = "Server error")
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
