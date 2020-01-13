package pictures.taking.washing.ejb.beans;

import pictures.taking.washing.ejb.interfaces.MessageSender;
import pictures.taking.washing.persistence.entities.User;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.mail.Session;
import java.util.concurrent.Future;

@Stateless
@Remote(MessageSender.class)

public class MessageSenderBean implements MessageSender {

    private static final String DEFAULT_SENDER = "maximilian-schmidt@hotmail.com";

    @Resource(name = "java:jboss/mail/Hotmail")
    private Session mailSession;

    @Override
    public Future<Boolean> send(String subject, String body, User user) {

        boolean result = false;

//		Communication email = null;
//		for (Communication communication : user.getCommunications()) {
//			if (communication.getCommunicationType() == CommunicationType.Email) {
//				email = communication;
//				break;
//			}
//		}

//		if (email != null) {
//
//			javax.mail.Message mail = new MimeMessage(mailSession);
//
//			try {
//
//				mail.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email.getValue()));
//
//				mail.setFrom(InternetAddress.parse(DEFAULT_SENDER)[0]);
//
//				mail.setSubject(subject);
//				mail.setText(body);
//
//				Transport.send(mail);
//
//				result = true;
//
//			} catch (MessagingException e) {
//				e.printStackTrace();
//			}
//
//		}

        return new AsyncResult<Boolean>(result);
    }

}
