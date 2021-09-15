package org.gs1;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gs1.model.Episode;
import org.gs1.model.TvSerie;
import org.gs1.proxy.EpisodeProxy;
import org.gs1.proxy.TvSeriesProxy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/*
    Simple java class imports data from api.tvmaze.com and shows in localhost:8080/movie
 */

@Path("/movie")
@Configuration
@PropertySource("classpath:application.properties")
public class TvSeriesResource {

    //@Value("${SqlUsername}")
    private String username = "root";
    private String password = "Qwertypoloasd321!";
    private String connectionUrl = "jdbc:mysql://localhost:3306/movie";

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

    @GET
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public String postSql(@QueryParam("title") String title) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            Statement statement = connection.createStatement()){
            //String table_name = "movies";
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS movies (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY(id))");
            statement.executeUpdate("insert into movies (name) select '" + title + "' from dual where not exists(select * from movies where name='" + title + "')");
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        }
        return "inserted " + title ;
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get2() {
//        TvSerie tvSerie = tvSeriesProxy.get(title);
        return Response.ok(tvSeries).build();
    }

}
