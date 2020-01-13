package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.*;
import pictures.taking.washing.ejb.interfaces.*;
import pictures.taking.washing.persistence.entities.*;
import pictures.taking.washing.persistence.entities.Events.InformationEvent;
import pictures.taking.washing.persistence.entities.Events.LocationEvent;
import pictures.taking.washing.persistence.entities.*;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.persistence.enums.UsergroupEnum;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

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
    private HikesectionDAO hikesectionDAO;
    @EJB
    private HikeeventDAO hikeeventDAO;
    @EJB
    private HikeDAO hikedao;

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
                for (int i = 0; i < 20; i++) {
                    User user2 = new User("default" + Math.random(), "test12345", "b@b.b_" + Math.random());
                    user2.setFirstName("Default");
                    user2.setLastName("User");
                    userDAO.create(user2);
                }

                // User anlegen
                User user = new User("admin", "test12345", "a@a.a");
                user.setFirstName("Admin");
                user.setLastName("User");

                String[] credentials = {"jdbc:postgresql://localhost:5432/washing", "xxx", "xxx"};

                // Rechtegruppen vergeben, welche zuvor mit Security Roles versehen wurden
                user.getUsergroups().add(UsergroupDAO.getUsergroupByName(UsergroupEnum.admins.name())); // ADMIN GROUP

                GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
                Point p1 = gf.createPoint(new Coordinate(10.34343, 60.232424));
                Point p2 = gf.createPoint(new Coordinate(20.34343, 50.232424));
                Point p3 = gf.createPoint(new Coordinate(30.34343, 40.232424));
                Point p4 = gf.createPoint(new Coordinate(40.34343, 30.232424));

                User resultUser = userDAO.create(user);

                Hike h1 = new Hike();
                h1.setTitle("Erste Tour XY");
                h1.setPublicurl("ganzTolleUndEindeutigeURL");
                h1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                h1.setLineargameplay(true);
                h1.setPrivatehike(false);
                h1.setSingleplayer(false);
                h1.setStartlocation(p1);
                h1.setEndlocation(p3);
                h1.setUser(resultUser);
                h1 = hikedao.create(h1);
                em.flush();

                Hike h2 = new Hike();
                h2.setTitle("Zweite Tour XYZ");
                h2.setPublicurl("ganzTolleUndEindeutigeURL2");
                h2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                h2.setLineargameplay(true);
                h2.setPrivatehike(false);
                h2.setSingleplayer(false);
                h2.setStartlocation(p1);
                h2.setEndlocation(p3);
                h2.setUser(resultUser);
                h2 = hikedao.create(h2);
//				em.flush();

                Hike h3 = new Hike();
                h3.setTitle("Dritte Tour XYZ");
                h3.setPublicurl("ganzTolleUndEindeutigeURL3");
                h3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                h3.setLineargameplay(true);
                h3.setPrivatehike(false);
                h3.setSingleplayer(false);
                h3.setStartlocation(p1);
                h3.setEndlocation(p3);
                h3.setUser(resultUser);
                h3 = hikedao.create(h3);
//				em.flush();

                Hikesection hs0 = new Hikesection();
                hs0.setLocation(p1);
                hs0.setTitle("my zero section");
                hs0.setPreviousHikesection(null);
                h1.addHikesection(hs0);
                hs0 = hikesectionDAO.create(hs0);
                em.flush();
                //SECTIONS
                Hikesection hs1 = new Hikesection();
                hs1.setLocation(p1);
                hs1.setTitle("my first section");
                hs1.setPreviousHikesection(hs0);
                h1.addHikesection(hs1);
                hs1 = hikesectionDAO.create(hs1);
                em.flush();

                Hikesection hs2 = new Hikesection();
                hs2.setLocation(p1);
                hs2.setTitle("my second section");
                hs2.setPreviousHikesection(hs1);
                h1.addHikesection(hs2);
                hs2 = hikesectionDAO.create(hs2);
                em.flush();

                hs0.setNextHikesection(hs1);
                hikesectionDAO.update(hs0);
                hs1.setNextHikesection(hs2);
                hikesectionDAO.update(hs1);
//				TransactionSynchronizationManager.isActualTransactionActive();
//				TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
                //EVENTS SECTION 1
                LocationEvent le = new LocationEvent();
                le.setLocationevent_findmandatory(false);
                le.setLocation(p4);
                le.setLocationevent_points(60);
                le.setLocationevent_text("Gehe nach xY");
                le.setLocationevent_showmapnotarrow(false);
                le.setLocationevent_findmandatory(true);
                le.setLocationevent_locationToGo(p1);
                le.setHikesection(hs1);
                le = (LocationEvent) hikeeventDAO.create(le);

                InformationEvent ie0 = new InformationEvent();
                ie0.setLocation(p2);
                ie0.setInformationevent_text("Tolle Informationene stehen hier!Tolle Informationene stehen hier!Tolle Informationene stehen hier!Tolle Informationene stehen hier!Tolle Informationene stehen hier!");
                ie0.setScorepoints(100);
                ie0.setPreviousHikeevent(le);
                ie0.setHikesection(hs1);
                ie0 = (InformationEvent) hikeeventDAO.create(ie0);

                InformationEvent ie1 = new InformationEvent();
                ie1.setLocation(p2);
                ie1.setInformationevent_text("Hier stehen ganz andere InformationenHier stehen ganz andere InformationenHier stehen ganz andere InformationenHier stehen ganz andere InformationenHier stehen ganz andere InformationenHier stehen ganz andere Informationen");
                ie1.setScorepoints(223);
                ie1.setHikesection(hs1);
                ie1.setPreviousHikeevent(ie0);
                ie1 = (InformationEvent) hikeeventDAO.create(ie1);

                le.setNextHikeevent(ie0);
                hikeeventDAO.update(le);
                ie0.setNextHikeevent(ie1);
                hikeeventDAO.update(ie0);

                //EVENTS SECTION 2
                LocationEvent le2 = new LocationEvent();
                le2.setLocationevent_findmandatory(false);
                le2.setLocation(p4);
                le2.setLocationevent_points(78);
                le2.setLocationevent_text("Gehe nach ZZZ");
                le2.setLocationevent_showmapnotarrow(false);
                le2.setLocationevent_findmandatory(true);
                le2.setLocationevent_locationToGo(p1);
                le2.setHikesection(hs2);
                le2 = (LocationEvent) hikeeventDAO.create(le2);

                InformationEvent ie0_1 = new InformationEvent();
                ie0_1.setLocation(p2);
                ie0_1.setInformationevent_text("Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!Gute Informationen!");
                ie0_1.setScorepoints(75);
                ie0_1.setPreviousHikeevent(le2);
                ie0_1.setHikesection(hs2);
                ie0_1 = (InformationEvent) hikeeventDAO.create(ie0_1);

                InformationEvent ie1_1 = new InformationEvent();
                ie1_1.setLocation(p2);
                ie1_1.setInformationevent_text("Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!Ein ganz ganz toller TExt!");
                ie1_1.setScorepoints(123);
                ie1_1.setHikesection(hs2);
                ie1_1.setPreviousHikeevent(ie0_1);
                ie1_1 = (InformationEvent) hikeeventDAO.create(ie1_1);

                le2.setNextHikeevent(ie0_1);
                hikeeventDAO.update(le2);
                ie0_1.setNextHikeevent(ie1_1);
                hikeeventDAO.update(ie0_1);
            }
        }
    }
}
