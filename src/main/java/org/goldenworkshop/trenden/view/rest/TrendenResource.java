package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.*;
import org.goldenworkshop.trenden.model.dto.CompanyDTO;
import org.jsoup.helper.Validate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */

@Path("trenden")
@Tag(name = "Trenden Resource", description = "API for querying shared entities in the Trenden application.")
public class TrendenResource {
    @Inject
    @GlobalProducer
    RecommendationSyncDAO dao;

    @GET
    @Path(value = "/companies")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads a list of company names currently in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK, data returned"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
            )
    public Response fetchCompanies(@QueryParam("sinceId") String sinceId,
                                   @QueryParam("pageSize") int pageSize) {
        List<Company> companies = dao.loadCompanies(sinceId, pageSize);
        List<CompanyDTO> collect = companies.stream().map(e -> new CompanyDTO(e.getId(), e.getName())).collect(Collectors.toList());
        return Response.ok().entity(collect).build();
    }

    @GET
    @Path(value = "/daily-values")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Loads daily data for recommendations",
            description = "Multiple status values can be provided with comma seperated strings",
            responses = {
                    @ApiResponse(description = "Data loaded.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Something went wrong")})

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

        RecommendationFilter filter = new RecommendationFilter();
        filter.setPageSize(pageSize);
        filter.setCompanyNames(companyNames);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(sinceDate == null && sinceId == null){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MONTH, -3);

            sinceDate = simpleDateFormat.format(instance.getTime());
        }

        if (sinceDate != null) {
            filter.setSinceDate(simpleDateFormat.parse(sinceDate));
        }

        filter.setSinceId(sinceId);

        if (pageSize == 0) {
            filter.setPageSize(100);
        }

        PaginatedResult<Recommendation> result = dao.loadRecommendationPage(filter);
        return Response.ok().entity(result).build();
    }

}
