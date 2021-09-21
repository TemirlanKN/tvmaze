package org.gs1;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gs1.model.Episode;
import org.gs1.model.TvSerie;
import org.gs1.proxy.EpisodeProxy;
import org.gs1.proxy.TvSeriesProxy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
/*
    Simple java class imports data from api.tvmaze.com and shows in localhost:8080/movie
 */

@Path("/tv-maze")
@Configuration
@PropertySource("classpath:application.properties")
@Tag(name = "TvMaze movies", description="Movie Rest API")
public class TvSeriesResource {

    private final JdbcTemplate jdbcTemplate;
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("12345");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RestClient
    TvSeriesProxy tvSeriesProxy;
    @RestClient
    EpisodeProxy episodeProxy;

    @GET
    @Path("/movie/show")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "showMovie", summary = "Show movie", description = "Show movie inserted in param")
    @APIResponse (responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response moviesGet(@QueryParam("title") String title)  {
        var tvSerie = tvSeriesProxy.get(title);
        return Response.ok(tvSerie).build();
    }

    @GET
    @Path("/series/show")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "showSeries", summary = "Show series", description = "Show series inserted in param")
    @APIResponse (responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response seriesGet(@QueryParam("title") String title) {
        var tvSerie = tvSeriesProxy.get(title);
        List<Episode> episodes = episodeProxy.get(tvSerie.getId());
        return Response.ok(episodes).build();
    }

    @GET
    @Path("/database")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "showHistory", summary = "Show history", description = "Show history of posted movies")
    @APIResponse (responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response get2() {
        var tvSeries = jdbcTemplate.query("SELECT * FROM movies", new BeanPropertyRowMapper<>(TvSerie.class));
        return Response.ok(tvSeries).build();
    }

    @DELETE
    @Path("/database")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "deleteMovie", summary = "Delete Movie", description = "Delete movie from database")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    public Response delete(@QueryParam("title") String title){
        int a = jdbcTemplate.update("delete from movies where name=? and CTID IN " +
                "(SELECT CTID from movies where name=? limit 1);", title, title);
        if (a != 0) {
            return Response.ok("Deleted: " + title).build();
        }
        return Response.ok("No inserted title: " + title).build();
    }

    @POST
    @Path("/database/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response DataBaseAdd(@QueryParam("title") String title) {
        var tvSerie = tvSeriesProxy.get(title);
        jdbcTemplate.update("INSERT INTO movies (name, url, type, language) VALUES (?,?,?,?)", tvSerie.getName(),
                tvSerie.getUrl(), tvSerie.getType(), tvSerie.getLanguage());
        return Response.ok(tvSerie).build();
    }
}
