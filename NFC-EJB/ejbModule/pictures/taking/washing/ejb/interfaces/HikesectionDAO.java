package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Hikesection;

import java.util.List;

public interface HikesectionDAO {
    Hikesection create(Hikesection Hikesection);

    Hikesection update(Hikesection Hikesection);

    void remove(Long id);

    Hikesection find(Long id);

    List<Hikesection> findAllByHike(Hike hike);
}
