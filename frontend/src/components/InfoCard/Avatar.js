import React from 'react';
import { useGetUserQuery } from '../../services/user';

function Avatar({userId, avatarSize, isBorder}) {
  const userData = useGetUserQuery(userId);
  
  const hasAvatarFile = 
    userId === -1 ? false : 
    userData.isFetching ? false : 
    userData.data.data.avatarId === 0 ? false : true;

  const initial = 
    userId === -1 ? "" : 
    userData.isFetching ? "" : 
    userData.data.data.username[0];
  
  const avatarFileId = 
    userId === -1 ? "" : 
    userData.isFetching ? "" : 
    userData.data.data.avatarId;  
  
  const avatarUrl = `url(http://localhost:8080/api/files/${avatarFileId})`;

  return (
    <div>
      { hasAvatarFile &&
        <div>
          <div style={{ 'backgroundImage': avatarUrl, 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/2 }} className='general-avatar'></div>
        </div>
      }

      { !hasAvatarFile &&
        <div>
          { isBorder &&
            <div style={{ 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'border': '2px solid #a0a0a0' }} className='general-avatar-text'>
              <label>{initial}</label>
            </div>
          }

          { !isBorder &&
            <div style={{ 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7 }} className='general-avatar-text'>
              <label>{initial}</label>
            </div>
          }
          
        </div>
      }
    </div>
  );
}

export default Avatar;