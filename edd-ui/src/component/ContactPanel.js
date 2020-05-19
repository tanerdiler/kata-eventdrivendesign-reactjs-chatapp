import React from 'react';
var eventbus = require('the.eventbus');

class ContactPanel extends React.Component {
    state = {
        contacts:[]
    }
    componentDidMount = () => {
        eventbus.listener(this).withMethod('onUserSignedIn').listen('USER_SIGNEDIN');
    }

    onUserSignedIn = (source)=>{
        const {contacts} = this.state;
        if (source.username !== this.props.username) {
            contacts.push({username:source.username})
            this.setState({contacts});
        }
    }

    render(){
        const {contacts} = this.state;
        return(
            <div className="contacts">
                {contacts.map(c=>(
                    <div className="contact">
                        <div className="pic rogers"></div>
                        <div className="name">{c.username}</div>
                    </div>))}

            </div>
        )
    }
}

export  default ContactPanel;
