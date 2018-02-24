package org.goldenworkshop.trenden.view.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path(value = "/foo")
public class FooResource {


    @GET
    @Path(value = "/list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response loadFoos(){
        List<Foo> foo = new ArrayList<>();
        foo.add(new Foo("a", 1));
        foo.add(new Foo("b", 2));
        return Response.ok().entity(foo).build();
    }
}
