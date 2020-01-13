package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.User;

import java.util.List;

//remote used interface, optional @Remote to force it being Remote
public interface HikeDAO {
    Hike create(Hike hike);

    Hike update(Hike hike);

    void remove(Long id);

    Hike find(Long id);

    List<Hike> findAllByUser(User user);

    Hike findByURL(String url);

    Hike findByURLAndUser(String url, User user);

    List<Hike> findAll();
}
