import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Header from '../../components/Layout/Header/Header';
import SignInUpContainer from '../../components/User/SignInUpContainer';
import Request from '../../components/Mail/Request';
import Messages from '../../components/Mail/Messages';
import { useGetRequestsForUserQuery } from '../../services/request';
import { closeMessage } from '../../redux/layoutSlice';

import { Icon } from '@rsuite/icons';
import { AiOutlineMail } from 'react-icons/ai';
import { AiOutlineArrowLeft } from 'react-icons/ai';

function AllMail() {
  const dispatch = useDispatch();

  const myUserId = useSelector((state) => state.userAuth.userId);
  const userRequestsResults = useGetRequestsForUserQuery(myUserId);

  const currentSelectedRequestId = useSelector((state) => state.layout.selectedRequestId);
  const isInMessage = useSelector((state) => state.layout.inMessage);

  return (
    <div className='general-page-container'>

      <Header title="所有收件箱" icon = {AiOutlineMail} />
      
      <SignInUpContainer />

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
  );
}

export default AllMail;