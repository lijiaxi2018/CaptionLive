import React from 'react';
import { useDispatch } from 'react-redux';
import Avatar from '../InfoCard/Avatar';
import { useGetUserQuery } from '../../services/user';
import { useGetMessagesForRequestQuery, useReadRequestMutation } from '../../services/request';
import { parseRequestType } from '../../utils/request';
import { openMessage, updateSelectedRequestId } from '../../redux/layoutSlice';

import { Icon } from '@rsuite/icons';
import { AiOutlineArrowRight } from 'react-icons/ai';


function Request({request, myUserId}) {
  const dispatch = useDispatch();

  const [readRequestMutation] = useReadRequestMutation();

  let otherId = request.sender === myUserId ? request.recipient : request.sender;
  let unread = (myUserId === request.sender && request.senderRead === false)
    || (myUserId === request.recipient && request.recipientRead === false);

  const userInfo = useGetUserQuery(otherId);
  const messages = useGetMessagesForRequestQuery(request.requestId);

  function handleOpenMessage() {
    dispatch(openMessage());
    dispatch(updateSelectedRequestId(request.requestId));

    readRequestMutation({
      requestId: request.requestId,
      userId: myUserId,
    })
    .then((response) => {
      // Handle Different Messages
    })
  }

  function parseToTitleText(unread) {
    if (unread) {
      return (
        <div>
          <label className='general-font-tiny' style={{'fontWeight' : 'bold'}}>{parseRequestType(request.type)}</label><br/>
          <label className='general-font-tiny-one-line' style={{'fontWeight' : 'bold'}}>{messages.data.data[0].content}</label>
        </div>
      );
    } else {
      return (
        <div>
          <label className='general-font-tiny'>{parseRequestType(request.type)}</label><br/>
          <label className='general-font-tiny-one-line'>{messages.data.data[0].content}</label>
        </div>
      );
    }
  }

  return (
    <div>
      { (!userInfo.isFetching && !messages.isFetching) &&
        <div className='mail-request-button'>
          <Avatar userId={otherId} avatarSize={50} type={1}></Avatar>

          <div style={{'marginRight' : '10px'}}></div>

          {parseToTitleText(unread)}
          
          <div className='general-right-buttons'>
            <button className='general-icon-button' onClick={() => handleOpenMessage()}><Icon as={AiOutlineArrowRight} size="2.5em" style={{ color : '#888888' }}/></button>
          </div>
          
        </div>
      }
    </div>
  );
}

export default Request;