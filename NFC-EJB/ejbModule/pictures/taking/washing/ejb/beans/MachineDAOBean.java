package pictures.taking.washing.ejb.beans;


import org.hibernate.Query;
import pictures.taking.washing.ejb.interfaces.MachineDAO;
import pictures.taking.washing.ejb.interfaces.MachineDAO;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.Machine;

import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import pictures.taking.washing.persistence.entities.Hike;


@Stateless
@Remote(MachineDAO.class)
@DeclareRoles(value = {"ADMINISTRATOR", "Machine", "GUEST"})
public class MachineDAOBean implements MachineDAO {
    @PersistenceContext
    private EntityManager em;

    public static final int  machineHoldTime = 10;


    @EJB
    private MachineDAO MachineDAO;


    @Override
    public Machine create(Machine machine) {
        if (getMachineById(machine.getId()) == null) {
            em.persist(machine);
            return machine;
        }
        return null;
    }

    @Override
    public Machine update(Machine Machine) {
        Machine updatedMachine = em.merge(Machine);
        return updatedMachine;
    }

    @Override
    public Machine remove(UUID id) {
        Machine deleteMachine = find(id);
        if (deleteMachine != null) {
            em.remove(deleteMachine);
            return deleteMachine;
        }
        return null;
    }

    @Override
    public Machine find(UUID id) {
        return em.find(Machine.class, id);
    }

    @Override
    public Machine addMachines(Machine body) {
        return null;
    }

    @Override
    public Machine deleteMachine(UUID machineId) {
        return remove(machineId);
    }

    @Override
    public Machine getMachineById(UUID machineId) {
        return null;
    }

    @Override
    public List<Machine> listAvailableMachines() {

        List<Machine> machines =em.createNamedQuery(Machine.QUERY_FINDAVAILABLE, Machine.class)
                .setParameter("holdingTime", machineHoldTime)
                .getResultList();
        System.out.println(em.createNamedQuery(Machine.QUERY_FINDAVAILABLE, Machine.class)
                .setParameter("holdingTime", machineHoldTime).getParameters());

        return machines;
    }

    @Override
    public List<Machine> listMachines() {
        return em.createNamedQuery(Machine.QUERY_FINDALL, Machine.class).getResultList();
    }

    @Override
    public Machine machineHold(UUID machineId, UUID MachineId, Date timestamp) {
        return null;
    }

    @Override
    public Machine machinePay(UUID machineId, UUID cardId) {
        return null;
    }












}

