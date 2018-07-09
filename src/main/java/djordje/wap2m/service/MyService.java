package djordje.wap2m.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import djordje.wap2m.model.EntryDAO;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author djordje gavrilovic
 */
@Path("/MyService")
public class MyService {

    @Context
    private UriInfo context;

    public MyService() {
    }

    private static EntryDAO entryDao = new EntryDAO();
    private static ObjectMapper om = new ObjectMapper();

    // initial GET
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String doGetAllData() throws JsonProcessingException {
        return (om.writeValueAsString(entryDao.getAllEntries()));
    }

    // POST - add new entry
    @POST
    public Response doPostSave(@FormParam("title") String title, @FormParam("txt") String txt) {
        if (title != null) {
            entryDao.addNewEntry(title, txt);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.PARTIAL_CONTENT).build();
        }
    }

    // DELETE selected entry
    @DELETE
    @Path("{entryId}")
    public Response doDelete(@PathParam("entryId") String id) {
        if (id != null) {
            entryDao.removeEntry(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
}
