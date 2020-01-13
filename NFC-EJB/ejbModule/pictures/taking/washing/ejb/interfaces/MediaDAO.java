package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Media;
import pictures.taking.washing.persistence.entities.User;

import java.util.List;

public interface MediaDAO {
    Media create(Media media);

    Media update(Media media);

    void remove(Long id);

    Media find(Long id);

    List<Media> findAllByHike(Hike hike);

    List<Media> findByUser(User user);

    List<Media> findAll();
}
