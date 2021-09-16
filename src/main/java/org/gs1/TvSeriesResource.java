package org.gs1;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
//import java.lang.invoke.TypeDescriptor;
import java.util.*;
import java.sql.*;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/*
    Simple java class imports data from api.tvmaze.com and shows in localhost:8080/movie
 */

@Path("/tv-maze")
@Configuration
@PropertySource("classpath:application.properties")
@Tag(name = "TvMaze movies", description="Movie Rest API")
public class TvSeriesResource {
    //@Value("${SqlUsername}")
    private String username = "root";
    private String password = "Qwertypoloasd321!";
    private String connectionUrl = "jdbc:mysql://localhost:3306/movie";

    @RestClient
    TvSeriesProxy tvSeriesProxy;
    @RestClient
    EpisodeProxy episodeProxy;

    private Map<String,TvSerie> tvSeries = new HashMap<>();

    private void updateData(String title) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            Statement statement = connection.createStatement()){
            //String table_name = "movies";
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS movies (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY(id))");
            statement.executeUpdate("insert into movies (name) select '" + title + "' from dual where not exists(select * from movies where name='" + title + "')");
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        }
    }

    @GET
    @Path("/movie/show")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "showMovie",
            summary = "Show movie",
            description = "Show movie inserted in param"
    )
    @APIResponse (
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response moviesGet(@QueryParam("title") String title)  {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        return Response.ok(tvSerie).build();
    }

    @GET
    @Path("/movie/post")
    @Operation(
            operationId = "postMovie",
            summary = "Post movie",
            description = "Post movie to the local HashMap"
    )
    @APIResponse (
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public String moviesPost(@QueryParam("title") String title)  {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        if (tvSeries.containsKey(title)){
            return "Already exist";
        }
        tvSeries.put(title, tvSerie);
        return "Added";
    }

    @GET
    @Path("/series/show")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "showSeries",
            summary = "Show series",
            description = "Show series inserted in param"
    )
    @APIResponse (
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response seriesGet(@QueryParam("title") String title) {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        List<Episode> episodes = episodeProxy.get(tvSerie.getId());
        return Response.ok(episodes).build();
    }

    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "showHistory",
            summary = "Show history",
            description = "Show history of posted movies"
    )
    @APIResponse (
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response get2() {
        return Response.ok(tvSeries).build();
    }

    @GET
    @Path("/dataBase/update")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            operationId = "dataBaseUpdate",
            summary = "Database update",
            description = "Update local database"
    )
    @APIResponse (
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Response DataBaseAdd(@QueryParam("title") String title) {
        try {
            updateData(title);
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok("Added").build();
    }
}
