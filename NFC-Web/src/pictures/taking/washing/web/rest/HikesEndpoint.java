package pictures.taking.washing.web.rest;

import pictures.taking.washing.ejb.interfaces.HikeDAO;
import pictures.taking.washing.persistence.entities.Hike;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hike")
@Stateless
public class HikesEndpoint {

    @EJB
    private HikeDAO hikeDAO;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hike> getAll() {
        return hikeDAO.findAll();
    }


}
