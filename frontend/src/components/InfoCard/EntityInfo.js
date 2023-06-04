import React from 'react';

function EntityInfo() {
  return (
    <div className='entity-info-window'>
      <div style={{ 'backgroundImage': 'url(https://via.placeholder.com/150)' }} className='entity-info-avatar'></div>

      <div className='entity-info-info'>
        <label style={{ 'color': '#767171' }} className='general-font-medium'>上原步梦</label>
        <button style={{ 'backgroundColor': '#e7e6e6', 'marginLeft': '450px' }} className="general-button-shadow" >编辑</button><br/>
        <label style={{ 'color': '#b3afaf' }} className='general-font-tiny'>2022年11月11日成加入</label><br/>

        <div className='entity-info-info-description'>
          <label style={{ 'color': '#afabab' }} className='general-font-small'>
            大家好，初次见面。我是上原步梦。那个，因为这是第一次像这样向大
            家传递讯息，有点紧张到心跳加速了。不过，成为学园偶像这件事，是
            我和重要的朋友找到的，想一起实现的梦想，所以我也想要像自己的名
            字那样，向著梦想一步一步，努力地前进下去！虽然唱歌和跳舞都还不
            熟练，外表也……大概，普通？不过，那个，只有努力我是很擅长的所以
            请支持我！请多多指教！
          </label>
        </div>

      </div>
    </div>
  );
}

export default EntityInfo;