import React from 'react';
import SignIn from '../../components/User/SignIn';
import SignUp from '../../components/User/SignUp';
import { useSelector } from 'react-redux'

function SignInUpContainer() {
  const isOpenSignInOnWindow = useSelector((state) => state.layout.openSignInOnWindow);
  const isInSignIn = useSelector((state) => state.layout.inSignIn);
  
  return (
    <div>
      { isOpenSignInOnWindow && isInSignIn &&
        <div className='sign-in-up-container'>
          <SignIn />
        </div>
      }

      { isOpenSignInOnWindow && (!isInSignIn) &&
        <div className='sign-in-up-container'>
          <SignUp />
        </div>
      }
    </div>
  )
}

export default SignInUpContainer;