package org.gs1;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gs1.model.Episode;
import org.gs1.model.TvSerie;
import org.gs1.proxy.EpisodeProxy;
import org.gs1.proxy.TvSeriesProxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/*
    Simple java class imports data from api.tvmaze.com and shows in localhost:8080/movie
 */

@Path("/movie")
public class TvSeriesResource {

    @RestClient
    TvSeriesProxy tvSeriesProxy;

    @RestClient
    EpisodeProxy episodeProxy;

    private List<TvSerie> tvSeries = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //URL example: http://localhost:8080/movie?title=game of thrones
    public Response get(@QueryParam("title") String title)  {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        tvSeries.add(tvSerie);
        return Response.ok(tvSeries).build();
    }

    @GET
    @Path("/series")
    //URL example = http://localhost:8080/movie/series?title=game of thrones
    @Produces(MediaType.APPLICATION_JSON)
    public Response get1(@QueryParam("title") String title) {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        List<Episode> episodes = episodeProxy.get(tvSerie.getId());
        return Response.ok(episodes).build();
    }
}
