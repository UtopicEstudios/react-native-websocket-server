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

    private ReactApplicationContext reactContext;

    public WebServer(InetSocketAddress inetSocketAddress, ReactApplicationContext reactContext) {
        super(inetSocketAddress);
        this.reactContext = reactContext;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        WritableMap payload = Arguments.createMap();
        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientOpen", params);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        WritableMap payload = Arguments.createMap();

        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());
        payload.putInt("code", code);
        payload.putString("reason", reason);
        payload.putBoolean("remote", remote);

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientClose", params);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        WritableMap payload = Arguments.createMap();

        payload.putString("message", message);

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientMessage", params);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        WritableMap payload = Arguments.createMap();
        
        payload.putString("connId", conn.getRemoteSocketAddress().getHostName());
        payload.putBoolean("exception", ex.toString());

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("onWebsocketClientError", params);
    }

    @Override
    public void onStart() {
        WritableMap payload = Arguments.createMap();

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("onWebsocketServerStart", params);
    }
}

