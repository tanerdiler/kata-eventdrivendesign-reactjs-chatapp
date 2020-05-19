import React from 'react';
import './LoginPanel.css';

class LoginPanel extends React.Component {

    state = {
        text:'',
        disabled: false,
    }

    signin = () => {
        const {text} = this.state;
        this.setState({disabled:true});
        this.props.changeUsername(text);
    }

    changeValue = (e, field) => {
        this.setState({[field]:e.target.value});
    }

    render() {
        const {text, disabled}=this.state;
        return (
            <div className='loginpanel'>
                <div style={{display: 'inline-block', marginRight: '0.3rem'}}>Username: <input value={text} disabled={disabled} onChange={(event)=>this.changeValue(event, 'text')}/></div>
                <div style={{display:'inline-block'}}><button onClick={this.signin}>Enter</button></div>
            </div>
        )
    }
}

export default LoginPanel;
