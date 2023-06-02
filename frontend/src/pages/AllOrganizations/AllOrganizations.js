import React from 'react';
import SignIn from '../../components/User/SignIn';
import SignUp from '../../components/User/SignUp';
import { updateUserId } from '../../redux/userSlice'
import { toggleSignInOnWindow, toggleSignInOnPage } from '../../redux/layoutSlice'
import { useSelector, useDispatch } from 'react-redux'
import { Icon } from '@rsuite/icons';
import { ImSphere } from 'react-icons/im';
import { useGetUserQuery } from '../../services/user'

function AllOrganizations() {
  const dispatch = useDispatch()

  const userId = useSelector((state) => state.userAuth.userId);
  const userData = useGetUserQuery(userId);
  const welcomePrompt = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : "欢迎, " + userData.data.data.username;

  const isOpenSignInOnWindow = useSelector((state) => state.layout.openSignInOnWindow);
  const isInSignIn = useSelector((state) => state.layout.inSignIn);

  const loginOut = () => {
    dispatch(updateUserId(-1))
  }

  const openloginInOut = () => {
    dispatch(toggleSignInOnWindow())
  }

  return (
    <div className='general-page-container'>
      <div className="page-header">
        <Icon as={ImSphere} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">所有字幕组</label>
        
        { userId === -1 &&
          <div className="page-header-username">
            <button style={{ 'backgroundColor': '#e7e6e6' }} className="general-page-sign-in-button" onClick={openloginInOut}>登陆/注册</button>
          </div>
        }
        
        { userId !== -1 &&
          <div className="page-header-username">
            <label>{welcomePrompt}</label>
            <button style={{ 'backgroundColor': '#e7e6e6' }} className="general-page-sign-in-button" onClick={loginOut}>登出</button>
          </div>
        }
        
      </div>
      
      { isOpenSignInOnWindow && isInSignIn &&
        <div className='sign-in-up-window'>
          <SignIn/>
        </div>
      }

      { isOpenSignInOnWindow && (!isInSignIn) &&
        <div className='sign-in-up-window'>
          <SignUp/>
        </div>
      }
    </div>
  );
}

export default AllOrganizations;