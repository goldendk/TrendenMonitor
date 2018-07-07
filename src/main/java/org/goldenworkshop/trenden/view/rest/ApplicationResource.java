package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.trenden.controller.GoogleAuthController;
import org.goldenworkshop.trenden.model.JobFactory;
import org.goldenworkshop.trenden.model.TrendenJob;
import org.goldenworkshop.trenden.model.TrendenJobResult;
import org.goldenworkshop.trenden.model.User;
import org.jsoup.helper.Validate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/application")
@Tag(name = "ApplicationResource", description = "The API for application level features <strong>HTML here </strong>")
public class ApplicationResource {


    @GET
    @Path("/jobs/{jobKey}")
    @Consumes("*/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startJob(@PathParam("jobKey") String jobKey) {
        TrendenJob build = JobFactory.build(jobKey);
        build.run();
        TrendenJobResult result = build.getResult();
        return Response.ok().entity(result).build();
    }


    @GET
    @Path("/auth/google")
    @Consumes("*/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateByGoogle(@QueryParam("idToken") String idToken) throws IOException {
        Validate.notNull(idToken, "idToken must be provided.");
        GoogleAuthController controller = new GoogleAuthController();
        User authenticate = controller.authenticate(idToken);

        if (authenticate == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok().entity(authenticate).build();
        }

    }

}
