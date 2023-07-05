import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { closePrompt } from '../../redux/layoutSlice';

function Prompt() {
  const dispatch = useDispatch() // Redux

  const message = useSelector((state) => state.layout.promptMessage);
  const isOpenPrompt = useSelector((state) => state.layout.inPrompt);

  const handleConfirm = () => {
    dispatch(closePrompt());
  }

  return (
    <div>
      { isOpenPrompt &&
        <div className="prompt-container">
          <p className="sign-in-up-title">提示</p>
    
          <label style={{ 'color': '#afabab' }} className='general-font-small'>{message}</label>
    
          <div className="sign-in-up-button-list">
            <button className="general-button-green" onClick={handleConfirm}>确认</button>
          </div>
        </div>
      }
    </div>
  )
}

export default Prompt;