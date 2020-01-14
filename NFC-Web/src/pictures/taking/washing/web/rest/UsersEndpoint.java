package pictures.taking.washing.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.http.HttpStatus;
import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.persistence.enums.UsergroupEnum;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Produces({"application/json"})
    @Path("")
    @Operation(summary = "Lists all users", description = "Lists all registered users.", tags = {"user"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "registered users", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public  List<User>  listUsers() {
        return  userDAO.findAll();
    }


    @GET
    @Path("allBase")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public List<BaseUserData> getAllBaseInfo() {
        return userDAO.findUsersBaseInfo();
    }

    @GET
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    @Operation(summary = "Find user by ID", description = "Returns a single user.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public String getUserById(@PathParam("userId") Long id) {
        try {
            return new ObjectMapper().writeValueAsString(userDAO.find(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("/{userId}/reserved")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check which machines are reserved by the given user", description = "Returns the currently reserved machines of the given user.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user's reserved machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class)))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public List<Machine> reservedMachines(@PathParam("userId") Long id) {
        System.out.println(authenticatedRESTUser);
        System.out.println(authenticatedRESTUser.getUsergroups());
        if(id.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.getUsergroups().contains(UsergroupEnum.admins.name())){

            return userDAO.reservedMachines(id);
        }
        else{
         throw new NotAuthorizedException("not authorized");
        }
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
