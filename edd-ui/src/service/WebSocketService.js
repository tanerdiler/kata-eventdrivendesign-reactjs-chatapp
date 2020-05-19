export default class WebSocketService {

    connect(username, fnMessage) {
        const self = this;
        this.ws = new WebSocket('ws://localhost:8080/event');
        this.ws.onopen = function(e) {
            self.sendData({connect:username});
        };
        this.ws.onmessage = fnMessage;
    }

    disconnect() {
        if (this.ws != null) {
            this.ws.close();
        }
    }

    sendData(data) {
        this.ws.send(JSON.stringify(data));
    }
}








