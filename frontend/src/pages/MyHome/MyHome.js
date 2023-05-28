import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { updateUserId, updateAccessToken } from '../../redux/userSlice'

function MyHome() {
  const myUserId = useSelector((state) => state.userAuth.userId)
  const myAccessToken = useSelector((state) => state.userAuth.accessToken)
  const dispatch = useDispatch()

  return (
    <div>
      <div>
        <h1>
          UserId: {myUserId}
        </h1>
        <h1>
          AccessToken: {myAccessToken}
        </h1>
        <button
          aria-label="Increment value"
          onClick={() => dispatch(updateUserId(0))}
        >
          Test Modify UserId
        </button>
        <button
          aria-label="Decrement value"
          onClick={() => dispatch(updateAccessToken('New Access Token'))}
        >
          Modify AccessToken
        </button>
      </div>
    </div>
  );
}

export default MyHome;