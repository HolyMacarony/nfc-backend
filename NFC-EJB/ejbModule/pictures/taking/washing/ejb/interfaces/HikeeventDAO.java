package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.persistence.entities.Hikesection;

import java.util.List;

public interface HikeeventDAO {
    List<Hikeevent> findAllByHikesection(Hikesection hikesection);

    Hikesection findSectionByHikeevent(Hikeevent hikeevent);

    List<Hikeevent> findAllByHikesection2();

    Hikeevent create(Hikeevent hikeevent);

    Hikeevent update(Hikeevent hikeevent);

    void remove(Long id);

    Hikeevent find(Long id);
}
