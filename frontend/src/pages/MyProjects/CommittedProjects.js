import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineProject } from 'react-icons/ai';

function CommittedProjects() {
  return (
    <div className='general-page-container'>

    <Header title="参与的项目" icon = {AiOutlineProject} />
    
    <SignInUpContainer />
    
  </div>
  );
}

export default CommittedProjects;