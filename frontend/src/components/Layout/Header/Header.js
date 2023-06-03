import React from 'react';
import { updateUserId } from '../../../redux/userSlice'
import { toggleSignInOnWindow } from '../../../redux/layoutSlice'
import { useSelector, useDispatch } from 'react-redux'
import { Icon } from '@rsuite/icons';
import { useGetUserQuery } from '../../../services/user'

function Header({title, icon}) {
  const dispatch = useDispatch()

  const userId = useSelector((state) => state.userAuth.userId);
  const userData = useGetUserQuery(userId);
  const welcomePrompt = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : "欢迎, " + userData.data.data.username;

  const loginOut = () => {
    dispatch(updateUserId(-1))
  }

  const openloginInOut = () => {
    dispatch(toggleSignInOnWindow())
  }

  return (
    <div className="page-header">
        <Icon as={icon} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{title}</label>
        
        { userId === -1 &&
          <div className="page-header-username">
            <button style={{ 'backgroundColor': '#e7e6e6' }} className="general-page-sign-in-up-button" onClick={openloginInOut}>登陆/注册</button>
          </div>
        }
        
        { userId !== -1 &&
          <div className="page-header-username">
            <label>{welcomePrompt}</label>
            <button style={{ 'backgroundColor': '#e7e6e6' }} className="general-page-sign-in-up-button" onClick={loginOut}>登出</button>
          </div>
        }
      </div>
  );
}

export default Header;