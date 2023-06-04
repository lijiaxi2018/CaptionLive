import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineMail } from 'react-icons/ai';

function UnreadMails() {
  return (
    <div className='general-page-container'>

      <Header title="未读" icon = {AiOutlineMail} />
      
      <SignInUpContainer />
      
    </div>
  );
}

export default UnreadMails;