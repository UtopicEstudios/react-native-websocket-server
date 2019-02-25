import { NativeModules } from 'react-native';

const { RNWebsocketServer } = NativeModules;

export default class WebsocketServer {
    constructor (ipAddress, port = 3770) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    start (onStart, onOpen, onClose, onMessage, onError) {
        RNWebsocketServer.start(this.ipAddress, this.port, onStart, onOpen, onClose, onMessage, onError);
    }

    stop () {
        RNWebsocketServer.stop();
    }

    broadcast(message) {
        RNWebsocketServer.broadcast(message);
    }
}