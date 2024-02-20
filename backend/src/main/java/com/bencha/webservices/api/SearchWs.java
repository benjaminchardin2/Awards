package com.bencha.webservices.api;

import com.bencha.services.SearchService;
import com.bencha.webservices.beans.AdditionalResults;
import com.bencha.webservices.beans.Nominee;
import com.bencha.webservices.beans.NomineeRequest;
import com.bencha.webservices.beans.SearchResult;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/search")
@Tag(name = "search", description = "Search movies / persons etc.")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class SearchWs {
    private final SearchService searchService;

    @Inject
    public SearchWs(SearchService searchService) {
        this.searchService = searchService;
    }

    @GET
    @Path("/movies")
    @Operation(description = "Search movie")
    public List<SearchResult> searchMovies(
            @Parameter(required = false) @QueryParam("page") Integer page,
            @Parameter(required = true) @QueryParam("query") String query
    ) {
        return searchService.searchMovies(query, page != null ? page : 0);
    }

    @GET
    @Path("/persons")
    @Operation(description = "Search movie")
    public List<SearchResult> searchPersons(
            @Parameter(required = false) @QueryParam("page") Integer page,
            @Parameter(required = true) @QueryParam("query") String query
    ) {
        return searchService.searchPersons(query, page != null ? page : 0);
    }

    @GET
    @Path("/movies/{movieId}/persons")
    @Operation(description = "Search movie persons")
    public AdditionalResults searchMoviePersons(
            @PathParam("movieId") Long movieId,
            @Parameter(required = false) @QueryParam("page") Integer page,
            @Parameter(required = true) @QueryParam("awardId") Long awardId
    ) {
        return searchService.searchMoviePersons(awardId, movieId, page);
    }

    @GET
    @Path("/persons/{personId}/credits")
    @Operation(description = "Search persons credits")
    public AdditionalResults searchPersonsCredits(
            @PathParam("personId") Long personId,
            @Parameter(required = false) @QueryParam("page") Integer page,
            @Parameter(required = true) @QueryParam("awardId") Long awardId
    ) {
        return searchService.searchPersonCredits(awardId, personId, page);
    }

    @POST
    @Path("/nominee")
    @Operation(description = "Convert search to nominee")
    public Nominee convertSearchToNominee(
        NomineeRequest nomineeRequest
    ) {
        return searchService.convertSearchToNominee(nomineeRequest);
    }
}
