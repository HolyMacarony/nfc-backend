package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.List;

//import pictures.taking.washing.persistence.entities.Hike;

public interface MachineDAO {

    Machine create(Machine Machine);

    Machine update(Machine Machine);

    Machine remove(Long id);

    Machine find(Long id);

    Machine addMachines(Machine body);

    Machine deleteMachine(String apiKey, Long machineId);

    Machine getMachineById(Long machineId);

    List<Machine>  listAvailableMachines();

    List<Machine>  listMachines();

    Machine machineHold(Long machineId, Long MachineId, Date timestamp);

    Machine machinePay(Long machineId, Long cardId);
}