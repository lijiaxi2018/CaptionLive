import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { closePrompt } from '../../redux/layoutSlice';
import { languagedata } from '../../assets/language';

function Prompt() {
  const dispatch = useDispatch() // Redux

  const message = useSelector((state) => state.layout.promptMessage);
  const isOpenPrompt = useSelector((state) => state.layout.inPrompt);
  const language = useSelector((state) => state.layout.language);

  const handleConfirm = () => {
    dispatch(closePrompt());
  }

  return (
    <div>
      { isOpenPrompt &&
        <div className="prompt-container">
          <p className="sign-in-up-title">{languagedata[language]['hint']}</p>
    
          <label style={{ 'color': '#afabab' }} className='general-font-small'>{message}</label>
    
          <div className="sign-in-up-button-list">
            <button className="general-button-green" onClick={handleConfirm}>{languagedata[language]['confirm']}</button>
          </div>
        </div>
      }
    </div>
  )
}

export default Prompt;