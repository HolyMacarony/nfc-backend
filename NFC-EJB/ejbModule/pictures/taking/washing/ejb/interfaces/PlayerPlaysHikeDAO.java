package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.PlayerPlaysHike;

import java.util.List;

//@Local // until there is an implementation
public interface PlayerPlaysHikeDAO {

    PlayerPlaysHike create(PlayerPlaysHike playerPlaysHike);

    PlayerPlaysHike update(PlayerPlaysHike playerPlaysHike);

    void remove(Long id);

    PlayerPlaysHike find(Long id);

    List<PlayerPlaysHike> findAllByPlayerPlaysHike(Hike hike);

    List<PlayerPlaysHike> findAll();
}
