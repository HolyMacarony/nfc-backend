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
import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.interfaces.MachineDAO;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.InlineResponse200;
import pictures.taking.washing.persistence.entities.InlineResponse2001;
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
import java.util.List;
import java.util.UUID;

@Path("/user")
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
    @Operation(summary = "Lists all machines", description = "Lists all machines.",  tags={ "machine" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All machines", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class))))
    })
    public List<Machine> listMachines() {
        if (authenticatedRESTUser.isAdmin()) {
            return machineDAO.listMachines();
        }
        throw new NotAuthorizedException("not authorized");

    }




}
