import React, { useReducer } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { toggleInEditUser } from '../../redux/layoutSlice';
import { useGetUserQuery } from '../../services/user';
import { usePutUserDescriptionMutation } from '../../services/user';

import FileUpload from '../Layout/Modal/FileUpload';
import Avatar from './Avatar';

function EntityInfo({userId}) {
  const dispatch = useDispatch();
  const [putUserDescription] = usePutUserDescriptionMutation();
  const isInEditUser = useSelector((state) => state.layout.inEditUser);
  
  const userData = useGetUserQuery(userId);
  const username = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : userData.data.data.username;
  const userDescription = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : userData.data.data.description;

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
    dispatch(toggleInEditUser());
    
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

  const handleSubmit = event => {
    event.preventDefault();
    
    editUser();

    setEditData({
      reset: true
    });

    dispatch(toggleInEditUser());
  }

  return (
    <div >

      { !isInEditUser &&
        <div className='entity-info-window'>
          <div className='entity-info-avatar'>
            <div style={{ 'marginTop': '25px' }}>
              <Avatar userId={userId} avatarSize={200} isBorder={false}></Avatar>
            </div>
          </div>

          <div className='entity-info-info'>
            <label style={{ 'color': '#767171' }} className='general-font-medium'>{username}</label>
            <button style={{ 
              'position': 'absolute',
              'top': '7.5%',
              'left': '90%',
              }} className="general-button-grey" onClick={handleEdit}>编辑
            </button><br/>
            <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>2022年11月11日成加入</label><br/>

            <div className='entity-info-info-description'>
              <label style={{ 'color': '#afabab' }} className='general-font-small'>{userDescription}</label>
            </div>
          </div>
        </div>
      }

      { isInEditUser &&
        <div className='entity-info-window'>
          <div className='entity-info-avatar'>
            <div style={{ 'marginTop': '25px' }}>
              <Avatar userId={userId} avatarSize={200} isBorder={false}></Avatar>
            </div>
            
            <div style={{ 'marginTop': '25px' }}>
              <FileUpload/>
            </div>
          </div>

          <div className='entity-info-info'>
            <label style={{ 'color': '#767171' }} className='general-font-medium'>{username}</label>
            <button style={{ 
              'position': 'absolute',
              'top': '7.5%',
              'left': '82.5%',
              }} className="general-button-green" onClick={handleSubmit}>完成
            </button>
            <button style={{ 
              'position': 'absolute',
              'top': '7.5%',
              'left': '90%',
              }} className="general-button-grey" onClick={handleEdit}>取消
            </button><br/>
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
  );
}

export default EntityInfo;