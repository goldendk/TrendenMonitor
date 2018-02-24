package org.goldenworkshop.trenden.view.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
    public Application() {
        // create custom ObjectMapper

        packages("org.goldenworkshop.trenden.view.rest");
    }
}
