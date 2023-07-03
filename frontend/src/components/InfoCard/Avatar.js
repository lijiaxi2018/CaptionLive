import React from 'react';
import { useGetUserQuery } from '../../services/user';
import { useGetProjectQuery } from '../../services/project';
import { useGetOrganizationQuery } from '../../services/organization';
import { configuration } from '../../config/config';

function Avatar({userId, avatarSize, type}) {
  
  // type === 1
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
      let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedData.data.data.avatarId})`;
      styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <div style={styleSheet} className='general-avatar'></div>
        </div>
      );
    }
  }

  // type === 2
  function parseProjectData(fetchedDataProject) {
    let styleSheet = { 'width': avatarSize * 1.33, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '10%' };
    
    let initial = fetchedDataProject.data.data.name[0];

    if (fetchedDataProject.data.data.coverFileRecordId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedDataProject.data.data.coverFileRecordId})`;
      styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <div style={styleSheet} className='general-avatar'></div>
        </div>
      );
    }
  }

  // type === 3
  function parseOrganizationData(fetchedDataOrganization) {
    let styleSheet = { 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '10%' };
    
    let initial = fetchedDataOrganization.data.data.name[0];

    if (fetchedDataOrganization.data.data.avatarId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedDataOrganization.data.data.avatarId})`;
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
  const fetchedDataOrganization = useGetOrganizationQuery(userId);
  
  const fetched = 
    userId === -1 ? false : 
    (fetchedData.isFetching || fetchedDataProject.isFetching || fetchedDataOrganization.isFetching) ? false : 
    true;

  return (
    <div>
      { fetched &&
        <div>
          { (type === 1) && parseUserData(fetchedData) }
          { (type === 2) && parseProjectData(fetchedDataProject) }
          { (type === 3) && parseOrganizationData(fetchedDataOrganization) }
        </div>
      }
    </div>
  );
}

export default Avatar;