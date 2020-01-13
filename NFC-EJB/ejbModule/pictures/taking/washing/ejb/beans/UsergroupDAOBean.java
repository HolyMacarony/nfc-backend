package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.UsergroupDAO;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.entities.Usergroup;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(UsergroupDAO.class)

public class UsergroupDAOBean implements UsergroupDAO {


    @PersistenceContext
    EntityManager em;

    public UsergroupDAOBean() {
    }

    @Override
    public Usergroup create(Usergroup Usergroup) {
        em.persist(Usergroup);
        return Usergroup;
    }


    @Override
    public void remove(Long id) {

    }

    @Override
    public Usergroup getUsergroup(Long id) {
        return em.find(Usergroup.class, id);

    }

    @Override
    public List<Usergroup> getAllUsergroups() {
        return em.createNamedQuery(Usergroup.QUERY_GETALL, Usergroup.class).getResultList();
    }

    @Override
    public List<User> getUsersOfGroup(Long id) {
        return null;
    }

    @Override
    public Usergroup getUsergroupByName(String name) {
        return em.createNamedQuery(Usergroup.QUERY_GETUsergroupBYNAME, Usergroup.class).setParameter("name", name)
                .setMaxResults(1).getSingleResult();
    }


}
