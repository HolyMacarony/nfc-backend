package pictures.taking.washing.ejb.interfaces;

import pictures.taking.washing.persistence.entities.User;

import java.util.concurrent.Future;

public interface MessageSender {

    Future<Boolean> send(String subject, String body, User customer);

}
