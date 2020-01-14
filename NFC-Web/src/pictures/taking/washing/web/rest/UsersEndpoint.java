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
import pictures.taking.washing.ejb.interfaces.UsergroupDAO;
import pictures.taking.washing.persistence.entities.InlineResponse200;
import pictures.taking.washing.persistence.entities.InlineResponse2001;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.persistence.enums.UsergroupEnum;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;

@Path("/user")
@Stateless
public class UsersEndpoint {

    @Inject
    @AuthenticatedRESTUser
    User authenticatedRESTUser;

    @EJB
    private UserDAO userDAO;
    @EJB
    private pictures.taking.washing.ejb.interfaces.SecurityroleDAO SecurityroleDAO;
    @EJB
    private pictures.taking.washing.ejb.interfaces.UsergroupDAO UsergroupDAO;


    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @GET
    @Path("/{userId}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.USER})
    @Operation(summary = "Check the user's balance", description = "Returns the balance of the given user.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user's balance", content = @Content(schema = @Schema(implementation = InlineResponse200.class))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public Double userBalance(@PathParam("userId") Long userId) {
        System.out.println(authenticatedRESTUser);

        if (userId != null && userId > 0 && authenticatedRESTUser != null) {
            System.out.println(authenticatedRESTUser);
            if (userId.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.isAdmin()) {

                return userDAO.userBalance(userId);
            }
        }
        throw new NotAuthorizedException("not authorized");

    }


    @GET
    @Produces({"application/json"})
    @Path("")
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Operation(summary = "Lists all users", description = "Lists all registered users.", tags = {"user"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "registered users", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public List<User> listUsers() {
        if (authenticatedRESTUser.isAdmin()) {
            return userDAO.findAll();
        }
        throw new NotAuthorizedException("not authorized");

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
            @SecurityRequirement(name = "bearerAuth")}, tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public String getUserById(@PathParam("userId") Long id) {
        if (id != null && id > 0) {
            if (id.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.isAdmin()) {
                try {
                    return new ObjectMapper().writeValueAsString(userDAO.find(id));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new NotAuthorizedException("not authorized");
    }

    @GET
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByCardId")
    @Operation(summary = "Find user by card ID", description = "Returns a single user ID, who is the owner of the card with the given ID.", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = InlineResponse2001.class))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public String getUserByCardId(@QueryParam("cardID") Long id) {
        if (id != null && id > 0) {
            try {
                return new ObjectMapper().writeValueAsString(userDAO.findByCardId(id));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        throw new NotAuthorizedException("not authorized");
    }


    @GET
    @Path("/{userId}/reserved")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check which machines are reserved by the given user", description = "Returns the currently reserved machines of the given user.", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user's reserved machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class)))),
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public List<Machine> reservedMachines(@PathParam("userId") Long id) {
        System.out.println(authenticatedRESTUser);

        if (id.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.isAdmin()) {
            return userDAO.reservedMachines(id);
        }
        throw new NotAuthorizedException("not authorized");
    }


    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Adds a new user", description = "Creates a new user.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "403", description = "User with specified ID already exists "),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    public User addUser(User user) {
        System.out.println(user);
//        User newUser = new User(user.getUserName(),user.getPassword(),user.getEmail());
//        System.out.println(newUser);
        return userDAO.create(user);
    }

    @DELETE
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a user", description = "Removes the user with matching ID.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public User deleteUser(@PathParam("id") Long id) {
        return userDAO.deleteUser(id);
    }
}
