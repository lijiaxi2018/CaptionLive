import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { FaCircle } from 'react-icons/fa';
import Segment from './Segment';
import Avatar from '../InfoCard/Avatar';
import AddSegment from '../../components/Project/AddSegment';
import ShareProject from '../../components/Project/ShareProject';
import FileUploader from '../Layout/Modal/FileUploader';
import { openUploaderWindow, updateCurrentIdToUpload, updateCurrentUploadType } from '../../redux/fileSlice';
import { openAddSegment, updateSelectedProjectId, openShareProject } from '../../redux/layoutSlice';
import { useDeleteProjectMutation } from '../../services/organization';
import { parseTaskType } from '../../utils/segment';
import { allWorkflowList } from '../../assets/workflows';
import { openPrompt, updatePromptMessage } from '../../redux/layoutSlice';
import Prompt from '../../components/InfoCard/Prompt';

function copyToClipboard(str) {
  const el = document.createElement('textarea');
  el.value = str;
  document.body.appendChild(el);
  el.select();
  document.execCommand('copy');
  document.body.removeChild(el);
};

function parseToStaffList(projectData) {
  let taskType2Nickname = {};
  for (const segment of projectData.segmentInfos) {
    for (const task of segment.taskInfos) {
      if ( !(task.type in taskType2Nickname) ) {
        taskType2Nickname[task.type] = new Set();
      }
      taskType2Nickname[task.type].add(task.workerUser.nickname);
    }
  }

  let staffList = "";
  let taskTypes = Object.keys(taskType2Nickname);
  taskTypes = taskTypes.sort(function(a,b) {
    return allWorkflowList.indexOf( a ) - allWorkflowList.indexOf( b );
  });

  for (let i = 0; i < taskTypes.length; i++) {
    staffList = staffList.concat(parseTaskType(taskTypes[i]));
    staffList = staffList.concat(': ');
    let nicknames = Array.from(taskType2Nickname[taskTypes[i]]);
    for (let j = 0; j < nicknames.length; j++) {
      staffList = staffList.concat(nicknames[j]);
      if (j !== nicknames.length - 1) {
        staffList = staffList.concat(', ');
      }
    }
    if (i !== taskTypes.length - 1) {
      staffList = staffList.concat('\n');
    }
  }
  return staffList;
}

function Worksheet({data}) {
  const dispatch = useDispatch();

  const segments = data.segmentInfos;

  const isOpenUploaderWindow = useSelector((state) => state.file.openUploaderWindow);
  const isOpenAddSegment = useSelector((state) => state.layout.inAddSegment);
  const isOpenShareProject = useSelector((state) => state.layout.inShareProject);
  const currentSelectedProjectId = useSelector((state) => state.layout.selectedProjectId);
  const [deleteProjectMutation] = useDeleteProjectMutation();

  function handleUpload(myProjectId) {
    dispatch(updateCurrentIdToUpload(myProjectId));
    dispatch(updateCurrentUploadType(2));
    dispatch(openUploaderWindow());
  }

  function handleOpenAddSegment(myProjectId) {
    dispatch(updateSelectedProjectId(myProjectId));
    dispatch(openAddSegment());
  }

  function handleOpenShareProject(myProjectId) {
    dispatch(updateSelectedProjectId(myProjectId));
    dispatch(openShareProject());
  }

  function handleDeleteProject(myProjectId) {
    deleteProjectMutation(myProjectId)
    .then((response) => {
      // TODO: Deal with other return messages
    })
  }
    
  function handleCopyStaff(projectData) {
    let staffList = parseToStaffList(projectData);
    // navigator.clipboard.writeText(staffList);
    copyToClipboard(staffList);

    dispatch(updatePromptMessage("制作人员名单已拷贝到剪贴板。"));
    dispatch(openPrompt());
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

      { isOpenAddSegment &&
        <AddSegment projectId={currentSelectedProjectId}/>
      }

      { isOpenShareProject &&
        <ShareProject projectId={currentSelectedProjectId}/>
      }

      <Prompt />

      <div className='worksheet-info-container'>
        
        <div className='general-row-align'>
          <FaCircle size="2em" style={{ color : parseStatusColor(data.isCompleted), marginRight: '10px' }}/>
          <label className='general-font-medium-small-bold'>{data.name}</label>
        </div>

        <div style={{ marginTop: '30px' }}></div>

        <div className='general-column-align-left'>
          <Avatar userId={data.projectId} avatarSize={250} type={2}></Avatar>
        </div>

      </div>

      <div className='worksheet-segments-container'>
        {segments.map((segment) =>
          <div key={segment.segmentId}>
            <Segment data={segment}/>
          </div>
        )}
      </div>

      <div className='worksheet-buttons-container'>
        <button className='general-button-grey' onClick={() => handleUpload(data.projectId)}>上传封面</button>
        <div style={{ marginTop: '10px' }}></div>
        <button className='general-button-grey' onClick={() => handleOpenShareProject(data.projectId)}>分享项目</button>
        <div style={{ marginTop: '10px' }}></div>
        <button className='general-button-grey' onClick={() => handleOpenAddSegment(data.projectId)}>新建段落</button>
        <div style={{ marginTop: '10px' }}></div>
        <button className='general-button-red' onClick={() => handleDeleteProject(data.projectId)}>删除项目</button>

        { data.isCompleted &&
          <div>
            <div style={{ marginTop: '10px' }}></div>
            <button className='general-button-grey' onClick={() => handleCopyStaff(data)}>名单抓取</button>
          </div>
        }
      </div>
      
    </div>
  );
}

export default Worksheet;