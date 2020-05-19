import React from 'react';
import './EventLogPanel.css';
var eventbus = require('the.eventbus');

class EventLogPanel extends React.Component {

    state = {
        logs : []
    }

    componentDidMount = () => {
        eventbus.listener(this).withMethod('onEvent').listen('MESSAGE_SENT');
        eventbus.listener(this).withMethod('onEvent').listen('CHAT_ROOM_CREATED');
        eventbus.listener(this).withMethod('onEvent').listen('MESSAGE_READ');
        eventbus.listener(this).withMethod('onEvent').listen('MESSAGE_DENIED');
        eventbus.listener(this).withMethod('onEvent').listen('USER_SIGNEDIN');
    }


    onEvent = (source) => {
        const { logs } = this.state;
        logs.push({event:source.event, text:((source.roomId?"ChatRoom#"+source.roomId:"")+", "+source.actor+", "+source.message)});
        this.setState({logs});
    }

    render() {
        const { logs } = this.state;
        return (
            <div className='eventlogpanel'>
                <div className='header'>Event Log Panel</div>
                <div style={{height:"95%",overflow: "scroll"}}>
                    <ul>
                        {logs.map(l=><li><b>{l.event}</b>:{l.text}</li>)}
                    </ul>
                </div>
            </div>
        )
    }
}

export default EventLogPanel;
