import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import { AiOutlineMail } from 'react-icons/ai';

function SentMails() {
  return (
    <div className='general-page-container'>

      <Header title="已发送" icon = {AiOutlineMail} />
      
      <SignInUpContainer />
      
    </div>
  );
}

export default SentMails;