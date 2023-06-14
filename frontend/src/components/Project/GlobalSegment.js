import React, { useState } from 'react';
import { useSelector } from 'react-redux'
import TaskStatusIcon from '../InfoCard/TaskStatusIcon';
import { useAssignTaskMutation, useWithdrawTaskMutation } from '../../services/organization';
import { parseTaskType } from '../../utils/segment';
import { Icon } from '@rsuite/icons';
import { FaAngleUp, FaAngleDown } from 'react-icons/fa';

// TODO: Submit File
// TODO: Secession Store If Expanded

function parseTaskText(task) {
  if (task.workerUser === null) {
    return [parseTaskType(task.type) + ':', ''];
  } else {
    return [parseTaskType(task.type) + ':', task.workerUser.username];
  }
}

function Segment({data}) {
  const myUserId = useSelector((state) => state.userAuth.userId);
  const [expanded, setExpanded] = useState(false);

  const [assignTaskMutation] = useAssignTaskMutation()
  const [withdrawTaskMutation] = useWithdrawTaskMutation()
  
  function assignTask(userId, taskId) {
    assignTaskMutation({
      userId: userId,
      taskId: taskId,
    })
    .then((response) => {
      // Handle Different Messages
    })
  }

  function withdrawTask(taskId) {
    withdrawTaskMutation(taskId)
    .then((response) => {
      // Handle Different Messages
    })
  }

  const summary = '概览';
  // const scope = data.scope;
  const tasks = data;
  
  function parseTaskButton(task) {
    if (task.status === 'NOT_ASSIGNED') {
      return (<button className='general-button-green' onClick={() => assignTask(myUserId, task.taskId)}>承接</button>);
    } else if (task.status === 'IN_PROGRESS' && task.workerUser.userId !== myUserId) {
      return;
    } else if (task.status === 'IN_PROGRESS' && task.workerUser.userId === myUserId) {
      return (
        <div>
          <button className='general-button-grey'>上传</button>
          <button className='general-button-red' onClick={() => withdrawTask(task.taskId)}>放弃</button>
        </div>
      );
    } else if (task.status === 'COMPLETED' && task.workerUser.userId !== myUserId) {
      return <p></p>;
    } else if (task.status === 'COMPLETED' && task.workerUser.userId === myUserId) {
      return (<button className='general-button-grey'>删除</button>);
    }
  }

  return (
    <div className='segment-container'>
      
      <div className='segment-title-container'>
        <button className='general-icon-button' onClick={() => setExpanded(!expanded)}><Icon as={expanded ? FaAngleUp : FaAngleDown} size="2.5em" style={{ marginRight: '10px' }}/></button>
        <label className='general-font-medium-small-bold'>{summary}</label>
      </div>

      <div className='segment-status-icon-container'>
        {tasks.map((task) =>
          <div key={task.taskId}>
            <TaskStatusIcon type={task.type} status={task.status}/>
          </div>
        )}
      </div>

      { expanded &&
      <div>
        {/* <div className='segment-item-container'>
          <label className='general-font-medium-small-bold'>起讫: </label>
          <label className='general-font-medium-small'>{parseScope(scope)}</label>
        </div> */}

        <div>
          {tasks.map((task) =>
            <div key={task.taskId} className='segment-item-container'>
              <label className='general-font-medium-small-bold'>
                {parseTaskText(task)[0]}
              </label>
              <label className='general-font-medium-small'>
                {parseTaskText(task)[1]}
              </label>
              {parseTaskButton(task)}
            </div>
          )}
        </div>
      </div>
      }
    </div>
  );
}

export default Segment;