import React from 'react';
import { 
  parseTaskTypeInitial,
  parseTaskStatus,
} from '../../utils/segment';

function TaskStatusIcon({type, status}) {
  const initial = parseTaskTypeInitial(type);
  const color = parseTaskStatus(status);
  const border = '3px solid ' + color;

  return (
    <div className='task-status-icon' style={{ 'color': color, 'border': border }}>
      {initial}
    </div>
  );
}

export default TaskStatusIcon;