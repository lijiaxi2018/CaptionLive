import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineMail } from 'react-icons/ai';

function AllMail() {
  return (
    <div className='general-page-container'>

      <Header title="所有收件箱" icon = {AiOutlineMail} />
      
      <SignInUpContainer />
      
    </div>
  );
}

export default AllMail;