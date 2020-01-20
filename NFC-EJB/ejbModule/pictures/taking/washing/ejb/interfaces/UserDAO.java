package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.ejb.dto.BaseUserData;
//import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Machine;
import pictures.taking.washing.persistence.entities.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface UserDAO {

    User create(User user);

    User update(User user);

    User remove(UUID id);

    User find(UUID id);

    User findByUsername(String userName);

    User findByEmail(String email);

    User findByCardId(UUID id);

    User deleteUser(UUID id);

    User userLinkCard(UUID id, UUID cardID);

    User userDeduct(UUID id, Double amount);

    User userRecharge(UUID id, Double amount);

    Timestamp getTimestampPlusMinutes(Timestamp originalTime, int minutes);

    String findPasswordByEmail(String userName);

    List<Machine> reservedMachines(UUID id);

    public Double userBalance(UUID userId);



    List<User> findAll();

//    List<Hike> findHikeAndSectionsByHike(Hike hike);

//    List<User> findByBirthday();

    List<BaseUserData> findUsersBaseInfo();

}
