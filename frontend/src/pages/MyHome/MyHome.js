import React from 'react';
import { useSelector } from 'react-redux';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import UserInfo from '../../components/InfoCard/UserInfo';
import { AiOutlineHome } from 'react-icons/ai';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';

function MyHome() {
  const myUserId = useSelector((state) => state.userAuth.userId);
  const language = useSelector((state) => state.layout.language);

  return (
    <div>
      <div className="header-title">
        <Icon as={AiOutlineHome} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{languagedata[language]['personalInformation']}</label>
      </div>

      <div className='general-page-container'>
        { myUserId !== -1 &&
          <div className='entity-info-container-my-home'>
            <UserInfo userId = {myUserId} access={true}/>
          </div>
        }
          
        <SignInUpContainer />
        
      </div>
    </div>
  );
}

export default MyHome;