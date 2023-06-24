import React from 'react';
import Avatar from '../InfoCard/Avatar';
import { useGetUserQuery } from '../../services/user';


function SingleMessage({senderId, message}) {
  const userInfo = useGetUserQuery(senderId);

  return (
    <div>
      { !userInfo.isFetching &&
        <div className='message-container'>
          
          <div style={{'marginTop' : '20px'}}></div>

          <div className='single-message-title'>
            <Avatar userId={senderId} avatarSize={50} type={1}></Avatar>

            <div style={{'marginRight' : '10px'}}></div>

            <div>
              <label className='general-font-tiny' style={{'fontWeight' : 'bold'}}>{userInfo.data.data.username}</label><br/>
              <label className='general-font-tiny-one-line'>{message.createdTime}</label>
            </div>
          </div>

          <div style={{'marginTop' : '20px'}}></div>

          <label className='general-font-tiny'>{message.content}</label>

          <div style={{'marginTop' : '60px'}}></div>

        </div>
      }
    </div>
  );
}

export default SingleMessage;