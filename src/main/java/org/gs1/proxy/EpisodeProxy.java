package org.gs1.proxy;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.gs1.model.Episode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/shows")
@Produces(MediaType.APPLICATION_JSON)
public interface EpisodeProxy {

    @GET
    @Path("{id}/episodes")
    List<Episode> get(@PathParam("id") Long id);

}
