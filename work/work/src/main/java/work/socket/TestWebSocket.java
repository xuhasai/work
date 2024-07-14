package work.socket;

import work.socket.config.WebSocketSessionConfig;
import org.springframework.stereotype.Component;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint(value = "/socket",configurator = WebSocketSessionConfig.class)
@Component
public class TestWebSocket{
    public static Map<String,Session> sessionMap = new Hashtable<String,Session>();
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        String token = session.getRequestParameterMap().get("token").get(0);
        //sessionMap.put("0",session);
        System.out.println(session.getQueryString());
        System.out.println(session.getRequestParameterMap());
    }
}
