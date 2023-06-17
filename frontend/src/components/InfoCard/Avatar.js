import React from 'react';
import { useGetUserQuery } from '../../services/user';
import { useGetProjectQuery } from '../../services/project';

function Avatar({userId, avatarSize, type}) {
  
  function parseUserData(fetchedData) {
    let styleSheet = { 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '50%' };

    let initial = fetchedData.data.data.username[0];

    if (fetchedData.data.data.avatarId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      let avatarUrl = `url(http://localhost:8080/api/files/${fetchedData.data.data.avatarId})`;
      styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <div style={styleSheet} className='general-avatar'></div>
        </div>
      );
    }
  }

  function parseProjectData(fetchedData) {
    let styleSheet = { 'width': avatarSize * 1.33, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '10%' };
    
    let initial = fetchedData.data.data.name[0];

    if (fetchedData.data.data.coverFileRecordId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      let avatarUrl = `url(http://localhost:8080/api/files/${fetchedData.data.data.coverFileRecordId})`;
      styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <div style={styleSheet} className='general-avatar'></div>
        </div>
      );
    }
  }

  const fetchedData = useGetUserQuery(userId);
  const fetchedDataProject = useGetProjectQuery(userId);
  
  const fetched = 
    userId === -1 ? false : 
    (fetchedData.isFetching || fetchedDataProject.isFetching) ? false : 
    true;

  return (
    <div>
      { fetched &&
        <div>
          { (type === 1) && parseUserData(fetchedData) }
          { (type === 2) && parseProjectData(fetchedDataProject) }
        </div>
      }
    </div>
  );
}

export default Avatar;