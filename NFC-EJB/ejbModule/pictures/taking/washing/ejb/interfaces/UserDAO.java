package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.ejb.dto.BaseUserData;
//import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;

import java.util.List;

public interface UserDAO {

    User create(User user);

    User update(User user);

    User remove(Long id);

    User find(Long id);

    User findByUsername(String userName);

    User findByEmail(String email);

    User findByCardId(Long id);

    User deleteUser(Long id);

    String findPasswordByEmail(String userName);

    List<Machine> reservedMachines(Long id);

    List<User> findAll();

//    List<Hike> findHikeAndSectionsByHike(Hike hike);

//    List<User> findByBirthday();

    List<BaseUserData> findUsersBaseInfo();

}
