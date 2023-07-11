import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Avatar from '../InfoCard/Avatar';
import { useGetSharedUsersQuery, useGetSharedOrgsQuery, useShareProjectToUserMutation, useShareProjectToOrgMutation } from '../../services/organization';
import { closeShareProject } from '../../redux/layoutSlice';
import { languagedata } from '../../assets/language';

function ShareProject({projectId}) {
  const dispatch = useDispatch() // Redux

  const shareUsers = useGetSharedUsersQuery(projectId);
  const shareOrgs = useGetSharedOrgsQuery(projectId);
  const fetched = (shareUsers.isFetching || shareOrgs.isFetching) ? false : true;
  const language = useSelector((state) => state.layout.language);

  const [shareProjectToUserMutation] = useShareProjectToUserMutation();
  const [shareProjectToOrgMutation] = useShareProjectToOrgMutation();

  const [username, setUsername] = useState('');
  const [orgname, setOrgname] = useState('');

  function findUserByUsername(myUsername, userList) {
    let myUserId = -1;
    for (let i = 0; i < userList.length; i++) {
      if (userList[i].username === myUsername) {
        myUserId = userList[i].userId;
        break;
      }
    }
    return myUserId;
  }

  function findOrgByName(myName, orgList) {
    let myOrgId = -1;
    for (let i = 0; i < orgList.length; i++) {
      if (orgList[i].name === myName) {
        myOrgId = orgList[i].organizationId;
        break;
      }
    }
    return myOrgId;
  }

  function handleShareUser(myProjectId, myUsername, userList) {
    let myUserId = findUserByUsername(myUsername, userList);

    shareProjectToUserMutation({
      projectId: myProjectId,
      userId: myUserId,
    })
    .then((response) => {
      let message = response.data.message;
    })
  }

  function handleShareOrg(myProjectId, myName, orgList) {
    let myOrgId = findOrgByName(myName, orgList);

    shareProjectToOrgMutation({
      projectId: myProjectId,
      organizationId: myOrgId,
    })
    .then((response) => {
      let message = response.data.message;
    })
  }

  const handleCancel = () => {
    dispatch(closeShareProject());
  }

  return (
    <div>
      { fetched && 
        <div className="share-project-container">
          <p className="sign-in-up-title">{languagedata[language]['shareProject']}</p>

          <div className='general-row-align'>
            <input id="ns-users-input" list="ns-users" className="sign-in-up-input" placeholder="分享给用户" onChange={(e) => setUsername(e.target.value)}></input>
              <datalist id="ns-users">
                {shareUsers.data.data.noSharedUserList.map((user) =>
                  <div key={user.userId}>
                    <option value={user.username}>{user.nickname}</option>
                  </div>
                )}
              </datalist>
            <button className="general-button-green" onClick={() => handleShareUser(projectId, username, shareUsers.data.data.noSharedUserList)}>
              {languagedata[language]['share']}
            </button>
          </div>

          <div className='general-flex-wrap'>
            {shareUsers.data.data.sharedUserList.map((user) =>
              <div key={user.userId} className='general-row-align'>
                <Avatar userId={user.userId} avatarSize={20} type={1}></Avatar>
                <div style={{ 'marginRight': '5px' }}></div>
                <label className='general-font-tiny' style={{ marginRight: '20px' }}>{user.username}</label>
              </div>
            )}
          </div>

          {/* <div className='general-row-align'>
            <input id="n-users-input" list="n-users" className="sign-in-up-input" placeholder="搜索用户"></input>
              <datalist id="n-users">
                {shareUsers.data.data.sharedUserList.map((user) =>
                  <div key={user.userId}>
                    <option value={user.username}>{user.nickname}</option>
                  </div>
                )}
              </datalist>
            <button className="general-button-red">撤销分享</button>
          </div> */}


          <div style={{ 'marginTop': '25px' }}></div>
          

          <div className='general-row-align'>
            <input id="ns-orgs-input" list="ns-orgs" className="sign-in-up-input" placeholder="分享给组织" onChange={(e) => setOrgname(e.target.value)}></input>
              <datalist id="ns-orgs">
                {shareOrgs.data.data.noSharedOrganizationList.map((org) =>
                  <div key={org.organizationId}>
                    <option value={org.name}></option>
                  </div>
                )}
              </datalist>
            <button className="general-button-green" onClick={() => handleShareOrg(projectId, orgname, shareOrgs.data.data.noSharedOrganizationList)}>
              {languagedata[language]['share']}
            </button>
          </div>

          <div className='general-flex-wrap'>
            {shareOrgs.data.data.sharedOrganizationList.map((org) =>
              <div key={org.organizationId} className='general-row-align'>
                <Avatar userId={org.organizationId} avatarSize={20} type={3}></Avatar>
                <div style={{ 'marginRight': '5px' }}></div>
                <label className='general-font-tiny' style={{ marginRight: '20px' }}>{org.name}</label>
              </div>
            )}
          </div>

          {/* <div className='general-row-align'>
            <input id="n-orgs-input" list="n-orgs" className="sign-in-up-input" placeholder="搜索组织"></input>
              <datalist id="n-orgs">
                {shareOrgs.data.data.sharedOrganizationList.map((org) =>
                  <div key={org.organizationId}>
                    <option value={org.name}></option>
                  </div>
                )}
              </datalist>
            <button className="general-button-red">撤销分享</button>
          </div> */}





          <div className="sign-in-up-button-list">
            <button className="general-button-red" onClick={handleCancel}>
              {languagedata[language]['cancel']}
            </button>
          </div>
          
        </div>
      }
    </div>
  )
}

export default ShareProject;