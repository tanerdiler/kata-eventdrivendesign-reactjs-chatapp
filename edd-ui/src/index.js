import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
var eventbus = require('the.eventbus');
eventbus.event('MESSAGE_DENIED').setDefaultMethod('onMessageDenied');
eventbus.event('MESSAGE_SENT').setDefaultMethod('onMessageSent');
eventbus.event('CHAT_ROOM_CREATED').setDefaultMethod('onChatRoomCreated');
eventbus.event('MESSAGE_READ').setDefaultMethod('onMessageRead');
eventbus.event('USER_SIGNEDIN').setDefaultMethod('onUserSignedIn');
var username = 'UNKNOWN';
ReactDOM.render(<App />, document.getElementById('root'));

