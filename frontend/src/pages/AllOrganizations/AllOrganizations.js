import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { ImSphere } from 'react-icons/im';

function AllOrganizations() {
  return (
    <div className='general-page-container'>

      <Header title="所有字幕组" icon = {ImSphere} />
      
      <SignInUpContainer />
      
    </div>
  );
}

export default AllOrganizations;