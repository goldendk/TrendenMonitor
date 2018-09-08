package org.goldenworkshop.warhammer.underhive.view.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/warhammmer/underhive")
@Tag(name = "UnderHiveResource", description = "The API for Underhive tools.")
public class UnderHiveResource {



    @GET
    @Path("/deck/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads list of decks for the current user in the system",
            summary = "This is a summary"
    )

    public Response loadDeckList(int first, int pageSize){


        return null;
    }

}
