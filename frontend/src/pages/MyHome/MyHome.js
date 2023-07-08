import React from 'react';
import { useSelector } from 'react-redux';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import UserInfo from '../../components/InfoCard/UserInfo';
import { AiOutlineHome } from 'react-icons/ai';

function MyHome() {
  const myUserId = useSelector((state) => state.userAuth.userId);

  return (
    <div className='general-page-container'>

      <Header title="个人信息" icon = {AiOutlineHome} />

      { myUserId !== -1 &&
        <div className='entity-info-container-my-home'>
          <UserInfo userId = {myUserId} access={true}/>
        </div>
      }
        
      <SignInUpContainer />
      
    </div>
  );
}

export default MyHome;