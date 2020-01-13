package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.persistence.entities.Hikesection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(HikeeventDAO.class)
public class HikeeventDAOBean implements HikeeventDAO {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Hikeevent create(Hikeevent hikeevent) {
        em.persist(hikeevent);
        return hikeevent;
    }

    @Override
    public Hikeevent update(Hikeevent Hikeevent) {
        Hikeevent updateHikeevent = em.merge(Hikeevent);
        return updateHikeevent;
    }

    @Override
    public void remove(Long id) {
        Hikeevent deleteHikeevent = find(id);
        em.remove(deleteHikeevent);
    }

    @Override
    public Hikeevent find(Long id) {
        return em.find(Hikeevent.class, id);
    }


    @Override
    public List<Hikeevent> findAllByHikesection(Hikesection hikesection) {
//		return em.createNativeQuery(	 "with recursive myhikeevent as( select hikeevent.id as joiner, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, 1 as level from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id where hikeevent.previoushikeevent_id is null and hikeevent.hikesection_id = :hikesectionId union all select hikeevent.id, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, t.level + 1 from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id join myhikeevent as t on hikeevent.previoushikeevent_id = t.joiner where hikeevent.hikesection_id = :hikesectionId) select * from myhikeevent order by level"
//				,Hikeevent.class).setParameter("hikesectionId",hikesection.getId())
//				.getResultList();
        return em.createNamedQuery(Hikeevent.QUERY_FINDALLBYHIKESECTION, Hikeevent.class)
                .setParameter("hikesectionId", hikesection.getId())
                .getResultList();
    }

    @Override
    public Hikesection findSectionByHikeevent(Hikeevent hikeevent) {
        return new Hikesection();
    }

    @Override
    public List<Hikeevent> findAllByHikesection2() {
        return em.createNamedQuery(Hikeevent.QUERY_FINDALLBYHIKESECTION2342, Hikeevent.class)
                .getResultList();
    }
}
