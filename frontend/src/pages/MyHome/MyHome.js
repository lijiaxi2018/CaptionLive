import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import EntityInfo from '../../components/InfoCard/EntityInfo';
import { AiOutlineHome } from 'react-icons/ai';



function MyHome() {
  return (
    <div className='general-page-container'>

      <Header title="我的主页" icon = {AiOutlineHome} />

      <div className='entity-info-container-my-home'>
        <EntityInfo />
      </div>
      
      <SignInUpContainer />
      
    </div>
  );
}

export default MyHome;