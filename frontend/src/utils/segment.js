export function parseTaskType(type) {
  switch(type) {
    case 'TIMELINE':
      return '时轴'
    
    case 'TRANSLATION':
      return '翻译'
    
    case 'CHECK':
      return '校对'
    
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
    
    default:
      return '';
  }
}

export function parseTaskStatus(status) {
  switch(status) {
    case 'NOT_ASSIGNED':
      return '#ff6765'
    
    default:
      return '';
  }
}