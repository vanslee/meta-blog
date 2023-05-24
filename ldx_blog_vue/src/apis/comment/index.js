import request from '@/utils/request'
export function getCommentBriefApi(params) {
  return request.post('comment/comment_brief', params)
}
