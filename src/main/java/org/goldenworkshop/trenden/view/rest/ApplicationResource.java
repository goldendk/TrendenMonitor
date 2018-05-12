package org.goldenworkshop.trenden.view.rest;

import io.swagger.annotations.Api;
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

@Api("Application")
@Path("/application")
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
