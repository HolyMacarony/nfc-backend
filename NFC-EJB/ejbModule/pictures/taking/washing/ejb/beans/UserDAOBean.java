package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.events.UserEvent;
import pictures.taking.washing.ejb.events.UserNotification;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.ejb.interfaces.UsergroupDAO;
//import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;

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


@Stateless
@Remote(UserDAO.class)
@DeclareRoles(value = {"ADMINISTRATOR", "USER", "GUEST"})
public class UserDAOBean implements UserDAO {
    @PersistenceContext
    private EntityManager em;
    @Inject
    @UserNotification
    private Event<UserEvent> event;
    @EJB
    private UsergroupDAO UsergroupDAO;

    @EJB
    private UserDAO userDAO;


    @Override

    public User create(User user) {
        // user.getUsergroups().add(UsergroupDAO.getUsergroupByName(UsergroupEnum.users.name()));
        // // DEFAULT USER GROUP

        if (findByUsername(user.getUserName()) == null) {
            em.persist(user);
//			em.flush();
            event.fire(new UserEvent("User angelegt!"));
            return user;
        }
        event.fire(new UserEvent("Username existiert bereits!"));
        return null;
    }

    @Override
    public User update(User user) {
        User updatedUser = em.merge(user);
//        event.fire(new UserEvent("User bearbeitet!"));
        return updatedUser;
    }

    @Override
    public User remove(UUID id) {
        User deleteUser = find(id);
        if (deleteUser != null) {
            em.remove(deleteUser);
            event.fire(new UserEvent("User gelöscht!"));
            return deleteUser;
        }
        return null;
    }

    @Override
    public User find(UUID id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        // event.fire(new UserEvent("Alle User ausgelesen!"));
        return em.createNamedQuery(User.QUERY_FINDALL, User.class).getResultList();
    }

//    @Override
//    public List<Hike> findHikeAndSectionsByHike(Hike hike) {
//        return em.createQuery("SELECT h FROM Hike h JOIN FETCH h.hikesections WHERE h = :hike ORDER BY h.createdAt DESC ", Hike.class)
//                .setParameter("hike", hike)
//                .getResultList();
//    }


    @Override
    public List<BaseUserData> findUsersBaseInfo() {
//        return em.createQuery("select new BaseUserData(u.id,u.userName,avg(u.id) ) from User u LEFT JOIN Hike h on u = h.user GROUP BY u", BaseUserData.class).getResultList();

        List<BaseUserData> users = em.createQuery("" +
                "SELECT new pictures.taking.washing.ejb.dto.BaseUserData(u.id, u.userName,u.email,u.createdAt, count(h.id) ) FROM User u LEFT JOIN Hike h on u = h.user GROUP BY u.id", BaseUserData.class).getResultList();

        return users;

        //        return em.createQuery("SELECT new BaseUserData(c.id, c.userName,1) FROM User c ", BaseUserData.class).getResultList();

    }


    @Override
    public String findPasswordByEmail(String email) {
        try {
            return em.createNamedQuery(User.QUERY_FINDPASSWORDBYEMAIL, String.class).setParameter("email", email).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Machine> reservedMachines(UUID userID) {
        final Long ONE_MINUTE_IN_MILLIS = 60000l;//millisecs

        Calendar date = Calendar.getInstance();
        Long t = date.getTimeInMillis();
        Date afterAddingTenMins = new Date(t + (10 * ONE_MINUTE_IN_MILLIS));

        return em.createNamedQuery(User.QUERY_FINDRESERVEDMACHINES, Machine.class).setParameter("endtime", afterAddingTenMins).setParameter("userID", userID).getResultList();
    }

    @Override
    public Double userBalance(UUID userId) {
        try {
            return em.createNamedQuery(User.QUERY_FINDBALANCE, Double.class).setParameter("userID", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User findByUsername(String userName) {
        try {
            return em.createNamedQuery(User.QUERY_FINDBYUSERNAME, User.class).setParameter("userName", userName).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
//    @RolesAllowed(value = {"USER"})
    public User findByEmail(String email) {
        try {
            return em.createNamedQuery(User.QUERY_FINDBYEMAIL, User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User findByCardId(UUID id) {
        try {
            return em.createNamedQuery(User.QUERY_FINDBYCARDID, User.class).setParameter("cardID", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User deleteUser(UUID id) {
        return remove(id);
    }

    @Override
    public User userDeduct(UUID id, Double amount) {
        User user = find(id);
        if (user != null) {
            if (user.getBalance() >= amount) {
                user.setBalance(user.getBalance() - amount);
                em.merge(user);
                em.flush();
//            }
            }
            return user;
        }
        return null;
    }

    @Override
    public User userRecharge(UUID id, Double amount) {
        User user = find(id);
        if (user != null) {
            if (amount > 0.0) {
                user.setBalance(user.getBalance() + amount);
                em.merge(user);
                em.flush();
            }
            return user;
        }
        return null;
    }


    @Override
    public User userLinkCard(UUID id, UUID cardID) {
        User user = find(id);
        if (user != null) {
            user.setCardId(cardID);
            em.merge(user);
            em.flush();
            return user;
        }
        return null;
    }
}

