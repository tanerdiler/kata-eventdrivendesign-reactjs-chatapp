import React from 'react';
var eventbus = require('the.eventbus');

class UnreadMessageBadge extends React.Component {

    state = {
        count : 0
    }

    componentDidMount = () => {
        eventbus.listener(this).withMethod('onMessageSent').listen('MESSAGE_SENT');
        eventbus.listener(this).withMethod('onMessageRead').listen('MESSAGE_READ');
    }

    onMessageSent = (source) => {
        const { roomId, actor } = source;

        if (roomId === this.props.roomId && actor !== this.props.username) {
            let { count } = this.state;
            const newCount = count+1;
            this.setState({ count:newCount });
        }
    }

    onMessageRead = (source) => {
        this.setState({ count:0 });
    }


    render(){
        const { count } = this.state;
        return(
            <div className="badge">{count}</div>
        )
    }
}

export default UnreadMessageBadge;
