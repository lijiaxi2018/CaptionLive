import React from 'react';
import { useDispatch } from 'react-redux';
import SingleMessage from './SingleMessage';
import { useGetMessagesForRequestQuery, useGetRequestQuery, useUnreadRequestMutation } from '../../services/request';
import { usePostMessageMutation, useApproveRequestMutation, useReadRequestMutation } from '../../services/request';
import { closeMessage } from '../../redux/layoutSlice';

import { Icon } from '@rsuite/icons';
import { AiOutlineArrowLeft } from 'react-icons/ai';

function toSingleMessage(message, senderId, recipientId) {
  let mySenderId = message.isReply ? recipientId : senderId;
  return (<SingleMessage senderId = {mySenderId} message={message}/>);
}


function Messages({requestId, userId}) {
  const dispatch = useDispatch();

  const [postMessageMutation] = usePostMessageMutation();
  const [unreadRequestMutation] = useUnreadRequestMutation();
  const [approveRequestMutation] = useApproveRequestMutation();
  const [rejectRequestMutation] = useReadRequestMutation();

  const messages = useGetMessagesForRequestQuery(requestId);
  const request = useGetRequestQuery(requestId);

  function handleReject(myRequestId, otherUserId) {
    // TODO: Change Request Status
    postMessageMutation({ 
      requestId: myRequestId,
      isReply: true,
      content: "请求已被拒绝。"
    })
    .then((response) => {
      
      unreadRequestMutation({
        requestId: myRequestId,
        userId: otherUserId,
      })
      .then((response) => {

        rejectRequestMutation({
          requestId: myRequestId,
        })
        .then((response) => {
        })
      })
    })
  }

  function handleAccept(myRequestId, otherUserId) {
    // TODO: Change Request Status
    postMessageMutation({ 
      requestId: myRequestId,
      isReply: true,
      content: "请求已被批准。"
    })
    .then((response) => {

      unreadRequestMutation({
        requestId: myRequestId,
        userId: otherUserId,
      })
      .then((response) => {
        approveRequestMutation({
          requestId: myRequestId,
        })
        .then((response) => {
        })
      })
    })
  }

  return (
    <div className='message-container'>
      <button className='general-icon-button' onClick={() => dispatch(closeMessage())}><Icon as={AiOutlineArrowLeft} size="2.5em" style={{ color : '#888888' }}/></button>
     
     { !messages.isFetching && !request.isFetching &&
        <div>
          <div>
            {messages.data.data.map((message) =>
              <div key={message.messageId}>
                {toSingleMessage(message, request.data.data.sender, request.data.data.recipient)}
              </div>
            )}
          </div>

          <div>
            { (request.data.data.status === 0 && userId === request.data.data.recipient) &&
              <div className="sign-in-up-button-list">
                <button className="general-button-red" onClick={() => handleReject(request.data.data.requestId, userId === request.data.data.sender ? request.data.data.recipient : request.data.data.sender)}>拒绝</button>
                <button className="general-button-green" onClick={() => handleAccept(request.data.data.requestId, userId === request.data.data.sender ? request.data.data.recipient : request.data.data.sender)}>接受</button>
              </div>
            }
          </div>
        </div>
      }    
    </div>
  );
}

export default Messages;