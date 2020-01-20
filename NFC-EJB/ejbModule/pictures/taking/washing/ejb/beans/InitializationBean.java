package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.*;
import pictures.taking.washing.ejb.interfaces.*;
import pictures.taking.washing.persistence.entities.*;
//import pictures.taking.washing.persistence.entities.Events.InformationEvent;
//import pictures.taking.washing.persistence.entities.Events.LocationEvent;
import pictures.taking.washing.persistence.entities.*;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.persistence.enums.UsergroupEnum;
import pictures.taking.washing.persistence.enums.WashingMachineEnum;
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.Point;
//import com.vividsolutions.jts.geom.PrecisionModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;

//import pictures.taking.washing.persistence.entities.Statement;

@Singleton
@Startup

public class InitializationBean {


    @PersistenceContext
    private EntityManager em;

    @EJB
    private pictures.taking.washing.ejb.interfaces.SecurityroleDAO SecurityroleDAO;
    @EJB
    private pictures.taking.washing.ejb.interfaces.UsergroupDAO UsergroupDAO;
    @EJB
    private UserDAO userDAO;
    @EJB
    private MachineDAO machineDAO;


    @PostConstruct
    public void initialize() {
        // ObjectMapper mapper = new ObjectMapper();
        // mapper.registerModule(new JtsModule());

        if (SecurityroleDAO.getAllSecurityroles().size() == 0) {
            for (SecurityroleEnum role : SecurityroleEnum.values()) {
                if (role != SecurityroleEnum.USER) {
                    SecurityroleDAO.create(new Securityrole(role.name()));
                }
            }
        }
        if (UsergroupDAO.getAllUsergroups().size() == 0) {
            for (UsergroupEnum uge : UsergroupEnum.values()) {
                switch (uge) {
                    case admins:
                        UsergroupDAO.create(new Usergroup(UsergroupEnum.admins.name(), new HashSet<>(Arrays.asList(
//								 SecurityroleDAO.getSecurityroleByName(SecurityroleEnum.USER.name()),
                                SecurityroleDAO.getSecurityroleByName(SecurityroleEnum.ADMINISTRATOR.name())))));
                        break;
                    // case users:
                    // UsergroupDAO.create(new Usergroup(UsergroupEnum.users.name()));
                    // break;
                    case mods:
                        UsergroupDAO.create(new Usergroup(UsergroupEnum.mods.name(), new HashSet<>(Arrays.asList(SecurityroleDAO.getSecurityroleByName(SecurityroleEnum.MODERATOR.name())))));
                        break;
                    default:
                        break;
                }
            }
        }
        if (true) {
            for (User u : userDAO.findAll()) {
                userDAO.remove(u.getId());
            }

            if (userDAO.findAll().size() == 0) {
                //Mocked users
                for (int i = 0; i < 20; i++) {
                    User user2 = new User("default" + Math.random(), "test12345", "b@b.b_" + Math.random());
                    userDAO.create(user2);
                }

                // User anlegen
                User user = new User("admin", "test12345", "a@a.a");

                String[] credentials = {"jdbc:postgresql://localhost:5432/washing", "xxx", "xxx"};

                // Rechtegruppen vergeben, welche zuvor mit Security Roles versehen wurden
                user.getUsergroups().add(UsergroupDAO.getUsergroupByName(UsergroupEnum.admins.name())); // ADMIN GROUP

//                GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
//                Point p1 = gf.createPoint(new Coordinate(10.34343, 60.232424));
//                Point p2 = gf.createPoint(new Coordinate(20.34343, 50.232424));
//                Point p3 = gf.createPoint(new Coordinate(30.34343, 40.232424));
//                Point p4 = gf.createPoint(new Coordinate(40.34343, 30.232424));

                User resultUser = userDAO.create(user);


                em.flush();


            }
            //mocked machines
//            for (Machine m : machineDAO.findAll()) {
//                machineDAO.remove(m.getId());
//            }
            if (machineDAO.listMachines().size() == 0) {
                for (int i = 0; i < 20; i++) {
                    Machine machine = new Machine(i, 5+ i * 0.25 + i, 45, WashingMachineEnum.WashingMachine);
                    machineDAO.create(machine);
                }
                em.flush();
            }
        }
    }
}
