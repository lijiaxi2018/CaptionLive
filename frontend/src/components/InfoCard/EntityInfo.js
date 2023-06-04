import React from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux'
import { toggleInEditUser } from '../../redux/layoutSlice'
import { useGetUserQuery } from '../../services/user'

function EntityInfo({userId}) {
  const dispatch = useDispatch();
  const isInEditUser = useSelector((state) => state.layout.inEditUser);

  const userData = useGetUserQuery(userId);
  console.log(userData)
  const username = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : userData.data.data.username;

  const handleEdit = () => {
    dispatch(toggleInEditUser());
  }

  return (
    <div >
      { !isInEditUser &&
        <div className='entity-info-window'>
          <div className='entity-info-avatar'>
            <div style={{ 'backgroundImage': 'url(https://via.placeholder.com/150)', 'marginTop': '15px' }} className='entity-info-avatar-avatar'></div><br/>
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
              <label style={{ 'color': '#afabab' }} className='general-font-small'>
                大家好，初次见面。我是上原步梦。那个，因为这是第一次像这样向大
                家传递讯息，有点紧张到心跳加速了。不过，成为学园偶像这件事，是
                我和重要的朋友找到的，想一起实现的梦想，所以我也想要像自己的名
                字那样，向著梦想一步一步，努力地前进下去！虽然唱歌和跳舞都还不
                熟练，外表也……大概，普通？不过，那个，只有努力我是很擅长的所以
                请支持我！请多多指教！
              </label>
            </div>
          </div>
        </div>
      }

      { isInEditUser &&
        <div className='entity-info-window'>
          <div className='entity-info-avatar'>
            <div style={{ 'backgroundImage': 'url(https://via.placeholder.com/150)', 'marginTop': '15px' }} className='entity-info-avatar-avatar'></div><br/>
            <button style={{ 'marginTop': '30px' }} className="general-button-grey" onClick={handleEdit}>上传头像</button>
          </div>

          <div className='entity-info-info'>
            <label style={{ 'color': '#767171' }} className='general-font-medium'>{username}</label>
            <button style={{ 
              'position': 'absolute',
              'top': '7.5%',
              'left': '82.5%',
              }} className="general-button-green" onClick={handleEdit}>完成
            </button>
            <button style={{ 
              'position': 'absolute',
              'top': '7.5%',
              'left': '90%',
              }} className="general-button-grey" onClick={handleEdit}>取消
            </button><br/>
            <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>2022年11月11日成加入</label><br/>

            <textarea style={{ 'resize': 'none' }} className='entity-info-info-description'>
              
            </textarea>
          </div>
        </div>
      }
    </div>
  );
}

export default EntityInfo;