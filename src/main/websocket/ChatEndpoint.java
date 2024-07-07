package main.websocket;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {
    private static Map<String, Session> clients = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        String username = session.getQueryString();

        if (username != null && username.contains("=")) {
            username = username.substring(username.indexOf('=') + 1);

            System.out.println("WebSocket opened for user: " + username);
        }

        clients.put(username, session);
    }

    @OnClose
    public void onClose(Session session) {
        String username = session.getQueryString();
        if (username != null && username.contains("=")) {
            username = username.substring(username.indexOf('=') + 1);
            clients.remove(username);
            System.out.println("WebSocket closed for user: " + username);
        } else {
            System.out.println("Error in onClose() method");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String username = session.getQueryString();
        username = username.substring(username.indexOf('=') + 1);
        System.out.println("Message from " + username + ": " + message);
        JSONObject senderMessage = (JSONObject) JSONValue.parse(message);
        String receiver = (String) senderMessage.get("to");
        try {
            JSONObject obj = new JSONObject();
            obj.put("senderName", username);
            obj.put("message", senderMessage.get("message"));

            clients.get(receiver).getBasicRemote().sendText(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
