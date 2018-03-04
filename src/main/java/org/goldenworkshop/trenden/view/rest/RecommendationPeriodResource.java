package org.goldenworkshop.trenden.view.rest;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.goldenworkshop.trenden.model.RecommendationSyncDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/recommendation-period")
public class RecommendationPeriodResource {

    @Inject
    RecommendationSyncDAO recommendationSyncDAO;

    @GET
    @Produces(value = {})
    public Response loadOpenRecommendations(){

        REcomm


    }

}
