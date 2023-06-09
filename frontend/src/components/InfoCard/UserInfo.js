import React, { useReducer, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { openUploaderWindow, updateCurrentIdToUpload, updateCurrentUploadType } from '../../redux/fileSlice';
import { usePutUserDescriptionMutation } from '../../services/user';
import { parseDBTimeYMD } from '../../utils/time';

import { useGetUser } from '../../api/user';

import FileUploader from '../Layout/Modal/FileUploader';
import Avatar from './Avatar';

function UserInfo({userId, access}) {
  const dispatch = useDispatch();

  const [editing, setEditing] = useState(false);

  const [putUserDescription] = usePutUserDescriptionMutation();

  const isOpenUploaderWindow = useSelector((state) => state.file.openUploaderWindow);
  
  const [fetched, userData] = useGetUser(userId);

  var userDescription;
  var userNickname;
  userDescription = fetched ? userData.description : "获取中...";
  userNickname = fetched ? userData.nickname : "获取中...";

  const editReducer = (state, event) => {
    if (event.reset) {
      return {
        description: userDescription,
        nickname: userNickname,
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

    setEditData({
      name: "nickname",
      value: userNickname,
    });
  }

  const [editData, setEditData] = useReducer(editReducer, {
    description: userDescription,
    nickname: userNickname,
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
      nickname: editData.nickname,
    })
    .then((response) => {
      // TODO: Deal with other return messages
    })
  }

  const handleSubmit = event => {
    event.preventDefault();

    editUser();
    
    setEditData({
      reset: true
    });

    setEditing(!editing);
  }

  function handleUpload(myUserId) {
    dispatch(updateCurrentIdToUpload(myUserId));
    dispatch(updateCurrentUploadType(1));
    dispatch(openUploaderWindow());
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
                  <Avatar userId={userId} avatarSize={200} type={1}></Avatar>
                </div>
              </div>

              <div className='entity-info-info'>
                <div className='general-row-align'>
                  <label style={{ 'color': '#767171' }} className='general-font-medium'>{userData.nickname}</label>

                  <div className='general-right-buttons'>
                    { access &&
                      <button className="general-button-grey" onClick={handleEdit}>编辑</button>
                    }
                  </div>
                  
                </div>
                
                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>{"用户名" + userData.username}</label><br/>
                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>{"创建于" + parseDBTimeYMD(userData.createdTime)}</label><br/>

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
                  <Avatar userId={userId} avatarSize={200} type={1}></Avatar>
                </div>
                
                <div style={{ 'marginTop': '25px' }}>
                  <button className='general-button-grey' onClick={() => handleUpload(userId)}>上传头像</button>
                </div>
              </div>

              <div className='entity-info-info'>
                <div className='general-row-align'>
                  <input 
                    name="nickname" 
                    className="edit-nickname-input" 
                    onChange={handleEditChange} 
                    defaultValue={userNickname}
                  />
                  
                  <div className='general-right-buttons'>
                    <button className="general-button-green" onClick={handleSubmit}>完成</button>
                    <button className="general-button-grey" onClick={handleEdit}>取消</button>
                  </div>
                </div>
                
                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>{"用户名" + userData.username}</label><br/>
                <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>{"创建于" + parseDBTimeYMD(userData.createdTime)}</label><br/>

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

export default UserInfo;