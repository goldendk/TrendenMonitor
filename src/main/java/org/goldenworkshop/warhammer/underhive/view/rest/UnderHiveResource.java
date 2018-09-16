package org.goldenworkshop.warhammer.underhive.view.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.goldenworkshop.necromunda.underhive.TacticCard;
import org.goldenworkshop.necromunda.underhive.deck.CardDeck;
import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.warhammer.underhive.cdi.DeckControllerBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/warhammer/underhive")
@Tag(name = "UnderHiveResource", description = "The API for Underhive tools.")
public class UnderHiveResource implements Serializable {

    private static final int DEFAULT_DECK_PAGE_SIZE = 5;
    @Inject
    DeckControllerBean deckControllerBean;




    @GET
    @Path("/meta")
    @Produces({MediaType.APPLICATION_JSON, "*/*"})
    @Operation(
            description = "Loads the list of tactic cards currently in the system",
            summary = "Loads all tactic cards."
    )
    public Response loadUnderHiveMeta(){
        return Response.ok().entity(new UnderHiveMeta()).build();
    }



        @GET
    @Path("/tacticcard/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads the list of tactic cards currently in the system",
            summary = "Loads all tactic cards."
    )
    public Response loadCardList(@QueryParam("first") int first, @QueryParam("pageSize") int pageSize){
        List<TacticCard> cardList = new ArrayList<>(deckControllerBean.getDeckController().loadActiveCards());

        return Response.ok().entity(cardList).build();
    }



    @GET
    @Path("/deck/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads list of decks for the current user in the system",
            summary = "This is a summary"
    )
    public Response loadDeckList(@QueryParam("first") int first, @QueryParam("pageSize") Integer pageSize){


        List<CardDeck> cardDecks = deckControllerBean.getDeckController().loadLastNDecks(AuthContext.get().getUser().getId(), pageSize == null ? DEFAULT_DECK_PAGE_SIZE : pageSize);
        return Response.ok().entity(cardDecks).build();
    }

    @POST
    @Path("/deck/draw/random")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Loads list of decks for the current user in the system",
            summary = "This is a summary"
    )
    public Response drawFromDeckRandom(@QueryParam("cardCount") int cardCount, @QueryParam("excludeDraws") boolean excludeDraws,
                                 @QueryParam("affiliation")
                                 Set<String> affilationFilter){
        CardDeck draw = deckControllerBean.getDeckController().drawRandom(AuthContext.get().getUser().getId(), cardCount, excludeDraws, affilationFilter);
        return Response.ok().entity(draw).build();
    }

    @POST
    @Path("/deck/draw/use-card")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Marks a card as used.",
            summary = "Use a card"
    )
    public Response undoUseCard(@QueryParam("deckId") String deckId, @QueryParam("drawIdx") int drawIdx,
                                       @QueryParam("cardId") String cardId){
        CardDeck cardDeck = deckControllerBean.getDeckController().markCardAsUsed(deckId, drawIdx, cardId);
        return Response.ok().entity(cardDeck).build();
    }

    @DELETE
    @Path("/deck/draw/use-card")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Marks a card as used.",
            summary = "Use a card"
    )
    public Response useCard(@QueryParam("deckId") String deckId, @QueryParam("drawIdx") int drawIdx,
                                       @QueryParam("cardId") String cardId){
        CardDeck cardDeck = deckControllerBean.getDeckController().markCardAsNotUsed(deckId, drawIdx, cardId);
        return Response.ok().entity(cardDeck).build();
    }

    @POST
    @Path("/deck/draw/selected")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Draws the selected cards.",
            summary = "Draws the selected cards."
    )
    public Response drawFromDeckSelected(@QueryParam("cardId") List<String> cardIds){
        CardDeck draw = deckControllerBean.getDeckController().drawSelected(AuthContext.get().getUser().getId(), cardIds);
        return Response.ok().entity(draw).build();
    }

    @DELETE
    @Path("/deck/draw")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Undo a draw from a deck", summary = "Undo draw.")
    public Response undoDraw(@QueryParam("deckId") String deckId, @QueryParam("drawIdx") int drawIdx){
        deckControllerBean.getDeckController().undoDraw(deckId, drawIdx);
        return Response.ok().entity(deckControllerBean.getDeckController().loadDeck(deckId)).build();
    }



    @POST
    @Path("/deck/archive")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Undo a draw from a deck", summary = "Undo draw.")
    public Response archiveDeck(@QueryParam("deckId") String deckId){
        deckControllerBean.getDeckController().archive(deckId);
        return loadDeckList(-1, null);
    }



}
