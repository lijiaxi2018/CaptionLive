import React from 'react';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import { AiOutlineMail } from 'react-icons/ai';
import { mailSideBar } from '../../assets/sidebar';

function SentMails() {
  return (
    <div className='general-page-container'>

      <Header title="已发送" icon = {AiOutlineMail} />
      <Sidebarlvl2 
        prefix={`/mail/`}
        data={mailSideBar}
        type='mail'
      />
      <SignInUpContainer />
    </div>
  );
}

export default SentMails;