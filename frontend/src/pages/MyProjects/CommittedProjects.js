import React from 'react';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineProject } from 'react-icons/ai';
import { myprojectSideBar } from '../../assets/sidebar';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';
import { useSelector } from 'react-redux';

function CommittedProjects() {
  const language = useSelector((state) => state.layout.language);
  return (
    <div>
      <div className="header-title">
        <Icon as={AiOutlineProject} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{languagedata[language]['myProject']}</label>
      </div>

      <div className='general-page-container'>
        <Sidebarlvl2 
          prefix={`/myprojects/`}
          data={myprojectSideBar}
          type='myproject'
        />
        <SignInUpContainer />

        <div className='general-page-container-reduced'>
          <h2>{languagedata[language]['comingSoon']}</h2>
        </div>
        
      </div>
    </div>
  );
}

export default CommittedProjects;