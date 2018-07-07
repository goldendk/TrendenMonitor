package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.cdi.GlobalProducer;
import org.goldenworkshop.trenden.model.Company;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;
import org.goldenworkshop.trenden.model.dto.CompanyDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path(value = "/paging")
    @Produces(MediaType.APPLICATION_JSON)
//    @ApiOperation(value = "Loads a list of companies in the system.",
//            response = CompanyDTO.class,
//            responseContainer = "List")
//    @ApiResponses(
//            value = {@ApiResponse(code = 200, message = "OK, data returned"),
//                    @ApiResponse(code = 500, message = "Server error")
//            })
    public Response fetchCompanies(@QueryParam("sinceId") String sinceId,
                                   @QueryParam("pageSize") int pageSize) {
        List<Company> companies = dao.loadCompanies(sinceId, pageSize);
        List<CompanyDTO> collect = companies.stream().map(e -> new CompanyDTO(e.getId(), e.getName())).collect(Collectors.toList());
        return Response.ok().entity(collect).build();
    }


}
