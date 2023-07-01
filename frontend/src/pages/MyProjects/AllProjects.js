import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineProject } from 'react-icons/ai';
import { myprojectSideBar } from '../../assets/sidebar';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';


function AllProjects() {
  return (
    <div className='general-page-container'>

      <Header title="所有项目" icon = {AiOutlineProject} />
      <Sidebarlvl2 
        prefix={`/myprojects/`}
        data={myprojectSideBar}
        type='myproject'
      />
      <SignInUpContainer />
      
    </div>
  );
}

export default AllProjects;