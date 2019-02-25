package com.unexus.websocketserver;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import com.facebook.react.bridge.Callback;

/**
 * Created by umsi on 27/11/2017.
 */

public class WebServer extends WebSocketServer {

    Callback onStart, onOpen, onClose, onMessage, onError;

    public WebServer(InetSocketAddress inetSocketAddress, 
                    Callback onStart, Callback onOpen,
                    Callback onClose, Callback onMessage, 
                    Callback onError) {
        super(inetSocketAddress);

        this.onStart = onStart;
        this.onOpen = onOpen;
        this.onClose = onClose;
        this.onMessage = onMessage;
        this.onError = onError;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        onOpen.invoke(conn.getRemoteSocketAddress().getHostName());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        onClose.invoke(conn.getRemoteSocketAddress().getHostName(), code, reason, remote);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        onMessage.invoke(conn.getRemoteSocketAddress().getHostName(), message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        onError.invoke(conn.getRemoteSocketAddress().getHostName(), ex.toString())
    }

    @Override
    public void onStart() {
        onStart.invoke();
    }
}

