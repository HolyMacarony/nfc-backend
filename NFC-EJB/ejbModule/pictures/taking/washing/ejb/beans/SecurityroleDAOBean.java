package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.SecurityroleDAO;
import pictures.taking.washing.persistence.entities.Securityrole;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(SecurityroleDAO.class)

public class SecurityroleDAOBean implements SecurityroleDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Securityrole create(Securityrole Securityrole) {
        em.persist(Securityrole);
        return Securityrole;
    }

    @Override
    public Securityrole getSecurityrole(Long id) {
        return em.find(Securityrole.class, id);
    }

    @Override
    public List<Securityrole> getAllSecurityroles() {
        return em.createNamedQuery(Securityrole.QUERY_GETALL, Securityrole.class).getResultList();

    }

    @Override
    public Securityrole getSecurityroleByName(String name) {
        return em.createNamedQuery(Securityrole.QUERY_GETSecurityroleBYNAME, Securityrole.class).setParameter("name", name)
                .setMaxResults(1).getSingleResult();

    }

}
