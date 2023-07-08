import React from 'react';
import { useSelector } from 'react-redux';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Request from '../../components/Mail/Request';
import Messages from '../../components/Mail/Messages';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import { useGetRequestsForUserQuery } from '../../services/request';
import { useGetRequestsForUser } from '../../api/request';
import { closeMessage } from '../../redux/layoutSlice';
import { Icon } from '@rsuite/icons';
import { AiOutlineMail } from 'react-icons/ai';
import { mailSideBar } from '../../assets/sidebar';

function AllMail() {
  const myUserId = useSelector((state) => state.userAuth.userId);
  const [fetched, userRequestsResults] = useGetRequestsForUser(myUserId);

  const currentSelectedRequestId = useSelector((state) => state.layout.selectedRequestId);
  const isInMessage = useSelector((state) => state.layout.inMessage);

  return (
    <div className='general-page-container'>

      <Header title="所有消息" icon = {AiOutlineMail} />
      
      <SignInUpContainer />
      <Sidebarlvl2 
        prefix={`/mail/`}
        data={mailSideBar}
        type='mail'
      />
      { myUserId !== -1 &&
          <div className='general-page-container-reduced'>
            { fetched &&
            <div>
              { !isInMessage &&
                <div>
                  {userRequestsResults.map((request) =>
                    <div key={request.requestId}>
                      <Request request={request} myUserId={myUserId}/>
                    </div>
                  )}
                </div>
              }

              { isInMessage &&
                <div>
                  <Messages requestId={currentSelectedRequestId} userId={myUserId}/>
                </div>
              }

              
            </div>
            }
          </div>
        }
      
    </div>
  );
}

export default AllMail;