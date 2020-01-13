package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.entities.Usergroup;

import java.util.List;

public interface UsergroupDAO {
    Usergroup create(Usergroup Usergroup);

    Usergroup getUsergroupByName(String name);

    void remove(Long id);

    Usergroup getUsergroup(Long id);

    List<Usergroup> getAllUsergroups();

    List<User> getUsersOfGroup(Long id);

}
