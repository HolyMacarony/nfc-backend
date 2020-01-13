package pictures.taking.washing.clients;

import pictures.taking.washing.ejb.events.UserEvent;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@ServerEndpoint("/userSocket")
@Stateless
public class UsersWebSocket {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onConnection(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onDisconnect(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        notifySessions(message);
    }

    public void onEvent(@Observes UserEvent event) {
        notifySessions(event.getMessage());
    }

    private void notifySessions(String message) {
        for (Session session : sessions) {

//			try {
            session.getAsyncRemote().sendText(message);
//				session.getBasicRemote().sendText("Time event:");
//            } catch (IOException ex) {
//            	Logger.getLogger(UsersWebSocket2.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

}
