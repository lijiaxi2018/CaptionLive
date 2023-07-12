import React from 'react';
import { updateUserId } from '../../../redux/userSlice';
import { toggleSignInOnWindow } from '../../../redux/layoutSlice';
import { useSelector, useDispatch } from 'react-redux';
import { Icon } from '@rsuite/icons';
// import { useGetUserQuery } from '../../../services/user'
import Avatar from '../../InfoCard/Avatar';

function Header({title, icon}) {
  const dispatch = useDispatch();

  const userId = useSelector((state) => state.userAuth.userId);
  // const userData = useGetUserQuery(userId);
  // const welcomePrompt = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : "欢迎, " + userData.data.data.username;

  const loginOut = () => {
    dispatch(updateUserId(-1));
  }

  const openloginInOut = () => {
    dispatch(toggleSignInOnWindow());
  }

  return (
    <div className="page-header">
        <Icon as={icon} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{title}</label>
        
        { userId === -1 &&
          <div className="page-header-username">
            <button className="general-button-grey" onClick={openloginInOut}>登陆/注册</button>
          </div>
        }
        
        { userId !== -1 &&
          <div className="page-header-username">
            {/* <label style={{ 'marginRight': '10px' }} >{welcomePrompt}</label> */}

            <div style={{ 'marginRight': '10px' }} >
              <Avatar userId={userId} avatarSize={50} type={1}></Avatar>
            </div>
            
            <button className="general-button-grey" onClick={loginOut}>登出</button>
          </div>
        }
      </div>
  );
}

export default Header;