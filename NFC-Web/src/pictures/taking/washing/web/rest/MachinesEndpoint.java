package pictures.taking.washing.web.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pictures.taking.washing.ejb.interfaces.MachineDAO;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/machine")
@Stateless
public class MachinesEndpoint {

    @Inject
    @AuthenticatedRESTUser
    User authenticatedRESTUser;

    @EJB
    private MachineDAO machineDAO;
    @EJB
    private pictures.taking.washing.ejb.interfaces.SecurityroleDAO SecurityroleDAO;
    @EJB
    private pictures.taking.washing.ejb.interfaces.UsergroupDAO UsergroupDAO;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Operation(summary = "Lists all machines", description = "Lists all machines.", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class))))
    })
    public List<Machine> listMachines() {
        if (authenticatedRESTUser.isAdmin()) {
            return machineDAO.listMachines();
        }
        throw new NotAuthorizedException("not authorized");
    }

    @GET
    @Path("/available")
    @Secured({SecurityroleEnum.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lists all available machines", description = "Lists all available machines. This includes all, that are neither on hold, nor occupied. ", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class))))})
    public List<Machine> listAvailableMachines() throws NotFoundException {
        return machineDAO.listAvailableMachines();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Operation(summary = "Adds a new machine", description = "Creates a new machine", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Machine created"),

            @ApiResponse(responseCode = "403", description = "Machine with specified ID already exists "),

            @ApiResponse(responseCode = "405", description = "Invalid input")})
    public Machine addMachines(@Parameter(description = "Machine object that needs to be added", required = true) Machine machine) {
        System.out.println(machine);
        return machineDAO.create(machine);
    }

    @Path("/{machineId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.ADMINISTRATOR})
    @Operation(summary = "Deletes a machine", description = "Removes the machine with matching ID.", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    public Machine deleteMachine(@Parameter(description = "", required = true) @PathParam("machineId") UUID machineId) throws NotFoundException {
        return machineDAO.deleteMachine(machineId);
    }

    @GET
    @Path("/{machineId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.USER})
    @Operation(summary = "Find machine by ID", description = "Returns a single machine.", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Machine.class))),

            @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    public Machine getMachineById(@PathParam("machineId") UUID machineId) throws NotFoundException {
        if (machineId != null) {
            if (machineId.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.isAdmin()) {
                return machineDAO.find(machineId);
            }
        }
        throw new NotAuthorizedException("not authorized");
    }

    @POST
    @Path("/{machineId}/hold")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({SecurityroleEnum.USER})
    @Operation(summary = "Holds the machine", description = "Holds the machine for the given user for 10 minutes.", tags = {"machine"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),

            @ApiResponse(responseCode = "400", description = "Machine already held by another user", content = @Content(schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Machine or user ID not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    public Machine machineHold(@PathParam("machineId") UUID machineId, @NotNull @QueryParam("userId") UUID userId, @NotNull @QueryParam("timestamp") Timestamp timestamp) throws NotFoundException {
        if (machineId != null && userId != null && timestamp != null) {
            if (userId.equals(authenticatedRESTUser.getId()) || authenticatedRESTUser.isAdmin()) {
                return machineDAO.machineHold(machineId, userId, timestamp);
            }
        }
        throw new NotAuthorizedException("not authorized");
    }
}
