package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.PlayerPlaysHikeDAO;
import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.PlayerPlaysHike;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(PlayerPlaysHikeDAO.class)
public class PlayerPlaysHikeDAOBean implements PlayerPlaysHikeDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public PlayerPlaysHike create(PlayerPlaysHike playerPlaysHike) {
        return null;
    }

    @Override
    public PlayerPlaysHike update(PlayerPlaysHike playerPlaysHike) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public PlayerPlaysHike find(Long id) {
        return null;
    }

    @Override
    public List<PlayerPlaysHike> findAllByPlayerPlaysHike(Hike hike) {
        return null;
    }

    @Override
    public List<PlayerPlaysHike> findAll() {
        return null;
    }
}
