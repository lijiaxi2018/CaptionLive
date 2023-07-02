export function parseDBTimeYMD(time) {
  return time ? time.substring(0, time.indexOf('T')) : "未知时间";
}