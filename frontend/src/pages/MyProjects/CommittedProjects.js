import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineProject } from 'react-icons/ai';
import { myprojectSideBar } from '../../assets/sidebar';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';

function CommittedProjects() {
  return (
    <div className='general-page-container'>

    <Header title="参与的项目" icon = {AiOutlineProject} />
    <Sidebarlvl2 
      prefix={`/myprojects/`}
      data={myprojectSideBar}
      type='myproject'
    />
    <SignInUpContainer />

    <div className='general-page-container-reduced'>
      <h2>制作中，敬请期待...</h2>
    </div>
    
  </div>
  );
}

export default CommittedProjects;