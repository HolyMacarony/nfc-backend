package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.MediaDAO;
import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Media;
import pictures.taking.washing.persistence.entities.User;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(MediaDAO.class)

public class MediaDAOBean implements MediaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Media create(Media media) {
        em.persist(media);
        return media;
    }

    @Override
    public Media update(Media media) {
        return em.merge(media);
    }

    @Override
    public void remove(Long id) {
        Media deleteMedia = find(id);
        em.remove(deleteMedia);
    }

    @Override
    public Media find(Long id) {
        return null;
    }

    @Override
    public List<Media> findAllByHike(Hike hike) {
        return null;
    }

    @Override
    public List<Media> findByUser(User user) {
        return null;
    }

    @Override
    public List<Media> findAll() {
        return null;
    }
}
