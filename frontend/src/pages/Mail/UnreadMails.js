import React from 'react';
import { useSelector } from 'react-redux';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Request from '../../components/Mail/Request';
import Messages from '../../components/Mail/Messages';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import { useGetRequestsForUserQuery } from '../../services/request';
import { AiOutlineMail } from 'react-icons/ai';
import { mailSideBar } from '../../assets/sidebar';

function UnreadMails() {
  function parseRequests(request, userId) {
    let unread = (userId === request.sender && !request.senderRead) || (userId === request.recipient && !request.recipientRead)
    if (unread) {
      return (<Request request={request} myUserId={myUserId}/>);
    }
  }

  const myUserId = useSelector((state) => state.userAuth.userId);
  const userRequestsResults = useGetRequestsForUserQuery(myUserId);

  const currentSelectedRequestId = useSelector((state) => state.layout.selectedRequestId);
  const isInMessage = useSelector((state) => state.layout.inMessage);

  return (
    <div className='general-page-container'>

      <Header title="未读" icon = {AiOutlineMail} />
      
      <SignInUpContainer />
      <Sidebarlvl2 
        prefix={`/mail/`}
        data={mailSideBar}
        type='mail'
      />
      { myUserId !== -1 &&
          <div className='general-page-container-reduced'>
            { !userRequestsResults.isFetching &&
            <div>
              { !isInMessage &&
                <div>
                  {userRequestsResults.data.data.map((request) =>
                    <div key={request.requestId}>
                      {parseRequests(request, myUserId)}
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

export default UnreadMails;