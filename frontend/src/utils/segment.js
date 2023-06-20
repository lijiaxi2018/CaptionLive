export function parseTaskType(type) {
  switch(type) {
    case 'TIMELINE':
      return '时轴'
    
    case 'TRANSLATION':
      return '翻译'
    
    case 'CHECK':
      return '校对'
    
    case 'SOURCE':
      return '源'
    
    case 'F_CHECK':
      return '终校'
    
    case 'RENDERING':
      return '压制'
    
    case 'K_TIMELINE':
      return 'K轴'
    
    case 'S_TIMELINE':
      return '歌轴'
    
    case 'EFFECT':
      return '特效'
    
    case 'POLISHING':
      return '润色'
    
    case 'EMBEDDING':
      return '嵌字'
    
    default:
      return '';
  }
}

export function parseTaskTypeInitial(type) {
  switch(type) {
    case 'TIMELINE':
      return '轴'
    
    case 'TRANSLATION':
      return '翻'
    
    case 'CHECK':
      return '校'
    
    case 'SOURCE':
      return '源'
    
    case 'F_CHECK':
      return '终'
    
    case 'RENDERING':
      return '压'

    case 'K_TIMELINE':
      return 'K'
    
    case 'S_TIMELINE':
      return '歌'
    
    case 'EFFECT':
      return '特'
    
    case 'POLISHING':
      return '润'
    
    case 'EMBEDDING':
      return '嵌'
    
    default:
      return '';
  }
}

export function parseTaskStatus(status) {
  switch(status) {
    case 'NOT_ASSIGNED':
      return '#ff6765'

    case 'IN_PROGRESS':
      return '#7fb9d8'
    
    case 'COMPLETED':
      return '#5bc96d'
    
    default:
      return '';
  }
}

export function parseScope(scope) {
  const colonIndex = scope.indexOf(':');
  const startTime = scope.substring(0, colonIndex);
  const endTime = scope.substring(colonIndex + 1);

  return parseTime(startTime) + ' - ' + parseTime(endTime);
}

function parseTime(time) {
  if (/^\d+$/.test(time) === false) {
    return time;
  }

  const intTime = parseInt(time);
  let parsed = (time % 60).toString();
  if (time % 60 < 10) {
    parsed = '0' + parsed;
  }

  if (intTime < 60) {
    parsed = '00:' + parsed;
  } else if (intTime >= 60) {
    parsed = (Math.floor(time / 60)).toString() + ':' + parsed;
    if (Math.floor(time / 60) < 10) {
      parsed = '0' + parsed;
    }
  }

  if (intTime > 3600) {
    parsed = (Math.floor(time / 3600)).toString() + ':' + parsed;
    if (Math.floor(time / 3600) < 10) {
      parsed = '0' + parsed;
    }
  }
  
  return parsed;
}

export function parseWorkflowCode(code, workflow) {
  let taskList = [];
  
  for (let i = 0; i < workflow.length; i++) {
    if ((code >> workflow[i].key) % 2 === 1) {
      taskList.push(workflow[i].taskCode);
    }
  }

  return taskList;
}