import { NativeModules } from 'react-native';

const { RNWebsocketServer } = NativeModules;

export default class WebsocketServer {
    constructor (ipAddress, port = 3770) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    start () {
        RNWebsocketServer.start(this.ipAddress, this.port);
    }

    stop () {
        RNWebsocketServer.stop();
    }

    broadcast(message) {
        RNWebsocketServer.broadcast(message);
    }

    async getActualPort() {
        return await RNWebsocketServer.getActualPort();
    }
}