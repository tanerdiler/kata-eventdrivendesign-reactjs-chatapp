import React from 'react';
import WebSocketService from "../service/WebSocketService";
import axios from "axios/index";
import UnreadMessageBadge from "./UnreadMessageBadge";
var eventbus = require('the.eventbus');

class ChatRoomPanel extends React.Component {

    state = {
        text:'',
        messages:[]
    }

    componentDidMount = () => {
        eventbus.listener(this).withMethod('onMessageSent').listen('MESSAGE_SENT');
        eventbus.listener(this).withMethod('onMessageDenied').listen('MESSAGE_DENIED');
        eventbus.listener(this).withMethod('onMessageRead').listen('MESSAGE_READ');
    }

    onMessageSent = (source) => {
        const { roomId, actor, message } = source;
        if (roomId === this.props.room.id) {
            const { messages } = this.state;
            messages.push({username: actor, text:message, read: false});
            this.setState({ messages });
        }
    }

    onMessageRead = (source) => {
        const { roomId, actor, message } = source;
        if (roomId === this.props.room.id) {
            const { messages } = this.state;
            messages.map(m=>m.read=true);
            this.setState({ messages });
        }
    }

    onMessageDenied = (source) => {
        const { roomId, actor } = source;
        if (roomId === this.props.room.id) {
            const { messages } = this.state;
            messages.push({sender: actor, text:"Your message contains stop word."});
            this.setState({ messages });
        }
    }

    sendMessage = (e) => {

        axios.post("http://localhost:8080/message/send", {username: this.props.username, roomId: this.props.room.id, message: this.state.text}, {
            // receive two    parameter endpoint url ,form data
        })
        .then(res => { // then print response status
            this.setState({text:''});
        })
    }

    changeValue = (e, field) => {
        this.setState({[field]:e.target.value});
    }

    focusChatRoom  = (e)  => {
        const { messages } = this.state;
        const unreadMessages = messages.filter(m=>m.read==false && m.username!==this.props.username);
        if (unreadMessages.length > 0) {
            axios.put("http://localhost:8080/message/read", {username: this.props.username, roomId: this.props.room.id}, {
                // receive two    parameter endpoint url ,form data
            });
        }
    }

    render() {
        const { messages, text } = this.state;
        const { room } = this.props;

        return (
            <div className='chat' >

                <div className="contact bar"><UnreadMessageBadge roomId={this.props.room.id} username={this.props.username}/> <div className="name">Chat Room #{room.id}</div></div>
                <div className="messages">
                    {messages.map((m)=><div className={`message ${m.username===this.props.username ? "parker" : "stark"}`}><b>{m.username}</b>: <span>{m.text}</span>
                        {m.username===this.props.username ? <div className={`state ${m.read ? "read" : "unread"}`}></div> : null}</div>)}
                </div>
                <div className="input">
                    <input name='text' value={text} placeholder="message" onChange={(event)=>this.changeValue(event, 'text')} onFocus={this.focusChatRoom}/>
                    <button onClick={this.sendMessage}>Send</button>
                </div>
            </div>
        )
    }
}

export default ChatRoomPanel;
