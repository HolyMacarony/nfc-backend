package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Hikesection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(HikesectionDAO.class)
public class HikesectionDAOBean implements HikesectionDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Hikesection create(Hikesection Hikesection) {
        em.persist(Hikesection);
        return Hikesection;
    }

    @Override
    public Hikesection update(Hikesection Hikesection) {
        Hikesection updateHikesection = em.merge(Hikesection);
        return updateHikesection;
    }

    @Override
    public void remove(Long id) {
        Hikesection deleteHikesection = find(id);
        em.remove(deleteHikesection);
    }

    @Override
    public Hikesection find(Long id) {
        return em.find(Hikesection.class, id);
    }

    @Override
    public List<Hikesection> findAllByHike(Hike hike) {
        return em.createNamedQuery(Hikesection.QUERY_FINDALLBYHIKE, Hikesection.class)
                .setParameter("hikeId", hike.getId())
                .getResultList();
    }
}
