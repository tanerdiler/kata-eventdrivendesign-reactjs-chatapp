import React from 'react';
import './App.css';
import EventLogPanel from "./component/EventLogPanel";
import LoginPanel from "./component/LoginPanel";
import ContactPanel from "./component/ContactPanel";
import ChatRoomContainer from "./component/ChatRoomContainer";
import WebSocketService from "./service/WebSocketService";
import axios from 'axios';
import generateHash from 'random-hash';

const superheroes = require('superheroes');
const eventbus = require('the.eventbus');

class App extends React.Component {
    //  {id:"adfasdf", messages:[{sender:'', text:''}]}
    state = {
        username: 'UNKNOWN',
        socket: null,
    }


    establishWebSocketConnection = () => {

    }

    updateUsername = (name) =>{
        const self = this;
        axios.post("http://localhost:8080/user/signin", {username: name}, {
            // receive two    parameter endpoint url ,form data
        })
        .then(res => { // then print response status
            this.setState({username: name});
            console.log(res.statusText);
            const socket = new WebSocketService();
            socket.connect(name, self.onEventReceived);
            this.setState({socket});
        })
    }

    onEventReceived = (message) => {
       const eventMessage = JSON.parse(message.data);
       const eventSource = eventMessage.detail;
       eventSource.event=eventMessage.event;
       eventSource.occurredAt=eventMessage.occurredAt;
       eventbus.event(eventMessage.event).fire(eventMessage.detail);
    }

    handleChatRoomAddClick = (e) => {
        const roomId = superheroes.random();
        axios.post("http://localhost:8080/room/add", {username: this.state.username, roomId}, {
            // receive two    parameter endpoint url ,form data
        });
    }

    render() {
        const {chatRooms, username} = this.state;
        return (
            <div className="App">
                <div className="sidebar">
                    <LoginPanel username={username} changeUsername={this.updateUsername}/>
                    <button className="newchatroom" onClick={this.handleChatRoomAddClick} >Add Chat Room</button>
                    <EventLogPanel />
                </div>
                <ContactPanel />
                <ChatRoomContainer username={username} />
            </div>
        );
    }
}

export default App;
