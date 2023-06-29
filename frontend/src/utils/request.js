export function parseRequestType(type) {
  switch(type) {
    case 'ANNOUNCEMENT':
      return '通知'
    
    case 'ADD_USER_TO_ORGANIZATION':
      return '加组请求'
    
    case 'SHARE_PROJECT_TO_USER':
      return '项目分享请求'
    
    case 'SHARE_PROJECT_TO_ORGANIZATION':
      return '项目分享请求'
    
    default:
      return '请求';
  }
}