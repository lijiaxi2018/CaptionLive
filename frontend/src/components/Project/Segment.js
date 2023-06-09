import React, { useState } from 'react';
import TaskStatusIcon from '../InfoCard/TaskStatusIcon';
import { useGetSegmentQuery } from '../../services/segment';
import { useGetOrganizationProjectsQuery } from '../../services/organization';
import { parseTaskType } from '../../utils/segment';
import { Icon } from '@rsuite/icons';
import { FaAngleUp, FaAngleDown } from 'react-icons/fa';

function Segment({segmentId, type, data}) {
  const orgId = 1;
  const orgProjectsResults = useGetOrganizationProjectsQuery(orgId);

  const orgProjectsFetched = 
    orgId === -1 ? false : 
    orgProjectsResults.isFetching ? false : 
    true;
  
  const segmentDataNew = orgProjectsFetched === false ? {} : orgProjectsResults.data.data[0].segmentInfos[0];
  console.log(segmentDataNew);
  
  const [expanded, setExpanded] = useState(false);


  
  
  const segmentResult = useGetSegmentQuery(segmentId);
  
  const segmentData = 
    segmentId === undefined ? {} : 
    segmentResult.isFetching ? {} : 
    segmentResult.data.data;
  
  const fetched = 
    segmentId === undefined ? false : 
    segmentResult.isFetching ? false : 
    true;
  
  const summary = data.summary;
  const scope = data.scope;
  const tasks = data.taskInfos;
  console.log(tasks);

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
          {tasks.map((task) =>
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