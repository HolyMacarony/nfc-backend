package pictures.taking.washing.web.rest;


import pictures.taking.washing.persistence.entities.Machine;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2020-01-13T21:38:14.993Z[GMT]")public class MachineApiServiceImpl implements MachineApi {
      public Response addMachines(Machine body, SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response deleteMachine(String apiKey, UUID machineId, SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response getMachineById(UUID machineId, SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response listAvailableMachines(SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response listMachines(SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response machineHold(UUID machineId, UUID userId, Date timestamp, SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
      public Response machinePay(UUID machineId, UUID cardId, SecurityContext securityContext) {
      // do some magic!
      return Response.ok().build();
  }
}
