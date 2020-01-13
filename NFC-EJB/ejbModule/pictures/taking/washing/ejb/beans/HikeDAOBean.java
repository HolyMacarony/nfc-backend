package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.HikeDAO;
import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.User;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(HikeDAO.class)
// so EJB Clients know to use CustomerDAO interface. EJB client(e.g. Web application) only uses remote view
public class HikeDAOBean implements HikeDAO {
    @EJB
    HikesectionDAO hikesectionDAO;


    @EJB
    HikeeventDAO hikeeventDAO;
    @PersistenceContext
    private EntityManager em;

    @EJB
    private HikeDAO hikedao;

    @Override
    public Hike create(Hike hike) {

        em.persist(hike);
        return hike;
    }

    @Override
    public Hike update(Hike hike) {
        Hike updateHike = em.merge(hike);
        return updateHike;
    }

    @Override
    public void remove(Long id) {
        Hike deleteHike = find(id);
        User u = em.merge(deleteHike.getUser());
        u.getHikes().remove(deleteHike);
        em.merge(u);
    }

    @Override
    public Hike find(Long id) {
        return em.find(Hike.class, id);
    }

    @Override
    public List<Hike> findAllByUser(User user) {
        return em.createNamedQuery(Hike.QUERY_FINDALLBYUSER, Hike.class).setParameter("userID", user.getId()).getResultList();
    }

    @Override
    public Hike findByURL(String url) {
        try {
            return em.createNamedQuery(Hike.QUERY_FINDBYURL, Hike.class).setParameter("url", url).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Hike findByURLAndUser(String url, User user) {
        try {
            return em.createNamedQuery(Hike.QUERY_FINDBYURLANDUSERID, Hike.class).
                    setParameter("url", url).
                    setParameter("userID", user.getId()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Hike> findAll() {
        return em.createNamedQuery(Hike.QUERY_FINDALL, Hike.class).getResultList();
    }
}
