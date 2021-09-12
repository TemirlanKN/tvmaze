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
//import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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

    @GET
    @Path("/insert")
    @Produces(MediaType.TEXT_PLAIN)
    public String get2(@QueryParam("title") String title) throws SQLException, ClassNotFoundException {
        TvSerie tvSerie = tvSeriesProxy.get(title);
        String username = "root";
        String password = "Qwertypoloasd321!";
        String connectionUrl = "jdbc:mysql://localhost:3306/movie";
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

}
