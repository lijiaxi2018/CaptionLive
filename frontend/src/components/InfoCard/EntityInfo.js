import React, { useReducer, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { openUploaderWindow, updateCurrentIdToUpload, updateCurrentUploadType } from '../../redux/fileSlice';
import { useGetUserQuery, usePutUserDescriptionMutation } from '../../services/user';
import { useGetOrganizationQuery, usePutOrganizationDescriptionMutation } from '../../services/organization';
import { usePostRequestMutation, usePostMessageMutation } from '../../services/request';

import FileUploader from '../Layout/Modal/FileUploader';
import Avatar from './Avatar';

function EntityInfo({userId, type, access, apply, applicant}) {
  const [editing, setEditing] = useState(false);

  const dispatch = useDispatch();

  const [putUserDescription] = usePutUserDescriptionMutation();
  const [putOrganizationDescription] = usePutOrganizationDescriptionMutation();

  const [postRequestMutation] = usePostRequestMutation();
  const [postMessageMutation] = usePostMessageMutation();

  const isOpenUploaderWindow = useSelector((state) => state.file.openUploaderWindow);
  
  var username;
  var userDescription;

  const userData = useGetUserQuery(userId);
  const orgData = useGetOrganizationQuery(userId);
  const applicantData = useGetUserQuery(applicant);

  console.log(applicantData);

  const fetched = 
    (userData.isFetching || orgData.isFetching || applicantData.isFetching) ? false : true;

  // type = 0: User Info
  // type = 1: Organization Info
  if (type === 0) {
    username = userData.isFetching ? "获取中..." : userData.data.data.username;
    userDescription = userData.isFetching ? "获取中..." : userData.data.data.description;
  } else if (type === 1) {
    username = orgData.isFetching ? "获取中..." : orgData.data.data.name;
    userDescription = orgData.isFetching ? "获取中..." : orgData.data.data.description;
  }

  const editReducer = (state, event) => {
    if (event.reset) {
      return {
        description: userDescription,
      }
    } else {
      return {
        ...state,
        [event.name]: event.value
      }
    }
  }

  const handleEdit = () => {
    setEditing(!editing);
    
    setEditData({
      name: "description",
      value: userDescription,
    });
  }

  const [editData, setEditData] = useReducer(editReducer, {
    description: userDescription,
  });

  const handleEditChange = event => {
    setEditData({
      name: event.target.name,
      value: event.target.value
    });
  }

  const editUser = () => {
    putUserDescription({
      userId: userId,
      description: editData.description,
    })
    .then((response) => {
      // TODO: Deal with other return messages
    })
  }

  const editOrganization = () => {
    putOrganizationDescription({
      organizationId: userId,
      description: editData.description,
    })
    .then((response) => {
      // TODO: Deal with other return messages
    })
  }

  const handleSubmit = event => {
    event.preventDefault();

    if (type === 0) {
      editUser();
    } else if (type === 1) {
      editOrganization();
    }
    
    setEditData({
      reset: true
    });

    setEditing(!editing);
  }

  const newMembershipRequest = (myUserId, myOrgData, applicantName) => {
    const membershipInfo = {
      userId: myUserId,
      organizationId: myOrgData.organizationId,
    };

    postRequestMutation({ 
      type: 1,
      status: 0,
      sender: myUserId,
      recipient: myOrgData.leaderIds[0],
      senderRead: true,
      recipientRead: false,
      body: JSON.stringify(membershipInfo),
    })
    .then((firstResponse) => {
      if (firstResponse.data.message === "success") {
        let myRequestId = firstResponse.data.data.requestId;
        postMessageMutation({ 
          requestId: myRequestId,
          isReply: false,
          content: "用户" + applicantName + "希望加入" + myOrgData.name + "。"
        })
        .then((secondResponse) => {
          let message = secondResponse.data.message;
        })
      }
    })
  }

  const handleApply = event => {
    event.preventDefault();
    newMembershipRequest(applicant, orgData.data.data, applicantData.data.data.username);
  }

  function handleUpload(myUserId) {
    if (type === 0) {
      dispatch(updateCurrentIdToUpload(myUserId));
      dispatch(updateCurrentUploadType(1));
      dispatch(openUploaderWindow());
    } else if (type === 1) {
      dispatch(updateCurrentIdToUpload(myUserId));
      dispatch(updateCurrentUploadType(4));
      dispatch(openUploaderWindow());
    }
  }

  function parseToAvatar(userId, type) {
    if (type === 0) {
      return (<Avatar userId={userId} avatarSize={200} type={1}></Avatar>);
    } else if (type === 1) {
      return (<Avatar userId={userId} avatarSize={200} type={3}></Avatar>);
    }
  }

  return (
    <div >
      { isOpenUploaderWindow === 1 &&
        <FileUploader />
      }

      { fetched &&
        <div>
          { !editing &&
            <div className='entity-info-window'>
              <div className='entity-info-avatar'>
                <div style={{ 'marginTop': '25px' }}>
                  {parseToAvatar(userId, type)}
                </div>
              </div>

              <div className='entity-info-info'>
                <div className='general-row-align'>
                  <label style={{ 'color': '#767171' }} className='general-font-medium'>{username}</label>

                  <div className='general-right-buttons'>
                    { access &&
                      <button className="general-button-grey" onClick={handleEdit}>编辑</button>
                    }
                    { (type === 1 && apply) &&
                      <button className="general-button-grey" onClick={handleApply}>申请加入</button>
                    }
                  </div>
                  
                </div>
                
                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>2022年11月11日成加入</label><br/>

                <div className='entity-info-info-description'>
                  <label style={{ 'color': '#afabab' }} className='general-font-small'>{userDescription}</label>
                </div>
              </div>
            </div>
          }

          { editing &&
            <div className='entity-info-window'>
              <div className='entity-info-avatar'>
                <div style={{ 'marginTop': '25px' }}>
                  {parseToAvatar(userId, type)}
                </div>
                
                <div style={{ 'marginTop': '25px' }}>
                  <button className='general-button-grey' onClick={() => handleUpload(userId)}>上传头像</button>
                </div>
              </div>

              <div className='entity-info-info'>
                <div className='general-row-align'>
                  <label style={{ 'color': '#767171' }} className='general-font-medium'>{username}</label>
                  
                  <div className='general-right-buttons'>
                    <button className="general-button-green" onClick={handleSubmit}>完成</button>
                    <button className="general-button-grey" onClick={handleEdit}>取消</button>
                  </div>
                </div>

                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>2022年11月11日成加入</label><br/>

                <textarea 
                  name="description" 
                  style={{ 'resize': 'none' }} 
                  className='entity-info-info-description' 
                  onChange={handleEditChange} 
                  defaultValue={userDescription}>
                </textarea>
                  
              </div>
            </div>
          }
        </div>
      }
    </div>
  );
}

export default EntityInfo;