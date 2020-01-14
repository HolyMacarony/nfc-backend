package pictures.taking.washing.web.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pictures.taking.washing.persistence.entities.Machine;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.UUID;

@Path("/machine")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2020-01-13T21:38:14.993Z[GMT]")public interface MachineApi  {
   
    @POST
    
    @Consumes({ "application/json" })
    
    @Operation(summary = "Adds a new machine", description = "Creates a new machine", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Machine created"),
                @ApiResponse(responseCode = "403", description = "Machine with specified ID already exists "),
                @ApiResponse(responseCode = "405", description = "Invalid input")
         })
    Response addMachines(@Parameter(description = "Machine object that needs to be added", required = true) Machine body, @Context SecurityContext securityContext);

    @DELETE
    @Path("/{machineId}")

    @Produces({ "application/json" })
    @Operation(summary = "Deletes a machine", description = "Removes the machine with matching ID.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
                @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
         })
    Response deleteMachine(@Parameter(description = "", required = true) @HeaderParam("api_key") String apiKey, @PathParam("machineId") UUID machineId, @Context SecurityContext securityContext);

    @GET
    @Path("/{machineId}")

    @Produces({ "application/json" })
    @Operation(summary = "Find machine by ID", description = "Returns a single machine.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Machine.class))),
                @ApiResponse(responseCode = "400", description = "The specified ID is not valid", content = @Content(schema = @Schema(implementation = Error.class))),
                @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = Error.class)))
         })
    Response getMachineById(@PathParam("machineId") UUID machineId, @Context SecurityContext securityContext);

    @GET
    @Path("/available")

    @Produces({ "application/json" })
    @Operation(summary = "Lists all available machines", description = "Lists all available machines. This includes all, that are neither on hold, nor occupied. ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Available machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class))))
         })
    Response listAvailableMachines(@Context SecurityContext securityContext);

    @GET

    @Produces({ "application/json" })
    @Operation(summary = "Lists all machines", description = "Lists all machines.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class))))
         })
    Response listMachines(@Context SecurityContext securityContext);

    @POST
    @Path("/{machineId}/hold")

    @Produces({ "application/json" })
    @Operation(summary = "Holds the machine", description = "Holds the machine for the given user for 10 minutes.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
                @ApiResponse(responseCode = "400", description = "Machine already held by another user", content = @Content(schema = @Schema(implementation = Error.class))),
                @ApiResponse(responseCode = "404", description = "Machine or user ID not found", content = @Content(schema = @Schema(implementation = Error.class)))
         })
    Response machineHold(@PathParam("machineId") UUID machineId, @NotNull @QueryParam("userId") UUID userId, @NotNull @QueryParam("timestamp") Date timestamp, @Context SecurityContext securityContext);

    @POST
    @Path("/{machineId}/pay")

    @Produces({ "application/json" })
    @Operation(summary = "Pay for the given machine", description = "Pays for the given machine. In order to be able to pay for a machine, the user must be holding the machine in question. ", security = {
        @SecurityRequirement(name = "api_key")    }, tags={ "machine" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
                @ApiResponse(responseCode = "400", description = "machine not held by user or not enough funds", content = @Content(schema = @Schema(implementation = Error.class))),
                @ApiResponse(responseCode = "404", description = "Machine or card ID not found", content = @Content(schema = @Schema(implementation = Error.class)))
         })
    Response machinePay(@PathParam("machineId") UUID machineId, @NotNull @QueryParam("cardId") UUID cardId, @Context SecurityContext securityContext);

}
