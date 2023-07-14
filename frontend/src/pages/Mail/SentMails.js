import React from 'react';
import { useSelector } from 'react-redux';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Request from '../../components/Mail/Request';
import Messages from '../../components/Mail/Messages';
import Sidebarlvl2 from '../../components/Layout/Sidebar/Sidebarlvl2';
import { useGetSentRequestsForUserQuery } from '../../services/request';
import { AiOutlineMail } from 'react-icons/ai';
import { mailSideBar } from '../../assets/sidebar';
import { languagedata } from '../../assets/language';
import { Icon } from '@rsuite/icons';

function SentMails() {
  const myUserId = useSelector((state) => state.userAuth.userId);
  const userRequestsResults = useGetSentRequestsForUserQuery(myUserId);

  const currentSelectedRequestId = useSelector((state) => state.layout.selectedRequestId);
  const isInMessage = useSelector((state) => state.layout.inMessage);
  const language = useSelector((state) => state.layout.language);

  return (
    <div>
      <div className="header-title">
        <Icon as={AiOutlineMail} size="3.1em" style={{ marginRight: '20px' }}/>
        <label className="page-header-title">{languagedata[language]['sent']}</label>
      </div>

      <div className='general-page-container'>
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
    </div>
  );
}

export default SentMails;