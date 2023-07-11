import React from 'react';
import { useGetUser } from '../../api/user';
import { useGetProject } from '../../api/project';
import { useGetOrganization } from '../../api/organization';
import { configuration } from '../../config/config';
import LazyImage from '../../utils/lazyimage';

function Avatar({userId, avatarSize, type}) {
  const userToken = JSON.parse(localStorage.getItem("clAccessToken"));
  // type === 1
  function parseUserData(fetchedData) {
    let styleSheet = { 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '50%' };

    let initial = fetchedData.username[0];

    if (fetchedData.avatarId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      // let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedData.avatarId})`;
      // styleSheet['backgroundImage'] = avatarUrl;
      
      return (
        <div>
          {/* <div style={styleSheet} className='general-avatar'></div> */}
          <LazyImage 
            imageFileId={fetchedData.avatarId} 
            userToken={userToken}
            stylesheet={styleSheet}
          />
        </div>
      );
    }
  }

  // type === 2
  function parseProjectData(fetchedDataProject) {
    let styleSheet = { 'width': avatarSize * 1.33, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '10%' };
    
    let initial = fetchedDataProject.name[0];

    if (fetchedDataProject.coverFileRecordId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      // let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedDataProject.coverFileRecordId})`;
      // styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <LazyImage 
            imageFileId={fetchedDataProject.coverFileRecordId} 
            userToken={userToken}
            stylesheet={styleSheet}
          />
        </div>
      );
    }
  }

  // type === 3
  function parseOrganizationData(fetchedDataOrganization) {
    let styleSheet = { 'width': avatarSize, 'height': avatarSize, 'fontSize': avatarSize/1.7, 'borderRadius': '10%' };
    
    let initial = fetchedDataOrganization.name[0];

    if (fetchedDataOrganization.avatarId === 0) {
      return (
        <div style={styleSheet} className='general-avatar-text'>
          <label>{initial}</label>
        </div>
      );
    } else {
      // let avatarUrl = `url(http://${configuration.HOSTNAME}:8080/api/files/${fetchedDataOrganization.avatarId})`;
      // styleSheet['backgroundImage'] = avatarUrl;

      return (
        <div>
          <LazyImage 
            imageFileId={fetchedDataOrganization.avatarId} 
            userToken={userToken}
            stylesheet={styleSheet}
          />
        </div>
      );
    }
  }

  const [userFetched, userData] = useGetUser(userId);
  const [projectFetched, projectData] = useGetProject(userId);
  const [organizationFetched, organizationData] = useGetOrganization(userId);
  
  const fetched = (userId !== -1 && userFetched && projectFetched && organizationFetched) ? true : false;

  return (
    <div>
      { fetched &&
        <div>
          { (type === 1) && parseUserData(userData) }
          { (type === 2) && parseProjectData(projectData) }
          { (type === 3) && parseOrganizationData(organizationData) }
        </div>
      }
    </div>
  );
}

export default Avatar;