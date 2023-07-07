import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineProject } from 'react-icons/ai';
import { myprojectSideBar } from '../../assets/sidebar';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';

function CommittedProjects() {
  return (
    <div className='general-page-container'>

    <Header title="我的项目" icon = {AiOutlineProject} />
    <Sidebarlvl2 
      prefix={`/myprojects/`}
      data={myprojectSideBar}
      type='myproject'
    />
    <SignInUpContainer />
    
  </div>
  );
}

export default CommittedProjects;