package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.controller.AuthContext;
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
    private static Logger logger = LogManager.getLogger(ApplicationResource.class);





    @GET
    @Path("/jobs")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads a list of job types in the system",
            summary = "This is a summary"
    )
    public Response loadJobs() {

        String[] jobList = JobFactory.JOB_KEY_LIST;

        return Response.ok().entity(jobList).build();
    }

    @POST
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
        controller.authenticate(idToken);
        User user = AuthContext.get().getUser();
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.ok().entity(user).build();
        }

    }

    @GET
    @Path("/session")
    @Consumes("*/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadSessionData() {
        User user = AuthContext.get().getUser();
        logger.info("Session requested for user: " + user );
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(user).build();
        }
    }

}
