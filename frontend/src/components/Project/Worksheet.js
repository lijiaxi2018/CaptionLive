import React from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { FaCircle } from 'react-icons/fa';
import Segment from './Segment';
import Avatar from '../InfoCard/Avatar';
import FileUploader from '../Layout/Modal/FileUploader';
import { openUploaderWindow, updateCurrentIdToUpload, updateCurrentUploadType } from '../../redux/fileSlice';

function Worksheet({data}) {
  const dispatch = useDispatch();

  const segments = data.segmentInfos;
  const isOpenUploaderWindow = useSelector((state) => state.file.openUploaderWindow);

  function handleUpload(myProjectId) {
    dispatch(updateCurrentIdToUpload(myProjectId));
    dispatch(updateCurrentUploadType(2));
    dispatch(openUploaderWindow());
  }

  function parseStatusColor(status) {
    if (status) {
      return '#a6ddaf';
    } else {
      return '#a9d0e3';
    }
  }

  function parseStatusBorder(status) {
    if (status) {
      return '5px solid #a6ddaf';
    } else {
      return '5px solid #a9d0e3';
    }
  }

  return (
    <div className='worksheet-container' style={{ 'border' : parseStatusBorder(data.isCompleted) }}>

      { isOpenUploaderWindow === 1 &&
        <FileUploader />
      }

      <div className='worksheet-info-container'>
        
        <div className='general-row-align'>
          <FaCircle size="2em" style={{ color : parseStatusColor(data.isCompleted), marginRight: '10px' }}/>
          <label className='general-font-medium-small-bold'>{data.name}</label>
        </div>

        <div style={{ marginTop: '30px' }}></div>

        <div className='general-column-align'>
          <div>
            <Avatar userId={1} avatarSize={175} type={2}></Avatar>
          </div>

          <div>
            <button className='general-button-grey' style={{ marginTop: '20px' }} onClick={() => handleUpload(data.projectId)}>上传封面</button>
          </div>
        </div>

      </div>

      <div className='worksheet-segments-container'>
        {segments.map((segment) =>
          <div key={segment.segmentId}>
            <Segment data={segment}/>
          </div>
        )}
      </div>
      
    </div>

    
  );
}

export default Worksheet;