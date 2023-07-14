import React from 'react';

function Confirm({message, onClose, onConfirm}) {
  return (
    <div className="prompt-container">
      <p className="sign-in-up-title">确认</p>

      <label style={{ 'color': '#afabab' }} className='general-font-small'>{message}</label>

      <div className="sign-in-up-button-list">
        <button className="general-button-green" onClick={onConfirm}>确认</button>
        <button className="general-button-red" onClick={onClose}>取消</button>
      </div>
    </div>
  )
}

export default Confirm;