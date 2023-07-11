import React from 'react';
import { updateUserId } from '../../../redux/userSlice';
import { toggleSignInOnWindow, updateLanguage } from '../../../redux/layoutSlice';
import { useSelector, useDispatch } from 'react-redux';
import { Icon } from '@rsuite/icons';
import Avatar from '../../InfoCard/Avatar';
import { languagedata } from '../../../assets/language';

function Header() {
  const dispatch = useDispatch();

  const userId = useSelector((state) => state.userAuth.userId);
  const language = useSelector((state) => state.layout.language);
  // const userData = useGetUserQuery(userId);
  // const welcomePrompt = userId === -1 ? "未登录" : userData.isFetching ? "获取中..." : "欢迎, " + userData.data.data.username;

  const loginOut = () => {
    dispatch(updateUserId(-1));
  }

  const openloginInOut = () => {
    dispatch(toggleSignInOnWindow());
  }

  const handleLangChange = (event) => {
    dispatch(updateLanguage(event.target.value))
  }

  return (
    <div className="page-header">
        {/* <Icon as={icon} size="3.1em" style={{ marginRight: '20px' }}/> */}
        {/* <label className="page-header-title">{title}</label> */}
        <select id='language-selector' onChange={handleLangChange}>
          <option value='cn'>中文</option>
          <option value='en'>English</option>
        </select>
        { userId === -1 &&
          <div className="page-header-username">
            <button 
              style={{ 'marginTop':'30px' }}
              className="general-button-grey" 
              onClick={openloginInOut}
            >
              {`${languagedata[language]['login']}/${languagedata[language]['register']}`}
            </button>
          </div>
        }
        
        { userId !== -1 &&
          <div className="page-header-username">
            {/* <label style={{ 'marginRight': '10px' }} >{welcomePrompt}</label> */}

            <div style={{ 'marginRight': '10px', 'marginTop':'30px' }} >
              <Avatar userId={userId} avatarSize={50} type={1}></Avatar>
            </div>
            
            <button 
              style={{ 'marginTop':'30px' }}
              className="general-button-grey" 
              onClick={loginOut}
            >
              {languagedata[language]['logout']}
            </button>
          </div>
        }
      </div>
  );
}

export default Header;