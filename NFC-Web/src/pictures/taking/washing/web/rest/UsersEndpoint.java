package pictures.taking.washing.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Stateless
public class UsersEndpoint {

    @Inject
    @AuthenticatedRESTUser
    User authenticatedRESTUser;

    @EJB
    private UserDAO userDAO;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @GET
    @Path("allBase")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public List<BaseUserData> getAllBaseInfo() {
        return userDAO.findUsersBaseInfo();
    }

    @GET
    @Path("{id}")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserById(@PathParam("id") Long id) {
        try {
           return new ObjectMapper().writeValueAsString(userDAO.find(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        return userDAO.create(user);
    }

    @DELETE
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Path("delete/{id}")
    public String deleteUser(@PathParam("id") Long id) {
        System.out.println(authenticatedRESTUser);
        return userDAO.remove(id).toString();
    }
}
