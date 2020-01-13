package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.MessageSender;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@ApplicationScoped
public class MailSender {

    @EJB
    private MessageSender messageSender;

    @EJB
    private UserDAO userDAO;

    @Resource(name = "DefaultManagedExecutorService")
    private ManagedExecutorService executor;

    public void sendMail(final String message, final String subject, final Long recipientId) {

        Runnable task = new Runnable() {

            @Override
            public void run() {

                User recipient = userDAO.find(recipientId);
                Future<Boolean> result = messageSender.send(subject, message, recipient);

                try {
                    result.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        executor.submit(task);
    }

}
