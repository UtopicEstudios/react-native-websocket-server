package com.unexus.websocketserver;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

/**
 * Created by umsi on 27/11/2017.
 */

public class RNWebsocketServerModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private WebServer webServer = null;
    private ReactApplicationContext reactContext;

    public RNWebsocketServerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNWebsocketServer";
    }

    @ReactMethod
    public void start(String ipAddress, int port) throws IOException, InterruptedException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, port);

        webServer = new WebServer(inetSocketAddress, reactContext);

        webServer.start();
    }

    @ReactMethod
    public void stop() throws IOException, InterruptedException {
        webServer.stop();
    }

    @ReactMethod
    public void broadcast(String message) {
        webServer.broadcast(message);
    }

    @ReactMethod
    public int getActualPort(){
        return webServer.getActualPort();
    }

    @Override
    public void onHostResume() {
        //
    }

    @Override
    public void onHostPause() {
        try {
            this.stop();
        } catch(Exception e) {
            //
        }
    }

    @Override
    public void onHostDestroy() {
        try {
            this.stop();
        } catch(Exception e) {
            //
        }
    }
}
