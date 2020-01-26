package pictures.taking.washing.ejb.tests;

import pictures.taking.washing.ejb.interfaces.SecurityroleDAO;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.Securityrole;
import pictures.taking.washing.persistence.entities.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;

//import javax.inject.Inject;


@RunWith(Arquillian.class)
public class ServiceTest {

    @EJB
    private SecurityroleDAO securityroleDAO;

    @EJB
    private UserDAO userDAO;


    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "tests.war")
                .addPackages(true, "pictures.taking.washing.ejb")
                .addPackages(true, "pictures.taking.washing.persistence")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testRegister() throws Exception {
        Securityrole s = new Securityrole("DDDDDFFff");
        s = securityroleDAO.create(s);
        assertEquals(s.getName(), "DDDDDFF");
    }

    @Test
    public void testRegisterUser() throws Exception {
        User u = new User("name", "pw", "email@email.de",randomUUID());
        u = userDAO.create(u);
        assertEquals("name!",u.getUserName());
    }
}