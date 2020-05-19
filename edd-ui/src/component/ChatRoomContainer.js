import React from 'react';
import ChatRoomPanel from "./ChatRoomPanel";
import axios from 'axios';

const superheroes = require('superheroes');
const eventbus = require('the.eventbus');

class ChatRoomContainer extends React.Component {

    state = {
        chatRooms: [],
    }

    componentDidMount = () => {
        eventbus.listener(this).withMethod('onChatRoomCreated').listen('CHAT_ROOM_CREATED');
        eventbus.listener(this).withMethod('onMessageSent').listen('MESSAGE_SENT');
    }

    addChatRoom = (room) => {
        const {chatRooms} = this.state;
        chatRooms.push(room);
        this.setState({chatRooms});
    }

    onChatRoomCreated = (source) => {
        const { chatRooms } = this.state;
        const chatRoom = chatRooms.find(r=>r.id===source.roomId);
        if (!chatRoom) {
            this.addChatRoom({id: source.roomId, message: {}});
        }
    }

    onMessageSent = (source) => {
        const { chatRooms } = this.state;
        const chatRoom = chatRooms.find(r=>r.id===source.roomId);
        if (!chatRoom) {
            this.addChatRoom({id: source.roomId, message: {}});
        }
    }

    render() {
        const {chatRooms} = this.state;
        const {username} = this.props;
        return (
            <div className="content">
                {chatRooms.map(cr=><ChatRoomPanel room={cr} username={username}/>)}
            </div>
        );
    }
}

export default ChatRoomContainer;
