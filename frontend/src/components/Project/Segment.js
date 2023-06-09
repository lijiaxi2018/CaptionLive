import React, { useState } from 'react';
import TaskStatusIcon from '../InfoCard/TaskStatusIcon';
import { useGetSegmentQuery } from '../../services/segment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';
import { parseTaskType } from '../../utils/segment';
import { Icon } from '@rsuite/icons';
import { FaAngleUp, FaAngleDown } from 'react-icons/fa';

function Segment({segmentId, type}) {
  const [expanded, setExpanded] = useState(false);

  const orgProjects = useGetOrganizationProjectsQuery(1);
  console.log(orgProjects);

  const segmentResult = useGetSegmentQuery(segmentId);
  
  const segmentData = 
    segmentId === undefined ? {} : 
    segmentResult.isFetching ? {} : 
    segmentResult.data.data;
  
  const fetched = 
    segmentId === undefined ? false : 
    segmentResult.isFetching ? false : 
    true;
  
  // console.log(segmentData);
  
  const summary = segmentData === {} ? '获取中...' : segmentData.summary;
  const scope = segmentData === {} ? '获取中...' : segmentData.scope;
  const tasks = segmentData === {} ? [] : segmentData.tasks;
  // console.log(tasks);

  return (
    <div className='segment-container'>
      
      <div className='segment-title-container'>
        <button className='general-icon-button' onClick={() => setExpanded(!expanded)}><Icon as={expanded ? FaAngleUp : FaAngleDown} size="2.5em" style={{ marginRight: '10px' }}/></button>
        <label className='general-font-medium-small-bold'>{summary}</label>
      </div>

      { expanded &&
      <div>
        <div className='segment-item-container'>
          <label className='general-font-medium-small-bold'>起讫: </label>
          <label className='general-font-medium-small'>{scope}</label>
        </div>

        <div>
          {fetched && tasks.map((task) =>
            <div key={task.taskId} className='segment-item-container'>
              <label className='general-font-medium-small-bold'>{parseTaskType(task.type)}:</label>
              <label className='general-font-medium-small'></label>
            </div>
          )}
        </div>
      </div>
      }
      <TaskStatusIcon type={'TIMELINE'} status={'NOT_ASSIGNED'}/>
    </div>

    
  );
}

export default Segment;