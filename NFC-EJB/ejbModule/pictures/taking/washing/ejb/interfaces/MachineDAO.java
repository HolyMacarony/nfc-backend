package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import pictures.taking.washing.persistence.entities.Hike;

public interface MachineDAO {

    Machine create(Machine Machine);

    Machine update(Machine Machine);

    Machine remove(UUID id);

    Machine find(UUID id);

    Machine addMachines(Machine machine);

    Machine deleteMachine(UUID machineId);

    Machine getMachineById(UUID machineId);

    List<Machine>  listAvailableMachines();

    List<Machine>  listMachines();

    Machine machineHold(UUID machineId, UUID MachineId, Date timestamp);

    Machine machinePay(UUID machineId, UUID cardId);
}