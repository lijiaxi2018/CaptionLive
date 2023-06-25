export function parseRequestType(type) {
  switch(type) {
    case 0:
      return '通知'
    
    case 1:
      return '加组请求'
    
    case 2:
      return '项目分享请求'
    
    case 3:
      return '项目分享请求'
    
    default:
      return '请求';
  }
}