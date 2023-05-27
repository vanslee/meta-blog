export function formatTimeStamp(timestamp) {
  const date = new Date(timestamp * 1000)
  const year = date.getFullYear()
  const month = addZero(date.getMonth() + 1)
  const day = addZero(date.getDate())
  const hour = addZero(date.getHours())
  const minute = addZero(date.getMinutes())
  const meridiem = hour >= 12 ? '下午' : '上午'
  const hour12 = hour % 12 === 0 ? 12 : hour % 12
  return `${year}年${month}月${day}日 ${meridiem} ${hour12}:${minute}分`
}
function addZero(num) {
  return num < 10 ? `0${num}` : num
}

export function formatTime(timestamp) {
  const now = new Date() // 当前时间
  const target = new Date(timestamp * 1000) // 目标时间，这里使用一个固定的时间作为示例
  const diff = now - target // 计算当前时间与目标时间的差距，单位为毫秒
  if (diff > 12 * 60 * 60 * 1000) {
    // 如果差距超过 12 小时
    const year = target.getFullYear()
    const month = target.getMonth() + 1
    const day = target.getDate()
    return `${year}年${month}月${day}日`
  } else {
    // 否则显示小时数
    const hours = Math.floor(diff / (60 * 60 * 1000)) // 计算小时数
    return `${hours}小时前`
  }
}
