import request from '@/apis/index'
export function getCommentBriefApi(params) {
  return request.post('comment/comment_brief', params)
}
