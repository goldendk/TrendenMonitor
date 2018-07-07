package org.goldenworkshop.trenden.view.rest;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@OpenAPIDefinition(
        info = @Info(
                title = "Trenden Monitor API",
                version = "1.0",
                description = "REST API for the Trenden Monitor application."
        )
//        ,
//        servers = {
//                @Server(
//                        description = "server 1",
//                        url = "http://foo",
//                        variables = {
//                                @ServerVariable(name = "var1", description = "var 1", defaultValue = "1", allowableValues = {"1", "2"}),
//                                @ServerVariable(name = "var2", description = "var 2", defaultValue = "1", allowableValues = {"1", "2"})
//                        })
//        }
        )
@ApplicationPath("/rest")
public class Application extends ResourceConfig {

    public Application() {
        packages("org.goldenworkshop.trenden.view.rest", "io.swagger.jaxrs.listing");

            OpenApiResource openApiResource = new OpenApiResource();
            register(openApiResource);
    }



}
