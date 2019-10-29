package com.unexus.websocketserver;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
/**
 * Created by umsi on 27/11/2017.
 */

public class WebServer extends WebSocketServer {

    private ReactApplicationContext reactContext;

    public WebServer(InetSocketAddress inetSocketAddress, ReactApplicationContext reactContext) {
        super(inetSocketAddress);
        this.reactContext = reactContext;
    }

    public int getActualPort() {
        return getPort();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        WritableMap payload = Arguments.createMap();
        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());

        reactContext.getJSModule(RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientOpen", payload);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        WritableMap payload = Arguments.createMap();

        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());
        payload.putInt("code", code);
        payload.putString("reason", reason);
        payload.putBoolean("remote", remote);

        reactContext.getJSModule(RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientClose", payload);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        WritableMap payload = Arguments.createMap();

        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());
        payload.putString("message", message);

        reactContext.getJSModule(RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientMessage", payload);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        WritableMap payload = Arguments.createMap();
        String connId = "";


        if (conn != null){
            Socket s = conn.getRemoteSocketAddress();
            if (s != null)
                connId = s.getHostName();
        }

        payload.putString("connId", connId);
        payload.putString("exception", ex.toString());

        reactContext.getJSModule(RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientError", payload);
    }

    @Override
    public void onStart() {
        WritableMap payload = Arguments.createMap();

        reactContext.getJSModule(RCTDeviceEventEmitter.class)
        .emit("onWebsocketServerStart", payload);
    }
}

